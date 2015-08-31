package com.epsilon.tester;

import java.util.concurrent.locks.ReentrantLock;

public class MinMaxTimeoutIndentifier {

	private int min;
	private int max;
	
	private ReentrantLock l;
	
	private static MinMaxTimeoutIndentifier theInstance = null;
	
	public static MinMaxTimeoutIndentifier getInstance() {
		
		if (theInstance==null) theInstance = new MinMaxTimeoutIndentifier();
		
		return theInstance;
		
	}
	
	private MinMaxTimeoutIndentifier() {
		
		min = 0;
		max = 0;
		l = new ReentrantLock();
	}
	
	public void updateMinMax(int m) {
		
		l.lock();
		
		if (min==0 || m < min) min = m;
		
		if (m > max) max = m;
		
		l.unlock();
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}


}
