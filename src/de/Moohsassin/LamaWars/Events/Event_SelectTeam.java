package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.Manager.GameManager;
import de.Moohsassin.LamaWars.Manager.GameManager.Status;
import de.Moohsassin.LamaWars.Manager.ScoreboardManager;

public class Event_SelectTeam implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_AIR) {
			
			Player p = e.getPlayer();
			
			if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR && p.getItemInHand().getType() == Material.BED && GameManager.status == Status.LOBBY) {
				
				Inventory inv = Bukkit.createInventory(p, 9, "Wähle ein Team");
				for(GameTeam teams : GameTeam.values()) {
					if(teams.isRequired(LamaWars.type)) {
						ItemStack stack = new ItemStack(Material.STAINED_CLAY, 1, (short) teams.getClayColor());
						ItemMeta meta = stack.getItemMeta();
						meta.setDisplayName(teams.getName());
						stack.setItemMeta(meta);
						inv.addItem(stack);
					}
				}
				
				p.openInventory(inv);
			}	
		}
	}
	
	@EventHandler
	public void onTeamSelect(InventoryClickEvent e) {
		
		if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BED) e.setCancelled(true);
		
		if(e.getInventory().getTitle().equalsIgnoreCase("Wähle ein Team")) {
			
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.STAINED_CLAY) {
				
				e.setCancelled(true);
				
				Player p = (Player) e.getWhoClicked();
				
				p.playSound(p.getEyeLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
				
				String name = e.getCurrentItem().getItemMeta().getDisplayName();
				GameTeam t = GameTeam.valueOf(name.substring(2));
				if(t.getSize() > GameTeam.getEmptiestTeam(LamaWars.type).getSize()) {
					p.closeInventory();
					p.sendMessage(LamaWars.pr + "§cDieses Team ist voll!");
					return;
				}
				
				t.addPlayer(p);
				p.sendMessage(LamaWars.pr + "Du bist nun in Team " + t.getName());
				ScoreboardManager.setColor(p);
			}
			
		}
		
	}
	
}
