package com.java.tarjeihs.plugin;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.command.custom.PingCommand;
import com.java.tarjeihs.plugin.configuration.ConfigurationFile;
import com.java.tarjeihs.plugin.configuration.SQLReference;
import com.java.tarjeihs.plugin.listener.PlayerListener;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import com.java.tarjeihs.plugin.mysql.MySQLHandler;
import com.java.tarjeihs.plugin.user.UserHandler;

public class JPlugin extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private MySQLAccessor sqlAccessor = null;
	private MySQLHandler sqlHandler = null;
	private ConfigurationFile sqlConfig;
	private UserHandler userHandler = new UserHandler(this);

	public void onEnable() {
		initializeConfigurations();
		registerEvents();
		registerCommands();

		establishConnection();

		this.userHandler.loadUsers(Bukkit.getOnlinePlayers());

		Regex.println("Plugin is activated with no errors detected while loading.");
	}

	public void onDisable() {
		this.sqlHandler.closeConnection();

		CommandHandler.getCommandMap().clearCommands();

		Regex.println("Plugin is dispatched and everything has been unloaded successfully.");
	}

	private void registerEvents() {
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerListener(this), this);
	}

	private void registerCommands() {
		CommandHandler pingCommand = new PingCommand(this);
		pingCommand.register();
	}

	public void initializeConfigurations() {
		HashMap<String, Object> entry = new HashMap<String, Object>();

		entry.put("sql.database.host", "default");
		entry.put("sql.database.database", "default");
		entry.put("sql.database.username", "username");
		entry.put("sql.database.password", "password");

		entry.put("sql.database.port", Integer.valueOf(0));

		this.sqlConfig = new ConfigurationFile(getDataFolder() + File.separator + "/configuration/config.yml", entry);

		SQLReference.MYSQL_DATABASE_HOST = this.sqlConfig.getConfig().getString("sql.database.host");
		SQLReference.MYSQL_DATABASE_DATABASE = this.sqlConfig.getConfig().getString("sql.database.database");
		SQLReference.MYSQL_DATABASE_USERNAME = this.sqlConfig.getConfig().getString("sql.database.username");
		SQLReference.MYSQL_DATABASE_PASSWORD = this.sqlConfig.getConfig().getString("sql.database.password");
		SQLReference.MYSQL_DATABASE_PORT = this.sqlConfig.getConfig().getInt("sql.database.port");
	}

	public void establishConnection() {
		String SQL_HOST = SQLReference.MYSQL_DATABASE_HOST;
		String SQL_DATABASE = SQLReference.MYSQL_DATABASE_DATABASE;
		String SQL_USERNAME = SQLReference.MYSQL_DATABASE_USERNAME;
		String SQL_PASSWORD = SQLReference.MYSQL_DATABASE_PASSWORD;

		int SQL_PORT = SQLReference.MYSQL_DATABASE_PORT;

		this.sqlHandler = new MySQLHandler(this, SQL_HOST, SQL_PORT, SQL_DATABASE, SQL_USERNAME, SQL_PASSWORD);
		this.sqlHandler.initializeConnection();

		this.sqlAccessor = new MySQLAccessor(this);
	}

	public MySQLHandler getSQLHandler() {
		return this.sqlHandler;
	}

	public ConfigurationFile getSQLConfiguration() {
		return this.sqlConfig;
	}

	public UserHandler getUserHandler() {
		return this.userHandler;
	}
}
