
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;

import javax.swing.JFileChooser;

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
public class SimulatorClient {
	
	public static Scanner console = new Scanner(System.in);
	public static Scheduler scheduler;
	private static Log log;
	
	public static void main(String[] args) throws InterruptedException, SecurityException, IOException {
		log = new Log("schedulerLog.txt");
		log.logger.setLevel(Level.FINE);
		
		System.out.println("Select a scenario file to run.");
		
		// open a scenario file to run.
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		int result = fileChooser.showOpenDialog(null);
		File selectedFile = fileChooser.getSelectedFile();
		
		// Create processes from selected file.
		ScenarioReader sr = new ScenarioReader(selectedFile);
		
		// Print simulator menu, where the user will choose a scheduling algorithm, from
		// which the Scheduler object will be initiated.
		mainMenu(scheduler);
		
		// Load initial job queue with processes created from a text file.
		try {
			scheduler.setJobQueue(sr.createProcesses());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// Run scheduling simulator.
		runScheduler(scheduler);
		
	}
	
	// Client Program Methods
	
	/**
	 * Assigns a new Scheduler object to the scheduler variable.
	 * This will be based on the choices in the mainMenu - the various
	 * schedule types extend the original Scheduler abstract class.
	 * @param newScheduler Schedule object
	 */
	public static void createScheduler(Scheduler newScheduler) {
		scheduler = newScheduler;
	}
	
	/**
	 * Creates a menu to select which scheduler pattern will be used
	 * during the current run of the program.
	 * @param scheduler Scheduler object 
	 */
	public static void mainMenu(Scheduler scheduler) {
		int userInput = -1;
		System.out.println("\n-- CPU Scheduling Simulator --\n");
		System.out.println("Choose a scheduling algorithm:\n\n" 
							+ " 1 -- First Come, First Serve\n" 
							+ " 2 -- Shortest Job First\n" 
							+ " 3 -- Priority\n" 
							+ " 4 -- Round Robin\n");
		// Handle input exceptions.
		while (userInput < 1 || userInput > 4) {
			System.out.print("Selection: ");
			try {
				userInput = console.nextInt();
				if (userInput < 1 || userInput > 4) {
					throw new Exception();
				}
			} catch (InputMismatchException e) {
				console.nextLine();
			} catch (Exception e) {
				console.nextLine();
			}
			
			// Create a set the Scheduler object to a specific Scheduler abstract class
			// based on the user's selected algorithm.
			switch (userInput) {
				case 1: createScheduler(new FCFS());
					break;
				case 2: createScheduler(new SJF());
					break;
				case 3: createScheduler(new Priority());
					break;
				case 4:
					int quantum = -1;
					// Handle input exceptions for setting the quantum time slice.
					while (quantum <= 0) {
						System.out.print("Enter quantum time slice: ");
						try {
							quantum = console.nextInt();
							if (quantum < 1 || quantum > 4) {
								throw new Exception();
							}
						} catch (InputMismatchException e) {
							console.nextLine();
						} catch (Exception e) {
							console.nextLine();
						}
						if (quantum <= 0) {
							System.out.println("  * Quantum time slice must be an integer greater than 0. *");
						}
					}
					createScheduler(new RoundRobin(quantum));
					break;
				default:
					System.out.println("  * Choose a number 1-4. *");
			}
		}
		
		
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
		
		// Print out the name of selected Scheduler.
		String sName = scheduler.getClass().getName();
		switch (scheduler.getClass().getName()) {
			case "FCFS": sName = "First Come, First Serve";
				break;
			case "SJF": sName = "Shortest Job First";
				break;
			case "Priority": sName = "Priority";
				break;
			case "RoundRobin": sName = "Round Robin";
		}
		System.out.println("\n-- " + sName + " Simulator --");
		
		// The while loop that runs the simulator, incrementing the system timer and 
		// executing bursts as it runs.
		boolean flag = false;		// A flag for continuing and exiting the while loop.
		System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
		while (flag == false) {
			scheduler.addNewProcess();
			scheduler.addToCPU();
			scheduler.addToIO();
			
			logTimeSlice();
			
			// Output which processes are in which queues, the CPU, and I/O.
			printSchedulerOutput(scheduler);
			
			scheduler.executeBursts();				// Execute next cycle of bursts (both CPU and I/O, if applicable).
			
			// Check if all processes are DONE. If not, exit while loop.
			int numDone = 0;
			for(Process p: scheduler.jobQueue) {
				if (!p.isDone()) {
					break;					// If process is not DONE, exit for loop. Else...
				} else {
					numDone++;				// Increment number of DONE processes.
					
					// If number of DONE processes is equal to total number of processes...
					if (numDone == scheduler.jobQueue.size()) {
						flag = true;		// Set flag to true to exit while loop.
					}
				}
			}
			
			
			
			// If flag has not been set to true, increment system timer and continue while loop.
			if (!flag) {
				scheduler.incrementSystemTimer();		// Increment the system timer.
				Thread.sleep(50);
				System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
			}
			
		}
		
		// Output results of simulation.
		System.out.println("\n** ALL JOBS FINISHED. **");
		System.out.println("\n Simulator End Time: " + scheduler.getSystemTimer() + "\n");
		String result = "SIMULATION METRICS: \n";
		for (Process p: scheduler.getTerminatedProcesses()) {
			result += " - " + p.getId() + "\n"
						+ "   State: " + p.getState() + "\n"
						+ "   Finish Time: " + p.getFinishTime() + "\n"
						+ "   Turnaround Time: " + p.getTurnaroundTime() + "\n"
						+ "   Wait Time: " + p.getWaitTime() + "\n"
						+ "   I/O Wait Time: " + p.getIoWaitTime() + "\n\n";
		}
		
		result += "Average Wait Time: " + scheduler.getAvgWaitTime() + "\n"
					+ "CPU Utilization: " + (100 * scheduler.getCpuUtilization()) + " %\n"
					+ "Throughput: " + scheduler.getThroughput() + "\n"
					+ "Average Turnaround Time: " + scheduler.getAvgTurnaroundTime();
		
		System.out.println(result);
		log.logger.info(result);
		System.out.println("\n** CHECK PROJECT FOLDER FOR 'schedulerLog.txt' **");
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
			System.out.print("\n\nCPU: [" + scheduler.getCurrentCPUProcess().getId() + "]");
		} else {
			System.out.print("CPU: [     ]");
		}
		if (scheduler.getCurrentIOProcess() != null) {
			System.out.print("   I/O: [" + scheduler.getCurrentIOProcess().getId() + "]\n");
		} else {
			System.out.print("   I/O: [     ]\n");
		}
	}
	
	public static void logTimeSlice() {
		String loggerInfo = "";
		loggerInfo += "System Time: " + scheduler.getSystemTimer() + " -------------\n\n\n";
		
		if (scheduler.currentCPUProcess != null) {
			loggerInfo += "Current CPU Process: [ " + scheduler.getCurrentCPUProcess().getId() + " ]\n";
			loggerInfo += "CPU burst: " + ((scheduler.getCurrentCPUProcess().getCpuBurstList().get(scheduler.getCurrentCPUProcess().getBurstCycle()) + 1) - scheduler.getCurrentCPUProcess().getRemainingBursts())
					+ " of " + scheduler.getCurrentCPUProcess().getCpuBurstList().get(scheduler.getCurrentCPUProcess().getBurstCycle()) + "\n\n";
		} else {
			loggerInfo += "Current CPU Process: [ EMPTY ]\n\n";
		}
		if (scheduler.currentIOProcess != null) {
			loggerInfo += "Current IO Process: [ " + scheduler.getCurrentIOProcess().getId() + " ]\n";
			loggerInfo += "I/O burst: " + ((scheduler.getCurrentIOProcess().getIoBurstList().get(scheduler.getCurrentIOProcess().getBurstCycle()) + 1) - scheduler.getCurrentIOProcess().getRemainingBursts())
					+ " of " + scheduler.getCurrentIOProcess().getIoBurstList().get(scheduler.getCurrentIOProcess().getBurstCycle()) + "\n\n";
		} else {
			loggerInfo += "Current IO Process: [ EMPTY ]\n";
		}
		loggerInfo += "\nREADY QUEUE: \n";
		
		if (scheduler.getReadyQueue().size() > 0) {
			loggerInfo += String.format("%-17s %-10s %-15s %-15s %s\n",
								"Name:", "State:", "Arrival:", "CPU Bursts:", "Io Bursts:");
		} else {
			loggerInfo += "EMPTY\n\n";
		}
		for (Process p : scheduler.getReadyQueue()) {
			loggerInfo += String.format("%-15s  %-15s %-11s %-15s %s\n",
								p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}

		loggerInfo += "\nIO QUEUE: \n";
		if (scheduler.getIoWaitQueue().size() > 0) {
			loggerInfo += String.format("%-17s %-10s %-15s %-15s %s\n",
					"Name:", "State:", "Arrival:", "CPU Bursts:", "Io Bursts:");
		} else {
			loggerInfo += "EMPTY\n\n";
		}
		for (Process p : scheduler.getIoWaitQueue()) {
			loggerInfo += String.format("%-15s  %-15s %-11s %-15s %s\n",
					p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}
		
		loggerInfo += "\nPROCESSES: \n";
		if (scheduler.getJobQueue().size() > 0) {
			
			loggerInfo += String.format("%-17s %-10s %-15s %-15s %s\n",
					"Name:", "State:", "Arrival:", "CPU Bursts:", "Io Bursts:");
		} else {
			loggerInfo += "EMPTY\n\n";
		}
		for (Process p : scheduler.getJobQueue()) {	
			loggerInfo += String.format("%-15s  %-15s %-11s %-15s %s\n",
					p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}
		
		loggerInfo += "\n\n";
		log.logger.info(loggerInfo);
	}
	

}
