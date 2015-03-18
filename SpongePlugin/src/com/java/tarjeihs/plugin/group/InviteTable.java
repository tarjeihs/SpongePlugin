package com.java.tarjeihs.plugin.group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;

public class InviteTable extends GroupHandler {
	
	public InviteTable(JPlugin instance) {
		super(instance);
	}
	
	public void sendInvite(Player to, Player from) {
		executeUpdate("INSERT INTO invitetable (invited, inviter) VALUES (?, ?)", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString()
		});
	}
	
//	public void acceptInvite(Player to, int groupId) {
//		executeUpdate("DELETE FROM invitetable WHERE invited=? AND groupId=?", new Object[]{
//				to.getUniqueId().toString(), groupId
//		});
//		
//		String groupName = getGroupName(groupId);
//		
//		String groupOwner = getGroupOwner(groupId);
//		
//		GroupData groupData = new GroupData(groupName, groupOwner, groupId);
//		
//		loadGroup(to, groupData);
//		
//		becomeMember(to, groupId);
//	}
	
	public void declineInvite(Player to, Player from) {
		executeUpdate("DELETE FROM invitetable WHERE invited=? AND inviter=? AND groupId=?", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString(), getGroupId(from)
		});
	}
	
	public List<String> getInvites(Player player) {
		String query = "SELECT * FROM invitetable WHERE invited=?";
		Connection conn = null;
		ResultSet rs = null;
		List<String> invites = new ArrayList<String>();
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, player.getUniqueId().toString());
					
			rs = ps.executeQuery();
			
			while (rs.next()) {
				invites.add(rs.getString("inviter"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invites;
	}
}
