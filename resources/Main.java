
public class Main {
	
	public static void main(String[] args) {
		Process[] processes = new Process[4];
		processes[0] = new Process(1, 10, 0, 1); //id, burst_time, arriving_time, priority
		processes[1] = new Process(2, 2, 1, 2); //id, burst_time, arriving_time, priority
		processes[2] = new Process(3, 3, 2, 1); //id, burst_time, arriving_time, priority
		processes[3] = new Process(4, 2, 5, 3); //id, burst_time, arriving_time, priority
		
		FCFS fcfs = new FCFS(processes);
		fcfs.printResult();
		
		SJF sjf = new SJF(processes);
		sjf.printResult();
		
		SJFPreemptive sjfp = new SJFPreemptive(processes);
		sjfp.printResult();
		
		RoundRobin roundRobin = new RoundRobin(processes, 2); //quantum = 2
		roundRobin.printResult();
		
		Priority priority = new Priority(processes);
		priority.printResult();
	}

}
