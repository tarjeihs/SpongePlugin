package com.java.tarjeihs.plugin.listener;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.user.UserHandler;

import de.inventivegames.util.title.TitleManager;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
	private final JPlugin plugin;
	private UserHandler userHandler;

	public PlayerListener(JPlugin instance) {
		this.plugin = instance;

		this.userHandler = this.plugin.getUserHandler();
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		event.setFormat(player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		User user = null;
		if (!this.userHandler.isRegistered(player)) {
			this.userHandler.addUser(player);

			user = new User(player.getName(), 
					player.getUniqueId(), 
					player, 
					1);
		} else {
			user = new User(this.userHandler.getName(player),
					this.userHandler.getUUID_(player), 
					player,
					this.userHandler.getRank(player));
		}
		this.userHandler.loadUser(player, user);

		player.setDisplayName(this.userHandler.getPrefix(player) + player.getName());

		player.setPlayerListName(ChatColor.GREEN + player.getName());

		int fadeIn = 30;
		int stay = 50;
		int fadeOut = 30;

		TitleManager.sendTitle(player, fadeIn, stay, fadeOut, "{\"text\":\"\",\"extra\":[{\"text\":\"Velkommen til Serr\",\"color\":\"blue\"}]}");
		TitleManager.sendSubTitle(player, fadeIn, stay, fadeOut, "{\"text\":\"\",\"extra\":[{\"text\":\"Get rekt u pussi\",\"color\":\"blue\"}]}");
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 100, 100);

		event.setJoinMessage(this.userHandler.getPrefix(player) + player.getName() + " " + "har logget på.");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		this.userHandler.unloadUser(player);

		event.setQuitMessage(ChatColor.RED + player.getDisplayName() + " har logget av.");
	}
}
