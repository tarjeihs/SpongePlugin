package com.java.tarjeihs.plugin.economy;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;

import com.java.tarjeihs.plugin.JPlugin;

public class BankManager {
	
	/**
	 * Creates instance of @BankManager
	 * BankManager will manage every single Bank places around the map.
	 * 
	 * BankHandler is SQL stuff..
	 * 
	 * Account class shall be also managed from here!
	 */
	
	private final HashMap<Location, UUID> storedBank = new HashMap<Location, UUID>();
	
	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	private BankHandler bankHandler;
	
	private AccountManager accountManager;
	
	public BankManager(UUID bankId) {
		
	}
	
	public BankManager(JPlugin plugin, Location location, UUID locationID) {
		this.plugin = plugin;
	}
	
	public AccountManager getAccountManager() {
		return accountManager;
	}
	
	public void loadBanks() {
		for (int i = 0; i < bankHandler.getBanks().size(); i++) {
			Bank bank = bankHandler.getBanks().get(i);
			
			storedBank.put(bank.getBankLocation(), bank.getUUID());
		}
	}
	
	public class AccountManager {
		
	}
}
