
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
	/**
	 * No need to sort these, as the FCFS model is sorted by arrival time.
	 */
	@Override
	public void sort() {
		// Nothing to do.
	}
	

}
