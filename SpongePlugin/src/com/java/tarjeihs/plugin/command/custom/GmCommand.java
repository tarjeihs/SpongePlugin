package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.GameMode;
import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class GmCommand extends CommandHandler {

	public GmCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "gm", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			if (player.getGameMode().equals(GameMode.SURVIVAL)) {
				player.setGameMode(GameMode.CREATIVE);
			} else if (player.getGameMode().equals(GameMode.CREATIVE)) {
				player.setGameMode(GameMode.SURVIVAL);
			}
		}
		return false;
	}
}
