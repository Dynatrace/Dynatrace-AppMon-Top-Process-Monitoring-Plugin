package br.com.compuware.topplugin;

public class ProcessWin extends ProcessInfo {

	/**
	 * Handles: The number of handles that the process has opened. Number of
	 * smart pointers a process has opened to storage areas in memory. When
	 * handles close, the memory is released.
	 */
	Integer Handles;

	/**
	 * NPM(K): The amount of non-paged memory that the process is using, in
	 * kilobytes. Non-paged pool is memory storage that is never paged to the
	 * hard disk, so it's more quickly accessible.
	 */
	int NonPageableMemory;

	/**
	 * PM(K): The amount of pageable memory that the process is using, in
	 * kilobytes. Paged pool may be sent to disk if space is required. This
	 * makes paged pool effectively larger than non-paged pool (since the
	 * storage is limited only by the page file on disk). Some memory reads may
	 * take longer because the data requested is now stored on disk.
	 */
	int PageableMemory;


	public ProcessWin() {
	}

	public ProcessWin(Integer iD, String processName, int cpuTime,
			int virtualMem, int handles, int nPM_k, int pM_k, int wS_k) {
		super(iD, processName, cpuTime, virtualMem);
		this.Handles = handles;
		NonPageableMemory = nPM_k;
		PageableMemory = pM_k;
		residentOrWorkingSetMemory = wS_k;
	}

	@Override
	public String toString() {
		return "ProcessWin [handles=" + Handles + ", NPM_k="
				+ NonPageableMemory + ", PM_k=" + PageableMemory + ", residentOrWorkingSetMemory="
				+ residentOrWorkingSetMemory + ", ID=" + ID + ", processName="
				+ processName + ", cpuTime=" + cpuTime + ", virtualMem="
				+ virtualMem + "]";
	}

}
