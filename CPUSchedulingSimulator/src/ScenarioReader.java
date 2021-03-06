/**
 * 
 * The ScenarioReader is part of a CPU scheduling simulator. The class
 * contains methods to read a file with a list of information about processes
 * and their arrival time, priority, cpu burst times, and IO burst times. The
 * class will create a linkedList of Process objects from the file to be used
 * by the Scheduler object.
 * 
 * 
 * @author Brian Steele
 * @author Cole Walsh
 * @author Allie Wolf
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ScenarioReader {
	
	// Data Members
	private BufferedReader br;
	private File file;
	
	// Constructor
	/**
	 * Constructor for the ScenarioReader
	 * @param fileName String containing the name of the file with process information.
	 */
	public ScenarioReader(File file) {
		super();
		this.file = file;
		try {
			this.br = new BufferedReader(new FileReader(this.file));
		} catch (FileNotFoundException e) {
			System.out.println("Cannot open file.");
			e.printStackTrace();
		}
	}
	
	// Getters and Setters
	
	
	// Methods
	/**
	 * Creates a list of Process objects based on the information in the 
	 * file with process information.
	 * @return
	 * @throws IOException
	 */
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
	
	
}
