/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/23/2021
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Priority extends Algorithm implements /*Runnable,*/ Comparator<Process> {
	
	// Data Members
	Scheduler scheduler;
	
	// Constructors
	public Priority() {
		// default constructor
	}
	
	public Priority(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	// Methods
	@Override
	public void sortReadyQueue() {
		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getReadyQueue();
		Comparator<Process> c = new Priority();
		Collections.sort(q, c);
		this.scheduler.setReadyQueue(q);
	}
	
	@Override
	public void sortIoWaitQueue() {
//		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getIoWaitQueue();
//		Comparator<Process> c = new Priority();
//		Collections.sort(q, c);
//		this.scheduler.setIoWaitQueue(q);
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
		int difference = o1.getPriorityLevel() - o2.getPriorityLevel();
		return difference;
	}
	
}
