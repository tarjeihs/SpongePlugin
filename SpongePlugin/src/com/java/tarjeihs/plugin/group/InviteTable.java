package com.java.tarjeihs.plugin.group;

import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;

public class InviteTable extends GroupHandler {
	
	public InviteTable(JPlugin instance) {
		super(instance);
	}
	
	public final void sendInvite(Player from, Player to) {
		executeUpdate("INSERT INTO invitetable (invited, inviter) VALUES (?, ?)", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString()
		});
	}
	
	public final void acceptInvite(Player from, Player to) {
		executeUpdate("DELETE FROM invitetable WHERE invited=? AND inviter=?", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString()
		});
		
		int groupId = getGroupId(from);
		
		String groupName = getGroupName(from);
		
		String groupOwner = getGroupOwner(from);
		
		GroupData groupData = new GroupData(groupName, groupOwner, groupId);
		
		loadGroup(to, groupData);
		
		becomeMember(to, from);
	}
	
	public final void declineInvite(Player from, Player to) {
		executeUpdate("DELETE FROM invitetable WHERE invited=? AND inviter=? AND groupId?", new Object[]{
				to.getUniqueId().toString(), from.getUniqueId().toString(), getGroupId(from)
		});
	}
}
