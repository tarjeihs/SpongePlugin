package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Messenger;

public class AdminCommand extends CommandHandler {

	public AdminCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "adminchat", rankRequired = 2)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument: /adminchat <melding>");
		} else if (args.length > 0) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (userHandler.getUser(players).getRank() >= 3) {
					players.sendMessage(BLUE + "serrno / adminchat > ".toUpperCase() + GOLD + player.getName() +  ": " + WHITE + Messenger.toMessage(0, args));
				}
			}
		}
		return false;
	}
}
