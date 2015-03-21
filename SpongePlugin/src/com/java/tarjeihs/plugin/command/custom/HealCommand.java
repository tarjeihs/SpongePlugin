package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class HealCommand extends CommandHandler{

	public HealCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "heal", rankRequired = 5)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.setHealth(20);
			player.setFoodLevel(20);
		} else if (args.length == 1) {
			Player victim = Bukkit.getPlayer(args[0]);
			if (victim != player) {
				if (victim != null) {
					victim.setHealth(20);
					victim.setFoodLevel(20);
				} else {
					player.sendMessage(RED + "Spilleren er avlogget");
				}
			} else {
			}
		}
		return false;
	}
}
