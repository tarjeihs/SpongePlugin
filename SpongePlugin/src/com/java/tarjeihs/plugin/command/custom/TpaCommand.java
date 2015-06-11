package com.java.tarjeihs.plugin.command.custom;

import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Regex;

public class TpaCommand extends CommandHandler {

	public TpaCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "tpa", description = "TPA-kommando(send forespørsel, godta og avslå)")
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument: /tpa godta|avslå|req <spiller>");
		} else if (args.length == 2) {
			Player victim = Regex.findPlayer(args[1]);
			if (victim != null) {
				if (victim == player) {
					for (Entry<Player, Player> e : plugin.getTpaMap().entrySet()) {
						if ((e.getKey() == victim) && (e.getValue() == player)) {
							if (args[0].equalsIgnoreCase("godta")) {
								player.teleport(victim.getLocation());
								player.sendMessage(BLUE + "Du godtok invitasjonen fra " + WHITE + victim.getName() + BLUE + ".");
								break;
							} else if (args[0].equalsIgnoreCase("avslå")) {
								plugin.getTpaMap().remove(victim, player);
								player.sendMessage(BLUE + "Du avslå invitasjonen fra " + WHITE + victim.getName() + BLUE + ".");
								break;
							}
							
						} else {
							if (args[0].equalsIgnoreCase("req")) {
								plugin.getTpaMap().put(player, victim);
								
								player.sendMessage(BLUE + "Du har sendt en invitasjon til " + WHITE + victim.getName() + BLUE + ".");
								victim.sendMessage(BLUE + "Du har mottatt en invitasjon fra " + WHITE + victim.getName() + BLUE + ".");
								
								return false;
							}
							
							player.sendMessage(RED + "Du har ikke mottatt noen invitasjoner fra spilleren.");
						}
					}
				} else {
					player.sendMessage(RED + "Du kan ikke inviterer deg selv.");
				}
			} else {
				player.sendMessage(RED + "Spilleren er avlogget.");
			}
		}
		return false;
	}
}
