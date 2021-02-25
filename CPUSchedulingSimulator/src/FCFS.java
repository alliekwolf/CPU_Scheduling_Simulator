/**
 * 
 * Extends Algorithm class. This class organizes Process objects into
 * a ready queue based on which Process has the arrives at the CPU first.
 * This FCFS object will be a data member of a Scheduler object - 
 * the FCFS object will organize Process objects in the Scheduler
 * object's readyQueue.
 * 
 * @author Brian Steele, Cole Walsh, Allie Wolf
 *
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends Algorithm implements Comparator<Process> {
	
	// Data Members
	Scheduler scheduler;
	
	// Constructors
	/**
	 * Default constructor
	 */
	public FCFS() {
		// default constructor
	}
	
	/**
	 * Constructor for FCFS object.
	 * 
	 * @param scheduler - a Scheduler object
	 */
	public FCFS(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// Methods
	/**
	 * This method sorts the Scheduler object's ready queue according to the 
	 * overridden compare() method, and will organize the scheduler readyQueue by
	 * which Process arrived at the CPU earliest.
	 */
	@Override
	public void sortReadyQueue() {
		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getReadyQueue();
		Comparator<Process> c = new FCFS();
		Collections.sort(q, c);
		this.scheduler.setReadyQueue(q);
		
	}
	
	/**
	 * This method sorts the readyQueue of the Scheduler according to the overridden
	 * compare module. In this case, it orders the processes in the queue by which processes
	 * have the earliest arrival time.
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
	 * Overridden compare() method to make the comparator work. This method
	 * subtracts the arrival time of the second process from the arrival time of the
	 * first, and returns the result as an int. The result will be used to sort the processes
	 * into the readyQueue of the scheduler, in this case by arrival time, earliest first.
	 * 
	 * @param o1 The first Process object to compare
	 * @param o2 The second Process object to compare
	 * @return difference int, the difference in the value of the arrival time of o1 and the
	 * 			arrival time of o2.
	 */
	@Override
	public int compare(Process o1, Process o2) {
		int difference = o1.getArrivalTime() - o2.getArrivalTime();
		return difference;
	}
	
	
}
