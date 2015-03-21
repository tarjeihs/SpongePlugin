package com.java.tarjeihs.plugin.economy;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

public class BankManager {
	
	/**
	 * Creates instance of @BankManager
	 * BankManager will manage every single Bank places around the map.
	 */
	
	private final HashMap<UUID, Location> bank = new HashMap<UUID, Location>();
	
	public BankManager(UUID bankId) {

	}
	
	public BankManager(Location location, UUID locationID) {
		
	}
	
	public void loadBanks() {
		
	}
}
