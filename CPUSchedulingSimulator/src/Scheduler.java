
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * Scheduler is an abstract class containing the basic 
 * methods and data members that FCFS, SJF, Priority and Round Robin
 * scenarios will inherit when they are created...
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */
public abstract class Scheduler {
	
	// Data Members
	protected int systemTimer;
	protected float cpuUtilization;
	protected float throughput;
	protected float avgWaitTime;
	protected float avgResponseTime;
	protected int simulationMode;
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
		this.quantumTimeSlice = 0;
		this.currentCPUProcess = null;
		this.currentIOProcess = null;
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
		this.jobQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
	}
	
	// Getters and Setters
	
	
	/**
	 * get the system time
	 * @return int the current system time.
	 */
	public int getSystemTimer() {
		return this.systemTimer;
	}
	
	/**
	 * Set the system time.
	 * @param systemTimer int the new system time.
	 */
	public void setSystemTimer(int systemTimer) {
		this.systemTimer = systemTimer;
	}
	
	/**
	 * Get the cpu utilization, cpu utilization is the total
	 * number of system time slices when the cpu is active divided
	 * by the total number of time slices in the scenario. 
	 * 
	 * @return float with cpu utilization
	 */
	public float getCpuUtilization() {
		return this.cpuUtilization;
	}
	
	/**
	 * Set the cpu utilization. The cpu utilization is the total
	 * number of system time slices when the cpu is active divided
	 * by the total number of time slices in the scenario.
	 * 
	 * @param cpuUtilization float holding cpu utilization
	 */
	public void setCpuUtilization(float cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	/**
	 * get the throughput of the cpu, which is the total
	 * number of process that have been completed in the scenario.
	 * @return throughput float the number of processes that complete in the scenario
	 */
	public float getThroughput() {
		return this.throughput;
	}
	
	/**
	 * set the throughput of the cpu, which the total number of processes
	 * that have been completed during the scenario.
	 * @param throughput float containing the number of processes that
	 * complete during the scenario.
	 */
	public void setThroughput(float throughput) {
		this.throughput = throughput;
	}
	
	/**
	 * Get the average wait time for processes in the scenario. To compute
	 * the average wait time, find the time slice when each process in the 
	 * scenario begins, sum those numbers, and divide the result by the
	 * total number of processes.
	 * 
	 * @return avgWaitTime float the average wait time.
	 */
	public float getAvgWaitTime() {
		return this.avgWaitTime;
	}
	
	/**
	 * Set the average wait time for the processes in the scenario.
	 * To compute the average wait time, find the time slice when each process in the 
	 * scenario begins, sum those numbers, and divide the result by the
	 * total number of processes.
	 * 
	 * @param avgWaitTime float the average wait time.
	 */
	public void setAvgWaitTime(float avgWaitTime) {
		this.avgWaitTime = avgWaitTime;
	}
	
	
	/**
	 * Get the average response time for the scenario. Compute the
	 * response time for a process by measuring the time from when the process
	 * arrives to when the process begins running in th cpu for the first time.
	 * The average response time would sum the response time for each process in the scenario, 
	 * and then divide that sum by the number of processes.
	 * 
	 * @return avResponseTime - float, the average response time for the processes in the scenario.
	 */
	public float getAvgResponseTime() {
		return this.avgResponseTime;
	}
	
	/**
	 * Set the average response time for the scenario. Compute the
	 * response time for a process by measuring the time from when the process
	 * arrives to when the process begins running in th cpu for the first time.
	 * The average response time would sum the response time for each process in the scenario, 
	 * and then divide that sum by the number of processes.
	 * 
	 * @param avgResponseTime float, the average response time for the scenario.
	 */
	public void setAvgResponseTime(float avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}
	
	/**
	 * get the current simulation mode. Should be either 0 for "manual" or 1 for "automatic".
	 * 
	 * @return simulationMode - int the current simulation mode of the scheduler.
	 */
	public int getSimulationMode() {
		return this.simulationMode;
	}
	
	/**
	 * Set the current simulation mode. Should be either 0 for "manual" or 1 for "automatic".
	 * 
	 * @param simulationMode - int the current simulation mode of the scheduler.
	 */
	public void setSimulationMode(int simulationMode) {
		this.simulationMode = simulationMode;
	}
	
	
	/**
	 * Get the current length of the quantum time slice - used for the Round-
	 * Robin model.
	 * 
	 * @return int the length of the current quantum time slice.
	 */
	public int getQuantumTimeSlice() {
		return this.quantumTimeSlice;
	}
	
	/**
	 * Set the current length of the quantum time slice - used for the Round-
	 * Robin model.
	 * 
	 * @param quatumTimeSlice int the length of the current quantum time slice.
	 */
	public void setQuantumTimeSlice(int quantumTimeSlice) {
		this.quantumTimeSlice = quantumTimeSlice;
	}
	
	/**
	 * get the current process in the cpu
	 * @return Process the current process in the CPU
	 */
	public Process getCurrentCPUProcess() {
		return this.currentCPUProcess;
	}
	
	/**
	 * set the current process in the cpu
	 * @param currentCPUProcess the Process to set to the current cpu process.
	 */
	public void setCurrentCPUProcess(Process currentCPUProcess) {
		this.currentCPUProcess = currentCPUProcess;
	}
	
	/**
	 * Get the current process using I/O
	 * 
	 * @return Proccess - the current process using I/O.
	 */
	public Process getCurrentIOProcess() {
		return this.currentIOProcess;
	}
	
	/**
	 * Set the current process using I/O
	 * @param currentIOProcess Process the current process using I/O.
	 */
	public void setCurrentIOProcess(Process currentIOProcess) {
		this.currentIOProcess = currentIOProcess;
	}
	
	/**
	 * Get the scenario reader object for this scenario.
	 * 
	 * @return ScenarioReader the current scenario reader object for this scenario.
	 */
	public ScenarioReader getsReader() {
		return this.sReader;
	}
	
	/**
	 * Set the scenario reader object for this scenario.
	 * 
	 * @param sReader ScenarioReader the scenario reader object for this scenario.
	 */
	public void setsReader(ScenarioReader sReader) {
		this.sReader = sReader;
	}
	
	/**
	 * Get the readyQueue for this scenario, the queue of processes ready 
	 * to use the cpu.
	 * 
	 * @return Queue<Process> the queue of processes in the ready que
	 */
	public Queue<Process> getReadyQueue() {
		return this.readyQueue;
	}
	
	/**
	 * Set the readyQueue for this scenario. The queue of processes ready to 
	 * use the cpu.
	 * 
	 * @param readyQueue Queue<Process> the ready queue for this scenario.
	 */
	public void setReadyQueue(Queue<Process> readyQueue) {
		this.readyQueue = readyQueue;
	}
	
	/**
	 * Get the IO Wait Queue, the queue of processes waiting for IO in this 
	 * scenario.
	 * 
	 * @return Queue<Process> the IO wait queue for this scenario.
	 */
	public Queue<Process> getIoWaitQueue() {
		return this.ioWaitQueue;
	}
	
	/**
	 * Set the IO Wait Queue, the queue of processes waiting for IO in this 
	 * scenario.
	 * 
	 * @param ioWaitQueue
	 */
	public void setIoWaitQueue(Queue<Process> ioWaitQueue) {
		this.ioWaitQueue = ioWaitQueue;
	}
	
	/**
	 * Get the job queue - these are Processes that have been created by the 
	 * scenario reader, but whose arrival times are less than the current system time.
	 * 
	 * @return ArrayList<Process> jobQueue - processes that have been created but have not
	 * yet arrived in the scenario.
	 */
	public ArrayList<Process> getJobQueue() {
		return this.jobQueue;
	}
	
	/**
	 * Set the job queue - these are Processes that have been created by the 
	 * scenario reader, but whose arrival times are less than the current system time.
	 * 
	 * @param jobQueue ArrayList<Process> jobQueue - processes that have been created but have not
	 * yet arrived in the scenario.
	 */
	public void setJobQueue(ArrayList<Process> jobQueue) {
		this.jobQueue = jobQueue;
	}
	
	/**
	 * Get the array list of process that have completed running in the scenario.
	 * 
	 * @return terminatedProcess ArrayList<Process> of processes that have completed their
	 * run in the scenario.
	 */
	public ArrayList<Process> getTerminatedProcesses() {
		return this.terminatedProcesses;
	}
	
	/**
	 * Set the array list of processes that have completed running in the scenario.
	 * 
	 * @param terminatedProcesses ArrayList<Process> of process that have completed their run 
	 * in the scenario.
	 */
	public void setTerminatedProcesses(ArrayList<Process> terminatedProcesses) {
		this.terminatedProcesses = terminatedProcesses;
	}
	
	// Methods
	
	/**
	 * Increment through the jobQueue of processes that have been read by the
	 * scenario reader. if the the arrival time is the same as the system time, set the 
	 * current burst cycle and add the process to the readyQueue.
	 */
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
	
	/**
	 * Set the process state to READ and add the process to the readyQueue
	 * 
	 * @param p Process to add to the readyQueue.
	 */
	public void addToReadyQueue(Process p) {
		p.isReady();								// Set process state to READY.
		this.readyQueue.add(p);
		this.sort();
	}
	
	/**
	 * Check if the CPU is empty and the readyQueue still has processes. If so,
	 * set the process at the head of the queue to the currentCPUProcess. Change 
	 * the process state to "Running"
	 */
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
			this.currentCPUProcess.isRunning();		// Set process state to RUNNING.
		}
	}
	
	/**
	 * Set the process state to "WAITING", update the IO burst cycle,
	 * add the process to the ioWaitQueue, then check for processes waiting
	 * for IO in the ioWaitQuee and set the next process to the currentIOProcess
	 * @param p Process to go into ioWaitQueue
	 */
	public void addToIoWaitQueue(Process p) {
		p.isWaiting();													// Set process state to WAITING.
		p.setRemainingBursts(p.getIoBurstList().get(p.getBurstCycle()));	// Set num. of I/O bursts to next I/O burst cycle.
		this.ioWaitQueue.add(p);
		this.addToIO();
	}
	
	/**
	 * Check if the currentIOProcess is empty and that there is at least
	 * one Process waiting in the ioWaitQueue. If so, get the Process at the
	 * head of the ioWaitQueue, set it to the currentIOProcess, and set the 
	 * process state to "Running"
	 */
	public void addToIO() {
		if (this.currentIOProcess == null && !this.ioWaitQueue.isEmpty()) {
			this.currentIOProcess = ioWaitQueue.poll();
			this.currentIOProcess.isRunning();		// Set process state to RUNNING.
		}
	}
	
	/**
	 * Checks to see if there is a process in the CPU. If so,
	 * it executes the simulators executeCPU(), if there is no
	 * process in the the CPU, it executes the scenarios executeIO().
	 */
	public void executeBursts() {
		if (this.currentCPUProcess != null) {
			this.executeCPU();
		} else {
			this.executeIO();
		}
	}
	
	/**
	 * Increments the system timer by 1.
	 */
	public void incrementSystemTimer() {
		this.systemTimer += 1;
	}
	
	/**
	 * Iterate through processes in readyQueue and add one to
	 * the value of the process waitTime.
	 */
	public void incrementWaitTimes() {
		for (Process p: readyQueue ) {
			if (this.getSystemTimer() > p.getArrivalTime() + 1) {
				p.incrementProcessWaitTime();
			}
		}
		
	}
	
	/**
	 * Iterate through the processes in the ioReadyQueue and add one
	 * to the value of the process ioWaitTime
	 */
	public void incrementIoWaitTimes() {
		for (Process p: ioWaitQueue ) {
			p.incrementProcessIoWaitTime();
			System.out.println("--------------");
			p.incrementProcessWaitTime();
		}
	}
	
	/**
	 * Runs the cpu use pattern specific to the scenario. Not
	 * implemented here because Scheduler is an abstract class,
	 * and each scenario will utilize the CPU differently.
	 */
	public abstract void executeCPU();
	
	/**
	 * runs the IO use pattern specific to the scenario. Not
	 * implemented here because Scheduler is an abstract class,
	 * and each scenario will utilize the CPU differently.
	 */
	public abstract void executeIO();
	
	/**
	 * Sorts the readyQueue for the specific scenario. Not implemented
	 * here because Scheduler is an abstract class, and each scenario
	 * will sort differently.
	 */
	public abstract void sort();
	
}
