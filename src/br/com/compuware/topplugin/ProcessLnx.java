package br.com.compuware.topplugin;

public class ProcessLnx extends ProcessInfo {

	/*
	 * The amount of shared memory used by the task
	 */
	int SharedMemory;


	/*
	 * Percentage of CPU used
	 */
	int CPU;

	/*
	 * Percentage of Memory used
	 */
	int Memory;

	/*
	 * The user name of the task's owner.
	 */
	String user;

	public ProcessLnx(int iD, String processName, int cpuTime,
			int virtualMem, int shared, int res, int cpuPer,
			int memPer, String user) {
		super(iD, processName, cpuTime, virtualMem);
		this.SharedMemory = shared;
		this.residentOrWorkingSetMemory = res;
		this.CPU = cpuPer;
		this.Memory = memPer;
		this.user = user;
	}

	@Override
	public String toString() {
		return "ProcessLnx [shared=" + SharedMemory + ", residentOrWorkingSetMemory=" + residentOrWorkingSetMemory + ", cpuPer="
				+ CPU + ", memPer=" + Memory + ", user=" + user + ", ID="
				+ ID + ", processName=" + processName + ", cpuTime=" + cpuTime
				+ ", virtualMem=" + virtualMem + "]";
	}

}
