package com.java.tarjeihs.plugin.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.java.tarjeihs.packet.TitlePacket;
import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.economy.AccountHandler;
import com.java.tarjeihs.plugin.economy.AccountInventoryEvent;
import com.java.tarjeihs.plugin.group.GroupHandler;
import com.java.tarjeihs.plugin.team.TeamBoard;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.user.UserHandler;
import com.java.tarjeihs.plugin.utilities.Messenger;

public class PlayerListener implements Listener {

	private final JPlugin plugin;
	
	private UserHandler userHandler;
	private GroupHandler groupHandler;
	private AccountHandler accountHandler;
	
	private final Random r = new Random();

	public PlayerListener(JPlugin instance) {
		this.plugin = instance;

		this.accountHandler = this.plugin.getAccountHandler();
		this.groupHandler = this.plugin.getGroupHandler();
		this.userHandler = this.plugin.getUserHandler();
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().hasMetadata("NPC")) {
			Location loc = event.getRightClicked().getLocation();
			Player player = event.getPlayer();
			
			if (accountHandler.isBank(loc)) {
				Bukkit.getPluginManager().callEvent(new AccountInventoryEvent(plugin, player));
			}
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		if (player.getKiller() instanceof Player) {
			event.setDeathMessage(player.getName() + ChatColor.BLUE + " ble rekt av " + ChatColor.WHITE + player.getKiller().getName());
		} else if (!(player.getKiller() instanceof Entity)){
			String[] suicidemessage = {"døde av aids", "fikk ebola", "testet en toløps hagle på hodet sitt", "voldtok seg selv", "tok selvmord", "lekte Jesus på korset", "lever ikke lengre"};			event.setDeathMessage(player.getName() + ChatColor.BLUE + " " + suicidemessage[r.nextInt(suicidemessage.length)]);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		String message = Messenger.filterCodeMessage(player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
		
		if (!Messenger.spam(player)) {
			event.setFormat(message);
		} else {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
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
		
		if (this.groupHandler.getInviteTable().getInvites(player).size() != 0) {
			player.sendMessage(ChatColor.BLUE + "Du har " + ChatColor.WHITE
					+ this.groupHandler.getInviteTable().getInvites(player).size() 
					+ ChatColor.BLUE + " gruppe-invitasjon(er) på vent.");
		}
		
		accountHandler.loadUser(player);
		
		TitlePacket titlePacket = new TitlePacket("Velkommen til Serr", player.getName(), 20, 50 ,20);
		titlePacket.setTitleColor(ChatColor.GREEN);
		titlePacket.setSubtitleColor(ChatColor.GOLD);
		titlePacket.sendPacket(player);

		player.setDisplayName(this.userHandler.getPrefix(player) + player.getName());

		player.setPlayerListName(this.userHandler.getSuffix(player) + (player.getName().length() > 14 ? player.getName().substring(0, 14) : player.getName()));

		TeamBoard.createHealthbar();

		player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 100, 100);

		event.setJoinMessage(this.userHandler.getSuffix(player) + player.getName() + ChatColor.GREEN + " " + "har joinet fellesskapet.");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		this.userHandler.unloadUser(player);
		
		this.plugin.getAccountHandler().unloadUser(player);
		
		this.plugin.getBankInventory().remove(player);
				
		String[] logout = {"stakk av", "taklet ikke mer awesomeness"};
		
		event.setQuitMessage(this.userHandler.getSuffix(player) + player.getName() + ChatColor.RED + " " + logout[r.nextInt(logout.length)]);
	}
}