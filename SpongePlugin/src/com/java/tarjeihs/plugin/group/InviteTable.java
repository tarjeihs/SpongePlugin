package com.java.tarjeihs.plugin.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;

public class InviteTable extends GroupHandler {
	
	public InviteTable(JPlugin instance) {
		super(instance);
	}
	
	public void sendInvite(Player to, Player from) {
		int groupId = getGroupId(from);
		
		executeUpdate("INSERT INTO invitetable (invited, inviter, groupId) VALUES (?, ?, ?)", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString(), groupId
		});
	}
	
	public void acceptInvite(Player to, int groupId) {
		executeUpdate("DELETE FROM invitetable WHERE invited=?", new Object[]{
				to.getUniqueId().toString()
		});
	}
	
	public void declineInvite(Player to, int groupId) {
		executeUpdate("DELETE FROM invitetable WHERE invited=? AND groupId=?", new Object[]{
				to.getUniqueId().toString(), groupId
		});
	}
	
	public boolean hasInviteFrom(Player player, int groupId) {
		String query = "SELECT groupId FROM invitetable WHERE invited=?";
		
		int id = Integer.parseInt(get(query, new Object[]{
				player.getUniqueId().toString()
		}, new Object[]{
				"groupId"
		}));
		
		if (id == groupId) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<String> getInvites(Player player) {
		String query = "SELECT * FROM invitetable WHERE invited=?";
		Connection conn = null;
		ResultSet rs = null;
		ArrayList<String> invites = new ArrayList<String>();
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
								
			ps.setString(1, player.getUniqueId().toString());
								
			rs = ps.executeQuery();
			while (rs.next()) {
				invites.add(rs.getString("inviter") + ":" + rs.getInt("groupId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invites;
	}
}
