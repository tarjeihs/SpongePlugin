package com.java.tarjeihs.plugin.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;

public class GroupHandler extends MySQLAccessor {
	
	private final HashMap<Integer, GroupData> groupData;
		
	private InviteTable inviteTable;
	
	public GroupHandler(JPlugin instance) {
		super(instance);
		
		inviteTable = instance.getInviteTable();
		
		groupData = new HashMap<Integer, GroupData>();
	}
	
	public void createGroup(String name, Player player) {
//		Firstly we create a statement for inserting groupName and owner into table.
//		After that we create another statement for adding the user to subtable.
		String query = "INSERT INTO GROUPTABLE (groupName, groupOwner) VALUES (?, ?)";
		
		executeUpdate(query, new Object[]{
				name, 
				player.getUniqueId().toString()
		});
		
//		Now we want to recieve the groupID from our newest group
		String query_ = "SELECT id FROM GROUPTABLE WHERE groupOwner=?";
		
		int id = Integer.parseInt(get(query_, new Object[]{
				player.getUniqueId().toString()
		}, new Object[]{
				"id"
		}));
		
//		Since there is no instance of this data from before of reloading, we add it with the others
		GroupData newGroup = new GroupData(name, player.getUniqueId(), id);
	
		newGroup.addGroupMember(player.getName());
		
//		Add the group to the HashMap
		loadGroup(id, newGroup);
		
//		And ofcourse we have to add the user into their own defined table so we can gather h*s information about the group
		String query__ = "INSERT INTO GROUPUSERTABLE (uuid, groupId) VALUES (?, ?)";
		
		executeUpdate(query__, new Object[]{
				player.getUniqueId().toString(), id
		});
	}
	
	public boolean hasGroup(Player player) {
		String query = "SELECT uuid FROM GROUPUSERTABLE WHERE uuid=?";

		String uuid = get(query,
				new Object[] { player.getUniqueId().toString() },
				new Object[] { "uuid" });

		if (uuid == null) {
			return false;
		}

		if (uuid.equalsIgnoreCase(player.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean groupExists(String name) {
		String query = "SELECT groupName FROM GROUPTABLE WHERE groupName=?";
		
		String name_ = get(query, new Object[]{
				name
		}, new Object[]{
				"groupName"
		});
		
		if (name_ == null) {
			return false;
		}
		
		if (name_.equalsIgnoreCase(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void becomeMember(int groupId, Player player) {		
		String query = "INSERT INTO GROUPUSERTABLE (uuid, groupid) VALUES (?, ?)";
		
		executeUpdate(query, new Object[]{
				player.getUniqueId().toString(), groupId
		});
		
		// Now since the user has been added to the database, we can begin working on with the groupData
		
		getGroupData(groupId).addGroupMember(player.getName());
	}
	
	private LinkedList<String> getMembersFromDB(int groupId) {
		LinkedList<String> members = new LinkedList<String>();
		
		String query = "SELECT uuid FROM GROUPUSERTABLE WHERE groupId=?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try { // random comment
			conn = getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, groupId);
		
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				String nameFromUUID = plugin.getUserHandler().getNameFromUUID(UUID.fromString(rs.getString("uuid")).toString());
								
//				members.add((Bukkit.getPlayer(nameFromUUID) != null ? ChatColor.GREEN + nameFromUUID : ChatColor.RED + nameFromUUID));
				
				members.add(nameFromUUID);			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
		}
		
		Collections.sort(members);
		
		return members;
	}
	
	public String getGroupName(int groupId) {
		String query = "SELECT groupName FROM GROUPTABLE WHERE id=?";
	
		String groupName = get(query, new Object[]{
				groupId
		}, new Object[]{
				"groupName"
		});
		
		return groupName;
	}
	public void kickMember(Player player) {
		int groupId = getGroupID(player);
		
		if (getGroupData(groupId).getGroupMembers().size() == 1) {
			String deleteGroup = "DELETE FROM GROUPTABLE WHERE id=?";
			
			executeUpdate(deleteGroup, new Object[]{
					groupId
			});
			
			getGroupData(groupId).deleteGroup();
			
			delGroup(player, groupId);
		}
		
		String query = "DELETE FROM GROUPUSERTABLE WHERE uuid=?";
		
		executeUpdate(query, new Object[]{
				player.getUniqueId().toString()
		});
		
		getGroupData(groupId).removeGroupMember(player.getName());
	}
	
	public String getGroupOwner(Player player) {
		int id = getGroupID(player);
		
		String query = "SELECT groupOwner FROM GROUPTABLE WHERE id=?";
		
		String uuid = UUID.fromString(
				get(query, new Object[] {
						id }, 
					new Object[] {
						"groupOwner" }))
				.toString();
		
		return uuid;
	}
	

	public int getGroupID(Player player) {
		String query = "SELECT groupId FROM GROUPUSERTABLE WHERE uuid=?";
		
		int id = Integer.parseInt(get(query, new Object[]{
				player.getUniqueId().toString()
		}, new Object[]{		
				"groupId"
		}));
		
		return (id != 0 ? id : -1);
	}
	
	private class GroupLoader implements Runnable {
		public void run() {
			String query = "SELECT * FROM GROUPTABLE";
			
			int groupId = 0;
			GroupData groupData = null;
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
						
			try {
				conn = getConnection();
				ps = conn.prepareStatement(query);
				
				rs = ps.executeQuery();
				
				while (rs.next()) {
					String name = rs.getString("groupName");
					UUID owner = UUID.fromString(rs.getString("groupOwner"));
					groupId = rs.getInt("id");
					
					groupData = new GroupData(name,
							owner,
							groupId);
					

//					for (String members : getMembersFromDB(groupId)) {
//						groupData.addGroupMember(members);
//					}
				
					groupData.addAllMembers(getMembersFromDB(groupId));
					
					loadGroup(groupId, groupData);					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
				if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
			}
		}
	}
	
	public void loadGroups() {
		Thread thread = new Thread(new GroupLoader());
		synchronized (thread) {
			if (!thread.isAlive()) 
				thread.start();
		}
	}
	
//	public void loadGroups() {	
//		String query = "SELECT * FROM GROUPTABLE";
//		
//		int groupId = 0;
//		GroupData groupData = null;
//		
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		
//		try {
//			conn = getConnection();
//			ps = conn.prepareStatement(query);
//			
//			rs = ps.executeQuery();
//			
//			while (rs.next()) {
//				String name = rs.getString("groupName");
//				UUID owner = UUID.fromString(rs.getString("groupOwner"));
//				groupId = rs.getInt("id");
//				
//				groupData = new GroupData(name,
//						owner,
//						groupId);
//				
//				loadGroup(groupId, groupData);
//				
//				groupData.addAllMembers(getMembersFromDB(groupId));
//				
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
//			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
//		}
//	}
	
	public void loadGroup(int groupId, GroupData storageData) {
		if (groupData.containsKey(groupId) ||
				groupData.containsValue(storageData)) {
			return;
		}
		
		this.groupData.put(groupId, storageData);
	}
	
	
	/**
	 * Deletes group from Map
	 * @param player Owner of the group
	 * @param groupId ID of the group
	 */
	private void delGroup(Player player, int groupId) {
		this.groupData.remove(groupId);
	}
	
	/**
	 * 
	 * @param groupId The selected group
	 * @return Data of the group
	 */
	public GroupData getGroupData(int groupId) {
		return groupData.get(groupId);
	}

	/**
	 * 
	 * @return Instance of InviteTable
	 */
	public InviteTable getInviteTable() {
		return inviteTable;
	}
}