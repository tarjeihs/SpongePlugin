package com.java.tarjeihs.plugin.listener;

import org.bukkit.event.Listener;

import com.java.tarjeihs.plugin.JPlugin;

public class BlockListener implements Listener {
	
	@SuppressWarnings("unused")
	private JPlugin plugin;
	
	public BlockListener(JPlugin instance) {
		this.plugin = instance;
	}
}
