
/**
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class RoundRobin extends Scheduler {
	
	// Data Members
	private int endOfSlice;
	
	// Constructor
	public RoundRobin(int quantumTimeSlice) {
		super();
		this.quantumTimeSlice = quantumTimeSlice;
		this.endOfSlice = 0;
	}
	
	// Scheduler Class Methods
	
	@Override
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
			this.currentCPUProcess.isRunning();		// Set process state to RUNNING.
			
			this.setEndOfSlice();
		}
	}
	
	@Override
	public void executeCPU() {
		this.currentCPUProcess.decrementRemainingBursts();
		
		// Output current burst.
		System.out.println("CPU burst: " + (this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()) - this.currentCPUProcess.getRemainingBursts())
							+ " of " + this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()));
		
		this.executeIO();
		
		if (this.currentCPUProcess.getRemainingBursts() == endOfSlice) {
			if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() < currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
				this.currentCPUProcess = null;
			} else if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() == currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.currentCPUProcess.isDone();							// Set process state to NULL.
				this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set finish time to system timer.
				this.terminatedProcesses.add(this.currentCPUProcess);		// Add to terminated processes. 
				
				System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
				this.currentCPUProcess = null;
			} else {
				this.addToReadyQueue(this.currentCPUProcess);
				this.currentCPUProcess = null;
			}
		}
	}
	
	@Override
	public void executeIO() {
		if (this.currentIOProcess != null) {
			this.currentIOProcess.decrementRemainingBursts();			// Decrement current burst in the cycle.
			
			// Output current burst of cycle.
			System.out.println("I/O burst: " + (this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()) - this.currentIOProcess.getRemainingBursts())
								+ " of " + this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()));
			
			if (this.currentIOProcess.getRemainingBursts() == 0) {		// If this was the last burst of the cycle...
				this.currentIOProcess.incrementBurstCycle();		// Set next burst cycle and reset current burst.
				this.currentIOProcess.setRemainingBursts(this.currentIOProcess.getCpuBurstList().get(
						this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);		// Move process to ready queue.
				this.currentIOProcess = null;
			}
		}
	}
	
	@Override
	public void sort() {
		
	}
	
	// Additional Methods
	
	private void setEndOfSlice() {
		if (this.currentCPUProcess.getRemainingBursts() > this.quantumTimeSlice) {
			this.endOfSlice = this.currentCPUProcess.getRemainingBursts() - this.quantumTimeSlice;
		} else {
			this.endOfSlice = 0;
		}
	}
	

}
