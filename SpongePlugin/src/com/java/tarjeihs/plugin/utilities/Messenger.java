package com.java.tarjeihs.plugin.utilities;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.group.GroupHandler;

public class Messenger {
	
	private static JPlugin plugin;
	
	private static Map<String, Long> spamFilter;
	
	public Messenger(JPlugin instance) {
		plugin = instance;
		
		spamFilter = new HashMap<String, Long>();
	}

	public static String toMessage(int indexBegin, String[] msg) {
		StringBuilder builder = new StringBuilder();
		
		
		for (int i = indexBegin; i < msg.length; i++) {
			builder.append(msg[i] + " ");
		}
		
		return builder.toString();
	}

	public static void broadcast(String prefix, String[] msg) {
		Bukkit.broadcastMessage(prefix + " " + ChatColor.WHITE + toMessage(0, msg));
	}
	
	public static boolean spam(Player player){
		if (spamFilter.containsKey(player.getName())) {
			long last = spamFilter.get(player.getName());
			long time = System.currentTimeMillis() / 1000;
			long calculateDifference = time - last;
			
			String convert = Long.toString(calculateDifference);
			
			int difference = Integer.parseInt(convert);
			
			if (difference > 10) {
				return false;
			} else if (difference < 10) {
				spamFilter.remove(player.getName());
				return true;
				
			}
		} else {
			spamFilter.put(player.getName(), System.currentTimeMillis() / 1000);
		}
		return false;
	}
	
	public static void sendGroupMessage(Player player, String[] msg) {
		GroupHandler groupHandler = plugin.getGroupHandler();
		
		if (groupHandler.getGroupData(player) == null) {
			return;
		}
				
		String groupName = groupHandler.getGroupData(player).getGroupName();
		
		for (String loop : groupHandler.getGroupData(player).getGroupMembers()) {
			Player player_ = Bukkit.getPlayer(loop);
			
			if (player_ != null)
				
			player_.sendMessage(ChatColor.GREEN + "(" + ChatColor.WHITE + groupName +
					ChatColor.GREEN + ") "+ ChatColor.WHITE + toMessage(1, msg));
		}
	}
	
	public static void sendGroupMessage(Player player, String msg) {
		GroupHandler groupHandler = plugin.getGroupHandler();
		
		if (groupHandler.getGroupData(player) == null) {
			return;
		}
				
		String groupName = groupHandler.getGroupData(player).getGroupName();
		
		for (String loop : groupHandler.getGroupData(player).getGroupMembers()) {
			Player player_ = Bukkit.getPlayer(loop);
			
			if (player_ != null)
				
			player_.sendMessage(ChatColor.GREEN + "(" + ChatColor.WHITE + groupName +
					ChatColor.GREEN + ") "+ ChatColor.WHITE + msg);
		}
	}

	public static String removeChar(String msg, char remove) {
		StringBuilder builder = new StringBuilder();
		
		char[] x = msg.toCharArray();
		for (int i = 0; i < x.length; i++) {
			if (x[i] != remove) {
				builder.append(x);
			}
		}
		
		return builder.toString();
	}
}
