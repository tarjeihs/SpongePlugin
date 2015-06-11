package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.npc.CNPC;
import com.java.tarjeihs.plugin.user.User;

public class NPCComand extends CommandHandler {

	public NPCComand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "cnpc", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 1) {
			new CNPC(args[0], player.getLocation());
		
			accountHandler.createBank(player.getLocation());
		}
		return false;
	}
}
