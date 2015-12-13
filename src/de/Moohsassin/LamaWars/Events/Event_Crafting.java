package de.Moohsassin.LamaWars.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class Event_Crafting implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		e.setCancelled(true);
	}
	
}
