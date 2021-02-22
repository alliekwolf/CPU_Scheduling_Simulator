import java.util.ArrayList;

public class RoundRobin extends FCFS {
  private int quantum;
	public RoundRobin(Process[] processes, int quantum) {
		super(processes);
		this.quantum = quantum;
		
	}
	@Override
	
	protected void apply() {
		GrantChart grantChart = new GrantChart();
		ArrayList<Process> list;
		time = 0;
		total_waiting_time = 0;
		average_waiting_time = 0;
		sort();
		list = new ArrayList<>(sorted_processes);
		
		int index = 0;
		while(list.size() > 0) {
			Process process = list.get(index);
			grantChart.schedule(process);
			int processed_time = Math.min(process.remaining_burst_time, quantum);
			grantChart.PassTime(processed_time);
			process.remaining_burst_time -= processed_time;
			if(process.remaining_burst_time == 0) {
				list.remove(index);
				if(list.size() > 0) {
					index = index % list.size();
				}
			} else {
				index = (index + 1) % list.size();
			}
			
		}
		grantChart.calculateWaitingTime();
		grantChart.print();
		average_waiting_time = grantChart.getTotal_waiting_time() / processes.length;
	}
}
