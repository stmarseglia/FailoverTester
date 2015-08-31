package com.epsilon.tester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantLock;

public class LogManager {

	private PrintWriter pw;
	private File logFile;
	private static LogManager theInstance;
	
	private ReentrantLock lock;
	
	public static LogManager getInstance() {
		
		if (theInstance==null) {
			System.out.println("No log file has been created. Specify a path first.");
			return null;
		}
		
		return theInstance;
		
	}
	
	public static LogManager getInstance(String logPath) {
		
		if (theInstance==null) {
			theInstance = new LogManager(logPath);
		}
		
		else System.out.println("LogFile has already been set.");
		
		return theInstance;
		
	}
	
	private LogManager(String logPath) {
		
		lock = new ReentrantLock(true);
		
		logFile = new File(logPath);
		
		try {	
			
			if (logFile.exists()) {
				logFile.delete();
				logFile.createNewFile();
			}
			pw = new PrintWriter(new FileOutputStream(logFile, true));
			pw.println("**************** LOGFILE ****************");
			writeLog("Log Started");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLog(String content) {
		
		lock.lock();

		pw.println(System.currentTimeMillis() + " - " + content);
		pw.flush();

		lock.unlock();
		
	}
	
	public void closeStream() {
		
		pw.close();
		
	}
	
}
