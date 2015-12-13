package de.Moohsassin.LamaWars.SpecialItems;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.ItemManager;

public class Item_Chests implements Listener {

	public static HashMap<GameTeam, ArrayList<Location>> GameTeamChests = new HashMap<>();
	HashMap<GameTeam, Inventory> GameTeamInv = new HashMap<>();
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if(e.getBlock().getType() == Material.ENDER_CHEST) {
			
			GameTeam t = GameTeam.getCurrentTeam(e.getPlayer());
			ArrayList<Location> locs = GameTeamChests.get(t);
			if(locs == null) locs = new ArrayList<>();
			
			locs.add(e.getBlock().getLocation());
			
			GameTeamChests.put(t, locs);
			
			e.getPlayer().sendMessage(LamaWars.pr + "GameTeamkiste erstellt!");
			
		}
		
	}
	
	@EventHandler
	public void onOpen(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				
				Player p = e.getPlayer();
				e.setCancelled(true);
				
				GameTeam t = null;
				for(GameTeam GameTeams : GameTeamChests.keySet()) {
					if(GameTeamChests.get(GameTeams).contains(e.getClickedBlock().getLocation())) t = GameTeams;
				}
				
				if(t != GameTeam.getCurrentTeam(p)) {
					p.sendMessage(LamaWars.pr + "Diese Kiste gehört nicht zu deinem GameTeam!");
					return;
				}
				
				Inventory inv = GameTeamInv.get(t);
				if(inv == null) {
					inv = Bukkit.createInventory(null, 9 * 3, "GameTeamkiste");
					GameTeamInv.put(t, inv);
				}
				
				p.openInventory(inv);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(e.getBlock().getType() == Material.ENDER_CHEST) {
			
			GameTeam t = null;
			for(GameTeam GameTeams : GameTeamChests.keySet()) {
				if(GameTeamChests.get(GameTeams).contains(e.getBlock().getLocation())) t = GameTeams;
			}
			
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(GameTeam.isInTeam(players) && GameTeam.getCurrentTeam(players).equals(t)) {
					players.sendMessage(LamaWars.pr + "Eine eurer GameTeamkisten wurde zerstört!");
					players.playSound(players.getEyeLocation(), Sound.ANVIL_USE, 1f, 1f);
				}
			}
			
			e.getBlock().setType(Material.AIR);
			
			ItemStack is = ItemManager.CHEST_3.getGameItemStack(e.getPlayer()); is.setAmount(1);
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), is);
			e.getBlock().setType(Material.AIR);
			
			ArrayList<Location> locs = GameTeamChests.get(t);
			locs.remove(e.getBlock().getLocation());
			
			if(locs.isEmpty()) GameTeamChests.remove(t);
			else GameTeamChests.put(t, locs);
			
		}
		
	}
	
}
