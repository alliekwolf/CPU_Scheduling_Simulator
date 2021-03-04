
/**
 * 
 * Child class of Scheduler, implements the First Come, First Served scheduling algorithm. 
 * The process that arrives first is put in the CPU and executes until the burst cycle 
 * completion, and the the next arrival is put in the CPU. If a process arrives while another
 * process is running, it is put in the readyQueue. When a process has an I/O burst, it is put 
 * in the ioWaitQue, and the next process in the readyQueue becomes the current CPU process. 
 * If there is no process running I/O, the head of the ioWaitQue becomes the current IO process 
 * until I/O completion, at which point it goes back in the readyQueue and waits for the CPU.
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class FCFS extends Scheduler {

	// Constructors
	/**
	 * Default constructor, 
	 * Inherits all data members from the Scheduler abstract class.
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
			this.currentCPUProcess.isDone();							// Set process state to NULL.
			this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set finish time to system timer.
			this.terminatedProcesses.add(this.currentCPUProcess);		// Add to terminated processes. 
			
			System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
			this.currentCPUProcess = null;
		}
	}
	
	/**
	 * No need to sort these, as the FCFS model is sorted by arrival time.
	 */
	@Override
	public void sort() {
		// Nothing to do.
	}
	

}
