package com.java.tarjeihs.plugin.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	public static final void regexChecher(String regex, String str2C) {
		Pattern checkRegex = Pattern.compile(regex);

		Matcher regexMatcher = checkRegex.matcher(str2C);
		while (regexMatcher.find()) {
			regexMatcher.group().length();
		}
	}

	public static void println(String message) {
		System.out.println("[serr] " + message + "\n");
	}

	public static void printf(String message, String format) {
	
	}
	
	public static final void progress(int lengthOfObject) {
		for (int i = 0; i < lengthOfObject; i++) {
//			System.out.println(i + "/" + lengthOfObject);
			System.out.println(Math.min(i, 100));
		}
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,5);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
