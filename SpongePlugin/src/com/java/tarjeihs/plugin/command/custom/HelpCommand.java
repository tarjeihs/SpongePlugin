package com.java.tarjeihs.plugin.command.custom;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.command.StoredCommand;
import com.java.tarjeihs.plugin.user.User;

public class HelpCommand extends CommandHandler {

	public HelpCommand(JPlugin instance) {
		super(instance);
	}

	
	@CommandAnnotation(command = "?", description = "Liste over tilgjengelige kommandoer")
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage(ChatColor.YELLOW + "Tilgjengelige kommandoer: ");
			player.sendMessage(StoredCommand.listCommands());
		}
		return false;
	}
}
