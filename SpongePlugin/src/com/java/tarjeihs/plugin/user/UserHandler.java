package com.java.tarjeihs.plugin.user;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.Regex;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class UserHandler extends MySQLAccessor {
	private HashMap<Player, User> userData = new HashMap<Player, User>();

	public UserHandler(JPlugin instance) {
		super(instance);
	}

	public void addUser(Player player) {
		String query = "INSERT INTO `user` (`name`, `uuid`, `rank`) VALUES (?, ?, ?)";

		executeUpdate(query, new Object[] { player.getName(), player.getUniqueId().toString(), 1});
	}

	public String getName(Player player) {
		String query = "SELECT name FROM `user` WHERE name=?";
		String name = get(query, new Object[] { player.getName() }, new Object[] { "name" });
		if (name == null) {
			return "N/A";
		}
		return name;
	}
	
	public boolean isIdenticalUUIDPlayer() {
		return false;
	}

	public int getRank(Player player) {
		String query = "SELECT rank FROM `user` WHERE name=?";
		int id = Integer.parseInt(get(query, new Object[] { player.getName() }, new Object[] { "rank" }));
		if (!(id > 0)) id = 1;
		return id;
	}

	public UUID getUUID_(Player player) {
		String uuid = getUUID(player);
		if (uuid == null) {
			return null;
		}
		UUID uuid_ = UUID.fromString(uuid);

		return uuid_;
	}

	public String getPrefix(Player player) {
		int rank = getRank(player);
		String prefix = null;
		switch (rank) {
		case 1:
			prefix = ChatColor.GRAY + "[Gjest] ";
			break;
		case 2:
			prefix = ChatColor.GREEN + "[Bruker] ";
			break;
		case 3:
			prefix = ChatColor.BLUE + "[Mod] ";
			break;
		case 4:
			prefix = ChatColor.AQUA + "[Dev] ";
			break;
		case 5:
			prefix = ChatColor.DARK_RED + "[" + ChatColor.RED + "Admin" + ChatColor.DARK_RED + "] ";
			break;
		default:
			prefix = "";
		}
		return prefix;
	}

	public String getUUID(Player player) {
		String query = "SELECT uuid FROM `user` WHERE name=?";
		String uuid = get(query, new Object[] { player.getName() }, new Object[] { "uuid" });
		if (uuid == null) return "N/A";
		return uuid;
	}

	public boolean isRegistered(Player player) {
		if (getName(player).equalsIgnoreCase(player.getName())) {
			return true;
		}
		return false;
	}

	public HashMap<Player, User> getUserData() {
		if (this.userData == null) {
			return new HashMap<Player, User>();
		}
		return this.userData;
	}

	public User getUser(Player player) {
		return (User) this.userData.get(player);
	}

	public void unloadUser(Player player) {
		if (!this.userData.containsKey(player)) {
			return;
		}
		this.userData.remove(player);
	}

	public void loadUsers(Collection<? extends Player> collection) {
		if ((collection.size() == 0) || (collection.size() <= 0)) {
			return;
		}
		for (Player players : collection) {
			User user = new User(getName(players), getUUID_(players), players, getRank(players));
			loadUser(players, user);
		}
		Regex.println("Players has been loaded. Amount of players: " + collection.size());
	}

	public void loadUser(Player player, User user) {
		if (this.userData.containsKey(player)) {
			return;
		}
		this.userData.put(player, user);
	}
}
