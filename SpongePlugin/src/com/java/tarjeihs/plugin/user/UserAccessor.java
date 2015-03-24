package com.java.tarjeihs.plugin.user;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.entity.Player;

interface UserAccessor extends Serializable {

	 String getName();

	 void setName(String name);

	 int getRank();

	 void setRank(int rank);

	 UUID getUUID();

	 void setUUID(UUID uuid);

	 Player getPlayer();

	 void setPlayer(Player player);

	 GameProfile getProfile();
	 
	 void setProfile(GameProfile profile);
}
