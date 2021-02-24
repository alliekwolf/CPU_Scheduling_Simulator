import java.util.ArrayList;
import java.util.Arrays;

public class SJFPreemptive extends SJF {

	public SJFPreemptive(Process[] processes) {
		super(processes);
	}


	@Override
	protected void apply() {
	  ArrayList<Process> list = new ArrayList<>(Arrays.asList(processes));	
	  GrantChart grantChart = new GrantChart();
	  time = 0;
	  total_waiting_time = 0;
	  average_waiting_time = 0;
	  Process process = null;
	  
	  while(list.size() > 0) {
		  process = nextProcess(list, time);
		  grantChart.schedule(process);
		  process.remaining_burst_time--;
		  if (process.remaining_burst_time == 0) {
			  list.remove(process);
		  }
		  time++;
	  }
	  grantChart.calculateWaitingTime();
	  grantChart.print();
	  average_waiting_time = grantChart.getTotal_waiting_time() / processes.length;
	  
	}


	@Override
	public String getName() {
		return "SJF Preemptive";

	
		
	}

}
