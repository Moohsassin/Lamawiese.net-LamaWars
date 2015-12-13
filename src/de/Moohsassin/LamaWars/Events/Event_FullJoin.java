package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Methods;
import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Manager.ServerManager;
import de.Moohsassin.LamaWars.Manager.ServerManager.GameStatus;

public class Event_FullJoin implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		
		int max = LamaWars.type.getMaxPlayers();
		Player p = e.getPlayer();
		
		if(Bukkit.getOnlinePlayers().size() >= max && ServerManager.status == GameStatus.LOBBY) {
			
			String rank = PlayerDatas.getRank(p.getName());
				
			for(int i = 0; i < PlayerDatas.getRankValue(rank); i ++) {
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(PlayerDatas.getRankValue(PlayerDatas.getRank(players.getName())) == i) {
							
						players.sendMessage("§cDu wurdest gekickt um einem Spieler Platz zu machen!");
						Methods.sendToHub(players);
						
						return;
					}
				}
			}
			
			e.disallow(Result.KICK_FULL, "§cKonnte keinen Spieler zum kicken finden!");
			
		}

		if(ServerManager.status != GameStatus.LOBBY) e.disallow(Result.KICK_OTHER, "§6Lamawiese §8┃ §7Das spiel läuft bereits!");
		
	}
	
}
 