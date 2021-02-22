/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/21/2021
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
	private ArrayList<Integer> cpuBurstArray;
	private ArrayList<Integer> ioBurstArray;
	
	// Constructor
	public Process(String id, int arrivalTime, int priorityLevel, ArrayList<Integer> cpuBurstArray,
			ArrayList<Integer> ioBurstArray) {
		super();
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.cpuBurstArray = cpuBurstArray;
		this.ioBurstArray = ioBurstArray;
		this.isReady();
	}
	
	// Getters and Setters
	// id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	// state
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	// arrivalTime
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	// priorityLevel
	public int getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	// startTime
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	// finishTime
	public int getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	// waitTime
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	// ioWaitTime
	public int getIoWaitTime() {
		return ioWaitTime;
	}
	public void setIoWaitTime(int ioWaitTime) {
		this.ioWaitTime = ioWaitTime;
	}
	
	// turnaroundTime
	public int getTurnaroundTime() {
		return turnaroundTime;
	}
	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}
	
	// cpuBurstArray
	public ArrayList<Integer> getCpuBurstArray() {
		return cpuBurstArray;
	}
	public void setCpuBurstArray(ArrayList<Integer> cpuBurstArray) {
		this.cpuBurstArray = cpuBurstArray;
	}
	
	// ioBurstArray
	public ArrayList<Integer> getIoBurstArray() {
		return ioBurstArray;
	}
	public void setIoBurstArray(ArrayList<Integer> ioBurstArray) {
		this.ioBurstArray = ioBurstArray;
	}
	
	// toString
	@Override
	public String toString() {
		return "Process ID: " + id + 
				"\nState: " + state + 
				"\nArrival Time: " + arrivalTime + 
				"\nPriority: " + priorityLevel + 
				"\nCPU Bursts: " + cpuBurstArray + 
				"\nIO Bursts: " + ioBurstArray;
	}
	
	// Methods
	public void isReady() {
		this.state = "READY";
	}
	
	public void isRunning() {
		this.state = "RUNNING";
	}
	
	public void isWaiting() {
		this.state = "WAITING";
	}
	
}
