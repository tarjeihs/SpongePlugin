package com.java.tarjeihs.plugin.command.custom;

import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.group.GroupData;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Messenger;
import com.java.tarjeihs.plugin.utilities.Regex;

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
					String groupOwner = Bukkit.getPlayer(groupHandler.getGroupData(player).getGroupOwner()).getName();
					int groupId = (int) groupHandler.getGroupData(player).getGroupID();
					
					if (args[0].equalsIgnoreCase("info")) {
						player.sendMessage("GruppeID: " + BLUE + groupId);
						player.sendMessage("Gruppenavn: " + BLUE + groupName);
						player.sendMessage("Gruppeeier: " + BLUE + groupOwner);
						
						player.sendMessage("Medlemmer: " + groupHandler.getGroupMembers(player));
					}
				} else {
					player.sendMessage(RED + "Du har ingen gruppe! " + BLUE + "Bruk: /group ny <gruppe_navn>");
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				if (inviteTable.getInvites(player).size() == 0) {
					player.sendMessage(RED + "Du har ingen gruppeinvitasjoner.");
					return false;
				}
				
				player.sendMessage(YELLOW + "Dine invitasjoner:");
				for (String inv : inviteTable.getInvites(player)) {
					UUID uuid = UUID.fromString((String) inv.split(":")[0]);
					OfflinePlayer player_ = Bukkit.getOfflinePlayer(uuid);
					player.sendMessage(BLUE + "Sendt fra: " + player_.getName());
					player.sendMessage(BLUE + "GruppeID: " + Integer.parseInt(inv.split(":")[1]));
					player.sendMessage(YELLOW + "----------------");
				}
			} else if (args[0].equalsIgnoreCase("forlat")) {
				player.sendMessage(RED + "Du har forlatt gruppen din.");
				if (groupHandler.hasGroup(player)) {
					groupHandler.kickGroupMember(player);
				} else {
					player.sendMessage(RED + "Du har ingen gruppe som du kan forlate.");
					return false;
				}
			}
		} else if (args.length > 1) {
			if (args[0].equalsIgnoreCase("msg")) {

				String groupName = groupHandler.getGroupData(player)
						.getGroupName();

				StringBuilder builder = new StringBuilder();

				for (int i = 1; i < args.length; i++) {
					builder.append(args[i] + " ");
				}

				for (String loop : groupHandler.getGroupData(player)
						.getGroupMembers()) {
					Player player_ = Bukkit.getPlayer(loop);

					if (player_ != null)

						player_.sendMessage(ChatColor.GREEN + "("
								+ ChatColor.WHITE + groupName + ChatColor.GREEN
								+ ") " + BLUE + player.getName() + ": " + ChatColor.WHITE + builder.toString());
				}
			}
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("inv")) {
					Player victim = Bukkit.getPlayer(args[1]);
					if (victim == null) {
						player.sendMessage(RED + "Spilleren er avlogget.");
						return false;
					}
					if (groupHandler.hasGroup(victim)) {
						player.sendMessage(RED + victim.getName() + " er allerede i en gruppe.");
						return false;
					} else if (!groupHandler.hasGroup(player)) {
						player.sendMessage(RED + "Du må først lage en gruppe.");
						return false;
					}
					
					if (victim == player) {
						player.sendMessage(RED + "Du er allerede i en gruppe, derfor kan du ikke inviterer deg selv.");
						return false;
					}
					
					victim.sendMessage(BLUE + "Du har mottatt en gruppeinvitasjon fra " + WHITE + player.getName() + BLUE + ".");
					victim.sendMessage(BLUE + "Skriv /group godta " + WHITE + groupHandler.getGroupData(player)
							.getGroupID() + BLUE + " for å godta invitasjonen.");
					player.sendMessage(BLUE + "Du har sendt en gruppeinvitasjon til " + WHITE + victim.getName() + BLUE + ".");
					inviteTable.sendInvite(victim, player);
				} else if (args[0].equalsIgnoreCase("decline")) {
					if (Regex.isInteger(args[1])) {
						int groupId = Integer.parseInt(args[1]);
						if (inviteTable.hasInviteFrom(player, groupId)) {
							inviteTable.declineInvite(player, groupId);
							
							String groupOwner = Bukkit.getPlayer(groupHandler.getGroupOwner(groupId)).getName();
							
							player.sendMessage(GREEN + "Du har avslått invitasjonen fra " + groupOwner + " sin gruppe.");
						} else {
							player.sendMessage(RED + "Du har ikke mottatt noen invitasjoner fra denne gruppen.");
						}
					}
				} else if (args[0].equalsIgnoreCase("godta")) {
					if (groupHandler.hasGroup(player)) {
						player.sendMessage(RED + "Du er allerede i en gruppe.");
						return false;
					}
					
					if (!Regex.isInteger(args[1])) {
						int id = Integer.parseInt(args[1]);
						if (inviteTable.hasInviteFrom(player, id)) {
							inviteTable.acceptInvite(player, id);
					
							String groupName = groupHandler.getGroupName(id);
							UUID groupOwner = groupHandler.getGroupOwner(id);
							
							GroupData groupData = new GroupData(groupName, groupOwner, id);
							groupHandler.loadGroup(player, groupData);
							
							groupHandler.becomeMember(player, id);
														
							Messenger.sendGroupMessage(player, player.getName() + BLUE + " har blitt med i gruppen.");
						} else {
							player.sendMessage(RED + "Du har ikke mottatt noen invitasjoner fra denne gruppen.");
						}
					} else {
						player.sendMessage(RED + "Ugyldig gruppeID. Prøv igjen.");
					}
					
				} else if (args[0].equalsIgnoreCase("ny")) {
					if (groupHandler.hasGroup(player)) {
						player.sendMessage(RED + "Du er allerede i en gruppe,\ndu må forlate eller tilegne en ny eier først.");
						return false;
					}
					
					if (groupHandler.isIdenticalName(args[1])) {
						player.sendMessage(RED + "En gruppe med likens navn eksisterer allerede.");
						return false;
					}
					
					String name = args[1].toLowerCase();
					groupHandler.becomeOwner(player, name);
				
					player.sendMessage(BLUE + "En ny gruppe med navnet " + WHITE + name + BLUE + " har blitt laget.");
					player.sendMessage(BLUE + "GruppeID: " + WHITE + groupHandler.getGroupData(player).getGroupID());
				} else if (args[0].equalsIgnoreCase("kick")) {
					if (groupHandler.getGroupData(player).getGroupOwner().toString()
							.equalsIgnoreCase(player.getUniqueId().toString())) {
						Player victim = Bukkit.getPlayer(args[1]);
						
						if (victim == player) {
							player.sendMessage(RED + "Du kan ikke kicke deg selv ut av gruppen. /group forlat");
							return false;
						}
						
						if (!groupHandler.hasGroup(victim)) {
							player.sendMessage(RED + "Spilleren er ikke i din gruppe.");
							return false;
						}
						
						if (groupHandler.getGroupData(victim).getGroupID() == groupHandler.getGroupId(player)) {
							groupHandler.kickGroupMember(victim);
						} else {
							player.sendMessage(RED + "Spilleren er ikke i din gruppe.");
							return false;
						}
					
						Messenger.sendGroupMessage(player, "Spilleren " + victim.getName() + " har blitt fjernet fra gruppen.");
					}
				}
			}
		}
		return false;
	}
}
