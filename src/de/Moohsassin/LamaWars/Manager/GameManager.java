package de.Moohsassin.LamaWars.Manager;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Methods;
import de.Moohsassin.LamaWars.PlayerDatas;
import de.Moohsassin.LamaWars.Stats;
import de.Moohsassin.LamaWars.Manager.ServerManager.GameStatus;

public class GameManager {

	public enum Status {
		LOBBY, INGAME, ENDING;
	}
	
	static BukkitTask task = null;
	static BukkitTask drops = null;
	
	public static Status status = Status.LOBBY;
	
	public static int countdown = 0;
	
	public static void checkForStop() {
		int required = LamaWars.type.getMaxPlayers() / 2;
		
		if(Bukkit.getOnlinePlayers().size() < required) {
			if(task != null) {
				Bukkit.broadcastMessage(LamaWars.pr + "§cDer Countdown wurde auf Grund von zu wenigen Spielern gestoppt!");
				task.cancel();
				task = null;
			}
		}
	}
	
	static void preparePlayers() {
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(!GameTeam.isInTeam(players)) {
				GameTeam.getEmptiestTeam(LamaWars.type).addPlayer(players);
				ScoreboardManager.setColor(players);
			}
			players.teleport(MapManager.locations.get(GameTeam.getCurrentTeam(players).getName()));
			players.getInventory().clear();
			players.setGameMode(GameMode.SURVIVAL);
		}
		for(Player players : Bukkit.getOnlinePlayers()) {
			ScoreboardManager.setScoreboard(players);
			ScoreboardManager.updateScoreboard(players);
		}
	}
	
	static void startToDropItems() {
		
		drops = new BukkitRunnable() {
			
			int time  = 0;

			@Override
			public void run() {
				
				time ++;

				// BRONZE
				for(Location locs : MapManager.drops.get("Bronze")) {
					Location loc = locs.clone();
					if(!loc.add(new Vector(0, 1, 0)).getBlock().getType().isTransparent()) loc.add(new Vector(0, -2, 0));
					loc.getWorld().dropItem(loc, ItemManager.BRONZE.getGameItemStack(null));
				}
				
				if(time % 10 == 0) {
					// EISEN
					for(Location locs : MapManager.drops.get("Iron")) {
						Location loc = locs.clone();
						if(!loc.add(new Vector(0, 1, 0)).getBlock().getType().isTransparent()) loc.add(new Vector(0, -2, 0));
						loc.getWorld().dropItem(loc, ItemManager.EISEN.getGameItemStack(null));
					}
				}
				
				if(time == 60) {
					time = 0;
					// GOLD
					for(Location locs : MapManager.drops.get("Gold")) {
						Location loc = locs.clone();
						if(!loc.add(new Vector(0, 1, 0)).getBlock().getType().isTransparent()) loc.add(new Vector(0, -2, 0));
						loc.getWorld().dropItem(loc, ItemManager.GOLD.getGameItemStack(null));
					}
				}
				
			}
		}.runTaskTimer(LamaWars.instance, 20, 20);
		
	}
	
	public static void endGame() {
		drops.cancel();
		
		status = Status.ENDING;
		
		ServerManager.setGameStatus(GameStatus.ENDING);
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setGameMode(GameMode.SPECTATOR);
		}
		
		GameTeam team = null;
		for(GameTeam teams : GameTeam.values()) {
			if(teams.getSize() > 0) team = teams;
		}
		
		Bukkit.broadcastMessage("  ");
		Bukkit.broadcastMessage(LamaWars.pr + "Der Gewinner steht fest!");
		Bukkit.broadcastMessage(LamaWars.pr + "Das beste Team in dieser Runde war " + team.getName());
		Bukkit.broadcastMessage("  ");
		
		final GameTeam gTeam = team;
		Executors.newCachedThreadPool().execute(new Runnable() { public void run() {
			for(Player players : Bukkit.getOnlinePlayers()) {

				int coins = 50;
				players.sendMessage("  ");
				players.sendMessage("§aFür deine Teilnahme erhältst du §l500 XP");
				
				if(GameTeam.teams.get(gTeam.getName()).contains(players.getName())) {
					players.sendMessage(LamaWars.pr + "§eHerzlichen Glückwunsch zu deinem Sieg!");
					players.sendMessage("§e§l+" + coins + " §7- Gewinner Team");
					coins = 50;
					Stats.addStats(players.getUniqueId(), 1, 0, 0, 0, 0);
				}
				
				PlayerDatas.addPoints(players, coins, 500);
				
			}
		}});
		
		Bukkit.broadcastMessage("  ");
		Bukkit.broadcastMessage("§c§lDer Server startet in 10 Sekunden neu!");
		Bukkit.broadcastMessage("  ");
		
		new BukkitRunnable() {@Override public void run() {
			
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.sendMessage("§cStarte Server neu...");
				Methods.sendToHub(players);
			}
			
		}}.runTaskLater(LamaWars.instance, 10 * 20);
		
		new BukkitRunnable() {@Override public void run() {
			
			Bukkit.unloadWorld("MAP", false);
			try {
				FileUtils.deleteDirectory(new File("MAP"));
				FileUtils.deleteDirectory(new File("logs"));
				FileUtils.deleteDirectory(new File("world/playerdata"));
				FileUtils.deleteDirectory(new File("world/stats"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Bukkit.spigot().restart();
			
		}}.runTaskLater(LamaWars.instance, 12 * 20);
		
	}
	
	public static void checkForStart() {
		
		int required = LamaWars.type.getMaxPlayers() / 2;
		
		if(Bukkit.getOnlinePlayers().size() >= required && status == Status.LOBBY) {
			
			if(task == null) {
				
				task = new BukkitRunnable() {int time = 31; @Override public void run() {

					countdown = time;
						
					if((time % 10 == 0 | time <= 3) && time > 0) {
						Bukkit.broadcastMessage(LamaWars.pr + "Das Spiel startet in §e" + time + " Sekunde" + (time != 1 ? "n" : "") + "!");
						for(Player players : Bukkit.getOnlinePlayers()) players.playSound(players.getEyeLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
					}
					
					if(time == 0) Bukkit.broadcastMessage(LamaWars.pr + "§eMöge das beste Team gewinnen!");
						
					if(time == -1) {
						cancel();
						task = null;
						status = Status.INGAME;
						
						ServerManager.setGameStatus(GameStatus.INGAME);
						
						preparePlayers();
						startToDropItems();
					}
					
					time --;
					
				}}.runTaskTimer(LamaWars.instance, 20, 20);	
			}	
		}	
	}
}
