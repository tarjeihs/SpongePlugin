package com.java.tarjeihs.plugin.command;

import java.util.HashMap;
import java.util.Map.Entry;

import net.md_5.bungee.api.ChatColor;

public class StoredCommand {
	
	private static final HashMap<String, String> commandData = new HashMap<String, String>();
	
	// No need for constructor
	
	public static void addCommand(String commandName, String value) {
		if (commandData.containsKey(commandName)) {
			return;
		}
		commandData.put(commandName, value);
	}
	
	public static void clearCommands() {
		if (commandData == null) return;
		
		commandData.clear();
	}
	
	public static String listCommands() {		
		StringBuilder builder = new StringBuilder();
		
		for (Entry<String, String> e : commandData.entrySet()) {
			builder.append(ChatColor.BLUE + "/" + ChatColor.AQUA + e.getKey() + " - " + e.getValue() + "\n");
		}		
	
		return builder.toString();
	}
	
	public static HashMap<String, String> getStoredCommands() {
		return commandData;
	}
}
