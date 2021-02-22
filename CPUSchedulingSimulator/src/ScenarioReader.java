/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/21/2021
 */

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ScenarioReader {
	
	// Data Members
	private BufferedReader br;
	private String fileName;
	
	// Constructor
	public ScenarioReader(String fileName) {
		super();
		this.fileName = fileName;
		try {
			this.br = new BufferedReader(new FileReader(this.fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file.");
			e.printStackTrace();
		}
	}
	
	// Getters and Setters
	
	
	// Methods
	public ArrayList<Process> createProcesses() throws IOException {
		ArrayList<Process> processList = new ArrayList<Process>();
		String line;
		while ((line = br.readLine()) != null) {
			String id = "";
			int arrivalTime = -1;
			int priorityLevel = -1;
			ArrayList<Integer> cpuBurstArray = new ArrayList<Integer>();
			ArrayList<Integer> ioBurstArray = new ArrayList<Integer>();
			StringTokenizer input = new StringTokenizer(line);
			while (input.hasMoreTokens()) {
				id = input.nextToken();
				try {
					arrivalTime = Integer.parseInt(input.nextToken());
					priorityLevel = Integer.parseInt(input.nextToken());
					while (input.hasMoreTokens()) {
						cpuBurstArray.add(Integer.parseInt(input.nextToken()));
						if (input.hasMoreElements()) {
							ioBurstArray.add(Integer.parseInt(input.nextToken()));
						}
					}
				} catch (NumberFormatException ex) {
					System.out.println("** Number Format Error **");
					break;
				}
			}
			processList.add(new Process(id, arrivalTime, priorityLevel, cpuBurstArray, ioBurstArray));
		}
		return processList;
	}
	
	public void closeFile() {
		
	}
	
}
