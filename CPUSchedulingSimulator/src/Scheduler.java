/**
 * 
 * The Scheduler class models a CPU scheduler in an effort to 
 * simulate CPU process scheduling according to a variety of different
 * scheduling models. 
 * 
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	
	// Data Members
	private int systemTimer;
	private float cpuUtilization;
	private float throughput;
	private float avgWaitTime;
	private float avgResponseTime;
	private int simulationMode;
	private float simulationTime;
	private int quantumTimeSlice;
	private Process currentCPUProcess;
	private Process currentIOProcess;
	private Algorithm algorithm;
	private ScenarioReader sReader;
	private Queue<Process> readyQueue;
	private Queue<Process> ioWaitQueue;
	private ArrayList<Process> jobQueue;
	private ArrayList<Process> terminatedProcesses;
	
	
	// Constructors
	/**
	 * 
	 * Default constructor
	 */
	public Scheduler() {
		this.systemTimer = 0;
		this.cpuUtilization = 0;
		this.throughput = 0;
		this.avgWaitTime = 0;
		this.avgResponseTime = 0;
		this.simulationMode = 0;
		this.simulationTime = 0;
		this.quantumTimeSlice = 0;
		this.currentCPUProcess = null;
		this.currentIOProcess = null;
		this.algorithm = null;
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
		this.jobQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
	}
	
	/**
	 * 
	 * Constructor for Scheduler
	 * 
	 * @param simulationMode - must be either 0, for "auto" mode, or 1 for "manual" mode.
	 * 			will determine how the simulation runs - in auto mode the simulation will run
	 * 			without intervention from the user. In manual mode, the user will step through
	 * 			each iteration of the simulation.
	 * @param quantumTimeSlice - represents the quantum of time given to each process in a 
	 * 			round-robin scheduling model.
	 * @param algorithm - An object that extends the Algorithm class, which will determine how
	 * 			processes are organized into the CPU, I/O, ready queue and I/O ready queue during the 
	 * 			simulation
	 * @param sReader - A ScenerioReader object which will open and process a file that contains the 
	 * 			information to model processes running in this scenario.
	 */
	public Scheduler(int simulationMode, int quantumTimeSlice, Algorithm algorithm, ScenarioReader sReader) {
		this.simulationMode = simulationMode;
		this.quantumTimeSlice = quantumTimeSlice;
		this.algorithm = algorithm;
		this.sReader = sReader;
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
	}
	
	
	// Getters and Setters
	/**
	 * @return systemTimer
	 */
	public int getSystemTimer() {
		return systemTimer;
	}
	/**
	 * @param systemTimer to set the systemTimer
	 */
	public void setSystemTimer(int systemTimer) {
		this.systemTimer = systemTimer;
	}
	
	/**
	 * @return cpuUtilization
	 */
	public float getCpuUtilization() {
		return cpuUtilization;
	}
	
	/**
	 * @param cpuUtilization to set the cpuUtilization
	 */
	public void setCpuUtilization(float cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	/**
	 * @return throughput
	 */
	public float getThroughput() {
		return throughput;
	}
	
	/**
	 * @param throughPut to set the throughPut
	 */
	public void setThroughput(float throughput) {
		this.throughput = throughput;
	}
	
	/**
	 * @return avgWaitTime
	 */
	public float getAvgWaitTime() {
		return avgWaitTime;
	}
	
	/**
	 * @param avgWaitTime to set the avgWaitTime
	 */
	public void setAvgWaitTime(float avgWaitTime) {
		this.avgWaitTime = avgWaitTime;
	}
	
	/**
	 * @return avgResponseTime
	 */
	public float getAvgResponseTime() {
		return avgResponseTime;
	}
	
	/**
	 * @param avgResponseTime to set the avgResponseTime
	 */
	public void setAvgResponseTime(float avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}
	
	/**
	 * @return simulationMode
	 */
	public int getSimulationMode() {
		return simulationMode;
	}
	
	/**
	 * @param simulationMode to set the simulationMode
	 */
	public void setSimulationMode(int simulationMode) {
		this.simulationMode = simulationMode;
	}
	
	/**
	 * @return simulationTime
	 */
	public float getSimulationTime() {
		return simulationTime;
	}
	
	/**
	 * 
	 * @param simulationTime to set the simulationTime
	 */
	public void setSimulationTime(float simulationTime) {
		this.simulationTime = simulationTime;
	}
	
	/**
	 * @return quantumTimeSlice
	 */
	public int getQuantumTimeSlice() {
		return quantumTimeSlice;
	}
	
	/**
	 * @param quantumTimeSlice to set the quantumTimeSlice
	 */
	public void setQuantumTimeSlice(int quantumTimeSlice) {
		this.quantumTimeSlice = quantumTimeSlice;
	}
	
	/**
	 * @return currentCPUProcess
	 */
	public Process getCurrentCPUProcess() {
		return currentCPUProcess;
	}
	
	/**
	 * @param currentCPUProcess to set the currentCPUProcess
	 */
	public void setCurrentCPUProcess(Process currentCPUProcess) {
		this.currentCPUProcess = currentCPUProcess;
	}
	
	/**
	 * @return currentIOProcess
	 */
	public Process getCurrentIOProcess() {
		return currentIOProcess;
	}
	
	/**
	 * @param currentIOProcess to set the currentIOProcess
	 */
	public void setCurrentIOProcess(Process currentIOProcess) {
		this.currentIOProcess = currentIOProcess;
	}
	
	/**
	 * @return algorithm
	 */
	public Algorithm getAlgorithm() {
		return algorithm;
	}
	
	/**
	 * @param algorithm to set the algorithm
	 */
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}
	
	/**
	 * @return sReader
	 */
	public ScenarioReader getsReader() {
		return sReader;
	}
	
	/**
	 * 
	 * @param sReader to set the sReader
	 */
	public void setsReader(ScenarioReader sReader) {
		this.sReader = sReader;
	}
	
	/**
	 * @return readyQueue
	 */
	public Queue<Process> getReadyQueue() {
		return this.readyQueue;
	}
	
	/**
	 * @param readyQueue to set the readyQueue
	 */
	public void setReadyQueue(Queue<Process> readyQueue) {
		this.readyQueue = readyQueue;
	}
	
	/**
	 * @return ioWaitQueue
	 */
	public Queue<Process> getIoWaitQueue() {
		return ioWaitQueue;
	}
	
	/**
	 * @param ioWaitQueue to set the ioWaitQueue
	 */
	public void setIoWaitQueue(Queue<Process> ioWaitQueue) {
		this.ioWaitQueue = ioWaitQueue;
	}
	
	/**
	 * @return jobQueue
	 */
	public ArrayList<Process> getJobQueue() {
		return this.jobQueue;
	}
	
	/**
	 * @param jobQueue
	 */
	public void setJobQueue(ArrayList<Process> jobQueue) {
		this.jobQueue = jobQueue;
	}
	/**
	 * @return terminatedProcesses
	 */
	public ArrayList<Process> getTerminatedProcesses() {
		return this.terminatedProcesses;
	}
	
	/**
	 * @param terminatedProcesses
	 */
	public void setTerminatedProcesses(ArrayList<Process> terminatedProcesses) {
		this.terminatedProcesses = terminatedProcesses;
	}
	
	
	// Additional Methods
	/**
	 * Apply the algorithm's scheduling method (FCFS, SJF, Priority, or RR).
	 */
	public void applyAlgorithm() {
		this.algorithm.apply();
	}
	
	/**
	 * Increment the system timer by 1.
	 */
	public void incrementSystemTimer() {
		this.systemTimer += 1;
	}
	
	/**
	 * Loops through the initial list of processes created by the ScenarioReader object in the 
	 * Client program and compares each process's arrival time to the Scheduler's system timer.  
	 * If the system time and the process's arrival time, match, then that process will be added  
	 * to the Scheduler's ready queue.
	 */
	public void addNewProcess() {
		for (int i = 0; i < this.jobQueue.size(); i++) {
			Process p = this.jobQueue.get(i);
			if (this.systemTimer == this.jobQueue.get(i).getArrivalTime()) {
				this.addToReadyQueue(this.jobQueue.get(i));
				this.applyAlgorithm();
				this.jobQueue.remove(i);
			}
		}
	}
	
	/**
	 * Adds a new process to the ready queue.
	 * 
	 * @param p to add new process to readyQueue
	 */
	public void addToReadyQueue(Process p) {
		this.readyQueue.add(p);
		this.applyAlgorithm();
	}
	
	/**
	 * If the CPU has no process in it, adds the next process in the 
	 * ready queue to the CPU.
	 */
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
		}
	}
	
	/**
	 * Adds a new process to the I/O wait queue.
	 * 
	 * @param p to add new process to ioWaitQueue
	 */
	public void addToIoWaitQueue(Process p) {
		if (this.ioWaitQueue.isEmpty()) {
			this.ioWaitQueue.add(p);
			this.addToIO();
		} else {
			this.ioWaitQueue.add(p);
		}
	}
	
	/**
	 * If the I/O has no process in it, adds the next process in the 
	 * I/O wait queue to the I/O.
	 */
	public void addToIO() {
		if (this.currentIOProcess == null && !this.ioWaitQueue.isEmpty()) {
			this.currentIOProcess = ioWaitQueue.poll();
		}
	}
	
	/**
	 * If the CPU has a process in it, executes a single burst in the current 
	 * burst cycle. If it is the last burst in the cycle, it will either add the 
	 * process to the I/O wait queue, or, if the process has completely finished, 
	 * to the terminated processes ArrayList.
	 * 
	 * This method also calls the executeIOBurst() method.
	 */
	public void executeCPUBurst() {
		this.executeIOBurst();  // Execute I/O burst.
		
		if (this.currentCPUProcess != null) {
			this.currentCPUProcess.decrementCurrentBurst();
			System.out.println("CPU burst: " + this.currentCPUProcess.getCurrentBurst());
			
			// If current burst reaches 0 and there are more burst cycles to go, move Process to I/O wait queue.
			// Else, if current burst reaches 0 and there are NO MORE burst cycles to go, Process is finished.
			if (this.currentCPUProcess.getCurrentBurst() == 0 && (this.currentCPUProcess.getBurstCycle()) < this.currentCPUProcess.getCpuBurstList().size()-1) {
				this.currentCPUProcess.setCurrentBurst(this.currentCPUProcess.getIoBurstList().get(this.currentCPUProcess.getBurstCycle()));
				this.addToIoWaitQueue(this.currentCPUProcess);
				this.currentCPUProcess = null;
			} else if (this.currentCPUProcess.getCurrentBurst() == 0 && (this.currentCPUProcess.getBurstCycle()) == this.currentCPUProcess.getCpuBurstList().size()-1) {
				this.terminatedProcesses.add(this.currentCPUProcess);
				System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
				this.currentCPUProcess = null;
			}
		}
	}
	
	/**
	 * If the I/O has a process in it, executes a single burst in the current 
	 * burst cycle. If it is the last burst of the cycle, it will increment the 
	 * process's burst cycle, and add the process back to the ready queue.
	 */
	public void executeIOBurst() {
		if (this.currentIOProcess != null) {
			this.currentIOProcess.decrementCurrentBurst();
			System.out.println("I/O burst: " + this.currentIOProcess.getCurrentBurst());
			if (this.currentIOProcess.getCurrentBurst() == 0) {
				this.currentIOProcess.incrementBurstCycle();
				this.currentIOProcess.setCurrentBurst(this.currentIOProcess.getCpuBurstList().get(this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);
				this.currentIOProcess = null;
			}
		}
	}
	
}
