package de.Moohsassin.LamaWars.SpecialItems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import de.Moohsassin.LamaWars.Events.Event_Building;

public class Item_Rettungsplatform implements Listener {

	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_AIR) {
			
			if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType().equals(Material.BEACON)) {
						
				e.setCancelled(true);
						
				if(p.getItemInHand().getAmount() == 1) p.setItemInHand(null);
				else p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
						
				p.updateInventory();
						
				Location pl = p.getLocation().add(new Vector(0, -3, 0));
				
				for(int x = -1; x < 2; x ++) { for(int z = -1; z < 2; z ++) {
					
					Location l = pl.clone().add(x, 0, z).getBlock().getLocation();
					
					if(l.getBlock().getType() == Material.AIR ) {
						
						Event_Building.blocks.add(l);
						l.getBlock().setType(Material.STAINED_GLASS);
						
					} else if(Event_Building.blocks.contains(l)) l.getBlock().setType(Material.STAINED_GLASS);
					
				}}
				
				p.setFallDistance(0);
			}
		}
	}
}
