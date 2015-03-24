package com.java.tarjeihs.plugin.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;

public class EntityListener implements Listener {

	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	public EntityListener(JPlugin instance) {
		this.plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityAttackEntity(EntityDamageByEntityEvent event) {		
		if (event.getDamager() instanceof Player) {
			if (event.getEntity() instanceof Player) {
				Player victim = (Player) event.getEntity();

				if (victim.isBlocking()) {
					int[] itemArray = {268, 272, 267, 276, 283};

					int[] reduceDmgArray = {1, 2, 3, 6, 4};

					int dmg = (int) event.getDamage();
					
					for (int i : itemArray) {
						ItemStack item = new ItemStack(Material.getMaterial(i));
						if (victim.getItemInHand().equals(item)) {
							if (victim.getItemInHand() != null) {
								if (item != null)
									victim.damage((dmg - reduceDmgArray[i]));
									break;
							}
						}
					}
					
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntityType().equals(EntityType.CREEPER)) {
			event.setCancelled(true);
		}
	}
}
