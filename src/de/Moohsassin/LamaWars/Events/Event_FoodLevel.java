package de.Moohsassin.LamaWars.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;

public class Event_FoodLevel implements Listener {

	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		if(GameManager.status != Status.INGAME) e.setFoodLevel(20);
	}
	
}
