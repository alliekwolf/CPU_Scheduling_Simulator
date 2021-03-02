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
		ScenarioReader sr = new ScenarioReader("scenarioData2.txt");
		Scheduler scheduler;
		
		scheduler = new SJF();
		try {
			scheduler.setJobQueue(sr.createProcesses());
		} catch (IOException e) {
			e.printStackTrace();
		}
		runScheduler(scheduler);
		
		
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
	private static void runScheduler(Scheduler scheduler) throws InterruptedException {
		scheduler.addNewProcess();
		boolean flag = false;
		while (flag == false) {
			System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
			scheduler.addNewProcess();
			scheduler.addToCPU();
			scheduler.addToIO();
			
			// Output which processes are in which queues, the CPU, and I/O.
			printSchedulerOutput(scheduler);
			
			// Execute next burst (the executeCPUBurst() method also calls executeIOBurst().)
			// Then, increment the system timer.
			scheduler.executeCPU();
			scheduler.incrementSystemTimer();
			
			// If all queues, the CPU, and I/O are empty, terminate execution.
			if (scheduler.getJobQueue().isEmpty() &&
				scheduler.getReadyQueue().isEmpty() &&
				scheduler.getIoWaitQueue().isEmpty() &&
				scheduler.getCurrentCPUProcess() == null &&
				scheduler.getCurrentIOProcess() == null) {
				flag = true;
			}
			
			Thread.sleep(200);
		}
		
		System.out.println("\n** ALL JOBS FINISHED. **");
		String result = "";
		for (Process p: scheduler.getTerminatedProcesses()) {
			result += " - " + p.getId() + " Finish Time: " + p.getFinishTime() + "\n";
		}
		System.out.println(result);
	}

	
	/**
	 * Outputs print statements in the console to show which processes are in the 
	 * Scheduler's ready and I/O wait queues, which process is in the CPU, and 
	 * which process is in I/O.
	 * 
	 * @param scheduler
	 */
	public static void printSchedulerOutput(Scheduler scheduler) {
		// Output data to show which processes are in which queues.
		String rqStr = "Ready Queue: ";
		if (scheduler.getReadyQueue().isEmpty()) {
			rqStr += " EMPTY";
		} else {
			for (Process p: scheduler.getReadyQueue()) {
				rqStr += " [" + p.getId() + "]";
			}
		}
		System.out.println(rqStr);
		String wqStr = "I/O Wait Queue: ";
		if (scheduler.getIoWaitQueue().isEmpty()) {
			wqStr += " EMPTY";
		} else {
			for (Process p: scheduler.getIoWaitQueue()) {
				wqStr += " [" + p.getId() + "]";
			}
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
	

}
