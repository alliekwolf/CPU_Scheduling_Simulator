
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 
 * Extends Scheduler to implement Priority scheduling. 
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
