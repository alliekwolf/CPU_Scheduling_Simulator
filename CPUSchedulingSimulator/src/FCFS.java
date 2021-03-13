
/**
 * 
 * Extends Scheduler to implement First Come, First Served scheduling.
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
