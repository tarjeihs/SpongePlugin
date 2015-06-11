package com.java.tarjeihs.plugin.economy;

import org.bukkit.Location;

public class Bank {
	
	private Location location;
	
	private int id;
	
	/**
	 * Creates a instanceof Bank class
	 * 
	 * Bank contains all users virtual money, and these are only accessed from a trader or a bank.
	 * Users may put in their money(which is a custom item), into this bank(owned by server).
	 * @param location Where the Bank is located, there will exist multiple banks around the server.
	 */
	public Bank(Location loc, int id) {
		this.location = loc;
		this.id = id;
	}
	
	public void setBankLocation(Location newLocation) {
		this.location = newLocation;
	}
	
	public Location getBankLocation() {
		return location;
	}

	public final int getID() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}