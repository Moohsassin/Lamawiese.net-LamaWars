package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;
import de.Moohsassin.LamaWars.Manager.MapManager;
import de.Moohsassin.LamaWars.Manager.ScoreboardManager;

public class Event_Respawn implements Listener {

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		
		Player p = e.getPlayer();
		GameTeam t = GameTeam.getCurrentTeam(p);
		if(t != null && t.canRespawn()) e.setRespawnLocation(MapManager.locations.get(t.getName()));
		else {
			
			p.setGameMode(GameMode.SPECTATOR);
			e.setRespawnLocation(MapManager.locations.get("Spectator"));
			if(t != null) {
				GameTeam.removePlayerFromCurrentTeam(p);
				
				ScoreboardManager.setColor(p);
				for(Player players : Bukkit.getOnlinePlayers()) ScoreboardManager.updateScoreboard(players);
				
				checkTeam(t);
			}
			
		}
		
	}
	
	public static void checkTeam(GameTeam lastTeam) {
		
		if(GameManager.status != Status.INGAME) return;
		
		if(lastTeam.getSize() == 0) {
			Bukkit.broadcastMessage(LamaWars.pr + "Team " + lastTeam.getName() + " §7wurde vernichtet!");
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.playSound(players.getEyeLocation(), Sound.ENDERDRAGON_GROWL, 1f, 1f);
			}
		}
		
		int i = 0;
		for(GameTeam teams : GameTeam.values()) {
			if(teams.getSize() > 0) i ++;
		}
		
		if(i == 1)  {
			
			GameManager.endGame();
			
		}
		
	}
	
}
