package com.java.tarjeihs.plugin.command.custom;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class GroupCommand extends CommandHandler {

	public GroupCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "group", description = "Kommando for gruppefunksjon", rankRequired = 1)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("info")) {
				if (groupHandler.hasGroup(player)) {
					int groupId = groupHandler.getGroupID(player);
					String groupName = groupHandler.getGroupData(groupId).getGroupName();
					String groupOwner = Bukkit.getPlayer(groupHandler.getGroupData(groupId).getGroupOwner()).getName();
					List<String> members = groupHandler.getGroupData(groupId).getGroupMembers();
					
					StringBuilder builder = new StringBuilder();
					
					player.sendMessage(BLUE + "GruppeID: " + WHITE + groupId);
					player.sendMessage(BLUE + "Gruppenavn: " + WHITE + groupName);
					player.sendMessage(BLUE + "Gruppeeier: " + WHITE + groupOwner);
					
					for (String s : members) {
						if (members.size() == 1) {
							builder.append(s);
						} else {
							builder.append(s + ", ");
						}
					}
					
					player.sendMessage(BLUE + "Medlemmer: " + WHITE + ((!(members.size() > 1)) ? builder.substring(builder.length() - 2) : builder.toString()));
					
					player.sendMessage("\n"); // Just to create some space
				} else {
					player.sendMessage(RED + "Du har ingen gruppe.");
				}
			} else if (args[0].equalsIgnoreCase("forlat")) {
				if (groupHandler.hasGroup(player)) {
					player.sendMessage(BLUE + "Du har forlatt gruppen din.");
														
					groupHandler.kickMember(player);
				} else {
					player.sendMessage(RED + "Du har ingen gruppe.");
				}
			}
		} else if (args.length > 1) {
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("ny")) {
					String name = args[1];
					if (!groupHandler.hasGroup(player)) {
						if (!groupHandler.groupExists(name)) {
							groupHandler.createGroup(name, player);
				
							player.sendMessage(GREEN + "Ny gruppe med navnet " + WHITE + name.toUpperCase() + GREEN + " har blitt laget.");
						} else {
							player.sendMessage(RED + "Det eksisterer allerede en gruppe med likens navn.");
						}
					} else {
						player.sendMessage(RED + "Du er allerede i en gruppe, bruk kommandoen \n/group forlat før du kan lage en ny gruppe.");
					}
				} else if (args[0].equalsIgnoreCase("kick")) {
					
				}
			}
		}
		return false;
	}
}
