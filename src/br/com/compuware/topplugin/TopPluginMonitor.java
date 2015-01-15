/**
 * This template file was generated by dynaTrace client.
 * The dynaTrace community portal can be found here: http://community.dynatrace.com/
 * For information how to publish a plugin please visit http://community.dynatrace.com/plugins/contribute/
 **/

package br.com.compuware.topplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

import com.dynatrace.diagnostics.pdk.Monitor;
import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.pdk.MonitorMeasure;
import com.dynatrace.diagnostics.pdk.Plugin;
import com.dynatrace.diagnostics.pdk.Status;
import com.dynatrace.diagnostics.pdk.Status.StatusCode;
import com.dynatrace.diagnostics.pdk.TaskEnvironment;

public class TopPluginMonitor implements Monitor {

	private static final String METRICS_AVAILABLE_FOR_WINDOWS = "Metrics available for Windows";

	private static final String METRICS_AVAILABLE_FOR_BOTH_WINDOWS_AND_LINUX = "Metrics available for both Windows and Linux";

	private static final String METRICS_AVAILABLE_FOR_LINUX = "Metrics available for Linux";

	private static final Logger log = Logger.getLogger(TopPluginMonitor.class
			.getName());

	String username, password, host, os;

	int topMemory, topCPU;

	Topper t;

	/**
	 * Initializes the Plugin. This method is called in the following cases:
	 * <ul>
	 * <li>before <tt>execute</tt> is called the first time for this scheduled
	 * Plugin</li>
	 * <li>before the next <tt>execute</tt> if <tt>teardown</tt> was called
	 * after the last execution</li>
	 * </ul>
	 * <p>
	 * If the returned status is <tt>null</tt> or the status code is a
	 * non-success code then {@link Plugin#teardown() teardown()} will be called
	 * next.
	 * <p>
	 * Resources like sockets or files can be opened in this method.
	 * 
	 * @param env
	 *            the configured <tt>MonitorEnvironment</tt> for this Plugin;
	 *            contains subscribed measures, but <b>measurements will be
	 *            discarded</b>
	 * @see Plugin#teardown()
	 * @return a <tt>Status</tt> object that describes the result of the method
	 *         call
	 */
	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		username = env.getConfigString("ssh-username");
		password = env.getConfigPassword("ssh-password");
		host = env.getHost().getAddress();
		os = env.getConfigString("os");
		topCPU = env.getConfigLong("topCPU").intValue();
		topMemory = env.getConfigLong("topMemory").intValue();

		log.info("Login in to: " + host + "(" + os + ")");
		t = Topper.createTopper(username, password, host, os);

		/*
		 * validate if subscribed measures are valid for selected operating
		 * system
		 */
		for (MonitorMeasure mm : env.getMonitorMeasures()) {
			if (mm.getMetricGroupName().equals(METRICS_AVAILABLE_FOR_LINUX)
					&& os.equals("Windows"))
				return new Status(
						Status.StatusCode.ErrorInternalConfigurationProblem,
						"Subscribed Linux Measures for Windows host!",
						"Cannot subscribe Linux Measures for Windows host: "
								+ mm);
			else if (mm.getMetricGroupName().equals(
					METRICS_AVAILABLE_FOR_WINDOWS)
					&& os.equals("Linux"))
				new Status(Status.StatusCode.ErrorInternalConfigurationProblem,
						"Subscribed Windows Measures for Linux host!",
						"Cannot subscribe Windows Measures for Linux host: "
								+ mm);
		}
		return new Status(Status.StatusCode.Success);
	}

	/**
	 * Executes the Monitor Plugin to retrieve subscribed measures and store
	 * measurements.
	 * 
	 * <p>
	 * This method is called at the scheduled intervals. If the Plugin execution
	 * takes longer than the schedule interval, subsequent calls to
	 * {@link #execute(MonitorEnvironment)} will be skipped until this method
	 * returns. After the execution duration exceeds the schedule timeout,
	 * {@link TaskEnvironment#isStopped()} will return <tt>true</tt>. In this
	 * case execution should be stopped as soon as possible. If the Plugin
	 * ignores {@link TaskEnvironment#isStopped()} or fails to stop execution in
	 * a reasonable time frame, the execution thread will be stopped
	 * ungracefully which might lead to resource leaks!
	 * 
	 * @param env
	 *            a <tt>MonitorEnvironment</tt> object that contains the Plugin
	 *            configuration and subscribed measures. These
	 *            <tt>MonitorMeasure</tt>s can be used to store measurements.
	 * @return a <tt>Status</tt> object that describes the result of the method
	 *         call
	 */
	@Override
	public Status execute(MonitorEnvironment env) throws Exception {

		log.info("Subscribed measures: " + env.getMonitorMeasures());

		Collection<MonitorMeasure> monitorMeasures = env.getMonitorMeasures();

		Collection<ProcessInfo> relevant = new HashSet<ProcessInfo>();
		try {
			List<ProcessInfo> pis = t.getProcessInfo();
			relevant = new ArrayList<ProcessInfo>();
			relevant.addAll(filterTop(true, pis, topMemory));
			relevant.addAll(filterTop(false, pis, topCPU));
		} catch (Exception e) {
			return new Status(StatusCode.ErrorTargetServiceExecutionFailed,
					"Problem collecting processInfo",
					"Problem collecting processInfo: " + e, e);
		}
		for (MonitorMeasure subscribedMonitorMeasure : monitorMeasures) {
			for (ProcessInfo pi : relevant) {
				MonitorMeasure dm = env.createDynamicMeasure(
						subscribedMonitorMeasure, "ProcessName", pi.processName
								+ "[PID:" + pi.ID + "]");
				String mg = subscribedMonitorMeasure.getMetricGroupName();
				String m = subscribedMonitorMeasure.getMeasureName();
				dm.setValue(-1);

				if (mg.equals(METRICS_AVAILABLE_FOR_BOTH_WINDOWS_AND_LINUX)) {
					if (m.equals("CPUTime"))
						dm.setValue(pi.cpuTime);
					else if (m.equals("VirtualMemory"))
						dm.setValue(pi.virtualMem);
					else if (m.equals("ResidentOrWorkingSetMemory"))
						dm.setValue(pi.residentOrWorkingSetMemory);
				} else if (mg.equals(METRICS_AVAILABLE_FOR_WINDOWS)) {
					ProcessWin pw = (ProcessWin) pi;
					if (m.equals("Handles"))
						dm.setValue(pw.Handles);
					else if (m.equals("NonPageableMemory"))
						dm.setValue(pw.NonPageableMemory);
					else if (m.equals("PageableMemory"))
						dm.setValue(pw.PageableMemory);

				} else if (mg.equals(METRICS_AVAILABLE_FOR_LINUX)) {
					ProcessLnx pl = (ProcessLnx) pi;
					if (m.equals("CPU"))
						dm.setValue(pl.CPU);
					else if (m.equals("Memory"))
						dm.setValue(pl.Memory);
					else if (m.equals("SharedMemory"))
						dm.setValue(pl.SharedMemory);
				}
			}
		}
		return new Status(Status.StatusCode.Success);
	}

	List<ProcessInfo> filterTop(final boolean memOrCPU, List<ProcessInfo> all,
			int top) {
		List<ProcessInfo> copy = new ArrayList<ProcessInfo>(all);
		Collections.sort(copy, new Comparator<ProcessInfo>() {
			@Override
			public int compare(ProcessInfo o2, ProcessInfo o1) {
				return memOrCPU ? o1.residentOrWorkingSetMemory
						- o2.residentOrWorkingSetMemory : o1.cpuTime
						- o2.cpuTime;
			}
		});
		copy = copy.subList(0, top);
		log.info("TOP (" + top + ") " + (memOrCPU ? "mem" : "cpu") + ": "
				+ copy);
		return copy;
	}

	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
	}
}
