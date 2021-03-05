
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 
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
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class Priority extends Scheduler implements Comparator<Process> {
	
	// Constructor
	/**
	 * Default constructor. 
	 * Inherits all data members from the Scheduler abstract class.
	 */
	public Priority() {
		super();
	}
	
	
	// Scheduler Class Methods
	@Override
	public void sort() {
		LinkedList<Process> q = (LinkedList<Process>)this.getReadyQueue();
		Comparator<Process> c = new Priority();
		Collections.sort(q, c);
		this.setReadyQueue(q);
	}
	
	
	// Comparator Class Method
	@Override
	public int compare(Process o1, Process o2) {
		int difference = o1.getPriorityLevel() - o2.getPriorityLevel();
		return difference;
	}
	

}
