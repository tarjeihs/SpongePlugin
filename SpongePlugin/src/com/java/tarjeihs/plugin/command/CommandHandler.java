package com.java.tarjeihs.plugin.command;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.user.User;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.entity.Player;

public abstract class CommandHandler implements CommandExecutor, Colour {
	
	private String command = "N/A";
	private String[] aliases = null;
	private String description = "N/A";
	private int rank = 1;
	private boolean use = true;
	
	@SuppressWarnings("unused")
	private User user = null;
	
	protected JPlugin plugin = null;
	
	protected Player player = null;
	
	private final ReadCommandAnnotation readAnnotation = new ReadCommandAnnotation(getClass());
	private static CommandMap commandMap = null;

	static {
		try {
			if ((Bukkit.getServer() instanceof CraftServer)) {
				Field field = CraftServer.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				commandMap = (CommandMap) field.get(Bukkit.getServer());
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public CommandHandler(JPlugin instance) {
		this.plugin = instance;

		this.readAnnotation.register();

		this.command = this.readAnnotation.getCommand();
		this.aliases = this.readAnnotation.getAliases();
		this.description = this.readAnnotation.getDescription();
		this.rank = this.readAnnotation.getRank();
		this.use = this.readAnnotation.isUseable();
	}

	public void register() {
		ReflectedCommand regCommand = new ReflectedCommand(this.command);
		commandMap.register("", regCommand);
		if (this.command != null) regCommand.setExecutor(this);
		if (this.aliases != null) regCommand.setAliases(Arrays.asList(this.aliases));
		if (this.description != null) regCommand.setDescription(this.description);
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (this.use) {
			if (sender instanceof Player) {
				this.player = ((Player) sender);
				User user = this.plugin.getUserHandler().getUser(this.player);
				if (user.getRank() >= this.rank) {
					return execute(user, command, args);
				}
				this.player.sendMessage(RED + "Du har ikke tilgang for denne kommandoen.");
			} else {
				System.out.println("Kommandoer er deaktivert for konsoll.");
			}
		} else {
			((Player) sender).sendMessage(RED + "Kommandoen er deaktivert.");
		}
		return false;
	}

	private class ReflectedCommand extends Command {
		private CommandHandler commandHandler = null;

		protected ReflectedCommand(String cmd) {
			super(cmd);
		}

		public boolean execute(CommandSender sender, String commandLabel, String[] args) {
			if (this.commandHandler != null) {
				this.commandHandler.onCommand(sender, this, commandLabel, args);
			}
			return false;
		}

		public void setExecutor(CommandHandler commandHandler) {
			this.commandHandler = commandHandler;
		}
	}

	public static CommandMap getCommandMap() {
		if (commandMap == null) {
			return null;
		}
		return commandMap;
	}

	public abstract boolean execute(User paramUser, Command paramCommand, String[] paramArrayOfString);
}
