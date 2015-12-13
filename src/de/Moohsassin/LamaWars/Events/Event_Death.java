package de.Moohsassin.LamaWars.Events;

import java.util.concurrent.Executors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Stats;

public class Event_Death implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		final Player p = e.getEntity();
		GameTeam team = GameTeam.getCurrentTeam(p);
		
		e.getDrops().clear();
		
		Player killer = null;
		if(p.getKiller() instanceof Player) {
			killer = (Player) p.getKiller();
		}
		
		if(killer == null) e.setDeathMessage(team.getColor() + p.getName() + "§7 ist gestorben!");
		else {
			GameTeam tk = GameTeam.getCurrentTeam(killer);
			e.setDeathMessage(team.getColor() + p.getName() + " §7wurde von " + tk.getColor() + killer.getName() + " §7getötet!");
		}
		
		if(!team.canRespawn()) {
			
			Event_Respawn.checkTeam(team);
			
			final Player k = killer;
			
			Executors.newCachedThreadPool().execute(new Runnable() {public void run() {
				Stats.addStats(p.getUniqueId(), 0, 1, 0, 1, 0);
				if(k != null) Stats.addStats(k.getUniqueId(), 0, 0, 1, 0, 0);
			}});
			
		}
		
		new BukkitRunnable() {@Override public void run() {
			
			p.spigot().respawn();
			
		}}.runTaskLater(LamaWars.instance, 3);
		
	}
	
}
