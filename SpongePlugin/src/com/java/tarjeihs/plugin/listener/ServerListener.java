package com.java.tarjeihs.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.java.tarjeihs.plugin.JPlugin;

public class ServerListener implements Listener {
	
	private JPlugin plugin;
	
	public ServerListener(JPlugin instance) {
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onServerListPing(ServerListPingEvent event) {	
		event.setMaxPlayers(plugin.SERVER_MAXIMUM_PLAYERS);
		
		event.setMotd(plugin.SERVER_MOTD);
	}
}
