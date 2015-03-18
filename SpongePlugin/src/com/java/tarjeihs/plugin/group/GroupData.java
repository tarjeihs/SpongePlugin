package com.java.tarjeihs.plugin.group;

import java.util.ArrayList;
import java.util.List;

public class GroupData implements GroupAccessor {
	
	private String groupName;
	
	private String groupOwner;
	
	private List<String> members;
	
	private int groupId;

	public GroupData(String groupName, String groupOwner, int groupId) {		
		this.groupName = groupName;
		
		this.groupId = groupId;
		
		this.groupOwner = groupOwner;
		
		this.members = new ArrayList<String>();
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
	public List<String> getGroupMembers() {
		return members;
	}

	@Override
	public void setGroupMembers(String player) {
		this.members.add(player);
	}

	@Override
	public void removeGroupMember(String player) {
		this.members.remove(player);
	}
}
