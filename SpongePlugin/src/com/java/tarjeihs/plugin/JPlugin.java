package com.java.tarjeihs.plugin;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.command.StoredCommand;
import com.java.tarjeihs.plugin.command.custom.AdminCommand;
import com.java.tarjeihs.plugin.command.custom.CECommand;
import com.java.tarjeihs.plugin.command.custom.ClearChatCommand;
import com.java.tarjeihs.plugin.command.custom.DropCommand;
import com.java.tarjeihs.plugin.command.custom.GmCommand;
import com.java.tarjeihs.plugin.command.custom.GroupCommand;
import com.java.tarjeihs.plugin.command.custom.HealCommand;
import com.java.tarjeihs.plugin.command.custom.HelpCommand;
import com.java.tarjeihs.plugin.command.custom.ICommand;
import com.java.tarjeihs.plugin.command.custom.KickCommand;
import com.java.tarjeihs.plugin.command.custom.PingCommand;
import com.java.tarjeihs.plugin.command.custom.RankCommand;
import com.java.tarjeihs.plugin.command.custom.ServerCommand;
import com.java.tarjeihs.plugin.command.custom.SetSpawnCommand;
import com.java.tarjeihs.plugin.command.custom.SpawnCommand;
import com.java.tarjeihs.plugin.configuration.ConfigurationFile;
import com.java.tarjeihs.plugin.configuration.SQLReference;
import com.java.tarjeihs.plugin.customitems.CustomItemLoader;
import com.java.tarjeihs.plugin.group.GroupHandler;
import com.java.tarjeihs.plugin.group.InviteTable;
import com.java.tarjeihs.plugin.listener.BlockListener;
import com.java.tarjeihs.plugin.listener.EntityListener;
import com.java.tarjeihs.plugin.listener.InventoryListener;
import com.java.tarjeihs.plugin.listener.PlayerListener;
import com.java.tarjeihs.plugin.listener.ServerListener;
import com.java.tarjeihs.plugin.listener.VehicleListener;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import com.java.tarjeihs.plugin.mysql.MySQLHandler;
import com.java.tarjeihs.plugin.team.TeamBoard;
import com.java.tarjeihs.plugin.user.UserHandler;
import com.java.tarjeihs.plugin.utilities.Messenger;
import com.java.tarjeihs.plugin.utilities.Regex;

public final class JPlugin extends JavaPlugin {
	
	private MySQLHandler sqlHandler = null;
	private InviteTable inviteTable = new InviteTable(this);
	private GroupHandler groupHandler = new GroupHandler(this);
	private UserHandler userHandler = new UserHandler(this);
	
	private CustomItemLoader customItemLoader;
	
	private TeamBoard teamBoard = new TeamBoard();
		
	private ConfigurationFile sqlConfig;
	private ConfigurationFile serverConfig;

	public String SERVER_MOTD;
	public int SERVER_MAXIMUM_PLAYERS;
	
//	Loading up everything that is necessary for the plugin to run 
	public void onEnable() {
		this.initializeConfigurations();
		
		this.registerEvents();
		this.registerCommands();
				
		this.establishConnection();
				
		this.customItemLoader = new CustomItemLoader(this);
		
		new Messenger(this);
		
		customItemLoader.addSuperAxeRecipe();
		customItemLoader.addBackpackRecipe();
		
		this.userHandler.loadUsers(Bukkit.getOnlinePlayers());
		this.groupHandler.loadGroups();
			
		loadBoards();
								
		Regex.println("Plugin is activated with no errors detected while loading.");
	}

	public void onDisable() {
		this.sqlHandler.closeConnection();

		for (Player players : Bukkit.getOnlinePlayers()) {
			this.userHandler.unloadUser(players);
		}
		
		CommandHandler.getCommandMap().clearCommands();
		StoredCommand.clearCommands();
		
		Regex.println("Plugin is dispatched and everything has been unloaded successfully.");
	}
	
	private void loadBoards() {
		TeamBoard.createHealthbar();
	}

	private void registerEvents() {
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerListener(this), this);
		pluginManager.registerEvents(new ServerListener(this), this);
		pluginManager.registerEvents(new BlockListener(this), this);
		pluginManager.registerEvents(new EntityListener(this), this);
		pluginManager.registerEvents(new VehicleListener(this), this);
		pluginManager.registerEvents(new InventoryListener(this), this);
	}

	/**
	 * Register the required classes for commands
	 */
	private void registerCommands() {
		CommandHandler pingCommand = new PingCommand(this);
		pingCommand.register();
		CommandHandler groupCommand = new GroupCommand(this);
		groupCommand.register();
		CommandHandler rankCommand = new RankCommand(this);
		rankCommand.register();
		CommandHandler helpCommand = new HelpCommand(this);
		helpCommand.register();
		CommandHandler spawnCommand = new SpawnCommand(this);
		spawnCommand.register();
		CommandHandler ceCommand = new CECommand(this);
		ceCommand.register();
		CommandHandler serverCommand = new ServerCommand(this);
		serverCommand.register();
		CommandHandler kickCommand = new KickCommand(this);
		kickCommand.register();
		CommandHandler adminCommand = new AdminCommand(this);
		adminCommand.register();
		CommandHandler dropCommand = new DropCommand(this);
		dropCommand.register();
		CommandHandler setSpawnCommand = new SetSpawnCommand(this);
		setSpawnCommand.register();
		CommandHandler healCommand = new HealCommand(this);
		healCommand.register();
		CommandHandler clearchatCommand = new ClearChatCommand(this);
		clearchatCommand.register();
		CommandHandler iCommand = new ICommand(this);
		iCommand.register();
		CommandHandler gmCommand = new GmCommand(this);
		gmCommand.register();
	}

	public void initializeConfigurations() {
		HashMap<String, Object> sqlConfig = new HashMap<String, Object>();

		sqlConfig.put("sql.database.host", "default");
		sqlConfig.put("sql.database.database", "default");
		sqlConfig.put("sql.database.username", "username");
		sqlConfig.put("sql.database.password", "password");

		sqlConfig.put("sql.database.port", Integer.valueOf(0));

		this.sqlConfig = new ConfigurationFile(getDataFolder() + File.separator + "/configuration/config.yml", sqlConfig);

		SQLReference.MYSQL_DATABASE_HOST = this.sqlConfig.getConfig().getString("sql.database.host");
		SQLReference.MYSQL_DATABASE_DATABASE = this.sqlConfig.getConfig().getString("sql.database.database");
		SQLReference.MYSQL_DATABASE_USERNAME = this.sqlConfig.getConfig().getString("sql.database.username");
		SQLReference.MYSQL_DATABASE_PASSWORD = this.sqlConfig.getConfig().getString("sql.database.password");
		SQLReference.MYSQL_DATABASE_PORT = this.sqlConfig.getConfig().getInt("sql.database.port");
		
		HashMap<String, Object> config = new HashMap<String, Object>();
		
		config.put("server.motd", "Velkommen til Serr");
		config.put("server.show_maximum_players", 1337);
		
		this.serverConfig = new ConfigurationFile(getDataFolder() + File.separator + "/configuration/server.yml", config);
		
		this.SERVER_MAXIMUM_PLAYERS = this.serverConfig.getConfig().getInt("server.show_maximum_players");
		this.SERVER_MOTD = this.serverConfig.getConfig().getString("server.motd");
	}

	public void establishConnection() {
		String SQL_HOST = SQLReference.MYSQL_DATABASE_HOST;
		String SQL_DATABASE = SQLReference.MYSQL_DATABASE_DATABASE;
		String SQL_USERNAME = SQLReference.MYSQL_DATABASE_USERNAME;
		String SQL_PASSWORD = SQLReference.MYSQL_DATABASE_PASSWORD;

		int SQL_PORT = SQLReference.MYSQL_DATABASE_PORT;

		this.sqlHandler = new MySQLHandler(this, SQL_HOST, SQL_PORT, SQL_DATABASE, SQL_USERNAME, SQL_PASSWORD);
		this.sqlHandler.initializeConnection();

		new MySQLAccessor(this);
	}

	public final MySQLHandler getSQLHandler() {
		return this.sqlHandler;
	}

	public final ConfigurationFile getSQLConfiguration() {
		return this.sqlConfig;
	}

	public final UserHandler getUserHandler() {
		return this.userHandler;
	}

	public final GroupHandler getGroupHandler() {
		return this.groupHandler;
	}

	public InviteTable getInviteTable() {
		return inviteTable;
	}
	
	public TeamBoard getTeamBoard() {
		return teamBoard;
	}
}
