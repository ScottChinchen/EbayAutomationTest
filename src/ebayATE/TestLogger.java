package ebayATE;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {

	private static LogLevel DefaultLogLevel = LogLevel.INFO;
	public static enum LogLevel {
		INFO, WARNING, ERROR
	}
	
	public static void setDefaultLogLevel(LogLevel l) {
		DefaultLogLevel = l;
	}
	
	public static void log(String msg) {
		log(DefaultLogLevel, msg);
	}
	
	public static void log(LogLevel l, String msg) {
		log(l, "", msg);
	}
	
	public static void log(LogLevel l, String module, String msg) {
		if (!module.isEmpty()) {
			module += ": ";
		}
		switch(l) {
		case INFO:
			Logger.getGlobal().log(Level.INFO, module + msg);
			break;
		case WARNING:
			Logger.getGlobal().log(Level.WARNING, module + msg);
			break;
		case ERROR:
			Logger.getGlobal().log(Level.SEVERE, module + msg);
			break;
		}
	}
	
}
