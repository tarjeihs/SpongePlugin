package com.java.tarjeihs.plugin.economy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;

public class BankHandler extends MySQLAccessor {

	public static final String SERVER_BANK = "SERVER_BANK";
	public static final int SERVER_ID = 1;
	
	public BankHandler(JPlugin instance) {
		super(instance);
	}
	
	@SuppressWarnings("unused")
	public Location getLocation(UUID uuid) {
		int x = 1;
		int y = 1;
		int z = 1;
		
		return null;
	}
	
	public UUID getUID(Location location) {
		return null;
	}
	
	@SuppressWarnings("unused")
	public List<Bank> getBanks() {
		String query = "SELECT * FROM BANK";
		
		List<Bank> banks = new ArrayList<Bank>();
	
		UUID uuid = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			ps = conn.prepareStatement("SELECT * FROM bank");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String uuid_ = rs.getString("uuid");
				
				String world = rs.getString("world");
				
				int x = rs.getInt("x");
				int y = rs.getInt("y");
				int z = rs.getInt("z");
				
				Location location = new Location(Bukkit.getWorld(world), x, y, z);
				
				Bank bank = new Bank(location, UUID.fromString(uuid_));
				
				banks.add(bank);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		return banks;
	}
}