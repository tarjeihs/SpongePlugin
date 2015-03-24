package com.java.tarjeihs.plugin.user;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.mysql.MySQLAccessor;
import com.java.tarjeihs.plugin.utilities.Regex;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UserHandler extends MySQLAccessor {
	private HashMap<Player, User> userData = new HashMap<Player, User>();

	public UserHandler(JPlugin instance) {
		super(instance);
	}

	public void addUser(Player player) {
		String query = "INSERT INTO `user` (`name`, `uuid`, `rank`) VALUES (?, ?, ?)";

		executeUpdate(query, new Object[] { player.getName(),
				player.getUniqueId().toString(), 1 });
	}

	public String getName(Player player) {
		String query = "SELECT name FROM `user` WHERE name=?";
		String name = get(query, new Object[] { player.getName() 
		}, new Object[] { 
			"name" 
		});
		if (name == null) {
			return "N/A";
		}
		return name;
	}

	public boolean isIdenticalUUIDPlayer() {
		return false;
	}

	public void setVisitorMode(Player player) {
		
	}

	public int getRank(Player player) {
		String query = "SELECT rank FROM user WHERE name=?";
		int id = Integer.parseInt(get(query, new Object[] { player.getName() }, new Object[] { "rank" }));
		if (!(id > 0)) id = 1;
		return id;
	}

	public void setRank(Player victim, int rank) {
		String query = "UPDATE user SET rank=? WHERE name=?";

		executeUpdate(query, new Object[] { rank, victim.getName() });
	}

	public String getNameFromUUID(String uuid) {
		String query = "SELECT name FROM user WHERE uuid=?";

		String name = get(query, new Object[] { uuid }, new Object[] { "name" });

		return (name != null ? name : "Unknown User");
	}

	public UUID getUUID_(Player player) {
		String uuid = getUUID(player);
		if (uuid == null) {
			return null;
		}
		UUID uuid_ = UUID.fromString(uuid);

		return uuid_;
	}

	public ChatColor getSuffix(Player player) {
		int rank = getRank(player);
		ChatColor suffix = null;
		switch (rank) {
		case 1:
			suffix = ChatColor.YELLOW;
			break;
		case 2:
			suffix = ChatColor.DARK_AQUA;
			break;
		case 3:
			suffix = ChatColor.RED;
			break;
		default:
			suffix = ChatColor.YELLOW;
			break;
		}
		return suffix;
	}

	public String getPrefix(Player player) {
		int rank = getRank(player);
		String prefix = null;
		switch (rank) {
		case 1:
			prefix = ChatColor.YELLOW + "";
			break;
		case 2:
			prefix = ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + "Mod"
					+ ChatColor.DARK_AQUA + "] ";
			break;
		case 3:
			prefix = ChatColor.DARK_RED + "[" + ChatColor.RED + "Admin"
					+ ChatColor.DARK_RED + "] ";
			break;
		default:
			prefix = ChatColor.YELLOW + "";
		}
		return prefix;
	}

	public String getUUID(Player player) {
		String query = "SELECT uuid FROM `user` WHERE name=?";
		String uuid = get(query, new Object[] { player.getName() },
				new Object[] { "uuid" });
		if (uuid == null)
			return "N/A";
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
			User user = new User(getName(players), getUUID_(players), players,
					getRank(players));
			loadUser(players, user);

			players.setDisplayName(getPrefix(players) + players.getName());
			players.setPlayerListName(getSuffix(players) + players.getName());
		}

		Regex.println("Players has been loaded. Amount of players: "
				+ userData.size());
	}

	public void loadUser(Player player, User user) {
		if (this.userData.containsKey(player)) {
			return;
		}
		this.userData.put(player, user);
	}
}
