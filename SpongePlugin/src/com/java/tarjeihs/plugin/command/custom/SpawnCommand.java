package com.java.tarjeihs.plugin.command.custom;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class SpawnCommand extends CommandHandler {

	public SpawnCommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command = "spawn", description = "Sender spilleren tilbake til spawn.")
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			Location location = player.getWorld().getSpawnLocation();
			player.teleport(location);
		} else if (args.length == 1) {
			World world = Bukkit.getWorld(args[0]);
			List<World> worlds = Bukkit.getWorlds();
			for (World x : worlds) {
				if (world != null) {
					if (x == world) {
						player.teleport(world.getSpawnLocation());
						break;
					}
				}
			}
		}
		return false;
	}
}
