package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Messenger;

public class ServerCommand extends CommandHandler {

	public ServerCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "server", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {

			player.sendMessage("Ukjent argument: /server <melding>");
		} else if (args.length > 0) {
			Messenger.broadcast(GREEN + "[SERVER]", args);
		}
		return false; 
	}
}
