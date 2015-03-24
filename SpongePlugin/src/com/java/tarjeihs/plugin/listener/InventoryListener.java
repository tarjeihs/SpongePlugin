package com.java.tarjeihs.plugin.listener;

import org.bukkit.event.Listener;

import com.java.tarjeihs.plugin.JPlugin;

public class InventoryListener implements Listener {

	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	public InventoryListener(JPlugin instance) {
		this.plugin = instance;
	}
}
