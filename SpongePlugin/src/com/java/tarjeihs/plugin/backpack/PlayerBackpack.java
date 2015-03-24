package com.java.tarjeihs.plugin.backpack;

import org.bukkit.entity.Player;

public class PlayerBackpack {

	private Backpack backpack;
	
	private Player player;
	
	private String backpackName;
	
	public PlayerBackpack(Player player, Backpack backpack, String backpackName) {
		this.player = player;
		this.backpack = backpack;
		this.backpackName = backpackName;
	}
	
	public int getSlots() {
		return backpack.getSlots();
	}
	
	public int getSize() {
		return backpack.getSize();
	}
	
	public Backpack getBackpack() {
		return backpack;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getDisplayName() {
		return backpackName;
	}
}