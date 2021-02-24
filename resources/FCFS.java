import java.util.TreeSet;

public class FCFS extends Algorithm {

	public FCFS(Process[] processes) {
		super(processes);

	}

	@Override
	protected void sort() {
		sorted_processes = new TreeSet<> (new ArriveTimeComparator());
		for(Process p : processes) {
		  sorted_processes.add(p);
		}
	}

	@Override
	protected void apply() {
	  GrantChart grantChart = new GrantChart();
	  time = 0;
	  total_waiting_time = 0;
	  average_waiting_time = 0;
	  sort();
	  
	  for (Process process : sorted_processes) {
		  grantChart.schedule(process);
		  grantChart.PassTime(process.getBurstTime());
	  }
	  grantChart.calculateWaitingTime();
	  grantChart.print();
	  average_waiting_time = grantChart.getTotal_waiting_time() / processes.length;
	}

	@Override
	protected void printResult() {
		if (sorted_processes == null) {
			apply();
		}
		
		System.out.println();
		System.out.println(Process.getHeader());
		
		for(Process process : sorted_processes) {
			System.out.println(process);
		}
		
		System.out.printf("%s Average waiting time: %.2f\n", getName(), average_waiting_time);
		
	}

}
