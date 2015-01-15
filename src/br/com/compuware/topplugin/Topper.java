package br.com.compuware.topplugin;

import java.util.List;

public abstract class Topper {

	protected String username, password, host;

	public static Topper createTopper(String username, String password,
			String host, String os) {
		if (os.equals("Windows")) {
			return new WinTop(username, password, host);
		}else{
			return new LinuxTop(username, password, host);
		}
	}

	Topper(String username, String password, String host) {
		this.username = username;
		this.password = password;
		this.host = host;
	}

	abstract List<ProcessInfo> getProcessInfo();
}
