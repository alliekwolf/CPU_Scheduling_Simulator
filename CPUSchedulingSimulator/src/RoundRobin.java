
/**
 * Extends Scheduler to implement Round Robin scheduling.
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
			this.currentCPUProcess.setState(State.RUNNING);		// Set process state to RUNNING.
			
			// If this is the first burst cycle, set the Process's start time.
			if (this.currentCPUProcess.getBurstCycle() == 0) {
				this.currentCPUProcess.setStartTime(this.systemTimer);
			}
			
			this.updateJobQueue(this.currentCPUProcess);
			this.setEndOfSlice();
			
			String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
					+ this.currentCPUProcess.getId() + " added to the CPU.\n\n\n\n";
			System.out.println(loggerInfo);
			SimulatorClient.log.logger.info(loggerInfo);
		}
	}
	
	@Override
	/**
	 * Overrides the Scheduler class executeCPU() method to handle the quantum time slice
	 * for the Round Robin algorithm.
	 */
	public void executeCPU() {
		this.currentCPUProcess.decrementRemainingBursts();
		
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
					this.currentCPUProcess.setState(State.DONE);				// Set process state to DONE.
					this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set process finish time to system timer.
					this.currentCPUProcess.calculateTurnaroundTime();			// Calculate process's turnaround time.
					
					this.updateJobQueue(this.currentCPUProcess);
					
					this.computeAvgWaitTime();								// Compute the avg. wait time of terminated processes.
					this.computeThroughput();
					this.computeAvgTurnaroundTime();
					
					String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
							+ this.currentCPUProcess.getId() + " terminated.\n\n\n\n";
					System.out.println(loggerInfo);
					SimulatorClient.log.logger.info(loggerInfo);
					
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
