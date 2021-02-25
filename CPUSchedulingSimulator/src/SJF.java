/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/23/2021
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 
 * Extends Algorithm class. This class organizes Process objects into
 * a ready queue based on which Process has the shortest job time.
 * This SJF object will be a data member of a Scheduler object - 
 * the SJF object will organize Process objects in the Scheduler
 * object's readyQueue.
 * 
 * @author Brian Steele, Cole Walsh, Allie Worlf
 *
 */
public class SJF extends Algorithm implements /*Runnable,*/ Comparator<Process> {
	
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
	 * This method will sort the scheduler's IoWaitQueue according to the 
	 * overridden compare() method, and will organize the schedules IoWaitQueue
	 * by which job has the shortest run time, shortest time first.
	 */
	@Override
	public void sortIoWaitQueue() {
		LinkedList<Process> q = (LinkedList<Process>)this.scheduler.getIoWaitQueue();
		Comparator<Process> c = new SJF();
		Collections.sort(q, c);
		this.scheduler.setIoWaitQueue(q);
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
	
	// TODO - need some clarification on this comparison
	@Override
	public int compare(Process o1, Process o2) {
		int difference = -1;
		int step1 = (o1.getBurstStep() / 2);
		int step2 = (o2.getBurstStep() / 2);
		// If the Processes' burstStep is even, then they are CPU bursts; check the Ready Queue.
		if ((o1.getBurstStep() % 2 == 0) && (o2.getBurstStep() % 2 == 0)) {
			if((step1 < o1.getCpuBurstList().size()) && (step2 < o2.getCpuBurstList().size())) {
				difference = o1.getCpuBurstList().get(step1) - o2.getCpuBurstList().get(step2);
				return difference;
			} else if (step1 >= o1.getCpuBurstList().size()) {
				difference = 0;
			} else if (step2 >= o2.getCpuBurstList().size()) {
				difference = -1;
			}
		// If the Processes' burstStep is odd, then they are I/O bursts; check the I/O Wait Queue.
		} else if ((o1.getBurstStep() % 2 == 1) && (o2.getBurstStep() % 2 == 1)) {
			if((step1 < o1.getIoBurstList().size()) && (step2 < o2.getIoBurstList().size())) {
				difference = o1.getIoBurstList().get(step1) - o2.getIoBurstList().get(step2);
				return difference;
			} else if (step1 >= o1.getCpuBurstList().size()) {
				difference = 0;
			} else if (step2 >= o2.getCpuBurstList().size()) {
				difference = -1;
			}
		}
		return difference;
		
	}
	
}
