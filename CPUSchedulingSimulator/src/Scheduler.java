/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/21/2021
 */

import java.util.ArrayList;
import java.util.Queue;


public class Scheduler {
	
	//Data members
	int systemTimer;
	float cpuUtilization;
	float throughPut;
	float avgWaitTime;
	float avgResponseTime;
	int simulationMode;
	float simulationTime;
	int quantumTimeSlice;
	//Algorithm selectedAlgorithm;
	Process currentCPUProcess;
	Process currentIOProcess;
	ScenarioReader sReader;
	Queue readyQueue;
	Queue ioWaitQueue;
	
	// Constructor
	public Scheduler(int simulationMode,
					int quantumTimeSlice,
					//Algorithm selectedAlgorithm,
					ScenarioReader sReader) {
		
		this.simulationMode = simulationMode;
		this.quantumTimeSlice = quantumTimeSlice;
		//this.selectedAlgorithm = selectedAlgorithm;
		this.sReader = sReader;
		}

	/**
	 * @return the simulationMode
	 */
	public int getSimulationMode() {
		return simulationMode;
	}

	/**
	 * @param simulationMode the simulationMode to set
	 */
	public void setSimulationMode(int simulationMode) {
		this.simulationMode = simulationMode;
	}

	/**
	 * @return the simulationTime
	 */
	public float getSimulationTime() {
		return simulationTime;
	}

	/**
	 * @param simulationTime the simulationTime to set
	 */
	public void setSimulationTime(float simulationTime) {
		this.simulationTime = simulationTime;
	}

	/**
	 * @return the quantumTimeSlice
	 */
	public int getQuantumTimeSlice() {
		return quantumTimeSlice;
	}

	/**
	 * @param quantumTimeSlice the quantumTimeSlice to set
	 */
	public void setQuantumTimeSlice(int quantumTimeSlice) {
		this.quantumTimeSlice = quantumTimeSlice;
	}

	/**
	 * @return the currentCPUProcess
	 */
	public Process getCurrentCPUProcess() {
		return currentCPUProcess;
	}

	/**
	 * @param currentCPUProcess the currentCPUProcess to set
	 */
	public void setCurrentCPUProcess(Process currentCPUProcess) {
		this.currentCPUProcess = currentCPUProcess;
	}

	/**
	 * @return the currentIOProcess
	 */
	public Process getCurrentIOProcess() {
		return currentIOProcess;
	}

	/**
	 * @param currentIOProcess the currentIOProcess to set
	 */
	public void setCurrentIOProcess(Process currentIOProcess) {
		this.currentIOProcess = currentIOProcess;
	}

	/**
	 * @return the cpuUtilization
	 */
	public float getCpuUtilization() {
		return cpuUtilization;
	}

	/**
	 * @return the throughPut
	 */
	public float getThroughPut() {
		return throughPut;
	}

	/**
	 * @return the avgWaitTime
	 */
	public float getAvgWaitTime() {
		return avgWaitTime;
	}

	/**
	 * @return the avgResponseTime
	 */
	public float getAvgResponseTime() {
		return avgResponseTime;
	}
	
	
	
}


