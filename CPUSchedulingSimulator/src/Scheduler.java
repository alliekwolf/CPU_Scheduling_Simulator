
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

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
	protected float avgTurnaroundTime;
	protected int simulationMode;
	protected int idleTime;
	protected int quantumTimeSlice;
	protected Process currentCPUProcess;
	protected Process currentIOProcess;
	protected Queue<Process> readyQueue;
	protected Queue<Process> ioWaitQueue;
	protected ArrayList<Process> jobQueue;
	
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
		this.avgTurnaroundTime = 0;
		this.simulationMode = 0;
		this.quantumTimeSlice = 0;
		this.currentCPUProcess = null;
		this.currentIOProcess = null;
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
		this.jobQueue = new ArrayList<Process>();
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
	 * Get the throughput of the CPU, which is the total
	 * number of process that have been completed in the scenario.
	 * 
	 * @return throughput float the number of processes that complete in the scenario
	 */
	public float getThroughput() {
		return this.throughput;
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
	 * Get the average turnaround time for all processes. To compute the average turnaround
	 * time, get the turnaround times for all processes, sum those numbers, and divide the 
	 * result by the total number of processes.
	 * 
	 * @return avgTurnaroundTime - float, the average turnaround time for the processes in the scenario.
	 */
	public float getAvgTurnaroundTime() {
		return this.avgTurnaroundTime;
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
	 * Get the readyQueue for this scenario, the queue of processes ready 
	 * to use the CPU.
	 * 
	 * @return Queue<Process> the queue of processes in the ready queue.
	 */
	public Queue<Process> getReadyQueue() {
		return this.readyQueue;
	}
	
	/**
	 * Set the readyQueue for this scenario. The queue of processes ready to 
	 * use the CPU.
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
	
	
	// Methods
	/**
	 * Increment through the jobQueue of processes that have been read by the
	 * scenario reader. if the the arrival time is the same as the system time, set the 
	 * current burst cycle and add the process to the readyQueue.
	 */
	public void addNewProcess() {
		for (Process p: this.jobQueue) {
			if (this.systemTimer == p.getArrivalTime()) {
				p.setRemainingBursts(p.getCpuBurstList().get(p.getBurstCycle()));		// Set current burst to first burst cycle.
				
				String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
						+ p.getId() + " created.\n\n\n";
				System.out.println(loggerInfo);
				SimulatorClient.log.logger.info(loggerInfo);
				
				this.addToReadyQueue(p);
				this.sort();
			}
		}
	}
	
	/**
	 * Set the process state to READY and add the process to the readyQueue
	 * 
	 * @param p Process to add to the readyQueue.
	 */
	public void addToReadyQueue(Process process) {
		process.setState(State.READY);					// Set process state to READY.
		this.readyQueue.add(process);
		this.sort();
		
		this.updateJobQueue(process);
		
		String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
				+ process.getId() + " added to the Ready Queue.\n\n\n";
		System.out.println(loggerInfo);
		SimulatorClient.log.logger.info(loggerInfo);
	}
	
	/**
	 * Check if the CPU is empty and the readyQueue still has processes. If so,
	 * set the process at the head of the queue to the currentCPUProcess. Change 
	 * the process state to "Running"
	 */
	public void addToCPU() {
		if (this.currentCPUProcess == null && !this.readyQueue.isEmpty()) {
			this.currentCPUProcess = this.readyQueue.poll();
			this.currentCPUProcess.setState(State.RUNNING);		// Set process state to RUNNING.
			
			// If this is the first burst cycle, set the Process's start time.
			if (this.currentCPUProcess.getBurstCycle() == 0) {
				this.currentCPUProcess.setStartTime(this.systemTimer);
			}
			
			String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
					+ this.currentCPUProcess.getId() + " added to the CPU.\n\n\n";
			System.out.println(loggerInfo);
			SimulatorClient.log.logger.info(loggerInfo);
		}
	}
	
	/**
	 * Set the process state to "WAITING", update the IO burst cycle,
	 * add the process to the ioWaitQueue, then check for processes waiting
	 * for IO in the ioWaitQuee and set the next process to the currentIOProcess.
	 * 
	 * @param p Process to go into ioWaitQueue
	 */
	public void addToIoWaitQueue(Process process) {
		process.setState(State.WAITING);													// Set process state to WAITING.
		process.setRemainingBursts(process.getIoBurstList().get(process.getBurstCycle()));	// Set num. of I/O bursts to next I/O burst cycle.
		this.ioWaitQueue.add(process);
		
		String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
				+ process.getId() + " added to the I/O Wait Queue.\n\n\n";
		System.out.println(loggerInfo);
		SimulatorClient.log.logger.info(loggerInfo);
		
		this.addToIO();
		this.updateJobQueue(process);
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
			
			this.updateJobQueue(this.currentIOProcess);
			
			String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
					+ this.currentIOProcess.getId() + " added to the I/O.\n\n\n";
			System.out.println(loggerInfo);
			SimulatorClient.log.logger.info(loggerInfo);
		}
	}
	
	/**
	 * Checks to see if there is a process in the CPU. If so,
	 * it executes the simulators executeCPU(), if there is no
	 * process in the the CPU, it apply executeIO().
	 */
	public void executeBursts() {
		this.incrementWaitTimes();
		this.incrementIoWaitTimes();
		if (this.currentCPUProcess != null) {
			this.executeCPU();
		} else {
			this.idleTime++;
			this.executeIO();
		}
		this.computeCpuUtilization();
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
		
		this.executeIO();
		
		// If current process has no more bursts remaining in this cycle...
		if (this.currentCPUProcess.getRemainingBursts() == 0) {
			
			// If current process still has burst cycles left...
			if (this.currentCPUProcess.getBurstCycle() < (currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
				this.currentCPUProcess = null;
				
				// Else, if process has no more burst cycles remaining...
			} else if (this.currentCPUProcess.getBurstCycle() == (currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.currentCPUProcess.setState(State.DONE);				// Set process state to DONE.
				this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set process finish time to system timer.
				this.currentCPUProcess.calculateTurnaroundTime();			// Calculate process's turnaround time.
				
				this.updateJobQueue(this.currentCPUProcess);
				
				this.computeAvgWaitTime();								// Compute the avg. wait time of terminated processes.
				this.computeThroughput();
				this.computeAvgTurnaroundTime();
				
				String loggerInfo = "\nSystem Time: " + this.systemTimer + " -------------\n\n" 
						+ this.currentCPUProcess.getId() + " terminated.\n\n\n";
				System.out.println(loggerInfo);
				SimulatorClient.log.logger.info(loggerInfo);
				
				this.currentCPUProcess = null;
			}
		}
	}
	
	/**
	 * Executes I/O bursts specific to the scenario.
	 * If there is a current IO Process, decrement the remaining time in it's current burst.
	 * - Print the number of IO bursts remaining.
	 * - If that was the last of the I/O bursts in this I/O burst cycle...
	 *   - Increment the burst cycle to get next index of both cpuBurstList and ioBurstList.
	 *   - Update the remaining bursts to value in next index of cpuBurstList.
	 *   - Add this process to the ready queue.
	 *   - Remove this process from the IO queue.
	 */
	public void executeIO() {
		if (this.currentIOProcess != null) {
			this.currentIOProcess.decrementRemainingBursts();			// Decrement current burst in the cycle.
			
			this.updateJobQueue(this.currentIOProcess);
			
			if (this.currentIOProcess.getRemainingBursts() == 0) {		// If this was the last burst of the cycle...
				this.currentIOProcess.incrementBurstCycle();			// Set next burst cycle and reset current burst.
				this.currentIOProcess.setRemainingBursts(this.currentIOProcess.getCpuBurstList().get(
						this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);			// Move process to ready queue.
				this.currentIOProcess = null;
			}
		}
	}
	
	/**
	 * Update the status of a particular process in the Job Queue.
	 * @param process
	 */
	public void updateJobQueue(Process process) {
		for (Process p: this.jobQueue) {
			if (process == p) {
				p = process;
			}
		}
	}
	
	/**
	 * Increments the system timer by 1.
	 */
	public void incrementSystemTimer() {
		this.systemTimer++;
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
	}
	
	/**
	 * Iterate through the processes in the ioReadyQueue and add one
	 * to the value of the process ioWaitTime.
	 */
	public void incrementIoWaitTimes() {
		for (Process p: ioWaitQueue ) {
			p.incrementProcessIoWaitTime();
		}
	}
	
	/**
	 * Compute the average wait time by summing the wait times for all completed 
	 * processes and dividing the result by the total number of completed processes.
	 */
	public void computeAvgWaitTime() {
		float sum = 0;
		float numProcesses = 0;
		
		for (Process p: this.jobQueue) {
			if (p.isDone()) {
				numProcesses++;
				sum += p.getWaitTime();
			}
		}
		
		this.avgWaitTime = sum / numProcesses;
	}
	
	/**
	 * Compute the throughput by summing the finish times for all completed processes 
	 * and dividing the result by the total number of completed processes.
	 */
	public void computeThroughput() {
		float sum = 0;
		float numProcesses = 0;
		
		for (Process p: this.jobQueue) {
			if (p.isDone()) {
				numProcesses++;
				sum += p.getFinishTime();
			}
		}
		
		this.throughput = sum / numProcesses;
	}
	
	/**
	 * Compute the average turnaround time by summing the turnaround times for all completed
	 * processes and dividing the result by the total number of completed processes.
	 */
	public void computeAvgTurnaroundTime() {
		float sum = 0;
		float numProcesses = 0;
		
		for (Process p: this.jobQueue) {
			if (p.isDone()) {
				numProcesses++;
				sum += p.getTurnaroundTime();
			}
		}
		
		this.avgTurnaroundTime = sum / numProcesses;
	}
	
	/**
	 * Compute the CPU utilization by taking the total simulation time (system time - idle time) 
	 * and dividing the result by the total system time.
	 */
	public void computeCpuUtilization() {
		this.cpuUtilization = (this.systemTimer - this.idleTime) / (float)this.systemTimer;
	}
	
	
	// Abstract Methods
	/**
	 * Sorts the readyQueue for the specific scenario. Not implemented
	 * here because Scheduler is an abstract class, and each scenario
	 * will sort differently.
	 */
	public abstract void sort();
	
}
