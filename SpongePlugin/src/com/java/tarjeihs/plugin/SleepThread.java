package com.java.tarjeihs.plugin;

public class SleepThread {

	// In seconds
	public static final void sleep(int millis) {
		try {
			Thread.sleep(millis * 1000);
		} catch (InterruptedException e) {

		}
	}
	
}
