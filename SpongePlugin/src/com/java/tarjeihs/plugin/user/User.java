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

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getRank() {
		return this.rank;
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Override
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public GameProfile getProfile() {
		return null;
	}

	@Override
	public void setProfile(GameProfile profile) {
		
	}
}