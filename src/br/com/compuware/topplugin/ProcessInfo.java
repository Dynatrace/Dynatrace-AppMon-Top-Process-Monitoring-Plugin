package br.com.compuware.topplugin;

public abstract class ProcessInfo {

	/**
	 * The process ID (PID) of the process. Unique identifier for a given
	 * process. Even on a shared computer, only one process will have a given
	 * process ID.
	 */
	int ID;
	/**
	 * ProcessName: The name of the process.
	 */
	String processName;

	/**
	 * CPU(s): The amount of processor time that the process has used on all
	 * processors, in seconds.
	 */
	int cpuTime;

	/**
	 * VM(M): The amount of virtual memory that the process is using, in
	 * kilobytes. Virtual memory includes storage in the paging files on disk.
	 * The amount of virtual memory committed for the process's sole use.
	 */
	int virtualMem;

	/**
	 * Resident task size (Linux) or WorkingSet(windows): The size of the memory
	 * allocated by the process, in kilobytes. The working set consists of the
	 * pages of memory that were recently referenced by the process. The working
	 * set is the set of pages of physical memory that a process is using. Only
	 * data stored in physical memory (not currently paged to disk) is in the
	 * working set.
	 */
	int residentOrWorkingSetMemory;

	protected ProcessInfo() {
	}

	protected ProcessInfo(int iD, String processName, int cpuTime,
			int virtualMem) {
		ID = iD;
		this.processName = processName;
		this.cpuTime = cpuTime;
		this.virtualMem = virtualMem;
	}

}
