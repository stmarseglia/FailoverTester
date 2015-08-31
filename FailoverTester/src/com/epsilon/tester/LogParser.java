package com.epsilon.tester;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogParser {

	private MinMaxTimeoutIndentifier identifier;
	private String logPath;
	
	public LogParser(String logPath, MinMaxTimeoutIndentifier identifier) {
		
		this.identifier = identifier;
		this.logPath = logPath;
		
	}
	
	public void getDowntime() {
		String path = "c:\\work\\HAtests\\WC_01.txt";
		int min = 50;
		int max = 60;
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			
//			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(new File(path)));
			
			String readString;
			String minMsec;
			String maxMsec;
			
			while (true) {
				readString = reader.readLine();
				
				if (readString == null) break;
				
				if (readString.matches("timeout")) {
					System.out.println(readString);
//					if (readString.substring(readString.lastIndexOf("-")+1, readString.lastIndexOf(":")+1).compareTo("" + min) == 0);
//						minMsec = readString.split(":")[1].trim();
//					System.out.println(minMsec);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
}
