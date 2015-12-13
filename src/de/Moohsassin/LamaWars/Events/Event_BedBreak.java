package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Methods;
import de.Moohsassin.LamaWars.Stats;
import de.Moohsassin.LamaWars.Manager.MapManager;
import de.Moohsassin.LamaWars.Manager.ScoreboardManager;

public class Event_BedBreak implements Listener {

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if(e.getItem().getItemStack().getType() == Material.BED) {
			e.getItem().remove();
		}
	}
	
	@EventHandler
	public void onBreak(final BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.BED_BLOCK) {
			
			GameTeam closest = null;
			double distance = 50000;
			for(GameTeam teams : GameTeam.values()) {
				if(teams.isRequired(LamaWars.type)) {

					double dis = MapManager.locations.get(teams.getName()).distanceSquared(e.getBlock().getLocation());
					if(dis < distance) {
						distance = dis;
						closest = teams;
					}
					
				}
			}
			
			Player p = e.getPlayer();
			GameTeam t = GameTeam.getCurrentTeam(p);
			
			if(closest.equals(t)) e.setCancelled(true);
			else {
				
				new BukkitRunnable() {@Override public void run() {
					for(Entity en : Bukkit.getWorld("MAP").getNearbyEntities(e.getBlock().getLocation(), 2, 2, 2)) {
						if(en instanceof Item) {
							Item item = (Item) en;
							if(item.getItemStack().getType() == Material.BED) item.remove();
						}
					}
				}}.runTaskLater(LamaWars.instance, 1);
				
				Bukkit.broadcastMessage(LamaWars.pr + t.getColor() + p.getName() + "§7 hat das Bett von Team " + closest.getName() + " §7zerstört!");
				
				GameTeam.cantRespawn.add(closest.getName());
				
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(GameTeam.isInTeam(players)) {
						if(GameTeam.getCurrentTeam(players).equals(closest)) Methods.sendTitle(players, "§cAchtung!", "§7Dein Bett wurde zerstört!", 10, 0, 0);
					}
					players.playSound(players.getEyeLocation(), Sound.WITHER_SPAWN, 1f, 1f);
					ScoreboardManager.updateScoreboard(players);
				}
				
				Stats.addStats(p.getUniqueId(), 0, 0, 0, 0, 1);
				
			}
			
		}
	}
	
}
