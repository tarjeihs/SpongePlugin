package com.java.tarjeihs.plugin.group;

import java.util.List;

import org.bukkit.entity.Player;

public interface GroupAccessor {
	
	final int groupOwnerID = 1;
	
	int getGroupID();
	
	void setGroupID(int id);
	
	String getGroupName();
	
	void setGroupName(String name);	
	
	String getGroupOwner();
	
	void setGroupOwner(String owner);
	
	List<Player> getGroupMembers();
	
	void setGroupMembers(Player player);
	
	void removeGroupMember(Player player);
	
}
