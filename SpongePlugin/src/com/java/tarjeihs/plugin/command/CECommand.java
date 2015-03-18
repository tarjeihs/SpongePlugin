package com.java.tarjeihs.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.user.User;

public class CECommand extends CommandHandler {

	public CECommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "ce", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		for (Entity e : player.getWorld().getEntities()) {
			if (e instanceof Item) {
				e.remove();
			}
		}
		return false;
	}
}
