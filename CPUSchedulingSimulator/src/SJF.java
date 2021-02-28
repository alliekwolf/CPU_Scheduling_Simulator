/**
 * 
 * Extends Algorithm class. This class organizes Process objects into
 * a ready queue based on which Process has the shortest job time.
 * This SJF object will be a data member of a Scheduler object - 
 * the SJF object will organize Process objects in the Scheduler
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

public class SJF extends Algorithm implements Comparator<Process> {
	
	// Data Members
	Scheduler scheduler;
	
	// Constructors
	/**
	 * Default constructor
	 */
	public SJF() {
		
	}
	
	/**
	 * Constructor for SJF object.
	 * 
	 * @param scheduler - a Scheduler object
	 */
	public SJF(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// Methods
	/**
	 * This method sorts the Scheduler object's ready queue according to the 
	 * overridden compare() method, and will organize the scheduler readyQueue by
	 * which Process has the shortest job time, lowest time first.
	 */
	@Override
	public void sortReadyQueue() {
		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getReadyQueue();
		Comparator<Process> c = new SJF();
		Collections.sort(q, c);
		this.scheduler.setReadyQueue(q);
	}
	
	/**
	 * This method sorts the readyQueue of the Scheduler according to the overridden
	 * compare module. In this case, it orders the processes in the queue by shortest job time,
	 * lowest value first.
	 */
	@Override
	public void apply() {
		this.sortReadyQueue();
	}
	
	/**
	 * not yet implemented.
	 */
	@Override
	public void printResult() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method is implemented from the Comparator class to compare two Processes, and is used 
	 * to sort the Scheduler's queue of Processes. In this instance, the comparator will compare 
	 * the number of bursts in the next CPU burst cycle of each Process.
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
