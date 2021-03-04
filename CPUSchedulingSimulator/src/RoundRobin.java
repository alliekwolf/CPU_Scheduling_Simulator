
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
	/**
	 * Inherits all data members from the Scheduler abstract class, and initializes 
	 * its own data member, endOfSlice, to 0 for handling the quantum time slice 
	 * necessary for the Round Robin scheduling algorithm.
	 * 
	 * @param quantumTimeSlice - int the length of the quantum time slice.
	 */
	public RoundRobin(int quantumTimeSlice) {
		super();
		this.quantumTimeSlice = quantumTimeSlice;
		this.endOfSlice = 0;
	}
	
	
	// Scheduler Class Methods
	@Override
	/**
	 * Overrides the Scheduler class addToCPU() method to handle the quantum time slice
	 * for the Round Robin algorithm.
	 */
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
			this.currentCPUProcess.isRunning();		// Set process state to RUNNING.
			
			this.setEndOfSlice();
		}
	}
	
	@Override
	/**
	 * Overrides the Scheduler class executeCPU() method to handle the quantum time slice
	 * for the Round Robin algorithm.
	 */
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
	/**
	 * No need to sort these, as this Round Robin model is non-preemptive, so process are
	 * sorted by arrival time.
	 */
	public void sort() {
		// Nothing to do.
	}
	
	
	// Additional Methods
	/**
	 * Sets the endOfSlice data member necessary for handling the quantum time slice.
	 */
	private void setEndOfSlice() {
		if (this.currentCPUProcess.getRemainingBursts() > this.quantumTimeSlice) {
			this.endOfSlice = this.currentCPUProcess.getRemainingBursts() - this.quantumTimeSlice;
		} else {
			this.endOfSlice = 0;
		}
	}
	

}
