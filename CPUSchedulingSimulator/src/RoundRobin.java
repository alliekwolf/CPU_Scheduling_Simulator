
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
		
		// If we have reached the end of this time slice...
		if (this.currentCPUProcess.getRemainingBursts() == endOfSlice) {
			
			// If current process has bursts remaining in this cycle...
			if (this.currentCPUProcess.getRemainingBursts() > 0) {			// If current process has bursts remaining...
				this.addToReadyQueue(this.currentCPUProcess);				// move process to ready queue.
				this.currentCPUProcess = null;
				
				// Else, if current process has NO MORE bursts remaining in this cycle...
			} else if (this.currentCPUProcess.getRemainingBursts() == 0) {
				
				// If current process still has burst cycles left...
				if (this.currentCPUProcess.getBurstCycle() < (currentCPUProcess.getCpuBurstList().size() - 1)) {
					this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
					this.currentCPUProcess = null;
					
					// Else, if process has no more burst cycles remaining...
				} else if (this.currentCPUProcess.getBurstCycle() == (currentCPUProcess.getCpuBurstList().size() - 1)) {
					this.currentCPUProcess.isDone();							// Set process state to NULL.
					this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set process finish time to system timer.
					this.currentCPUProcess.calculateTurnaroundTime();			// Calculate process's turnaround time.
					this.terminatedProcesses.add(this.currentCPUProcess);		// Move to terminated processes.
					this.computeAverageWaitTime();								// Compute the avg. wait time of terminated processes.
					
					System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
					this.currentCPUProcess = null;
				}
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
