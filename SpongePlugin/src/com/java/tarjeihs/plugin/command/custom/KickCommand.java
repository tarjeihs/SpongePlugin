package com.java.tarjeihs.plugin.command.custom;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.java.tarjeihs.plugin.JPlugin;
import com.java.tarjeihs.plugin.command.CommandAnnotation;
import com.java.tarjeihs.plugin.command.CommandHandler;
import com.java.tarjeihs.plugin.user.User;
import com.java.tarjeihs.plugin.utilities.Messenger;

public class KickCommand extends CommandHandler {

	public KickCommand(JPlugin instance) {
		super(instance);
	}

	@SuppressWarnings("deprecation")
	@CommandAnnotation(command = "spark", rankRequired = 2)
	@Override
	public boolean execute(User user, Command command, String[] args) {
		if (args.length == 0) {
			player.sendMessage("Ukjent argument: /kick <spiller> <grunnlag>");
		} else if (args.length == 1) {
			Player victim = Bukkit.getPlayer(args[0]);
			if (victim != null) {
				if (victim != player) {
					victim.kickPlayer("Du har blitt kicket fra serveren.");
					Bukkit.broadcastMessage(victim.getName() + RED + " har blitt kicket fra serveren av en moderator.");
					
					player.sendMessage(victim.getName() + RED + " har blitt sparket fra serveren.");
				}
			}
		} else if (args.length >= 2) {
			Player victim = Bukkit.getPlayer(args[0]);
			if (victim != null) {
				if (victim != player) {
					victim.kickPlayer(Messenger.toMessage(1, args));
					Bukkit.broadcastMessage(victim.getName() + RED + " har blitt sparket fra serveren av en moderator.");
					Bukkit.broadcastMessage(RED + "Grunn: " + WHITE + Messenger.toMessage(1, args));
					
					player.sendMessage(victim.getName() + RED + " har blitt sparket fra serveren.");
				}
			}
		}
		return false;
	}
}
