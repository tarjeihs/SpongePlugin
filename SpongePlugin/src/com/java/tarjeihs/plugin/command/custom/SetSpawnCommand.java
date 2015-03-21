package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class SetSpawnCommand extends CommandHandler {

	public SetSpawnCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command="setspawn", rankRequired = 5, description = "Setter nytt spawnpoint.")
	@Override
	public boolean execute(User user, Command command, String[] args) {
		player.getWorld().setSpawnLocation((int) player.getLocation().getX(),
				(int) player.getLocation().getY(),
				(int) player.getLocation().getZ());
	
		player.sendMessage(BLUE + "Ny spawnpoint er satt.");
		return false;
	}
}
