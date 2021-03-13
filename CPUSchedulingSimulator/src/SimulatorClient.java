
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
	public static Log log;
	
	/**
	 * Main method for the project
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws SecurityException
	 * @throws IOException
	 */
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
		mainMenu();
		
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
	public static void mainMenu() {
		int userInput = -1;
		System.out.println("\n-- CPU Scheduling Simulator --\n");
		System.out.println(" Choose a scheduling algorithm: \n\n" 
							+ "   1 -- First Come, First Serve\n" 
							+ "   2 -- Shortest Job First\n" 
							+ "   3 -- Priority\n" 
							+ "   4 -- Round Robin\n");
		// Handle input exceptions.
		while (userInput < 1 || userInput > 4) {
			System.out.print("   Selection: ");
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
						System.out.print("   Enter quantum time slice: ");
						try {
							quantum = console.nextInt();
							if (quantum < 1) {
								throw new Exception();
							}
						} catch (InputMismatchException e) {
							System.out.println("   * Quantum time slice must be an integer greater than 0. *");
							console.nextLine();
						} catch (Exception e) {
							System.out.println("   * Quantum time slice must be an integer greater than 0. *");
							console.nextLine();
						}
					}
					createScheduler(new RoundRobin(quantum));
					break;
				default:
					System.out.println("   * Choose a number 1-4. *");
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
		
		int sleepTime = 0;
		
		System.out.println("\n Simulation Mode: \n");
		System.out.println("   M - Manual" + "\n"
							+ "   A - Automatic" + "\n");
		String userInput = "";
		boolean flag1 = false;
		while (!flag1) {
			System.out.print("   Selection: ");
			userInput = console.next();
			
			switch (userInput) {
				case "M":
				case "m":
					scheduler.setSimulationMode(1);
					flag1 = true;
					break;
				case "A":
				case "a":
					scheduler.setSimulationMode(2);
					boolean flag2 = false;
					while(!flag2) {
						System.out.print("   Enter sleep time (in ms): ");
						try {
							sleepTime = console.nextInt();
							if (sleepTime < 0) {
								throw new Exception();
							}
							flag2 = true;
						} catch (InputMismatchException e) {
							System.out.println("   * Sleep time must be an integer 0 or greater. *");
							console.nextLine();
						} catch (Exception e) {
							System.out.println("   * Sleep time must be an integer 0 or greater. *");
							console.nextLine();
						}
					}
					flag1 = true;
					break;
				default:
					System.out.println("   * Choose either 'M' for 'Manual' or 'A' for 'Automatic'. *");
			}
		}
		
		
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
		System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
		boolean doneFlag = false;		// A flag for continuing and exiting the while loop.
		while (!doneFlag) {
			scheduler.addNewProcess();
			scheduler.addToCPU();
			scheduler.addToIO();
			
			logTimeSlice();
			
			// Output which processes are in which queues, the CPU, and I/O.
			printSchedulerOutput(scheduler);
			
			scheduler.executeBursts();		// Execute next cycle of bursts (both CPU and I/O, if applicable).
			
			// Check if all processes are DONE. If not, exit while loop.
			int numDone = 0;
			for(Process p: scheduler.jobQueue) {
				if (!p.isDone()) {
					break;					// If process is not DONE, exit for loop. Else...
				} else {
					numDone++;				// Increment number of DONE processes.
					
					// If number of DONE processes is equal to total number of processes...
					if (numDone == scheduler.jobQueue.size()) {
						doneFlag = true;		// Set flag to true to exit while loop.
					}
				}
			}
			
			
			
			// If flag has not been set to true, increment system timer and continue while loop.
			if (!doneFlag) {
				
				// If Scheduler is in Manual Mode, ask user to press 'N' for 'Next system time'.
				if (scheduler.getSimulationMode() == 1) {
					boolean innerFlag = false;
					System.out.println("\n  N - Get next time slice\n"
										+ "  A - Switch to Automatic Mode\n");
					while (!innerFlag) {
						System.out.print("  Selection: ");
						userInput = console.next();
						
						switch (userInput) {
							case "N":
							case "n":
								innerFlag = true;
								break;
							case "A":
							case "a":
								scheduler.setSimulationMode(2);
								boolean innerFlag2 = false;
								while(!innerFlag2) {
									System.out.print("  Enter sleep time (in ms): ");
									try {
										sleepTime = console.nextInt();
										if (sleepTime < 0) {
											throw new Exception();
										}
										innerFlag2 = true;
									} catch (InputMismatchException e) {
										System.out.println("   * Sleep time must be an integer 0 or greater. *");
										console.nextLine();
									} catch (Exception e) {
										System.out.println("   * Sleep time must be an integer 0 or greater. *");
										console.nextLine();
									}
								}
								innerFlag = true;
								break;
							default:
								System.out.println("  * Press 'N' for next time slice, or 'A' for Automatic Mode. *");
						}
					}
					System.out.println();
				}
				
				Thread.sleep(sleepTime);				// Pause thread for alloted time.
				scheduler.incrementSystemTimer();		// Increment the system timer.
				System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
			}
		}
		
		// Output results of simulation.
		System.out.println("\n** ALL JOBS FINISHED. **");
		System.out.println("\n Simulator End Time: " + scheduler.getSystemTimer());
		String loggerInfo = "\nSIMULATION METRICS: \n";
		for (Process p: scheduler.getJobQueue()) {
			loggerInfo += " - " + p.getId() + "\n"
						+ "   State: " + p.getState() + "\n"
						+ "   Finish Time: " + p.getFinishTime() + "\n"
						+ "   Turnaround Time: " + p.getTurnaroundTime() + "\n"
						+ "   Wait Time: " + p.getWaitTime() + "\n"
						+ "   I/O Wait Time: " + p.getIoWaitTime() + "\n\n";
		}
		
		loggerInfo += "Average Wait Time: " + scheduler.getAvgWaitTime() + "\n"
					+ "CPU Utilization: " + (100 * scheduler.getCpuUtilization()) + " %\n"
					+ "Throughput: " + scheduler.getThroughput() + "\n"
					+ "Average Turnaround Time: " + scheduler.getAvgTurnaroundTime();
		
		System.out.println(loggerInfo);
		log.logger.info(loggerInfo);
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
	 * Log the information about the time slice in an external log file.
	 */
	public static void logTimeSlice() {
		String loggerInfo = "";
		loggerInfo += "\nSystem Time: " + scheduler.getSystemTimer() + " -------------\n\n";
		
		if (scheduler.currentCPUProcess != null) {
			loggerInfo += "Current CPU Process: [ " + scheduler.getCurrentCPUProcess().getId() + " ]\n";
			loggerInfo += "CPU burst: " + ((scheduler.getCurrentCPUProcess().getCpuBurstList().get(scheduler.getCurrentCPUProcess().getBurstCycle()) + 1) - scheduler.getCurrentCPUProcess().getRemainingBursts())
					+ " of " + scheduler.getCurrentCPUProcess().getCpuBurstList().get(scheduler.getCurrentCPUProcess().getBurstCycle()) + "\n\n";
		} else {
			loggerInfo += "Current CPU Process: [ EMPTY ]\n\n";
		}
		if (scheduler.currentIOProcess != null) {
			loggerInfo += "Current I/O Process: [ " + scheduler.getCurrentIOProcess().getId() + " ]\n";
			loggerInfo += "I/O burst: " + ((scheduler.getCurrentIOProcess().getIoBurstList().get(scheduler.getCurrentIOProcess().getBurstCycle()) + 1) - scheduler.getCurrentIOProcess().getRemainingBursts())
					+ " of " + scheduler.getCurrentIOProcess().getIoBurstList().get(scheduler.getCurrentIOProcess().getBurstCycle()) + "\n\n";
		} else {
			loggerInfo += "Current I/O Process: [ EMPTY ]\n";
		}
		loggerInfo += "\nREADY QUEUE: \n";
		
		if (scheduler.getReadyQueue().size() > 0) {
			loggerInfo += String.format("%-15s %-13s %-13s %-15s %s\n",
								"Name:", "State:", "Arrival:", "CPU Bursts:", "I/O Bursts:");
		} else {
			loggerInfo += " EMPTY\n";
		}
		for (Process p : scheduler.getReadyQueue()) {
			loggerInfo += String.format(" %-15s %-13s %6s       %-15s %s\n",
								p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}

		loggerInfo += "\nI/O QUEUE: \n";
		if (scheduler.getIoWaitQueue().size() > 0) {
			loggerInfo += String.format("%-15s %-13s %-13s %-15s %s\n",
					"Name:", "State:", "Arrival:", "CPU Bursts:", "I/O Bursts:");
		} else {
			loggerInfo += " EMPTY\n";
		}
		for (Process p : scheduler.getIoWaitQueue()) {
			loggerInfo += String.format(" %-15s %-13s %6s       %-15s %s\n",
					p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}
		
		loggerInfo += "\nPROCESSES: \n";
		if (scheduler.getJobQueue().size() > 0) {
			
			loggerInfo += String.format("%-15s %-13s %-13s %-15s %s\n",
					"Name:", "State:", "Arrival:", "CPU Bursts:", "I/O Bursts:");
		} else {
			loggerInfo += " EMPTY\n\n";
		}
		for (Process p : scheduler.getJobQueue()) {	
			loggerInfo += String.format(" %-15s %-13s %6s       %-15s %s\n",
					p.getId(), p.getState(), p.getArrivalTime(), p.getCpuBurstList(), p.getIoBurstList());
		}
		
		loggerInfo += "\n\n\n";
		log.logger.info(loggerInfo);
	}
	

}
