package com.java.tarjeihs.plugin.command;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.group.GroupHandler;
import com.java.tarjeihs.plugin.group.InviteTable;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.user.UserHandler;
import com.java.tarjeihs.plugin.utilities.Regex;

public abstract class CommandHandler implements CommandExecutor, Colour {
	
	private String command = "I/T";
	private String[] aliases = null;
	private String description = "I/T";
	private int rank = 1;
	private boolean use = true;
	
	private User user = null;
	
	protected JPlugin plugin = null;
	
	protected Player player = null;
	
	protected GroupHandler groupHandler = null;
	
	protected InviteTable inviteTable = null;
	
	protected UserHandler userHandler = null;
	
	private final ReadCommandAnnotation readAnnotation = new ReadCommandAnnotation(getClass());
	private static CommandMap commandMap = null;

	static {
		try {
			if ((Bukkit.getServer() instanceof CraftServer)) {
				final Field field = CraftServer.class.getDeclaredField("commandMap");
				field.setAccessible(true);
				commandMap = (CommandMap) field.get(Bukkit.getServer());
			}
		} catch (Exception e) {
			Regex.println("Error: " + e.getMessage());
		}
	}

	public CommandHandler(JPlugin instance) {
		this.plugin = instance;
		
		this.groupHandler = instance.getGroupHandler();
		this.userHandler = instance.getUserHandler();
		
		this.inviteTable = this.groupHandler.getInviteTable();
		
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
		
		if (use) {
			if (description != "I/T" || description != null)
				StoredCommand.addCommand(command, description);
		}
	}

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (this.use) {
			if (sender instanceof Player) {
				this.player = ((Player) sender);
				User user = this.plugin.getUserHandler().getUser(player);
				if (user.getRank() >= this.rank || user.getPlayer().isOp()) {
					return execute(user, command, args);
				} else {
					this.player.sendMessage(RED + "Du har ikke tilgang for denne kommandoen.");
				}
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
			throw new NullPointerException("commandmap cannot be null");
		}
		return commandMap;
	}
	
	protected User getUser() {
		return user;
	}
	
	public abstract boolean execute(User user, Command command, String[] args);
}
