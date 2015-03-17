package com.java.tarjeihs.plugin.command.custom;

import net.minecraft.server.v1_8_R1.EntityPlayer;

import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class PingCommand extends CommandHandler {
	
	public PingCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "ping", description = "Sender en packet for å sjekke spillerens ping.", rankRequired = 1)
	public boolean execute(User user, Command command, String[] args) {
		CraftPlayer cp = (CraftPlayer) super.player;
		EntityPlayer ep = cp.getHandle();

		player.sendMessage("Din ping er: " + BLUE + ep.ping);
		
		return true;
	}
}
