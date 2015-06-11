package com.java.tarjeihs.plugin.utilities;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
		for (int i = 100; i > lengthOfObject; i++) {
//			System.out.println(i + "/" + lengthOfObject);
			System.out.println(Math.min(i, 100));
		}
	}
	
	public static Player findPlayer(String name) {
		Collection<? extends Player> player = Bukkit.getOnlinePlayers();
		for (Player players : player) {
			if (players.getDisplayName().contains(name)) {
				return players;
			}
		}
		
		return null;
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s, 5);
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
