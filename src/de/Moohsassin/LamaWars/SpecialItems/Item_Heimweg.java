package de.Moohsassin.LamaWars.SpecialItems;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.MapManager;

public class Item_Heimweg implements Listener {

	ArrayList<String> didntMove = new ArrayList<>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		if(didntMove.contains(e.getPlayer().getName())) {
			Location f = e.getFrom(), t = e.getTo();
			if(f.getBlockX() != t.getBlockX() | f.getBlockZ() != t.getBlockZ()) {
				didntMove.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage(LamaWars.pr + "§cTeleportation abgebrochen!");
			}
		}
	}
	
	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		
		final Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType().equals(Material.EMPTY_MAP)) {

				e.setCancelled(true);
						
				if(p.getItemInHand().getAmount() == 1) p.setItemInHand(null);
				else p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
						
				p.updateInventory();

				didntMove.add(p.getName());

				p.sendMessage(LamaWars.pr + "§aTeleportation eingeleitet...");
				p.sendMessage(LamaWars.pr + "Nicht bewegen!");
						
				new BukkitRunnable() {int i = 0; @SuppressWarnings("deprecation") @Override public void run() {
								
					if(!didntMove.contains(p.getName())) {
						cancel();
						return;
					}
								
					if(i == 4) {
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						cancel();
						
						didntMove.remove(p.getName());
						p.teleport(MapManager.locations.get(GameTeam.getCurrentTeam(p).getName()));
						p.sendMessage(LamaWars.pr + "§aTeleportation abgeschlossen.");
						
						return;
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						players.playSound(p.getLocation(), Sound.NOTE_PLING, 1f, 1f);
						players.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
					}
					
					i ++;
								
				}}.runTaskTimer(LamaWars.instance, 0, 20);
				
			}
		}
	}
}
