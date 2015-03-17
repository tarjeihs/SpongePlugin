package com.java.tarjeihs.plugin.user;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User implements UserAccessor {

	private static final long serialVersionUID = 3098668047114047949L;

	private String name;

	private Player player;

	private UUID uuid;

	private int rank;

	public User(Player player) {
		this(player.getName(), player.getUniqueId(), player, 1);
	}
	
	public User(UUID uuid) {
		this(Bukkit.getPlayer(uuid).getName(), uuid, Bukkit.getPlayer(uuid), 1);
	}

	public User(String name, UUID uuid, Player player, int rank) {
		this.name = name;
		this.uuid = uuid;
		this.player = player;
		this.rank = rank;
	}

	public String getName() {
		return this.name;
	}

	public int getRank() {
		return this.rank;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
