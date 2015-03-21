package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class ClearChatCommand extends CommandHandler {

	public ClearChatCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command="clearchat", rankRequired=4)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		for (int i = 0; i < 150; i++) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.sendMessage("");
			}
		}
		return false;
	}
}
