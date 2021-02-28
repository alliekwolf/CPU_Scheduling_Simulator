/**
 * 
 * The Process class simulates a process control block for a CPU
 * process schedule simulation. It also acts as a container for information
 * about the processes run in the simulator, including things like wait time
 * and turn around time.
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

import java.util.ArrayList;

public class Process {
	
	// Data Members
	private String id;
	private String state;
	private int arrivalTime;
	private int priorityLevel;
	private int startTime;
	private int finishTime;
	private int waitTime;
	private int ioWaitTime;
	private int turnaroundTime;
	private int burstCycle;
	private int currentBurst;
	private ArrayList<Integer> cpuBurstList;
	private ArrayList<Integer> ioBurstList;
	
	
	// Constructor
	/**
	 * Constructor for the Process class.
	 * 
	 * @param id String holds the process name
	 * @param arrivalTime int contains the time slice in the simulation in which the process
	 * 			arrives at the CPU.
	 * @param priorityLevel int contains the priority level of the process, with lower values 
	 * 			indicating a higher priority.
	 * @param cpuBurstList int array of values for the CPU bursts in a process.
	 * @param ioBurstList int array of values for the IO bursts in a process.
	 */
	public Process(String id, int arrivalTime, int priorityLevel, ArrayList<Integer> cpuBurstList,
			ArrayList<Integer> ioBurstList) {
		super();
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.cpuBurstList = cpuBurstList;
		this.ioBurstList = ioBurstList;
		this.burstCycle = 0;
		this.currentBurst = this.cpuBurstList.get(this.burstCycle);
		this.isWaiting();
	}
	
	
	// Getters and Setters
	/**
	 * Get the id of the process
	 * @return String id, the name of the process
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set the id (name) of the process.
	 * @param id String holding the id of the process.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Get the state of the process. The returned value should be one of
	 * 			the following: ["READY", "RUNNING", "WAITING"]
	 * @return String containing the state of the process.
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Get the time slice when the Process arrives at the CPU.
	 * @return int containing time slice at which the Process arrives at the CPU.
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Set the time slice at which the Process arrives at the CPU.
	 * @param arrivalTime int containing time slice at which the Process arrives at the CPU.
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Get the priority level of the Process, lower value indicates higher priority.
	 * @return int containing priority level of the Process.
	 */
	public int getPriorityLevel() {
		return priorityLevel;
	}
	
	/**
	 * Set the priority level of the Process, lower value indicates higher priority.
	 * @param priorityLevel int containing priority level of the Process.
	 */
	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	/**
	 * Get the time slice when the Process first begins to run. This may not be the same as
	 * the arrival time, given that a priority algorithm may make a Process wait in the ready 
	 * queue after its arrival time.
	 * @return int containing the time slice when the Process first begins to run.
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * Set the time slice when the Process first begins to run. This may not be the same as
	 * the arrival time, given that a priority algorithm may make a Process wait in the ready 
	 * queue after its arrival time.
	 * @param startTime int containing the time slice when the Process first begins to run.
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Get the time slice when the Process has completed running. This may be longer than
	 * the length of the CPU and IO bursts, especially during the round-robin algorithm.
	 * @return finishTime int containing the time slice when the Process completes running.
	 */
	public int getFinishTime() {
		return finishTime;
	}
	
	/**
	 * Set the time slice when the Process has completed running. This may be longer than
	 * the length of the CPU and IO bursts, especially during the round-robin algorithm.
	 * @param finishTime int containing the time slice when the Process completes running.
	 */
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	/**
	 * Get the accumulated time that the Process has been waiting for the CPU.
	 * @return int waitTime, the accumulated time that the Process has been waiting
	 * for the CPU.
	 */
	public int getWaitTime() {
		return waitTime;
	}
	
	/**
	 * Set the accumulated wait time that the Process has been waiting for the CPU.
	 * @param waitTime int the accumulated time that the process has been waiting for the CPU.
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * Get the accumulated time that the Process has been waiting in the IO queue.
	 * @return ioWaitTime int the accumulated time the Process has been waiting in the
	 * 			IO queue.
	 */
	public int getIoWaitTime() {
		return ioWaitTime;
	}
	
	/**
	 * Set the accumulated time that the Process has been waiting in the IO queue.
	 * @param ioWaitTime int the accumulated time the Process has been waiting in the 
	 * 			IO queue.
	 */
	public void setIoWaitTime(int ioWaitTime) {
		this.ioWaitTime = ioWaitTime;
	}
	
	/**
	 * Get the total time from the time the Process arrives to the time the Process finishes.
	 * In effect, getFinishTime - getStartTime.
	 * @return turnaroundTime int
	 */
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
	
	/**
	 * Set the total time from the time the Process arrives to the time the Process finishes.
	 * @param turnaroundTime int
	 */
	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}
	
	/**
	 * Gets which cycle of bursts the Process is ready to execute.
	 * @return burstCycle int
	 */
	public int getBurstCycle() {
		return burstCycle;
	}
	
	public int getCurrentBurst() {
		return currentBurst;
	}
	
	public void setCurrentBurst(int currentBurst) {
		this.currentBurst = currentBurst;
	}
	
	/**
	 * Gets an arrayList of Integers that contain the CPU bursts of a Process.
	 * @return cpuBurstList ArrayList<Integer>
	 */
	public ArrayList<Integer> getCpuBurstList() {
		return cpuBurstList;
	}
	
	/**
	 * Sets an arrayList of Integers that contain the CPU bursts of a Process.
	 * @param cpuBurstList ArrayList<Integer>
	 */
	public void setCpuBurstList(ArrayList<Integer> cpuBurstList) {
		this.cpuBurstList = cpuBurstList;
	}
	
	// ioBurstArray
	/**
	 * gets an arrayList of Integers that contain the IO bursts of a Process.
	 * @return ioBurstList ArrayList<Integer>
	 */
	public ArrayList<Integer> getIoBurstList() {
		return ioBurstList;
	}
	
	/**
	 * Sets an arrayList of Integers that contain the IO bursts of a Process.
	 * @param ioBurstList ArrayList<Integer>
	 */
	public void setIoBurstList(ArrayList<Integer> ioBurstList) {
		this.ioBurstList = ioBurstList;
	}
	
	// toString
	/**
	 * Returns a String with readable information about a process, for debugging.
	 * @return String
	 */
	@Override
	public String toString() {
		return "Process ID: " + id + 
				"\nState: " + state + 
				"\nArrival Time: " + arrivalTime + 
				"\nPriority: " + priorityLevel + 
				"\nCPU Bursts: " + cpuBurstList + 
				"\nIO Bursts: " + ioBurstList;
	}
	
	// Methods
	/**
	 * Changes the state of the Process to "READY"
	 */
	public void isReady() {
		this.state = "READY";
	}
	
	/**
	 * Changes the state of the Process to "Running"
	 */
	public void isRunning() {
		this.state = "RUNNING";
	}
	
	/**
	 * Changes the state of the Process to "Waiting"
	 */
	public void isWaiting() {
		this.state = "WAITING";
	}
	
	/**
	 * Increments the Process burstCycle by one each time a cycle 
	 * of CPU bursts finishes.
	 */
	public void incrementBurstCycle() {
		this.burstCycle += 1;
	}
	
	/**
	 * Decrements the Process's current burst cycle by one each time 
	 * a burst is executed.
	 */
	public void decrementCurrentBurst() {
		if (this.currentBurst > 0) {
			--this.currentBurst;
		}
	}
}
