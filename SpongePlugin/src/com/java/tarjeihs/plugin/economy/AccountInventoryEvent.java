package com.java.tarjeihs.plugin.economy;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.java.tarjeihs.plugin.JPlugin;

public class AccountInventoryEvent extends Event {

	private final HandlerList handlerList = new HandlerList();
	
	private Player player;
	
	private JPlugin plugin;
			
	private AccountHandler accountHandler;
	
	public AccountInventoryEvent(JPlugin plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		
		this.accountHandler = plugin.getAccountHandler();
	
		openInventory();
	}
	
	private void openInventory() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int size = 9*3;
				String name = ChatColor.RED + "Du har ingen penger på kontoen"; 
							
				UUID uuid = player.getUniqueId();
				
				Inventory inventory = Bukkit.createInventory(null, size, name);
				
				if (accountHandler.hasAccount(uuid)) {
					if (accountHandler.getAccount(uuid).getCurrency() > 0) {
//						Create coins						
						int currency = accountHandler.getAccount(uuid).getCurrency();
						
						name = ChatColor.GREEN + "Din pengebalanse";
						
						inventory = Bukkit.createInventory(null, size, name + ": " + currency);					
						
						
						ItemStack item = getItemStack(new ItemStack(Material.DOUBLE_PLANT));
						int counter = 0;
						for (int i = 0; i < currency; i++) {							
							counter++;
						}
						item.setAmount(counter);
						
						inventory.addItem(item);
					}
				}
			
				player.openInventory(inventory);
				
				if (plugin.getBankInventory().contains(player)) 
					plugin.getBankInventory().remove(player);
				plugin.getBankInventory().add(player);
				
			}
		}).start();
	}
	
	private ItemStack getItemStack(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Kroner");

		List<String> lore = (List<String>) Arrays.asList(
				"Kan legges inn ved en bank",
				"som finnes rundt på kartet.",
				"Finnes ingen virtuell bank.",
				"Statens Penger");
		
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.LUCK, 2, true);
		
		item.setItemMeta(meta);
		return item;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
}
