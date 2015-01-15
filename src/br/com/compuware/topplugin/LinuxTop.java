package br.com.compuware.topplugin;

/**
 * This program will demonstrate remote exec.
 *  $ CLASSPATH=.:../build javac Exec.java 
 *  $ CLASSPATH=.:../build java Exec
 * You will be asked username, hostname, displayname, passwd and command.
 * If everything works fine, given command will be invoked 
 * on the remote side and outputs will be printed out.
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class LinuxTop extends Topper {

	LinuxTop(String username, String password, String host) {
		super(username, password, host);
	}

	@Override
	public List<ProcessInfo> getProcessInfo() {
		Session session = null;
		Channel channel = null;
		BufferedReader br = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, 22);
			session.setPassword(password.getBytes());
			JSch.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			String command = "top -n 1 -b";
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
			br = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));
			boolean startReading = false;
			List<ProcessInfo> pis = new ArrayList<ProcessInfo>();
			while (!channel.isClosed() && session.isConnected()) {
				String s;
				while ((s = br.readLine().trim()) != null && br.ready()) {
					if (startReading) {
						String[] split = s.split(" ++");
						Integer procId = new Integer(split[0]);
						String procUser = split[1];
						int virt = convertMega(split[4]);
						int res = convertMega(split[5]);
						int shr = convertMega(split[6]);
						int cpuPer = new Double(split[8].replace(',', '.'))
								.intValue();
						int memPer = new Double(split[9].replace(',', '.'))
								.intValue();
						int cpuTime = convertTime(split[10]);
						String procCommand = s.substring(s
								.lastIndexOf(split[11]));
						ProcessInfo pi = new ProcessLnx(procId, procCommand,
								cpuTime, virt, shr, res, cpuPer, memPer,
								procUser);
						pis.add(pi);
					}
					if (s.contains("PID")) {
						startReading = true;
					}
				}
				if (channel.isClosed()) {
					br.close();
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			return pis;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (channel != null)
				channel.disconnect();
			if (session != null)
				session.disconnect();
		}
	}

	private int convertTime(String formattedTime) {
		int idx = formattedTime.indexOf(":");
		int minutes = new Integer(formattedTime.substring(0, idx));
		double seconds = new Double(formattedTime.substring(idx + 1));
		int time = (int) ((minutes * 60) + seconds);
		return time;
	}

	private int convertMega(String s) {
		s = s.replace(',', '.');
		int idxM = s.indexOf("m");
		int idxG = s.indexOf("g");
		double kB = 0;
		if (idxM > -1) {
			kB = new Double(s.substring(0, idxM)) * 1024;
		} else if (idxG > -1) {
			kB = new Double(s.substring(0, idxG)) * 1024 * 1024;
		} else {
			kB = new Double(s);
		}
		return (int) kB;
	}

	public static void main(String[] args) {
		System.out.println();
	}

}
