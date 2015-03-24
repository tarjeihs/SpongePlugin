package com.java.tarjeihs.plugin.utilities;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.group.GroupHandler;

public class Messenger {
	
	private static JPlugin plugin;
	
	public static HashMap<Player, Integer> spamFilter = new HashMap<Player, Integer>();
		
	public Messenger(JPlugin instance) {
		plugin = instance;
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
		if (!spamFilter.containsKey(player)) {
			spamFilter.put(player, (int) System.currentTimeMillis());
			return false;
		}
		
		int sec = (int) System.currentTimeMillis();
		
		if (sec - spamFilter.get(player) <= 2500) {
			player.sendMessage("Altfor mange meldinger de 3~ siste sekundene.");
			return true;
		} else {
			spamFilter.remove(player);
			return false;
		}
	}
	
	public static String filterCodeMessage(String message) {
		String returnMessage = message;
		
		// TODO work here!!!
		
		return returnMessage;
	}
	
	public static void sendGroupMessage(Player player, String[] msg) {
		GroupHandler groupHandler = plugin.getGroupHandler();
		
		int groupId = groupHandler.getGroupID(player);
		
		String groupName = groupHandler.getGroupData(groupId).getGroupName();
		
		for (String members : groupHandler.getGroupData(groupId).getGroupMembers()) {
			Player player_ = Regex.findPlayer(members);
			
			if (player_ != null)
				
			player_.sendMessage(ChatColor.GREEN + "(" + ChatColor.WHITE + groupName +
					ChatColor.GREEN + ") "+ ChatColor.WHITE + toMessage(1, msg));
		}
	}
	
	public static void sendGroupMessage(Player player, String msg) {
		GroupHandler groupHandler = plugin.getGroupHandler();
		
		int groupId = groupHandler.getGroupID(player);
				
		String groupName = groupHandler.getGroupData(groupId).getGroupName();
		
		for (String loop : groupHandler.getGroupData(groupId).getGroupMembers()) {
			Player player_ = Regex.findPlayer(loop);
			
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
