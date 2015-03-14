package com.java.tarjeihs.plugin.user;

import java.io.Serializable;
import java.util.UUID;
import org.bukkit.entity.Player;

public abstract interface UserAccessor extends Serializable {
	public abstract String getName();

	public abstract void setName(String paramString);

	public abstract int getRank();

	public abstract void setRank(int paramInt);

	public abstract UUID getUUID();

	public abstract void setUUID(UUID paramUUID);

	public abstract Player getPlayer();

	public abstract void setPlayer(Player paramPlayer);
}
