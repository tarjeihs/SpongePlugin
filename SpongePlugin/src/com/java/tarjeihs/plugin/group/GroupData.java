package com.java.tarjeihs.plugin.group;

public class GroupData implements GroupAccessor {

	private long guid;
	
	private String groupName;

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String name) {
		this.groupName = name;
	}

	@Override
	public long getGroupID() {
		return guid;
	}

	@Override
	public void setGroupID(long guid) {
		this.guid = guid;
	}
}
