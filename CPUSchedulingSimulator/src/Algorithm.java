/**
 * 
 * Algorithm is a superclass to be used as a parent for a group of classes that model
 * the behavior of a cpu process scheduler. This class defines some common methods that will
 * be used by FCFS, Priority, SJF, and Round-Robin classes that will implement scheduling models.
 * The classes will be used in the Scheduler class to organize the scheduler.readyQueue.
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

public abstract class Algorithm {
	
	/**
	 * Sorts the incoming processes into a ready queue according to the 
	 * scheduling models rules - by priority for the Priority algorithm, for example
	 */
	public abstract void sortReadyQueue();
	
	/**
	 * Starts the sorting process for the algorithm.
	 */
	public abstract void apply();
	
	/**
	 * Not implemented yet
	 */
	public abstract void printResult();
	
}
