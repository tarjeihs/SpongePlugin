package com.java.tarjeihs.plugin.command.custom;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class GroupCommand extends CommandHandler {

	public GroupCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "group", description = "Kommando for gruppefunksjon", rankRequired = 2)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage(BLUE +"/group ny <navn>");
			player.sendMessage(BLUE +"/group inv <spiller>");
			player.sendMessage(BLUE +"/group godta <spiller>");
			player.sendMessage(BLUE +"/group avslå <spiller>");
			player.sendMessage(BLUE + "/group info");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("info")) {
				if (groupHandler.hasGroup(player)) {

					String groupName = groupHandler.getGroupData(player).getGroupName();

					String groupOwner = groupHandler.getGroupData(player).getGroupOwner();

					int groupId = (int) groupHandler.getGroupData(player).getGroupID();
					
					List<Player> members = groupHandler.getGroupData(player).getGroupMembers();

					StringBuilder builder = new StringBuilder();
					
					if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage("GruppeID: " + BLUE + groupId);
						player.sendMessage("Gruppenavn: " + BLUE + groupName);
						player.sendMessage("Gruppeeier: " + BLUE + groupOwner);
					
						for (Player players : members) {
							builder.append(players.getName() + BLUE + " " + ChatColor.WHITE);
						}
						
						player.sendMessage("Medlemmer: " + builder.toString());
					}
				} else {
					player.sendMessage(RED + "Du har ingen gruppe");
				}
			}
		} else if (args.length == 2) {
			Player victim = Bukkit.getPlayer(args[0]);
			if (args[0].equalsIgnoreCase("inv")) {
				inviterTable.sendInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("decline")) {
				inviterTable.declineInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("godta")) {
				inviterTable.acceptInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("ny")) {
				String name = args[1].toLowerCase();
				groupHandler.becomeOwner(player, name);
			
				player.sendMessage(BLUE + "Du har lagd en gruppe med navnet " + name + ".");
				player.sendMessage(BLUE + "GruppeID: " + groupHandler.getGroupData(player).getGroupID());
			}
		}
		return false;
	}
}
