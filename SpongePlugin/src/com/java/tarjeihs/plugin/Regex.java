package com.java.tarjeihs.plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static void regexChecher(String regex, String str2C) {
		Pattern checkRegex = Pattern.compile(regex);

		Matcher regexMatcher = checkRegex.matcher(str2C);
		while (regexMatcher.find()) {
			regexMatcher.group().length();
		}
	}

	public static void println(String message) {
		System.out.println("[serr.no] " + message + "\n");
	}

	public static void printf(String message, String format) {
	}
}
