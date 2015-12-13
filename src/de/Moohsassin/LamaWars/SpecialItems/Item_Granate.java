package de.Moohsassin.LamaWars.SpecialItems;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.Moohsassin.LamaWars.LamaWars;

public class Item_Granate implements Listener {

	public static ItemStack getToThrow() {
		ItemStack toThrow = new ItemStack(Material.FIREBALL);
		ItemMeta meta = toThrow.getItemMeta();
		meta.setDisplayName("§cDONT_PICKUP" + UUID.randomUUID());
		toThrow.setItemMeta(meta);
		return toThrow;
	}
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		Item item = e.getItem();
		if(item.getItemStack().getItemMeta() != null && item.getItemStack().getItemMeta().hasDisplayName()) {
			if(item.getItemStack().getItemMeta().getDisplayName().startsWith("§cDONT_PICKUP")) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType().equals(Material.FIREBALL)) {
				
				e.setCancelled(true);
				
				if(p.getItemInHand().getAmount() == 1) p.setItemInHand(null);
				else p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
						
				p.updateInventory();
						
				final Item item = p.getWorld().dropItem(p.getEyeLocation(), getToThrow());
				item.setVelocity(p.getEyeLocation().getDirection().multiply(0.8).setY(0.4));
						
				new BukkitRunnable() {@Override public void run() {
					if(item != null) {
						TNTPrimed tnt = item.getWorld().spawn(item.getLocation(), TNTPrimed.class);
						tnt.setFuseTicks(0);
						item.remove();
					}
				}}.runTaskLater(LamaWars.instance, 20 * 5);
				
			}
		}
	}
}
