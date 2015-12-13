package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;
import de.Moohsassin.LamaWars.Manager.MapManager;
import de.Moohsassin.LamaWars.Manager.ScoreboardManager;
import de.Moohsassin.LamaWars.Manager.ServerManager;

public class Event_Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.updateInventory();
		
		String jmsg = null;
		if(GameManager.status == Status.LOBBY) {
			jmsg = PlayerDatas.getChatColor(p.getUniqueId()) + p.getName() + "§7 hat die Lobby betreten!";
			GameManager.checkForStart();
			
			p.setGameMode(GameMode.ADVENTURE);
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
			
			ItemStack stack = new ItemStack(Material.BED);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName("§eWähle ein Team");
			stack.setItemMeta(meta);
			
			p.getInventory().addItem(stack);
			
		} else {
			
			p.setGameMode(GameMode.SPECTATOR);
			p.teleport(MapManager.locations.get("Spectator"));
			
		}
		e.setJoinMessage(jmsg);
		
		ScoreboardManager.registerScoreboard(p);
		ScoreboardManager.setColor(p);
		ScoreboardManager.setScoreboard(p);
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.updateScoreboard(players);
		}
		
		ServerManager.update();
		
	}
	
}
