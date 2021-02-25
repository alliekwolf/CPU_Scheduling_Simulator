/**
 * The Scheduler class models a CPU scheduler in an effort to 
 * simulate CPU process scheduling according to a variety of different
 * scheduling models. 
 * 
 * 
 * @author Brian Steele, Cole Walsh, Allie Wolf
 *
 */

import java.util.Queue;

public class Scheduler {
	
	// Data Members
	private int systemTimer;
	private float cpuUtilization;
	private float throughPut;
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
	
	
	// Constructors
	/**
	 * 
	 * Default constructor
	 */
	public Scheduler() {
		
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
	 * 			processes are organized into the cpu, i/o, read queue and i/o ready queue during the 
	 * 			simulation
	 * @param sReader - A ScenerioReader object which will open and process a file that contains the 
	 * 			information to model processes running in this scenario.
	 */
	public Scheduler(int simulationMode, int quantumTimeSlice, Algorithm algorithm, ScenarioReader sReader) {
		this.simulationMode = simulationMode;
		this.quantumTimeSlice = quantumTimeSlice;
		this.algorithm = algorithm;
		this.sReader = sReader;
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
	 * @return throughPut
	 */
	public float getThroughPut() {
		return throughPut;
	}
	
	/**
	 * @param throughPut to set the throughPut
	 */
	public void setThroughPut(float throughPut) {
		this.throughPut = throughPut;
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
	
	
	// Additional Methods
	/**
	 * Apply the algorithm's sorting method.
	 */
	public void start() {
		this.algorithm.apply();
	}
	
}
