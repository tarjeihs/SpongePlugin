package com.java.tarjeihs.plugin.economy;

import java.util.UUID;

public class Account {

	private UUID uuid;
	private int currency;
	
	public Account(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Account(UUID uuid, int currency) {
		this.uuid = uuid;
		this.currency = currency;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public int getCurrency() {
		return currency;
	}
	
	public void setCurrency(int curr) {
		this.currency = curr;
	}
}
