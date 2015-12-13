package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;

@SuppressWarnings("deprecation")
public class Event_Chat implements Listener {

	@EventHandler
	public void onChat(PlayerChatEvent e) {
		
		Player p = e.getPlayer();
		GameTeam t = GameTeam.getCurrentTeam(p);
		
		e.setCancelled(true);
		
		String name = PlayerDatas.getChatColor(p.getUniqueId()) + p.getName();
		String prefix = "§7§o";
		if(t != null) prefix = t.getName() + " §8┃ ";
		
		String howlPrefix;
		
		if(GameManager.status == Status.LOBBY) howlPrefix = prefix + name;
		else if(t != null) howlPrefix = t.getColor() + p.getName();
		else howlPrefix = prefix + p.getName();
				
		String toSend = howlPrefix + " §8» §7" + e.getMessage();
		
		if(GameManager.status != Status.INGAME) Bukkit.broadcastMessage(toSend);
		
		else if(t == null) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(!GameTeam.isInTeam(players)) players.sendMessage(toSend);
			}
		}
		
		else if(e.getMessage().startsWith("@")) Bukkit.broadcastMessage("§6GLOBAL §8┃ " + toSend.replaceFirst("@", ""));
		
		else {
			
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(GameTeam.isInTeam(players)) {
					if(GameTeam.getCurrentTeam(players).equals(t)) players.sendMessage(toSend);
				}
			}
			
		}
		
	}
	
}
