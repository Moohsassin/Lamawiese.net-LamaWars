package de.Moohsassin.LamaWars.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.ItemManager;
import de.Moohsassin.LamaWars.Manager.MapManager;

public class Event_Building implements Listener {

	public static ArrayList<Location> blocks = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		if(e.getPlayer().getItemInHand().getType() == Material.BED) {
			e.getPlayer().setItemInHand(null);
			e.getPlayer().updateInventory();
			return;
		}
		if(e.getBlock().getType() == Material.TNT) return;
		
		Location center = Bukkit.getWorld("MAP").getHighestBlockAt(MapManager.locations.get("Spectator")).getLocation();
		Location loc = e.getBlock().getLocation();
		Player p = e.getPlayer();
		
		if(loc.distance(center) < 150) blocks.add(loc);
		else {
			p.teleport(e.getBlockAgainst().getLocation().add(new Vector(0.5, 1.3, 0.5)));
			p.sendMessage(LamaWars.pr + "§cDu hast das Ende der Welt erreicht!");
			p.playEffect(p.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, 15);
			p.playSound(p.getEyeLocation(), Sound.LAVA_POP, 10f, 1f);
			
			e.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(e.getBlock().getType() == Material.BED_BLOCK) return;
		
		Location loc = e.getBlock().getLocation();
		
		if(blocks.contains(loc)) blocks.remove(loc);
		else {
			e.setCancelled(true);
			return;
		}
		
		if(e.getBlock().getType() == Material.HARD_CLAY) {
			ItemStack is = ItemManager.BLOCK_1.getGameItemStack(e.getPlayer()); is.setAmount(1);
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), is);
			e.getBlock().setType(Material.AIR);
		}
		if(e.getBlock().getType() == Material.ENDER_STONE) {
			ItemStack is = ItemManager.BLOCK_2.getGameItemStack(e.getPlayer()); is.setAmount(1);
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), is);
			e.getBlock().setType(Material.AIR);
		}
		if(e.getBlock().getType() == Material.IRON_BLOCK) {
			ItemStack is = ItemManager.BLOCK_3.getGameItemStack(e.getPlayer()); is.setAmount(1);
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), is);
			e.getBlock().setType(Material.AIR);
		}
		if(e.getBlock().getType() == Material.WEB) {
			ItemStack is = ItemManager.BLOCK_4.getGameItemStack(e.getPlayer()); is.setAmount(1);
			e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), is);
			e.getBlock().setType(Material.AIR);
		}
		
	}
	
	@EventHandler
	public void onItemFrame(HangingBreakEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		
		for(Block b : e.blockList()) {
			
			if(blocks.contains(b.getLocation())) {
				blocks.remove(b.getLocation());
				b.getLocation().getBlock().setType(Material.AIR);
			}
			
		}
		
		e.blockList().clear();
		
	}
	
}
