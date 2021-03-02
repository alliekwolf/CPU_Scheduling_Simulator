import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Priority extends Scheduler implements Comparator<Process> {
	
	public Priority() {
		this.systemTimer = 0;
		this.cpuUtilization = 0;
		this.throughput = 0;
		this.avgWaitTime = 0;
		this.avgResponseTime = 0;
		this.simulationMode = 0;
		this.simulationTime = 0;
		this.quantumTimeSlice = 0;
		this.currentCPUProcess = null;
		this.currentIOProcess = null;
		this.readyQueue = new LinkedList<Process>();
		this.ioWaitQueue = new LinkedList<Process>();
		this.jobQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
	}
	
	@Override
	public int compare(Process o1, Process o2) {
		int difference = o1.getPriorityLevel() - o2.getPriorityLevel();
		return difference;
	}
	
	@Override
	public void sort() {
		LinkedList<Process> q = (LinkedList<Process>)this.getReadyQueue();
		Comparator<Process> c = new Priority();
		Collections.sort(q, c);
		this.setReadyQueue(q);
	}
	
	@Override
	public void executeCPU() {
		if (this.currentCPUProcess != null) {
			this.currentCPUProcess.decrementCurrentBurst();
			System.out.println("CPU burst: " + this.currentCPUProcess.getCurrentBurst());
			
			this.executeIO();
			
			// If current burst reaches 0 and there are more burst cycles to go, move Process to I/O wait queue.
			// Else, if current burst reaches 0 and there are NO MORE burst cycles to go, Process is finished.
			if (this.currentCPUProcess.getCurrentBurst() == 0 && (this.currentCPUProcess.getBurstCycle() < currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
				this.currentCPUProcess = null;
			} else if (this.currentCPUProcess.getCurrentBurst() == 0 && (this.currentCPUProcess.getBurstCycle() == currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.currentCPUProcess.isDone();								// Set process state to NULL.
				this.currentCPUProcess.setFinishTime(this.systemTimer);		// Set finish time to system timer.
				this.terminatedProcesses.add(this.currentCPUProcess);		// Add to terminated processes. 
				
				System.out.println("** " + this.currentCPUProcess.getId() + " is finished. **");
				this.currentCPUProcess = null;
			}
		} else {						// If nothing is in the CPU, still check I/O 
			this.executeIO();			// to execute I/O burst.
		}
	}
	
	@Override
	public void executeIO() {
		if (this.currentIOProcess != null) {
			this.currentIOProcess.decrementCurrentBurst();			// Decrement current burst in the cycle.
			System.out.println("I/O burst: " + this.currentIOProcess.getCurrentBurst());
			if (this.currentIOProcess.getCurrentBurst() == 0) {		// If this was the last burst of the cycle...
				this.currentIOProcess.incrementBurstCycle();		// Set next burst cycle and reset current burst.
				this.currentIOProcess.setCurrentBurst(this.currentIOProcess.getCpuBurstList().get(
						this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);		// Move process to ready queue.
				this.currentIOProcess = null;
			}
		}
	}
	

}
