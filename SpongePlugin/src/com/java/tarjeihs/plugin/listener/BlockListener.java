package com.java.tarjeihs.plugin.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.customitems.CustomItemLoader;

public class BlockListener implements Listener {
	
	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	public BlockListener(JPlugin instance) {
		this.plugin = instance;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		ItemStack itemInHand = player.getItemInHand();
		
		if (itemInHand != null) {
			if (itemInHand.getTypeId() == CustomItemLoader.getID("SuperPickAxe")) {
				Material wood = Material.LOG;
			
				if (block.getType() == wood) {
					Location loc = block.getLocation();
					loc.add(0, 1, 0);
					while (player.getWorld().getBlockAt(loc).getType() == wood) {
						Block block_ = player.getWorld().getBlockAt(loc);
						
						for (ItemStack itemStack : block_.getDrops()) {
							player.getWorld().dropItemNaturally(loc, itemStack);
						}
						
						block_.setType(Material.AIR);
						loc.add(0, 1, 0);
					}
				}
			}
		}
	}
}
