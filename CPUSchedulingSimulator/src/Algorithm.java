/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/23/2021
 */

public abstract class Algorithm /*implements Runnable*/ {
	
	public abstract void sortReadyQueue();
	
	public abstract void sortIoWaitQueue();
	
	public abstract void apply();
	
	public abstract void printResult();
	
}
