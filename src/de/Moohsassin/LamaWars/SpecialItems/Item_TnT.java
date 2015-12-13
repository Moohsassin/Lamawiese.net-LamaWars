package de.Moohsassin.LamaWars.SpecialItems;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Item_TnT implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.TNT) {
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PRIMED_TNT);
		}
	}
	
}
