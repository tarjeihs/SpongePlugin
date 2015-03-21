package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Regex;

public class ICommand extends CommandHandler {

	public ICommand(JPlugin instance) {
		super(instance);
	}

	@CommandAnnotation(command="i", rankRequired = 5)
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument: /i <itemID> <antall>|<data>");
		} else if (args.length == 1) {
			int stack = 1;
			
			ItemStack item = null;
			
			if (Regex.isInteger(args[0])) {
				
				item = new ItemStack(Material.getMaterial(Integer.parseInt(args[0])), stack);
		
			}
					
			if (item != null) {
				
				player.getInventory().addItem(item);
			
			}
			
		} else if (args.length == 2) {
			
			if (!Regex.isInteger(args[1])) {
				player.sendMessage("Ukjent argument /i <itemID> <antall>|<data>");
			}
			
			int stack = Integer.parseInt(args[1]);
			
			ItemStack item = null;
			
			if (Regex.isInteger(args[0])) {
				
				item = new ItemStack(Material.getMaterial(Integer.parseInt(args[0])), stack);
		
			}
			
			if (item != null) {
				
				player.getInventory().addItem(item);
			
			}
			
		} else if (args.length == 3) {
			if (!Regex.isInteger(args[1])) {
				player.sendMessage("Ukjent argument /i <itemID> <antall>|<data>");
			}
			
			int stack = Integer.parseInt(args[1]);
			
			byte data = Byte.parseByte(args[2]);
			
			ItemStack item = null;
			
			if (Regex.isInteger(args[0])) {
				
				item = new ItemStack(Material.getMaterial(Integer.parseInt(args[0])), stack);
		
			} 
			
			if (item != null) {
				
				player.getInventory().addItem(item);
			
			}
									
			if (data == item.getData().getData()) {
				if (item != null) {
					player.getInventory().addItem(item);
				}
			}
		}
		return false;
	}
}
