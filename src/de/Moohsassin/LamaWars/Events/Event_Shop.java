package de.Moohsassin.LamaWars.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.Moohsassin.LamaWars.GameTeam;
import de.Moohsassin.LamaWars.Manager.ItemManager;

public class Event_Shop implements Listener {

	private Inventory getTopMenu(Player p) {

		ItemStack is = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7); 
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("");
		is.setItemMeta(im);
		
		Inventory inv = Bukkit.createInventory(p, 9 * 2, "§oShop-Hauptmenu");
		for(int i = 0; i < inv.getSize(); i ++) {
			inv.setItem(i, is);
		}
		
		inv.setItem(0, ItemManager.MENU_BOW.getInvItemStack(p));
		inv.setItem(1, ItemManager.MENU_SWORD.getInvItemStack(p));
		inv.setItem(2, ItemManager.MENU_PICKAXE.getInvItemStack(p));
		inv.setItem(3, ItemManager.MENU_FOOD.getInvItemStack(p));
		inv.setItem(4, ItemManager.MENU_ARMOR.getInvItemStack(p));
		inv.setItem(5, ItemManager.MENU_BLOCK.getInvItemStack(p));
		inv.setItem(6, ItemManager.MENU_CHEST.getInvItemStack(p));
		inv.setItem(7, ItemManager.MENU_POTION.getInvItemStack(p));
		inv.setItem(8, ItemManager.MENU_SPECIAL.getInvItemStack(p));
		return inv;
	}
	
	private Inventory bowMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		for(int i = 1; i <= 4; i ++) inv.setItem(i + 10, ItemManager.valueOf("BOWS_" + i).getInvItemStack(p));
		inv.setItem(16, ItemManager.BOWS_5.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory swordMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(10, ItemManager.SWORD_1.getInvItemStack(p));
		for(int i = 2; i <= 6; i ++) inv.setItem(i + 10, ItemManager.valueOf("SWORD_" + i).getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory pickaxeMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(11, ItemManager.PICKAXE_1.getInvItemStack(p));
		inv.setItem(12, ItemManager.PICKAXE_2.getInvItemStack(p));
		inv.setItem(13, ItemManager.PICKAXE_3.getInvItemStack(p));
		inv.setItem(15, ItemManager.PICKAXE_4.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory foodMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(10, ItemManager.FOOD_1.getInvItemStack(p));
		inv.setItem(12, ItemManager.FOOD_2.getInvItemStack(p));
		inv.setItem(13, ItemManager.FOOD_3.getInvItemStack(p));
		inv.setItem(14, ItemManager.FOOD_4.getInvItemStack(p));
		inv.setItem(16, ItemManager.FOOD_5.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory armorMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		for(int i = 1; i <= 9; i ++) inv.setItem(i + 8, ItemManager.valueOf("ARMOR_" + i).getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory blockMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(11, ItemManager.BLOCK_1.getInvItemStack(p));
		inv.setItem(12, ItemManager.BLOCK_2.getInvItemStack(p));
		inv.setItem(13, ItemManager.BLOCK_3.getInvItemStack(p));
		inv.setItem(15, ItemManager.BLOCK_4.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory chestMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(11, ItemManager.CHEST_1.getInvItemStack(p));
		inv.setItem(15, ItemManager.CHEST_3.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory potionMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(10, ItemManager.POTION_1.getInvItemStack(p));
		inv.setItem(12, ItemManager.POTION_2.getInvItemStack(p));
		inv.setItem(13, ItemManager.POTION_3.getInvItemStack(p));
		inv.setItem(14, ItemManager.POTION_4.getInvItemStack(p));
		inv.setItem(16, ItemManager.POTION_5.getInvItemStack(p));
		
		return inv;
	}
	
	private Inventory specialMenu(Player p) {
		Inventory inv = getTopMenu(p);
		
		inv.setItem(10, ItemManager.SPECIAL_1.getInvItemStack(p));
		for(int i = 2; i <= 6; i ++) inv.setItem(i + 10, ItemManager.valueOf("SPECIAL_" + i).getInvItemStack(p));
		
		return inv;
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		
		if(!GameTeam.isInTeam(e.getPlayer())) {
			e.setCancelled(true);
			return;
		}
		
		if(e.getRightClicked() instanceof Villager) {
			e.setCancelled(true);
			e.getPlayer().closeInventory();
			e.getPlayer().openInventory(getTopMenu(e.getPlayer()));	
		}	
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory().getTitle().startsWith("§oShop-Hauptmenu")) {
			if(e.getCurrentItem() != null) {
				if(e.getCurrentItem().getType() != Material.AIR | e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
					
					e.setCancelled(true);

					if(!e.getCurrentItem().hasItemMeta()) return;
					if(e.getCurrentItem().getItemMeta().hasLore()) {
						
						if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§6")) {
							
							p.playSound(p.getEyeLocation(), Sound.CLICK, 1f, 1f);
							
							if(e.getCurrentItem().equals(ItemManager.MENU_BOW.getInvItemStack(p))) p.openInventory(bowMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_SWORD.getInvItemStack(p))) p.openInventory(swordMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_PICKAXE.getInvItemStack(p))) p.openInventory(pickaxeMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_FOOD.getInvItemStack(p))) p.openInventory(foodMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_ARMOR.getInvItemStack(p))) p.openInventory(armorMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_BLOCK.getInvItemStack(p))) p.openInventory(blockMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_CHEST.getInvItemStack(p))) p.openInventory(chestMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_POTION.getInvItemStack(p))) p.openInventory(potionMenu(p));
							else if(e.getCurrentItem().equals(ItemManager.MENU_SPECIAL.getInvItemStack(p))) p.openInventory(specialMenu(p));
							
						} else {
							
							for(ItemManager manager : ItemManager.values()) {
								if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(manager.n)) {
									if(e.getClick().isShiftClick()) {
										if(manager.name().startsWith("ARMOR") | manager.name().startsWith("SWORD") | manager.name().startsWith("PICKAXE")) manager.buyItem(p);
										else {
											int times = 64 / manager.getGameItemStack(p).getAmount();
											for(int i = 0; i < times; i ++) manager.buyItem(p);
										}
									} else manager.buyItem(p);
								}
							}
							
						}
						
					}
					
				}
			}
		}
	}
	
}