package de.Moohsassin.LamaWars.Events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;
import de.Moohsassin.LamaWars.Manager.MapManager;

public class Event_Damage implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(GameManager.status != Status.INGAME) { 
			e.setCancelled(true);
			return;
		}
		
		if(e.getEntity() instanceof Villager) {
			e.setCancelled(true);
		}
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if(GameTeam.getCurrentTeam(p).equals(GameTeam.getCurrentTeam(d))) e.setCancelled(true);
			
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if(GameManager.status != Status.INGAME) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getCause() == DamageCause.VOID) {
			if(e.getEntity() instanceof Player) {
				
				Player p = (Player) e.getEntity();
				
				if(GameTeam.isInTeam(p)) p.setHealth(2);
				else p.teleport(MapManager.locations.get("Spectator"));
				
			}
		}
	}
	
}
