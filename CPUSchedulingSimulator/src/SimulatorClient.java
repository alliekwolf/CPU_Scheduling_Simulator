/**
 * 
 * The SimulatorClient program is currently being used to test all of the classes in the 
 * CPUSchedulingSimulator Project. It is currently a console-based program, with plans that 
 * it will be converted to a GUI-based program once all classes and methods have been tested. 
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

import java.io.IOException;

public class SimulatorClient {

	public static void main(String[] args) throws InterruptedException {
		
		// variables
		ScenarioReader sr = new ScenarioReader("scenarioData.txt");
		Scheduler scheduler = new Scheduler();
		
		try {
			scheduler.setJobQueue(sr.createProcesses());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		scheduler.setAlgorithm(new Priority(scheduler));
//		testQueueSorting(scheduler);
		
		scheduler.setAlgorithm(new Priority(scheduler));	// So far, both Priority and SJF work.
		runScheduler(scheduler);							// Change setAlgorithm() to test.
		
	}
	
	/**
	 * This method runs the simulator. By calling this method, processes are added to the 
	 * Scheduler's queues, methods are called to execute CPU and I/O bursts, and the states of 
	 * the queues and processes are output in the console. At the end of each loop in the while 
	 * loop, the Scheduler's system timer is incremented and the thread is paused for a duration.
	 * If at the end of the loop all queues, the CPU, and the I/O are clear, then all jobs are 
	 * finished and the simulator terminates.
	 * 
	 * @param scheduler
	 * @throws InterruptedException
	 */
	public static void runScheduler(Scheduler scheduler) throws InterruptedException {
		scheduler.addNewProcess();
		boolean flag = false;
		while (flag == false) {
			System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
			scheduler.addNewProcess();
			scheduler.addToCPU();
			scheduler.addToIO();
			
			// Output which processes are in which queues, the CPU, and I/O.
			printSchedulerQueues(scheduler);
			
			// Execute next burst (the executeCPUBurst() method also calls executeIOBurst().)
			// Then, increment the system timer.
			scheduler.executeCPUBurst();
			scheduler.incrementSystemTimer();
			
			// If all queues, the CPU, and I/O are empty, terminate execution.
			if (scheduler.getJobQueue().isEmpty() &&
					scheduler.getReadyQueue().isEmpty() &&
					scheduler.getIoWaitQueue().isEmpty() &&
					scheduler.getCurrentCPUProcess() == null &&
					scheduler.getCurrentIOProcess() == null) {
				flag = true;
			}
			
			Thread.sleep(500);
		}
		
		System.out.println("\n** ALL JOBS FINISHED. **");
	}
	
	/**
	 * Outputs print statements in the console to show which processes are in the 
	 * Scheduler's ready and I/O wait queues, which process is in the CPU, and 
	 * which process is in I/O.
	 * 
	 * @param scheduler
	 */
	public static void printSchedulerQueues(Scheduler scheduler) {
		// Output data to show which processes are in which queues.
		String rqStr = "Ready Queue: ";
		for (Process p: scheduler.getReadyQueue()) {
			rqStr += " [" + p.getId() + "]";
		}
		System.out.println(rqStr);
		String wqStr = "I/O Wait Queue: ";
		for (Process p: scheduler.getIoWaitQueue()) {
			wqStr += " [" + p.getId() + "]";
		}
		// Output data to show which processes are in the CPU and I/O.
		System.out.println(wqStr);
		if (scheduler.getCurrentCPUProcess() != null) {
			System.out.print("CPU: [" + scheduler.getCurrentCPUProcess().getId() + "]");
		} else {
			System.out.print("CPU: [     ]");
		}
		if (scheduler.getCurrentIOProcess() != null) {
			System.out.print("   I/O: [" + scheduler.getCurrentIOProcess().getId() + "]\n");
		} else {
			System.out.print("   I/O: [     ]\n");
		}
	}
	
	
	/**
	 * This method is for testing the different sorting of the ready queue and I/O wait queue in the
	 * Scheduler to make sure the different algorithms (SJF, Priority, etc.) are working properly.
	 * 
	 * @param scheduler
	 * @param processList
	 */
	public static void testQueueSorting(Scheduler scheduler) {
		for (Process p: scheduler.getJobQueue()) {
			scheduler.addToReadyQueue(p);
			scheduler.addToIoWaitQueue(p);
		}
		
		System.out.println();
		for (Process p: scheduler.getReadyQueue()) {
			System.out.println(p + "\n");
		}
		System.out.println();
		
		String result = "";
		
		// Set Scheduler's algorithm to Priortiy, run it, then print output.
		scheduler.setAlgorithm(new Priority(scheduler));
		scheduler.applyAlgorithm();
		
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
			p.incrementBurstCycle();				  // Comment out this "for loop" to compare first 
		}											  // cycle of bursts.
		scheduler.applyAlgorithm();
		
		System.out.println("   After SJF Sorting (CPU)   ");
		System.out.println("-----------------------------");
		for (Process p: scheduler.getReadyQueue()) {
			result += " - Process: " + p.getId() + "\n   CPU Bursts: " + p.getCpuBurstList() + "\n";
		}
		System.out.println(result + "\n");
		result = "";
	}

}
