package com.java.tarjeihs.plugin.command.custom;

import java.util.UUID;

import org.bukkit.Bukkit;
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
			
			player.sendMessage(YELLOW + "Tilgjengelige kommandoer:");
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
					
					if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage("GruppeID: " + BLUE + groupId);
						player.sendMessage("Gruppenavn: " + BLUE + groupName);
						player.sendMessage("Gruppeeier: " + BLUE + groupOwner);
						
						player.sendMessage("Medlemmer: " + groupHandler.getMembers(player));
					}
				} else {
					player.sendMessage(RED + "Du har ingen gruppe! " + BLUE + "/group ny <gruppe_navn>");
				}
			} else if (args[0].equalsIgnoreCase("invs")) {
				player.sendMessage(YELLOW + "Dine invitasjoner:");
				for (String inv : inviteTable.getInvites(player)) {
					Player player_ = Bukkit.getPlayer(UUID.fromString(inv));
					player.sendMessage(BLUE + player_.getName());
				}
			} else if (args[0].equalsIgnoreCase("forlat")) {
				player.sendMessage(RED + "Du har forlatt gruppen din.");
				groupHandler.kick(player);
			}
		} else if (args.length == 2) {
			Player victim = Bukkit.getPlayer(args[1]);
			if (args[0].equalsIgnoreCase("inv")) {
				if (groupHandler.hasGroup(victim)) {
					player.sendMessage(RED + victim.getName() + " er allerede i en gruppe.");
					return false;
				} else if (!groupHandler.hasGroup(player)) {
					player.sendMessage(RED + "Du må først lage en gruppe.");
				}
				inviteTable.sendInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("decline")) {
				player.sendMessage(RED + "Du avslo invitasjonen fra " + victim.getName() + ".");
				inviteTable.declineInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("godta")) {
				if (groupHandler.hasGroup(player)) {
					player.sendMessage(RED + "Du er allerede i en gruppe.");
					return false;
				}
//				inviteTable.acceptInvite(player, victim);
			} else if (args[0].equalsIgnoreCase("ny")) {
				if (groupHandler.hasGroup(player)) {
					player.sendMessage(RED + "Du er allerede i en gruppe,\ndu må forlate eller tilegne ny eier først.");
					return false;
				}
				
				String name = args[1].toLowerCase();
				groupHandler.becomeOwner(player, name);
			
				player.sendMessage(BLUE + "En ny gruppe med navnet " + name + " har blitt lagd.");
				player.sendMessage(BLUE + "GruppeID: " + groupHandler.getGroupData(player).getGroupID());
			}
		}
		return false;
	}
}
