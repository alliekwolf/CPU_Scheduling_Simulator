
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * 
 * The SimulatorClient contains the main() for the CPUSchedulingSimulator project.
 * Start the program here. 
 * 
 * The program reads in a 
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public class SimulatorClient {
	
	public static Scanner console = new Scanner(System.in);
	public static Scheduler scheduler;
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		System.out.println("Select a scenario file to run");
		
		//open a scenario file to run.
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		int result = fileChooser.showOpenDialog(null);
		File selectedFile = fileChooser.getSelectedFile();
		
		
		
		// variables
		ScenarioReader sr = new ScenarioReader(selectedFile);
		
		// Print simulator menu.
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
		
		// print the menu
		System.out.println("\n-- CPU Scheduling Simulator --\n");
		System.out.println("Choose a scheduling algorithm:\n\n" 
							+ " 1 -- First Come, First Serve\n" 
							+ " 2 -- Shortest Job First\n" 
							+ " 3 -- Priority\n" 
							+ " 4 -- Round Robin\n");
		
		// handle input issues
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
			
			// sort the choices and create a scheduler type based
			// on the original Scheduler abstract class...
			switch (userInput) {
				case 1: createScheduler(new FCFS());
					break;
				case 2: createScheduler(new SJF());
					break;
				case 3: createScheduler(new Priority());
					break;
				case 4:
					// handle the creation of a quantum time slice for 
					// round robin.
					int quantum = -1;
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
		
		
		// print a title indicating the scheduler type...
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
		
		
		// The while loop is where all the magic happens...
		
		boolean flag = false;
		while (flag == false) {
			System.out.println("\nSystem Time: " + scheduler.getSystemTimer());
			scheduler.addNewProcess();
			scheduler.addToCPU();
			scheduler.addToIO();
			
			
			// Output which processes are in which queues, the CPU, and I/O.
			printSchedulerOutput(scheduler);
			
			// Execute next cycle of bursts (both CPU and I/O, if applicable).
			// Then, increment the system timer.
			scheduler.executeBursts();
			scheduler.incrementSystemTimer();
			
			scheduler.incrementWaitTimes();
			scheduler.incrementIoWaitTimes();
			
			// If all queues are empty, and no processes are running, terminate execution.
			if (scheduler.getJobQueue().isEmpty() &&
				scheduler.getReadyQueue().isEmpty() &&
				scheduler.getIoWaitQueue().isEmpty() &&
				scheduler.getCurrentCPUProcess() == null &&
				scheduler.getCurrentIOProcess() == null) {
				flag = true;
			}
			
			Thread.sleep(400);
			
			
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
