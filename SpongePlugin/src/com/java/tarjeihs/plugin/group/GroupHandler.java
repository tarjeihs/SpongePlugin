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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.Regex;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;

public class GroupHandler extends MySQLAccessor {
	
	private HashMap<Player, GroupData> groupData = new HashMap<Player, GroupData>();
		
	public GroupHandler(JPlugin instance) {
		super(instance);
	}
	
	public String getGroupName(Player player) {
		String query = "SELECT groupId FROM GROUPUSERTABLE WHERE uuid=?";

		int groupId = Integer.parseInt(get(query, new Object[] { player.getUniqueId().toString() }, new Object[] { "groupId" }));

		String query_ = "SELECT groupName FROM GROUPTABLE WHERE groupId=?";

		String groupName = get(query_, new Object[] {
			groupId
		}, new Object[] {
			"groupName" 
		});
	
		return groupName;
	}
	
	public int getGroupId(Player player) {
		String query = "SELECT groupId FROM GROUPUSERTABLE WHERE uuid=?";
		
		int id = Integer.parseInt(get(query, new Object[] {
					player.getUniqueId().toString() 
				}, new Object[] {
					"groupId" 
				}));
		
		return id;
	}
	
	public void kick(Player player) {
		String query = "REMOVE * FROM GROUPUSERTABLE WHERE uuid=?";
		
		executeUpdate(query, new Object[]{
			player.getUniqueId().toString()
		});
	}
	
	/**
	 * Player must be added in GroupData to be able to use this
	 * @param login Player who is logging in
	 */
	public void addMember(Player login) {
		int groupId = getGroupData(login).getGroupID();	// Must be in GroupData
	
		List<String> members = new ArrayList<String>();
		
		String query = "SELECT uuid FROM GROUPUSERTABLE WHERE groupId=?";
		
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
			this.getGroupData(login).setGroupMembers(s);
		}
	}
	
	public String getMembers(Player player) {
		int groupId = getGroupId(player);
		
		if (!(groupId > 0)) return null;
	
		List<String> members = getGroupData(player).getGroupMembers();
		StringBuilder builder = new StringBuilder();
		
		for (String x : members) {
			OfflinePlayer z = Bukkit.getOfflinePlayer(UUID.fromString(x));
			if (z != null) {
				if (x.length() == 1) {
					builder.append(z.getName());
				}
			
				builder.append(z.getName() + ", ");
			}
		}
		
		return (builder.toString() != null ? builder.substring(0, builder.length() -2) : "Fant ingen spillere");
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
	
	public String getGroupOwner(Player player) {
		String query = "SELECT groupId FROM GROUPUSERTABLE WHERE uuid=?";
		
		int groupId = Integer.parseInt(get(query, new Object[]{
				player.getUniqueId().toString()
		}, new Object[]{
				"groupId"
		}));
		
		String query_ = "SELECT groupOwner FROM GROUPTABLE WHERE groupId=?";
		
		String owner = get(query_, new Object[]{
				groupId
				}, new Object[]{
				"groupOwner" 
				});
		
		return owner;
	}
	
	public void becomeMember(Player player, Player inviter) {
		String query = "INSERT INTO GROUPUSERTABLE (uuid, groupId) VALUES (?, ?)";
		
		int groupId = getGroupId(inviter);
		
		executeUpdate(query, new Object[]{
				player.getUniqueId().toString(), groupId
		});
	}
	
	public void becomeOwner(Player player, String groupName) {
		int groupId = new Random().nextInt(10000);
		
		String query = "INSERT INTO GROUPTABLE (groupId, groupName, groupOwner) VALUES (?, ?, ?)";

		executeUpdate(query, new Object[]{
				groupId, groupName, player.getName()});
		
		GroupData groupData = new GroupData(groupName, player.getName(), groupId);
		
		loadGroup(player, groupData);
	
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
			GroupData groupData = new GroupData(getGroupName(players), getGroupOwner(players), getGroupId(players));
			loadGroup(players, groupData);
		}
		Regex.println("Players has been loaded. Amount of players: " + groupData.size());
	}
	
	public HashMap<Player, GroupData> getGroupData() {
		if (groupData == null) return new HashMap<Player, GroupData>();
		
		return groupData;
	}
	
	public GroupData getGroupData(Player player) {
		if (!groupData.containsKey(player)) {
			throw new NullPointerException("GroupData cannot be null");
		}
		return this.groupData.get(player);
	}
	
	public void loadGroup(Player player, GroupData groupData_) {
		if (groupData.containsKey(player) && groupData.containsValue(player)) {
			return;
		}
		this.groupData.put(player, groupData_);
		
		addMember(player);
	}
	
	public void unloadGroup(Player player) {
		if (!(groupData.containsKey(player) && groupData.containsValue(groupData.get(player)))) {
			return;
		}
	}
}
