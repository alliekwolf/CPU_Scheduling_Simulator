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
	private int burstStep;
	private ArrayList<Integer> cpuBurstList;
	private ArrayList<Integer> ioBurstList;
	
	// Constructor
	public Process(String id, int arrivalTime, int priorityLevel, ArrayList<Integer> cpuBurstList,
			ArrayList<Integer> ioBurstList) {
		super();
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.cpuBurstList = cpuBurstList;
		this.ioBurstList = ioBurstList;
		this.burstStep = 0;
		this.isWaiting();
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
	
	// burstStep
	public int getBurstStep() {
		return burstStep;
	}
	public void setBurstStep(int burstStep) {
		this.burstStep = burstStep;
	}
	
	// cpuBurstArray
	public ArrayList<Integer> getCpuBurstList() {
		return cpuBurstList;
	}
	public void setCpuBurstList(ArrayList<Integer> cpuBurstList) {
		this.cpuBurstList = cpuBurstList;
	}
	
	// ioBurstArray
	public ArrayList<Integer> getIoBurstList() {
		return ioBurstList;
	}
	public void setIoBurstList(ArrayList<Integer> ioBurstList) {
		this.ioBurstList = ioBurstList;
	}
	
	// toString
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
