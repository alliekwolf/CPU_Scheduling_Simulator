
/**
 * Child class of Scheduler, implements a scheduling 
 * scenario based on the First Come, First Served model. Process
 * That arrive first are put in the CPU until the complete, and then
 * the next arrival is put in the CPU. If a process arrives while another
 * process is running, it is put in the readyQueue. When a process has an 
 * IO burst, it is put in the ioWaitQue, and the next process in the
 * readyQueue becomes the current CPU process. When IO opens up, the 
 * head of the ioWaitQue becomes the current IO process until it finishes, at
 * which point it goes back in the readyQueue for the CPU.
 * 
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class FCFS extends Scheduler {

	// Constructors
	/**
	 * 
	 * Default constructor
	 */
	public FCFS() {
		super();
	}
	
	// Scheduler Class Methods
	
	@Override
	/**
	 * Execute the CPU according to the First Come, First Served scenario.
	 * - decrement the number of bursts left in the CPU burst length.
	 * - print the number of CPU bursts left in this cycle for this process.
	 * - apply the executeIO method.
	 * - If the current burst is 0, send the process to the IO waiting queue.
	 */
	public void executeCPU() {
		this.currentCPUProcess.decrementRemainingBursts();
		
		// Output current burst of cycle.
		System.out.println("CPU burst: " + (this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()) - this.currentCPUProcess.getRemainingBursts())
							+ " of " + this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()));
		
		this.executeIO();
		
		// If current burst reaches 0 and there are more burst cycles to go, move Process to I/O wait queue.
		// Else, if current burst reaches 0 and there are NO MORE burst cycles to go, Process is finished.
		if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() < currentCPUProcess.getCpuBurstList().size() - 1)) {
			this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
			this.currentCPUProcess = null;
		} else if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() == currentCPUProcess.getCpuBurstList().size() - 1)) {
			this.currentCPUProcess.isDone();								// Set process state to NULL.
			this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set finish time to system timer.
			this.terminatedProcesses.add(this.currentCPUProcess);		// Add to terminated processes. 
			
			System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
			this.currentCPUProcess = null;
		}
	}
	
	@Override
	/**
	 * If there is a current IO Process, decrement the remaining time in it's current burst.
	 * - print the number of IO bursts remaining.
	 * - If that was the last of the io bursts in this io burst cycle...
	 * - increment the index of ioburstcycles
	 * - update the values of cpuburstcycles and ioburstcycles
	 * - add this process to the ready queue
	 * - remove this process from the IO queue
	 */
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
	
	/**
	 * No need to sort these, as the FCFS model is sorted by arrival time.
	 */
	@Override
	public void sort() {
		
	}
	

}
