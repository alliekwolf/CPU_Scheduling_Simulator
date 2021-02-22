/*
 * Brian Steele, Cole Walsh, Allie Wolf
 * CS 405: Operating Systems
 * Project 2: CPU Scheduler
 * Date: 2/21/2021
 */

import java.io.IOException;
import java.util.ArrayList;

public class SimulatorClient {

	public static void main(String[] args) {
		
		// variables
		ScenarioReader sr = new ScenarioReader("scenarioData.txt");
		ArrayList<Process> processList = new ArrayList<Process>();
		
		try {
			processList = sr.createProcesses();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < processList.size(); i++) {
			System.out.println(processList.get(i) + "\n");
		}
		
	}

}
