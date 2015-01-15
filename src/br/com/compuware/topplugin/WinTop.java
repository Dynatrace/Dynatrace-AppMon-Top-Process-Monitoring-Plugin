package br.com.compuware.topplugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;

public class WinTop extends Topper {

	private static final Logger log = Logger.getLogger(WinTop.class.getName());

	/**
	 * Enable powershell on remote host: powershell enable-psremoting -force
	 */
	private static final String CMD = "powershell invoke-command -computer %s \"{get-process | fl ProcessName, ID, Handles, NPM, PM, WS, VM, CPU}\"";

	WinTop(String username, String password, String host) {
		super(username, password, host);
	}

	public static void main(String[] args) {
		System.out.println(new WinTop(null, null, "192.168.1.10")
				.getProcessInfo());
	}

	@Override
	public List<ProcessInfo> getProcessInfo() {
		ByteArrayOutputStream stdout = new ByteArrayOutputStream();

		ExecuteWatchdog ewd = new ExecuteWatchdog(15000);
		DefaultExecutor executor = new DefaultExecutor();
		try {
			String command = String.format(CMD, host);
			CommandLine cmdLine = CommandLine.parse(command);
			executor.setStreamHandler(new PumpStreamHandler(stdout));
			executor.setWatchdog(ewd);
			executor.execute(cmdLine);
			log.info("Executed process: " + command);
			StringReader sr = new StringReader(stdout.toString());
			BufferedReader stdInput = new BufferedReader(sr);
			ArrayList<ProcessInfo> pis = new ArrayList<ProcessInfo>();
			String s;
			while ((s = stdInput.readLine()) != null) {
				if (s.contains("ProcessName")) {
					ProcessWin pw = new ProcessWin();
					if (s.contains("Idle"))
						continue;
					pw.processName = s.substring(s.lastIndexOf(':') + 2);
					s = stdInput.readLine();
					pw.ID = (int) getDoubleValue(s);
					s = stdInput.readLine();
					pw.Handles = (int) getDoubleValue(s);
					s = stdInput.readLine();
					pw.NonPageableMemory = (int) getDoubleValue(s) / 1024;
					s = stdInput.readLine();
					pw.PageableMemory = (int) getDoubleValue(s) / 1024;
					s = stdInput.readLine();
					pw.residentOrWorkingSetMemory = (int) getDoubleValue(s) / 1024;
					s = stdInput.readLine();
					pw.virtualMem = (int) getDoubleValue(s) / 1024;
					s = stdInput.readLine();
					pw.cpuTime = (int) getDoubleValue(s);
					pis.add(pw);
				}
			}
			return pis;
		} catch (ExecuteException ee) {
			throw new RuntimeException(ewd.killedProcess() ? "Timeout!"
					: stdout.toString(), ee);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private double getDoubleValue(String s) {
		s = s.substring(s.lastIndexOf(' ')).replace(',', '.').trim();
		if (s.isEmpty()) {
			return 0;
		}
		return new Double(s);
	}

}
