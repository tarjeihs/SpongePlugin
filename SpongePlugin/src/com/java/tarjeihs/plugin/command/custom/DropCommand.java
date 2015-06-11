package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class DropCommand extends CommandHandler {

	public DropCommand(JPlugin instance) {
		super(instance);
	}

	@SuppressWarnings("deprecation")
	@CommandAnnotation(command = "drop", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		int[] itemArray = {264, 266, 281,
				310, 320, 319,
				};
		
		World world = player.getWorld();
			
		Location location = new Location(player.getWorld(), player.getLocation().getX(), 
				player.getLocation().getY() - 2, player.getLocation().getZ());
				
		for (int i : itemArray) {
			ItemStack item = new ItemStack(i);
			
			world.dropItemNaturally(location, item);
		}
		
		return false;
	}
}
