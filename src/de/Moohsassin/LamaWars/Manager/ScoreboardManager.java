package de.Moohsassin.LamaWars.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;

public class ScoreboardManager {

	public static void registerScoreboard(Player p) {
		unregisterScoreboard(p);
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective score = board.registerNewObjective("main", "dummy");
		score.setDisplayName("LamaWars"); score.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		board.registerNewTeam("lamas");
		board.getTeam("lamas").addEntry("/" + LamaWars.type.getMaxPlayers());
		
		for(GameTeam team : GameTeam.values()) {
			board.registerNewTeam(team.getName());
			board.getTeam(team.getName()).addEntry("▌ " + team.getName() + "§7: §e");
		}
		
		p.setScoreboard(board);
		
	}
	
	public static void unregisterScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();
		board.clearSlot(DisplaySlot.SIDEBAR);
		for(Team teams : board.getTeams()) {
			teams.unregister();
		}
		for(Objective scores : board.getObjectives()) {
			scores.unregister();
		}
	}
	
	public static void setColor(Player p) {
		
		Scoreboard board = p.getScoreboard();
		
		String color = "§7";
		
		Team team = board.getTeam(p.getName());
		GameTeam gameTeamP = GameTeam.getCurrentTeam(p);
		
		if (team == null) {
			team = board.registerNewTeam(p.getName());
			team.addEntry(p.getName());
		}
		team.setPrefix((gameTeamP != null ? gameTeamP.getName().substring(0, 2) : color));
		
		for (Player players : Bukkit.getOnlinePlayers()) {

			Scoreboard b = players.getScoreboard();
			
			Team te = b.getTeam(p.getName());
			if (te == null) {
				te = b.registerNewTeam(p.getName());
				te.addEntry(p.getName());
			}
			te.setPrefix((gameTeamP != null ? gameTeamP.getName().substring(0, 2) : color));
			
			if (players == p) continue;

			Team t = board.getTeam(players.getName());
			if (t == null) {
				t = board.registerNewTeam(players.getName());
				t.addEntry(players.getName());
			}
			GameTeam gameTeam = GameTeam.getCurrentTeam(players);
			t.setPrefix((gameTeam != null ? gameTeam.getName().substring(0, 2) : color));
		}
	}

	public static void updateScoreboard(Player p) {
		Scoreboard board = p.getScoreboard();
		
		if(GameManager.status == Status.LOBBY) {
			
			Team team = board.getTeam("lamas");
			team.setPrefix("§e" + Bukkit.getOnlinePlayers().size());
			
			return;
		}
		
		for(GameTeam teams : GameTeam.values()) {
			if(teams.isRequired(LamaWars.type)) {
				Team team = board.getTeam(teams.getName());
				team.setPrefix((teams.canRespawn() ? "§a" : "§c"));
				team.setSuffix(teams.getSize() + "");
			}
		}
	}
	
	public static void setScoreboard(Player p) {
		
		Scoreboard board = p.getScoreboard();
		
		board.getObjective("main").unregister();
		Objective score = board.registerNewObjective("main", "dummy");
		
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		score.setDisplayName("§6§lLamaWars");
		
		score.getScore("§1  ").setScore(16);
		score.getScore("§7Karte:").setScore(15);
		score.getScore("§e" + MapManager.mapName).setScore(14);
		score.getScore("§3  ").setScore(13);

		int s = 12;
		
		if(GameManager.status != Status.LOBBY) {

			for(GameTeam teams : GameTeam.values()) {
				if(teams.isRequired(LamaWars.type)) {
					score.getScore("▌ " + teams.getName() + "§7: §e").setScore(s); s --;
				}
			}
			
		} else {
			score.getScore("§7Lamas:").setScore(s); s --;
			score.getScore("/" + LamaWars.type.getMaxPlayers()).setScore(s); s --;
		}
		
		score.getScore("§2  ").setScore(s);
		score.getScore("§6play.lamawiese.net").setScore(s - 1);
		
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		
	}
}