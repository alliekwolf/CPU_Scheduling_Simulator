import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Scheduler {
	
	// Data Members
	protected int systemTimer;
	protected float cpuUtilization;
	protected float throughput;
	protected float avgWaitTime;
	protected float avgResponseTime;
	protected int simulationMode;
	protected float simulationTime;
	protected int quantumTimeSlice;
	protected Process currentCPUProcess;
	protected Process currentIOProcess;
	protected ScenarioReader sReader;
	protected Queue<Process> readyQueue;
	protected Queue<Process> ioWaitQueue;
	protected ArrayList<Process> jobQueue;
	protected ArrayList<Process> terminatedProcesses;
	
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
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
		this.jobQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
	}
	
	
	public int getSystemTimer() {
		return this.systemTimer;
	}
	
	public void setSystemTimer(int systemTimer) {
		this.systemTimer = systemTimer;
	}
	
	public float getCpuUtilization() {
		return this.cpuUtilization;
	}
	
	public void setCpuUtilization(float cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	public float getThroughput() {
		return this.throughput;
	}
	
	public void setThroughput(float throughput) {
		this.throughput = throughput;
	}
	
	public float getAvgWaitTime() {
		return this.avgWaitTime;
	}
	
	public void setAvgWaitTime(float avgWaitTime) {
		this.avgWaitTime = avgWaitTime;
	}
	
	public float getAvgResponseTime() {
		return this.avgResponseTime;
	}
	
	public void setAvgResponseTime(float avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}
	
	public int getSimulationMode() {
		return this.simulationMode;
	}
	
	public void setSimulationMode(int simulationMode) {
		this.simulationMode = simulationMode;
	}
	
	public float getSimulationTime() {
		return this.simulationTime;
	}
	
	public void setSimulationTime(float simulationTime) {
		this.simulationTime = simulationTime;
	}
	
	public int getQuantumTimeSlice() {
		return this.quantumTimeSlice;
	}
	
	public void setQuantumTimeSlice(int quantumTimeSlice) {
		this.quantumTimeSlice = quantumTimeSlice;
	}
	
	public Process getCurrentCPUProcess() {
		return this.currentCPUProcess;
	}
	
	public void setCurrentCPUProcess(Process currentCPUProcess) {
		this.currentCPUProcess = currentCPUProcess;
	}
	
	public Process getCurrentIOProcess() {
		return this.currentIOProcess;
	}
	
	public void setCurrentIOProcess(Process currentIOProcess) {
		this.currentIOProcess = currentIOProcess;
	}
	
	public ScenarioReader getsReader() {
		return this.sReader;
	}
	
	public void setsReader(ScenarioReader sReader) {
		this.sReader = sReader;
	}
	
	public Queue<Process> getReadyQueue() {
		return this.readyQueue;
	}
	
	public void setReadyQueue(Queue<Process> readyQueue) {
		this.readyQueue = readyQueue;
	}
	
	public Queue<Process> getIoWaitQueue() {
		return this.ioWaitQueue;
	}
	
	public void setIoWaitQueue(Queue<Process> ioWaitQueue) {
		this.ioWaitQueue = ioWaitQueue;
	}
	
	public ArrayList<Process> getJobQueue() {
		return this.jobQueue;
	}
	
	public void setJobQueue(ArrayList<Process> jobQueue) {
		this.jobQueue = jobQueue;
	}
	
	public ArrayList<Process> getTerminatedProcesses() {
		return this.terminatedProcesses;
	}
	
	public void setTerminatedProcesses(ArrayList<Process> terminatedProcesses) {
		this.terminatedProcesses = terminatedProcesses;
	}
	
	public void addNewProcess() {
		for (int i = 0; i < this.jobQueue.size(); i++) {
			if (this.systemTimer == this.jobQueue.get(i).getArrivalTime()) {
				this.jobQueue.get(i).setRemainingBursts(this.jobQueue.get(i).getCpuBurstList().get(
						this.jobQueue.get(i).getBurstCycle()));		// Set current burst to first burst cycle.
				this.addToReadyQueue(this.jobQueue.get(i));
				this.sort();
				this.jobQueue.remove(i);
			}
		}
	}
	
	public void addToReadyQueue(Process p) {
		p.isReady();								// Set process state to READY.
		this.readyQueue.add(p);
		this.sort();
	}
	
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
			this.currentCPUProcess.isRunning();		// Set process state to RUNNING.
		}
	}
	
	public void addToIoWaitQueue(Process p) {
		p.isWaiting();													// Set process state to WAITING.
		p.setRemainingBursts(p.getIoBurstList().get(p.getBurstCycle()));	// Set num. of I/O bursts to next I/O burst cycle.
		this.ioWaitQueue.add(p);
		this.addToIO();
	}
	
	public void addToIO() {
		if (this.currentIOProcess == null && !this.ioWaitQueue.isEmpty()) {
			this.currentIOProcess = ioWaitQueue.poll();
			this.currentIOProcess.isRunning();		// Set process state to RUNNING.
		}
	}
	
	/**
	 * Increments the system timer by 1.
	 */
	public void incrementSystemTimer() {
		this.systemTimer += 1;
	}
	
	public abstract void executeCPU();
	
	public abstract void executeIO();
	
	public abstract void sort();
	
}
