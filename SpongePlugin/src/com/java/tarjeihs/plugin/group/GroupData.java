package com.java.tarjeihs.plugin.group;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class GroupData {
	
	private String groupName;
	
	private UUID groupOwner;
	
	private List<String> members;
	
	private int groupId;

	public GroupData(String groupName, UUID groupOwner, int groupId) {		
		this.groupName = groupName;
		
		this.groupId = groupId;
		
		this.groupOwner = groupOwner;
		
		this.members = new ArrayList<String>();
	}
	/**
	 * 
	 * @return Name of the Group
	 */
	public String getGroupName() {
		return this.groupName;
	}
	
	public void setGroupName(String name) {
		this.groupName = name;
	}
	
	/**
	 * Generated ID for the group
	 * @return
	 */
	public int getGroupID() {
		return this.groupId;
	}
	
	public void setGroupID(int id) {
		this.groupId = id;
	}

	public List<String> getGroupMembers() {
		return members;
	}

	public void addGroupMember(String player) {
		this.members.add(player);
	}
	
	public void addAllMembers(LinkedList<String> list) {
		this.members.addAll(members);
	}

	public void removeGroupMember(String player) {
		this.members.remove(player);
	}

	public UUID getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(UUID owner) {
		this.groupOwner = owner;
	}
	
	public void deleteGroup() {
		this.groupId = 0;
		
		this.groupOwner = null;
		
		this.groupName = null;
	
		this.members.clear();
	}
}
