import java.util.SortedSet;

public abstract class Algorithm {
	protected Process[] processes;
	protected SortedSet<Process> sorted_processes;
	protected double total_waiting_time;
	protected double average_waiting_time;
	protected int time;
	
	public Algorithm(Process[] processes) {
		this.processes = processes;
		for (Process p : processes) {
			p.reset();
		}
		time = 0;
	}
	
	protected abstract void sort();
	protected abstract void apply();
	protected abstract void printResult();

	public String getName() {
		return getClass().getName();
	}
	
	
	
	
}
