package com.java.tarjeihs.plugin.economy;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;

public class AccountInventoryExitEvent extends Event {
	
	private final HandlerList handlerList = new HandlerList();
	
	private Player player;
	
	private InventoryCloseEvent event;
	
	private AccountHandler accountHandler;
	
	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	public AccountInventoryExitEvent(JPlugin plugin, Player player, InventoryCloseEvent event) {
		this.player = player;
		this.event = event;
		
		this.accountHandler = plugin.getAccountHandler();
	
		if (plugin.getBankInventory().contains(player)) {
			exitInventory();
			
			plugin.getBankInventory().remove(player);
		}
	}
	
	private final void exitInventory() {
		UUID uuid = player.getUniqueId();
		int newCurrency = 0;
		for (ItemStack itemStack : event.getInventory().getContents()) {
			if (itemStack != null) {
				if (itemStack.hasItemMeta() && itemStack.getItemMeta().getLore().get(3).startsWith("Statens Penger")) {
					newCurrency += itemStack.getAmount();
				} else {
					player.getInventory().addItem(itemStack);
				}
			}
		}
		
		if (accountHandler.hasAccount(uuid)) {
			accountHandler.updateCurrency(uuid, newCurrency);
		} else {
			accountHandler.createAccount(uuid, newCurrency);
		}
	}
	
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
}