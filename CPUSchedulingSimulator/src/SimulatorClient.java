/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/21/2021
 */

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class SimulatorClient {

	public static void main(String[] args) {
		
		/*
		 * This program is being used to test CPUSchedulingSimulator classes.
		 * Most code will be deleted for final version of project.
		 */
		
		// variables
		ScenarioReader sr = new ScenarioReader("scenarioData.txt");
		Queue<Process> processList = new LinkedList<Process>();
		Scheduler scheduler = new Scheduler();
		
		try {
			processList = sr.createProcesses();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		scheduler.setReadyQueue(processList);
		scheduler.setIoWaitQueue(processList);
		for (Process p: scheduler.getReadyQueue()) {
			System.out.println(p + "\n");
		}
		System.out.println();
		
		// Output Ready Queue before algorithm has been chosen.
		System.out.println("   Before Sorting   ");
		System.out.println("--------------------");
		String result = "";
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   Priority: " + p.getPriorityLevel() + "\n   CPU Bursts: " + p.getCpuBurstList() + "\n   I/O Bursts" + p.getIoBurstList() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
		
		
		// Set Scheduler's algorithm to Priortiy, run it, then print output.
		scheduler.setAlgorithm(new Priority(scheduler));
		scheduler.start();
		
		System.out.println("   After Priority Sorting   ");
		System.out.println("----------------------------");
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   Priority: " + p.getPriorityLevel() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
		
		
		// Set Scheduler's algorithm to SJF, run it, then print output.
		scheduler.setAlgorithm(new SJF(scheduler));
		scheduler.start();
		
		System.out.println("   After SJF Sorting (CPU)   ");
		System.out.println("-----------------------------");
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   CPU Bursts: " + p.getCpuBurstList() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
		
		
		// Set Scheduler's algorithm to SJF for I/O Wait Queue, run it, then print output.
		// First, set burstStep to 1.
		for (Process p: scheduler.getIoWaitQueue()) {
			p.setBurstStep(1);
		}
		scheduler.setAlgorithm(new SJF(scheduler));
		scheduler.start();
		
		System.out.println("   After SJF Sorting (I/O)   ");
		System.out.println("-----------------------------");
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   I/O Bursts: " + p.getIoBurstList() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
		
	}

}
