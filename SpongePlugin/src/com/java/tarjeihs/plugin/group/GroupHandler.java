package com.java.tarjeihs.plugin.group;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;

public class GroupHandler extends MySQLAccessor {
	
	private HashMap<Player, GroupData> groupData = new HashMap<Player, GroupData>();
	
	public GroupHandler(JPlugin instance) {
		super(instance);
	}
	
	public String getGroupName(Player player) {
		String playerName = player.getName();
		String query = "";
		
		String groupName = get(query, new Object[]{playerName}, new Object[]{"groupName"});
	
		return groupName;
	}
	
	public int getGroupId(Player player) {
		String playerName = player.getName();
		String query = "";
		
		int id = Integer.parseInt(get(query, new Object[]{playerName}, new Object[]{"groupName"}));
		
		return id;
	}
	
	public boolean hasGroup(Player player) {
		String playerName = player.getName();
		String query = "SELECT * FROM group WHERE name=?";

		
		
		return false;
	}
	
	public void joinGroup(Player player, String groupName) {
		
	}
	
	class InviteTable {
		
		String name;
		
		int groupId;
		
		String from;
		
		InviteTable(String name, int groupId, String from) {
			
		}
	}
	
	public HashMap<Player, GroupData> getGroupData() {
		if (groupData == null) return new HashMap<Player, GroupData>();
		
		return groupData;
	}
	
	public GroupData getGroupData(Player player) {
		if (!(groupData.containsKey(player) && groupData.containsValue(player))) {
			return null;
		}
		return this.groupData.get(player);
	}
	
	public void loadGroup(Player player, GroupData groupData_) {
		if (groupData.containsKey(player) && groupData.containsValue(player)) {
			return;
		}
		this.groupData.put(player, groupData_);
	}
	
	public void unloadGroup(Player player) {
		if (!(groupData.containsKey(player) && groupData.containsValue(groupData.get(player)))) {
			return;
		}
		this.groupData.remove(player);
	}
}
