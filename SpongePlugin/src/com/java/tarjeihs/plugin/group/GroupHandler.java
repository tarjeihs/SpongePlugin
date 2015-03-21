package com.java.tarjeihs.plugin.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import com.java.tarjeihs.plugin.utilities.Regex;

public class GroupHandler extends MySQLAccessor {
	
	private HashMap<Player, GroupData> groupData = new HashMap<Player, GroupData>();
	
	private InviteTable inviteTable;
		
	public GroupHandler(JPlugin instance) {
		super(instance);

		inviteTable = instance.getInviteTable();
	}
	
	public String getGroupName(int groupId) {
		String query = "SELECT groupName FROM GROUPTABLE WHERE groupId=?";

		String groupName = get(query, new Object[] {
			groupId
		}, new Object[] {
			"groupName"
		});
	
		return groupName;
	}
	
	public int getGroupId(Player player) {
		String query = "SELECT groupId FROM GROUPUSERTABLE WHERE uuid=?";
		
		int id = Integer.parseInt(get(query, new Object[]{
				player.getUniqueId().toString()
		}, new Object[]{
				"groupId"
		}));
		
		return id;
	}
	
	public void kickGroupMember(Player player) {		
		if (getGroupData(player).getGroupMembers().size() == 1) {
			String deleteGroup = "DELETE FROM grouptable WHERE groupId=?";
				
			executeUpdate(deleteGroup, new Object[]{
					getGroupData(player).getGroupID()
			});
			
			this.getGroupData().remove(player);
			
			return;
		}
		
		String query = "DELETE FROM groupusertable WHERE uuid=?";
		
		executeUpdate(query, new Object[]{
			player.getUniqueId().toString()
		});
		
		int groupId = getGroupData(player).getGroupID();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (getGroupData(players).getGroupID() == groupId) {
				getGroupData(players).removeGroupMember(player.getName());
			}
		}
		
		this.getGroupData().remove(player);
	}
	
	/**
	 * Player must be added in GroupData to be able to use this
	 * @param login Player who is logging in
	 */
	private void addDBGroupMembersToList(Player login) {
		int groupId = getGroupData(login).getGroupID();	// Must be in GroupData
	
		List<String> members = new ArrayList<String>();
		
		String query = "SELECT * FROM GROUPUSERTABLE WHERE groupId=?";
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, groupId);
				
			rs = ps.executeQuery();
			
			while (rs.next()) {
				members.add(rs.getString("uuid"));					
			}
		} catch (SQLException e) {
			Regex.println(e.getMessage());
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
		}
		
		for (String s : members) {
			String name = plugin.getUserHandler().getNameFromUUID(s);
			
			this.getGroupData(login).setGroupMembers(name);
		}
	}
	
	public String getGroupMembers(Player player) {
		int groupId = getGroupId(player);
		
		if (!(groupId > 0)) return null;
	
		List<String> members = getGroupData(player).getGroupMembers();
		StringBuilder builder = new StringBuilder();
		
		for (String x : members) {
			Player z = Bukkit.getPlayer(x);
			if (z != null) {
				if (x.length() == 1) {
					builder.append(z.getName());
				}
			
				builder.append(z.getName() + ", ");
			}
		}
		
		return builder.toString();
	}
	
	public boolean hasGroup(Player player) {
		String query = "SELECT uuid FROM GROUPUSERTABLE WHERE uuid=?";
		
		String uuid = get(query, new Object[]{
			player.getUniqueId().toString()
		}, new Object[]{
			"uuid" 
		});
		
		if (uuid == null) {
			return false;
		}
				
		if (uuid.equalsIgnoreCase(player.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isIdenticalName(String name) {
		String query = "SELECT groupName FROM GROUPTABLE WHERE groupName=?";
		
		String exists = get(query, new Object[]{
				name
		}, new Object[]{ "groupName"});
		
		if (exists == null) {
			return false;
		}
		
		if (exists.equalsIgnoreCase(query)) {
			return true; 
		} else {
			return false;
		}
	}
	
	public UUID getGroupOwner(int groupId) {
		String query_ = "SELECT groupOwner FROM GROUPTABLE WHERE groupId=?";
		
		String owner = get(query_, new Object[]{
				groupId
			}, new Object[]{
				"groupOwner" 
			});
		
		UUID uuid = UUID.fromString(owner);
		
		return uuid;
	}
	
	public void becomeMember(Player player, int groupId) {
		String query = "INSERT INTO GROUPUSERTABLE (uuid, groupId) VALUES (?, ?)";
				
		executeUpdate(query, new Object[]{
				player.getUniqueId().toString(), groupId
		});
		
		String groupName = getGroupName(groupId);
		
		UUID groupOwner = getGroupOwner(groupId);
		
		GroupData groupData = new GroupData(groupName, groupOwner, groupId);
		
		loadGroup(player, groupData);
		
		// Adding the new player to every other group member
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (hasGroup(players)) {
				if (getGroupData(players).getGroupID() == groupId) {
					getGroupData(players).setGroupMembers(player.getName());
				}
			}
		}
	}
	
	public void becomeOwner(Player player, String groupName) {
		int groupId = new Random().nextInt(999999);
		
		String query = "INSERT INTO GROUPTABLE (groupId, groupName, groupOwner) VALUES (?, ?, ?)";

		executeUpdate(query, new Object[]{
				groupId, groupName, player.getUniqueId().toString()});
		
		GroupData groupData = new GroupData(groupName, player.getUniqueId(), groupId);
		
		loadGroup(player, groupData);
		
		getGroupData(player).setGroupMembers(player.getName());
	
//		Adding user to GroupUserTable
		String query_ = "INSERT INTO GROUPUSERTABLE (uuid, groupId) VALUES (?, ?)";
		
		executeUpdate(query_, new Object[]{
				player.getUniqueId().toString(), groupId
		});
	}
	
	public void loadGroup(Collection<? extends Player> collection) {
		if ((collection.size() == 0) || (collection.size() <= 0)) {
			return;
		}

		for (Player players : collection) {
			if (hasGroup(players)) {
				int groupId = getGroupId(players);
			
				GroupData groupData = new GroupData(getGroupName(groupId), getGroupOwner(groupId), groupId);
				loadGroup(players, groupData);
			}
		}
		
		Regex.println("Groups has been loaded. Amount of players loaded into group: " + getGroupData().size() + "/" + collection.size());
	}
	
	public HashMap<Player, GroupData> getGroupData() {
		if (groupData == null) return new HashMap<Player, GroupData>();
		
		return groupData;
	}
	
	public GroupData getGroupData(Player player) {
//		if (!groupData.containsKey(player)) {
//			throw new NullPointerException("GroupData cannot be null");
//		}
		return this.groupData.get(player);
	}
	
	public InviteTable getInviteTable() {
		return (inviteTable != null ? inviteTable : null);
	}
	
	public void loadGroup(Player player, GroupData groupData_) {
		if (groupData.containsKey(player) && groupData.containsValue(groupData_)) {
			return;
		}
		this.groupData.put(player, groupData_);
	
		this.addDBGroupMembersToList(player);
	}
	
	public void unloadGroup(Player player) {
		if (!(groupData.containsKey(player) && groupData.containsValue(groupData.get(player)))) {
			return;
		}
		
		this.groupData.remove(player);
	}
}
