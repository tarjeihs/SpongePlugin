package com.java.tarjeihs.plugin.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.md_5.bungee.api.ChatColor;

public class StoredCommand {
	
	private static final HashMap<String, String> commandData = new HashMap<String, String>();
	
	private static final ArrayList<String> commands = new ArrayList<String>();
		
	// No need for constructor
	
	public static void addCommand(String commandName, String value) {
		if (commandData.containsKey(commandName)) {
			return;
		}
		commandData.put(commandName, value);
	}
	
	public static final void clearCommands() {
		if (commandData == null) return;
		
		commandData.clear();
	}
	
	public static String listCommands() {		
		StringBuilder builder = new StringBuilder();
		
		int counter = 1;
		for (Entry<String, String> e : commandData.entrySet()) {
			
			if (counter <= 16) {
			
				builder.append(ChatColor.BLUE + "/" + ChatColor.AQUA + e.getKey() + " - " + e.getValue() + "\n");
				
				counter++;
			} else {
				
				page(ChatColor.BLUE + "/" + ChatColor.AQUA + e.getKey() + " - " + e.getValue() + "\n");
			}
		
		}
		return (counter <= 16 ? builder.toString() : builder.toString() + "\nSide 1 av 2");
	}
	
	private static final void page(String comment) {		
		commands.add(comment);
	}
	
	public static List<String> getOtherPage() {
		return commands;
	}
	
	public static HashMap<String, String> getStoredCommands() {
		return commandData;
	}
}
