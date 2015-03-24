package com.java.tarjeihs.plugin.listener;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.java.tarjeihs.plugin.JPlugin;

public class VehicleListener implements Listener {
	
	@SuppressWarnings("unused")
	private JPlugin plugin;
		
	public VehicleListener(JPlugin instance) {
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onVehicleExit(VehicleExitEvent event) {
		if (event.getExited() instanceof Player) {
			Player player = (Player) event.getExited();
			if (player.getGameMode().equals(GameMode.CREATIVE)) {
				event.getVehicle().remove();
				return;
			}
			
			if (event.getVehicle() instanceof Boat) {
				player.getInventory().addItem(new ItemStack(Material.BOAT, 1));
				event.getVehicle().remove();
			} else if (event.getVehicle() instanceof Minecart) {
				player.getInventory().addItem(new ItemStack(Material.MINECART, 1));
				event.getVehicle().remove();
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void mariocart(VehicleMoveEvent event) {		
		if (event.getVehicle() instanceof Minecart) {
			if (event.getVehicle().getPassenger() instanceof Player) {
				Player player = (Player) event.getVehicle().getPassenger();
				if (player.getInventory().getBoots() != null && player.getInventory().getBoots().equals(new ItemStack(Material.GOLD_BOOTS))) {
					Minecart minecart = (Minecart) event.getVehicle();

					double speed = 1.5;

					Vector vector = minecart.getVelocity();

					vector.setX(player.getLocation().getDirection().getX() * speed);
					vector.setZ(player.getLocation().getDirection().getZ() * speed);

					minecart.setMaxSpeed(speed);
					minecart.setVelocity(vector);
				}
			}
		}
	}
}
