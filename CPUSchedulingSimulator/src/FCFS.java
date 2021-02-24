/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/23/2021
 */

import java.util.Comparator;
import java.util.Queue;

public class FCFS extends Algorithm implements /*Runnable,*/ Comparator<Process> {
	
	// Data Members
	Scheduler scheduler;
	
	// Constructors
	public FCFS() {
		// default constructor
	}
	
	public FCFS(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// Methods
	@Override
	public void sortReadyQueue() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sortIoWaitQueue() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void apply() {
		this.sortReadyQueue();
	}
	
	@Override
	public void printResult() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int compare(Process o1, Process o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
