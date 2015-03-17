package com.java.tarjeihs.plugin.group;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GroupData implements GroupAccessor {
	
	private String groupName;
	
	private String groupOwner;
	
	private List<Player> members;
	
	private int groupId;

	public GroupData(String groupName, String groupOwner, int groupId) {		
		this.groupName = groupName;
		
		this.groupId = groupId;
		
		this.groupOwner = groupOwner;
		
		this.members = new ArrayList<Player>();
	}
	
	@Override
	public String getGroupName() {
		return this.groupName;
	}
	
	public int getGroupID() {
		return this.groupId;
	}
	
	@Override
	public void setGroupID(int id) {
		this.groupId = id;
	}
	
	@Override
	public void setGroupName(String name) {
		this.groupName = name;
	}

	@Override
	public String getGroupOwner() {
		return groupOwner;
	}

	@Override
	public void setGroupOwner(String owner) {
		this.groupOwner = owner;
	}
	
	public int getGroupOwnerID() {
		return groupOwnerID;
	}

	@Override
	public List<Player> getGroupMembers() {
		return members;
	}

	@Override
	public void setGroupMembers(Player player) {
		this.members.add(player);
	}

	@Override
	public void removeGroupMember(Player player) {
		this.members.remove(player);
	}
}
