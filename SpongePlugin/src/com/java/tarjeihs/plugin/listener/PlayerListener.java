package com.java.tarjeihs.plugin.listener;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.group.GroupData;
import com.java.tarjeihs.plugin.group.GroupHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.user.UserHandler;

import de.inventivegames.util.title.TitleManager;

public class PlayerListener implements Listener {

	private final JPlugin plugin;
	
	private UserHandler userHandler;
	private GroupHandler groupHandler;

	public PlayerListener(JPlugin instance) {
		this.plugin = instance;

		
		this.groupHandler = this.plugin.getGroupHandler();
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
		GroupData groupData = null;
		
		int fadeIn = 40;
		int stay = 50;
		int fadeOut = 40;
		
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
		
		if (this.groupHandler.hasGroup(player)) {
			String groupName = this.groupHandler.getGroupName(player);
			String groupOwner = this.groupHandler.getGroupOwner(player);
			int guid = (int) this.groupHandler.getGroupId(player);
			
			groupData = new GroupData(groupName, groupOwner, guid);
			
			this.groupHandler.loadGroup(player, groupData);	
		}
		
		TitleManager.sendTitle(player, fadeIn, stay, fadeOut,
				"{\"text\":\"\",\"extra\":[{\"text\":\"Velkommen til Serr!\",\"color\":\"blue\"}]}");
		TitleManager.sendSubTitle(player, fadeIn, stay, fadeOut,
				"{\"text\":\"\",\"extra\":[{\"text\":\"" + player.getName() + "\",\"color\":\"gold\"}]}");

		player.setDisplayName(this.userHandler.getPrefix(player) + player.getName());

		player.setPlayerListName(this.userHandler.getSuffix(player) + player.getName());



		player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 100, 100);

		event.setJoinMessage(this.userHandler.getPrefix(player) + player.getName() + ChatColor.GREEN + " " + "har logget på.");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		this.userHandler.unloadUser(player);
		
		this.groupHandler.unloadGroup(player);

		event.setQuitMessage(this.userHandler.getPrefix(player) + player.getName() + ChatColor.RED + " " + "har logget av.");
	}
}
