package com.java.tarjeihs.plugin.economy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import com.java.tarjeihs.plugin.utilities.Regex;

public class BankHandler extends MySQLAccessor {

	private final List<Bank> banks;
	private final HashMap<UUID, Account> accounts;
	
	private AccountHandler accountHandler;
	
	public BankHandler(JPlugin instance) {
		super(instance);
		
		banks = new ArrayList<Bank>();
		accounts = new HashMap<UUID, Account>();
	}
	
	public HashMap<UUID, Account> getAccounts() {
		return accounts;
	}
	
	public void addAccount(UUID uuid, Account account) {
		if (this.accounts.containsKey(uuid)) return;
		
		this.accounts.put(uuid, account);
	}
	
	public boolean isBank(Location loc) {
		for (Bank bank : banks) {
			if (bank.getBankLocation().equals(loc)) {
				return true;
			}
		}
		return false;
	}
	
	public void createBank(Location location) {
		World world = location.getWorld();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		
		String query = "INSERT INTO bank (world, x, y, z) VALUES (?, ?, ?, ?)";
		
		int id = executeUpdate(query, new Object[]{
				world.getName().toString(), x, y, z
		});
		
		Bank bank = new Bank(location, id);
	
		addBank(bank);
	}
	
	public void loadBanks() {
		for (Bank bank : getBanks()) {
			addBank(bank);
		}
	}
	
	private void addBank(Bank bank) {
		if (this.banks == null) return;
		
		if (this.banks.contains(bank)) return;
		
		this.banks.add(bank);
	}
	
	public List<Bank> getBanks() {
		List<Bank> list = new ArrayList<Bank>();
		
		String query = "SELECT * FROM BANK";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				
				World world = Bukkit.getWorld(rs.getString("world"));
								
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				int z = rs.getInt("z");
				
				Bank bank = new Bank(new Location(world, x, y, z), id);
				
				list.add(bank);
			}
		} catch (SQLException e) {
			Regex.println(e.getMessage());
		} finally {
			if (ps != null) { try { ps.close(); ps = null; } catch (SQLException ignored) {}}
			if (rs != null) { try { rs.close(); rs = null; } catch (SQLException ignored) {}}
		}
		
		return list;
	}
	
	public AccountHandler getAccountHandler() {
		return accountHandler;
	}
}
