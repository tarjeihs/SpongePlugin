package com.java.tarjeihs.plugin.backpack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackpackEvent extends Event implements Cancellable {
	
	int backpackId;	
	
	Player player;
	
	private final HandlerList handlers = new HandlerList();
	
	private boolean cancel;
	
	public boolean hasInventoryBackpack(Player player) {		
		for (ItemStack items : player.getInventory().getContents()) {
			if (items != null) {
				if (items.hasItemMeta()) {
					ItemMeta meta = items.getItemMeta();

					if (meta.getDisplayName().contains("#")) {
						int id = Integer.parseInt(meta.getDisplayName().split(
								"#")[1]);

						Bukkit.broadcastMessage(id + "");

					}
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
