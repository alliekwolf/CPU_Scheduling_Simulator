

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
			result += " - Process: " + p.getId() + "\n   Priority: " + p.getPriorityLevel() + "\n   CPU Bursts: " + p.getCpuBurstList() + "\n";
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
		for (Process p: scheduler.getReadyQueue()) {  // Increments the CPU burst cycle by 2, so 
			p.incrementBurstCycle();				  // it will compare the third cycle of bursts.
			p.incrementBurstCycle();				  // Comment out for loop to compare first cycle
		}											  // of bursts.
		scheduler.start();
		
		System.out.println("   After SJF Sorting (CPU)   ");
		System.out.println("-----------------------------");
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   CPU Bursts: " + p.getCpuBurstList() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
		
	}

}
