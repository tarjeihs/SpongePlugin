package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.command.Command;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class CECommand extends CommandHandler {

	public CECommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "ce", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		
		int counter=0;
		for (Entity e : player.getWorld().getEntities()) {
			if (e instanceof Item || e instanceof Arrow
					|| e instanceof Boat || e instanceof Minecart) {
				e.remove();
			
				counter++;
			}
		}
		
		player.sendMessage("Fjernet " + counter + " entities.");
		return false;
	}
}
