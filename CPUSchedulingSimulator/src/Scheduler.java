
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
	protected float simulationTime;
	protected int simulationEndTime;
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
	
	
	// Getters and Setters
	/**
	 * Get the system time.
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
	 * Get the CPU utilization, CPU utilization is the total
	 * number of system time slices when the CPU is active divided
	 * by the total number of time slices in the scenario. 
	 * 
	 * @return float with CPU utilization
	 */
	public float getCpuUtilization() {
		return this.cpuUtilization;
	}
	
	/**
	 * Set the CPU utilization. The CPU utilization is the total
	 * number of system time slices when the CPU is active divided
	 * by the total number of time slices in the scenario.
	 * 
	 * @param cpuUtilization float holding CPU utilization
	 */
	public void setCpuUtilization(float cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	
	/**
	 * Get the throughput of the cpu, which is the total
	 * number of process that have been completed in the scenario.
	 * 
	 * @return throughput float the number of processes that complete in the scenario
	 */
	public float getThroughput() {
		return this.throughput;
	}
	
	/**
	 * Set the throughput of the cpu, which the total number of processes
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
	 * Get the current simulation mode. Should be either 0 for "manual" or 1 for "automatic".
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
	
//	public float getSimulationTime() {
//		return this.simulationTime;
//	}
//	
//	public void setSimulationTime(float simulationTime) {
//		this.simulationTime = simulationTime;
//	}
	
	/**
	 * Get the value of the last time slice of the simulation
	 * run.
	 * @return simulationEndTime, int, the last time slice of the simulation.
	 */
	public int getSimulationEndTime() {
		return this.simulationEndTime;
	}

	/**
	 * set the last time of the simulation run
	 * @param simulationEndTime int the value of the last time slice of the simulation.
	 */
	public void setSimulationEndTime(int simulationEndTime) {
		this.simulationEndTime = simulationEndTime;
	}
	
	/**
	 * Set the current length of the quantum time slice - used for the Round-
	 * Robin model.
	 * 
	 * @param quatumTimeSlice - int the length of the quantum time slice.
	 */
	public int getQuantumTimeSlice() {
		return this.quantumTimeSlice;
	}
	
	/**
	 * Set the current length of the quantum time slice - used for the Round-
	 * Robin model.
	 * 
	 * @param quatumTimeSlice - int the length of the quantum time slice.
	 */
	public void setQuantumTimeSlice(int quantumTimeSlice) {
		this.quantumTimeSlice = quantumTimeSlice;
	}
	
	/**
	 * Get the current process in the CPU.
	 * @return Process the current process in the CPU
	 */
	public Process getCurrentCPUProcess() {
		return this.currentCPUProcess;
	}
	
	/**
	 * set the current process in the CPU.
	 * @param currentCPUProcess the Process to set to the current CPU process.
	 */
	public void setCurrentCPUProcess(Process currentCPUProcess) {
		this.currentCPUProcess = currentCPUProcess;
	}
	
	/**
	 * Get the current process using I/O.
	 * 
	 * @return Process - the current process using I/O.
	 */
	public Process getCurrentIOProcess() {
		return this.currentIOProcess;
	}
	
	/**
	 * Set the current process using I/O.
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
	 * to use the CPU.
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
	 * Set the process state to READu and add the process to the readyQueue
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
	 * for IO in the ioWaitQuee and set the next process to the currentIOProcess.
	 * 
	 * @param p Process to go into ioWaitQueue
	 */
	public void addToIoWaitQueue(Process p) {
		p.isWaiting();														// Set process state to WAITING.
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
		}
	}
	
	/**
	 * Checks to see if there is a process in the CPU. If so,
	 * it executes the simulators executeCPU(), if there is no
	 * process in the the CPU, it apply executeIO().
	 */
	public void executeBursts() {
		if (this.currentCPUProcess != null) {
			this.incrementCpuUtilization();		//if the cpu is busy, increment cpu utilization...
			this.executeCPU();
		} else {
			this.executeIO();
		}
	}
	
	/**
	 * Executes CPU bursts specific to the scenario.
	 * - Decrement the number of bursts left in the CPU burst length.
	 * - Print the number of CPU bursts left in this cycle for this process.
	 * - Apply the executeIO method.
	 * - If the current burst is 0, either send the process to the IO waiting queue
	 *   or terminate the process.
	 */
	public void executeCPU() {
		this.currentCPUProcess.decrementRemainingBursts();
		
		// Output current burst of cycle.
		System.out.println("CPU burst: " + (this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()) - this.currentCPUProcess.getRemainingBursts())
							+ " of " + this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()));
		
		this.executeIO();
		
		// If current burst reaches 0 and there are more burst cycles to go, move Process to I/O wait queue.
		// Else, if current burst reaches 0 and there are NO MORE burst cycles to go, Process is finished.
		if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() < currentCPUProcess.getCpuBurstList().size() - 1)) {
			this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
			this.currentCPUProcess = null;
		} else if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() == currentCPUProcess.getCpuBurstList().size() - 1)) {
			this.currentCPUProcess.isDone();							// Set process state to NULL.
			this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set finish time to system timer.
			this.terminatedProcesses.add(this.currentCPUProcess);		// Add to terminated processes. 
			
			System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
			this.currentCPUProcess = null;
		}
	}
	
	/**
	 * Executes I/O bursts specific to the scenario.
	 * If there is a current IO Process, decrement the remaining time in it's current burst.
	 * - Print the number of IO bursts remaining.
	 * - Increment all wait times for all applicable processes by applying the 
	 *   incrementWaitTimes() and incrementIoWaitTimes() methods.
	 * - If that was the last of the I/O bursts in this I/O burst cycle...
	 *   - Increment the burst cycle to get next index of both cpuBurstList and ioBurstList.
	 *   - Update the remaining bursts to value in next index of cpuBurstList.
	 *   - Add this process to the ready queue.
	 *   - Remove this process from the IO queue.
	 * Otherwise, apply incrementWaitTimes() and incrementIoWaitTimes() methods.
	 */
	public void executeIO() {
		if (this.currentIOProcess != null) {
			this.currentIOProcess.decrementRemainingBursts();			// Decrement current burst in the cycle.
			
			// Output current burst of cycle.
			System.out.println("I/O burst: " + (this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()) - this.currentIOProcess.getRemainingBursts())
								+ " of " + this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()));
			
			this.incrementWaitTimes();
			this.incrementIoWaitTimes();
			
			if (this.currentIOProcess.getRemainingBursts() == 0) {		// If this was the last burst of the cycle...
				this.currentIOProcess.incrementBurstCycle();			// Set next burst cycle and reset current burst.
				this.currentIOProcess.setRemainingBursts(this.currentIOProcess.getCpuBurstList().get(
						this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);			// Move process to ready queue.
				this.currentIOProcess = null;
			}
		} else {
			this.incrementWaitTimes();
			this.incrementIoWaitTimes();
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
		for (Process p: this.readyQueue) {
			if (this.getSystemTimer() > p.getArrivalTime()) {
				p.incrementProcessWaitTime();
			}
		}
		for (Process p: this.ioWaitQueue) {
			p.incrementProcessWaitTime();
		}
		if (this.currentIOProcess != null) {
			this.currentIOProcess.incrementProcessWaitTime();
		}
		
	}
	
	/**
	 * Iterate through the processes in the ioReadyQueue and add one
	 * to the value of the process ioWaitTime
	 */
	public void incrementIoWaitTimes() {
		for (Process p: ioWaitQueue ) {
			p.incrementProcessIoWaitTime();
		}
		if (this.currentIOProcess != null) {
			this.currentIOProcess.incrementProcessIoWaitTime();
		}
	}
	
	/**
	 * sum all of the wait times for Processes in the terminatedProcess
	 * Queue, and divide them by the size of the list to get the average
	 * wait time for processes in the simulation.
	 * @return
	 */
	public float computeAverageWaitTime() {
		float sum = 0;
		float averageWaitTime = 0;
		float numProcesses = this.terminatedProcesses.size();
		
		for (Process p: this.terminatedProcesses) {
			sum += p.getWaitTime();
		}
		averageWaitTime = sum/numProcesses;
		
		return averageWaitTime;
	}
	
	/**
	 * increase the cpuUtilization by 1.
	 */
	public void incrementCpuUtilization() {
		this.cpuUtilization++;
	}
	
	public float computeCpuUtilization() {
		return (this.getCpuUtilization()/(float)this.getSimulationEndTime())* 100;
	}
	
	// Abstract Methods
	/**
	 * Sorts the readyQueue for the specific scenario. Not implemented
	 * here because Scheduler is an abstract class, and each scenario
	 * will sort differently.
	 */
	public abstract void sort();
	
}
