package com.java.tarjeihs.plugin.economy;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;

public class AccountHandler extends BankHandler {

	public AccountHandler(JPlugin instance) {
		super(instance);
	}
		
	public void createAccount(UUID playerUUID, int currency) {
		Account account = new Account(playerUUID);
		account.setCurrency(currency);
	
		addAccount(playerUUID, account);
	}
	
	public void deleteAccount() {}
	
	public void loadAccounts() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			UUID uuid = players.getUniqueId();
			if (hasAccount(uuid)) {
				loadUser(players);
			}
		}
	}
	
	public void loadUser(Player player) {
		UUID uuid = player.getUniqueId();
		Account account = new Account(player.getUniqueId(),
				getCurrency(uuid));
		
		addAccount(uuid, account);
	}
	
	public int getCurrency(UUID uuid) {
		String query = "SELECT currency FROM account WHERE uuid=?";
		int id = Integer.parseInt(get(query, new Object[]{
				uuid.toString()
		}, new Object[]{
				"currency"
		}));
		
		return id;
	}
	
	private Account getAccountFromUUID(UUID playerUUID) {
		for (Entry<UUID, Account> e : getAccounts().entrySet()) {
			if (e.getKey() == playerUUID) {
				return (Account) e.getValue();
			}
		}	
		return null;
	}
	
	public boolean hasAccount(UUID playerUUID) {
		String query = "SELECT uuid FROM ACCOUNT WHERE uuid=?";
	
		boolean exists = exists(query, playerUUID.toString());
		
		if (exists) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updateCurrency(UUID playerUUID, int newCurrency) {
		Account account = getAccountFromUUID(playerUUID);
	
		account.setCurrency(newCurrency);
	}
	
	public void saveAccount() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			saveAccount(players.getUniqueId());
		}
	}
	
	public void saveAccount(UUID playerUUID) {
		Account account = getAccountFromUUID(playerUUID);
		
		if (!hasAccount(playerUUID)) {
			if (account != null) {
				String query = "INSERT INTO ACCOUNT (uuid, currency) VALUES (?, ?)";
			
				executeUpdate(query, new Object[]{
						account.getUUID().toString(),
						account.getCurrency()
				});
			}
		} else {
			String query = "UPDATE ACCOUNT SET currency=? WHERE uuid=?";
			
			executeUpdate(query, new Object[]{
					account.getCurrency(),
					account.getUUID().toString()
			});
		}
	}

	public Account getAccount(UUID uuid) {
		return (Account) getAccounts().get(uuid);
	}

	public void unloadUser(Player player) {
		saveAccount(player.getUniqueId());
		
		getAccounts().remove(player.getUniqueId());
	}
}
