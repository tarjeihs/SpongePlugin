package com.java.tarjeihs.plugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.economy.AccountHandler;
import com.java.tarjeihs.plugin.economy.AccountInventoryExitEvent;

public class InventoryListener implements Listener {

	private final JPlugin plugin;
	
	@SuppressWarnings("unused")
	private AccountHandler accountHandler;
	
	public InventoryListener(JPlugin plugin) {
		this.plugin = plugin;
		
		this.accountHandler = plugin.getAccountHandler();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Bukkit.getPluginManager().callEvent(new AccountInventoryExitEvent(plugin, player, event));
	}
}
