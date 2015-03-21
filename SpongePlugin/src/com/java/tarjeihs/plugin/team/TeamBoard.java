package com.java.tarjeihs.plugin.team;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.java.tarjeihs.plugin.group.GroupData;

public class TeamBoard {
	
	@SuppressWarnings("unused")
	private HashMap<Team, GroupData> groupTeam;
	
	public TeamBoard() {
		groupTeam = new HashMap<Team, GroupData>();
	}
	
	public static void createHealthbar() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
	
		Scoreboard board = manager.getNewScoreboard();
		
		Objective objective = board.registerNewObjective("healthbar", "health");
		
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		
		objective.setDisplayName("/20" + ChatColor.RED + Character.toString('\u2665'));
				
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(board);
			players.setHealth(players.getHealth());
		}
	}
}
