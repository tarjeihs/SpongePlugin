package com.java.tarjeihs.plugin.npc;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class CNPC {

	public CNPC(String name, Location bornLocation) {
		NPCRegistry reg = CitizensAPI.getNPCRegistry();
		NPC npc = reg.createNPC(EntityType.PLAYER, name);
				
		npc.spawn(bornLocation);
	}
}
