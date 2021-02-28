/**
 * 
 * Extends Algorithm class. This class organizes Process objects into
 * a ready queue based on the priority level assigned to the Process, where the
 * lowest priority int is first in the queue. This Priority object will be a data member
 * of a Scheduler object - the Priority object will organize Process objects in the Scheduler
 * object's readyQueue.
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Priority extends Algorithm implements Comparator<Process> {
	
	// Data Members
	Scheduler scheduler;
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public Priority() {
		
	}
	
	/**
	 * Constructor for Priority object.
	 * 
	 * @param scheduler - a Scheduler object
	 */
	public Priority(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// Methods
	/**
	 * This method sorts the Scheduler object's ready queue according to the 
	 * overridden compare() method, and will organize the scheduler readyQueue by
	 * priority, lowest priority first.
	 */
	@Override
	public void sortReadyQueue() {
		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getReadyQueue();
		Comparator<Process> c = new Priority();
		Collections.sort(q, c);
		this.scheduler.setReadyQueue(q);
	}
	
	/**
	 * This method sorts the readyQueue of the Scheduler according to the overridden
	 * compare module. In this case, it orders the processes in the queue by priority, lowest
	 * value first.
	 */
	@Override
	public void apply() {
		this.sortReadyQueue();
	}
	
	/**
	 * Overridden compare method to make the comparator work. This method
	 * subtracts the priority level of the second process from the first, and 
	 * returns the result as an int. The result will be used to sort the processes
	 * into the readyQueue of the scheduler, in this case by Priority value, lowest value first.
	 * 
	 * @param o1 The first Process object to compare
	 * @param o2 The second Process object to compare
	 * @return difference int, the difference in the value of the priority of o1 and the
	 * 			priority of o2.
	 */
	@Override
	public int compare(Process o1, Process o2) {
		int difference = o1.getPriorityLevel() - o2.getPriorityLevel();
		return difference;
	}
	
}
