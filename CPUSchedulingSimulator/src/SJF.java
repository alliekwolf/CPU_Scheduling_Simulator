
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Extends the Scheduler to implement Shortest Job First scheduling.
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class SJF extends Scheduler implements Comparator<Process> {
	
	// Constructor
	/**
	 * Default constructor. 
	 * Inherits all data members from the Scheduler abstract class.
	 */
	public SJF() {
		super();
	}
	
	
	// Scheduler Class Methods
	/**
	 * Sort the list of processes
	 */
	@Override
	public void sort() {
		LinkedList<Process> q = (LinkedList<Process>)this.getReadyQueue();
		Comparator<Process> c = new SJF();
		Collections.sort(q, c);
		this.setReadyQueue(q);
	}
	
	// Comparator Class Method
	/**
	 * Compare one process to another to determine the sort order.
	 */
	@Override
	public int compare(Process o1, Process o2) {
		int difference = -1;
		int cycle1 = o1.getBurstCycle();
		int cycle2 = o2.getBurstCycle();
		
		if((cycle1 < o1.getCpuBurstList().size()) && (cycle2 < o2.getCpuBurstList().size())) {
			difference = o1.getCpuBurstList().get(cycle1) - o2.getCpuBurstList().get(cycle2);
			return difference;
		} else if (cycle1 >= o1.getCpuBurstList().size()) {
			difference = 0;
		} else if (cycle2 >= o2.getCpuBurstList().size()) {
			difference = -1;
		}
		return difference;
	}
	

}
