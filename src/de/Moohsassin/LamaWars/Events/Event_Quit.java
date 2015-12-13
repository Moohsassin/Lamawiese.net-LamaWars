package de.Moohsassin.LamaWars.Events;

import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Stats;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;
import de.Moohsassin.LamaWars.Manager.ScoreboardManager;
import de.Moohsassin.LamaWars.Manager.ServerManager;

public class Event_Quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		final Player p = e.getPlayer();
		
		String qmsg = null;
		
		GameTeam t = GameTeam.getCurrentTeam(p);
		if(t != null) {
			GameTeam.removePlayerFromCurrentTeam(p);
			
			if(GameManager.status == Status.INGAME) {

				Event_Respawn.checkTeam(t);
				
				Executors.newCachedThreadPool().execute(new Runnable() {public void run() {
					Stats.addStats(p.getUniqueId(), 0, 1, 0, 0, 0);
				}});
			}
			
		}
		
		if(GameManager.status == Status.LOBBY) {
			qmsg = PlayerDatas.getChatColor(p.getUniqueId()) + p.getName() + "§7 hat die Lobby verlassen!";
		}
		
		new BukkitRunnable() {@Override public void run() {
			
			GameManager.checkForStop();
			for(Player players : Bukkit.getOnlinePlayers()) {
				ScoreboardManager.updateScoreboard(players);
			}
			ServerManager.update();
			
		}}.runTaskLater(LamaWars.instance, 2);
		
		e.setQuitMessage(qmsg);
	}
	
}
