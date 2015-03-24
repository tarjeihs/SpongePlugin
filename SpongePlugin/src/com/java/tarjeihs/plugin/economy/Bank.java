package com.java.tarjeihs.plugin.economy;

import java.util.UUID;

import org.bukkit.Location;

public class Bank {
	
	private Location location;
	
	private UUID uuid;
	
	/**
	 * Creates a instanceof Bank class
	 * 
	 * Bank contains all users virtual money, and these are only accessed from a trader or a bank.
	 * Users may put in their money(which is a custom item), into this bank(owned by server).
	 * @param location Where the Bank is located, there will exist multiple banks around the server.
	 */
	public Bank(Location loc, UUID uuid) {
		this.location = loc;
		this.uuid = uuid;
	}
	
	public void setBankLocation(Location newLocation) {
		this.location = newLocation;
	}
	
	public Location getBankLocation() {
		return (location != null ? location : null);
	}
	
	public UUID getUUID() {
		return (uuid != null ? uuid : null);
	}
}