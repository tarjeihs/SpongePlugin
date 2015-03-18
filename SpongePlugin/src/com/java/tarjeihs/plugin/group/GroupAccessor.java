package com.java.tarjeihs.plugin.group;

import java.util.List;

public interface GroupAccessor {
	
	final int groupOwnerID = 1;
	
	int getGroupID();
	
	void setGroupID(int id);
	
	String getGroupName();
	
	void setGroupName(String name);	
	
	String getGroupOwner();
	
	void setGroupOwner(String owner);
	
	List<String> getGroupMembers();
	
	void setGroupMembers(String player);
	
	void removeGroupMember(String player);
	
}
