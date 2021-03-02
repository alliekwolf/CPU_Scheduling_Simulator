import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Priority extends Scheduler implements Comparator<Process> {
	
	// Constructor
	public Priority() {
		super();
	}
	
	// Scheduler Class Methods
	
	@Override
	public void executeCPU() {
		if (this.currentCPUProcess != null) {
			this.currentCPUProcess.decrementRemainingBursts();
			
			// Output current burst of cycle.
			System.out.println("CPU burst: " + (this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()) - this.currentCPUProcess.getRemainingBursts())
								+ " of " + this.currentCPUProcess.getCpuBurstList().get(this.currentCPUProcess.getBurstCycle()));
			
			this.executeIO();
			
			// If current burst reaches 0 and there are more burst cycles to go, move Process to I/O wait queue.
			// Else, if current burst reaches 0 and there are NO MORE burst cycles to go, Process is finished.
			if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() < currentCPUProcess.getCpuBurstList().size() - 1)) {
				this.addToIoWaitQueue(this.currentCPUProcess);				// Add process to I/O wait queue.
				this.currentCPUProcess = null;
			} else if (this.currentCPUProcess.getRemainingBursts() == 0 && (this.currentCPUProcess.getBurstCycle() == currentCPUProcess.getCpuBurstList().size() - 1)) {
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
			this.currentIOProcess.decrementRemainingBursts();			// Decrement current burst in the cycle.
			
			// Output current burst of cycle.
			System.out.println("I/O burst: " + (this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()) - this.currentIOProcess.getRemainingBursts())
								+ " of " + this.currentIOProcess.getIoBurstList().get(this.currentIOProcess.getBurstCycle()));
			
			if (this.currentIOProcess.getRemainingBursts() == 0) {		// If this was the last burst of the cycle...
				this.currentIOProcess.incrementBurstCycle();		// Set next burst cycle and reset current burst.
				this.currentIOProcess.setRemainingBursts(this.currentIOProcess.getCpuBurstList().get(
						this.currentIOProcess.getBurstCycle()));
				this.addToReadyQueue(this.currentIOProcess);		// Move process to ready queue.
				this.currentIOProcess = null;
			}
		}
	}
	
	@Override
	public void sort() {
		LinkedList<Process> q = (LinkedList<Process>)this.getReadyQueue();
		Comparator<Process> c = new Priority();
		Collections.sort(q, c);
		this.setReadyQueue(q);
	}
	
	// Comparator Class Method
	
	@Override
	public int compare(Process o1, Process o2) {
		int difference = o1.getPriorityLevel() - o2.getPriorityLevel();
		return difference;
	}
	

}
