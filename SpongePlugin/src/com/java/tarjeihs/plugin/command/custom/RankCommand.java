package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;

public class RankCommand extends CommandHandler {

	public RankCommand(JPlugin instance) {
		super(instance);
	}

	@SuppressWarnings("deprecation")
	@CommandAnnotation(command = "rank", rankRequired = 3)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument: /rank <spiller> <level>");
		} else if (args.length == 2) {
			Player victim = Bukkit.getPlayer(args[0]);
			int rank = Integer.parseInt(args[1]);
			
			this.userHandler.getUser(victim).setRank(rank);
			this.userHandler.setRank(victim, rank);
			
			String rankType = "UDEFINERT";
			
			switch (rank) {
			case 1:
				rankType = "Bruker";
				player.setOp(false);
				break;
			case 2:
				rankType = "Moderator";
				player.setOp(false);
				break;
			case 3:
				rankType = "Administrator";
				player.setOp(true);
				break;
			default:
				rankType = "Bruker";
				player.setOp(false);
				break;
			}
			
			victim.setDisplayName(this.userHandler.getPrefix(victim) + victim.getName());

			victim.setPlayerListName(this.userHandler.getSuffix(victim) + victim.getName());
			
			Bukkit.broadcastMessage(GOLD + "Spillerranken til " + victim.getName() + " har blitt forandret til " + rankType);			
					
			victim.playSound(victim.getLocation(), Sound.LEVEL_UP, 300, 300);
		}
		return false;
	}
}
