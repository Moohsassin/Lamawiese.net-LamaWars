package de.Moohsassin.LamaWars.Manager;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.Moohsassin.LamaWars.GameTeam;

public enum ItemManager {

	BRONZE(Material.CLAY_BRICK, 1,  "§6Bronze", null, null, null),
	EISEN(Material.IRON_INGOT, 1,  "§7Eisen", null, null, null),
	GOLD(Material.GOLD_INGOT, 1,  "§eGold", null, null, null),
	
	MENU_BOW(Material.BOW, 1, "§6Bögen", null, null, new String[]{"§7Hier kannst du Bögen kaufen!"}),
	MENU_SWORD(Material.IRON_SWORD, 1,  "§6Schwerter", null, null, new String[]{"§7Hier kannst du Schwerter kaufen!"}),
	MENU_PICKAXE(Material.STONE_PICKAXE, 1, "§6Spitzhacken", null, null, new String[]{"§7Hier kannst du Spitzhacken kaufen!"}),
	MENU_FOOD(Material.COOKED_MUTTON, 1,  "§6Essen", null, null, new String[]{"§7Hier kannst du Essen kaufen!"}),
	MENU_ARMOR(Material.CHAINMAIL_CHESTPLATE, 1,  "§6Rüstung", null, null, new String[]{"§7Hier kannst du Rüstung kaufen!"}),
	MENU_BLOCK(Material.SMOOTH_BRICK, 1,  "§6Blöcke", null, null, new String[]{"§7Hier kannst du Blöcke kaufen!"}),
	MENU_CHEST(Material.CHEST, 1,  "§6Kisten", null, null, new String[]{"§7Hier kannst du Kisten kaufen!"}),
	MENU_POTION(Material.POTION, 1,  "§6Tränke", null, null, new String[]{"§7Hier kannst du Tränke kaufen!"}),
	MENU_SPECIAL(Material.NETHER_STAR, 1,  "§6Lamaspielzeug", null, null, new String[]{"§7Hier kannst du Lamaspielzeug kaufen!"}),
	
	BOWS_1(Material.BOW, 1,  "§eBogen Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§81x §eGold"}),
	BOWS_2(Material.BOW, 1,  "§eBogen Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE}, new int[]{1,1}, new String[]{"§83x §eGold"}),
	BOWS_3(Material.BOW, 1,  "§eBogen Lv.3", new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_INFINITE}, new int[]{1,1,1}, new String[]{"§87x §eGold"}),
	BOWS_4(Material.BOW, 1,  "§eEpiclamashooter", new Enchantment[]{Enchantment.DURABILITY, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_INFINITE, Enchantment.ARROW_KNOCKBACK}, new int[]{1,2,1,1}, new String[]{"§815x §eGold"}),
	BOWS_5(Material.ARROW, 16,  "§ePfeil", null, null, new String[]{"§84x §7Eisen"}),
	
	SWORD_1(Material.STICK, 1,  "§eLamahuf", new Enchantment[]{Enchantment.KNOCKBACK}, new int[]{2}, new String[]{"§816x §6Bronze"}),
	SWORD_2(Material.WOOD_SWORD, 1,  "§eSchwert Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§816x §6Bronze"}),
	SWORD_3(Material.WOOD_SWORD, 1,  "§eSchwert Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL}, new int[]{1,1}, new String[]{"§82x §7Eisen"}),
	SWORD_4(Material.STONE_SWORD, 1,  "§eSchwert Lv.3", new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL}, new int[]{1,1}, new String[]{"§86x §7Eisen"}),
	SWORD_5(Material.IRON_SWORD, 1,  "§eSchwert Lv.4", new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.KNOCKBACK}, new int[]{1,1,1}, new String[]{"§82x §eGold"}),
	SWORD_6(Material.DIAMOND_SWORD, 1,  "§eSchwert Lv.5", new Enchantment[]{Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.KNOCKBACK}, new int[]{1,2,2}, new String[]{"§86x §eGold"}),
	
	PICKAXE_1(Material.WOOD_PICKAXE, 1,  "§eSpitzhacke Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§88x §6Bronze"}),
	PICKAXE_2(Material.STONE_PICKAXE, 1,  "§eSpitzhacke Lv.2", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§84x §7Eisen"}),
	PICKAXE_3(Material.IRON_PICKAXE, 1,  "§eSpitzhacke Lv.3", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§816x §7Eisen"}),
	PICKAXE_4(Material.GOLD_PICKAXE, 1,  "§eSpitzhacke Lv.4", new Enchantment[]{Enchantment.DURABILITY}, new int[]{3}, new String[]{"§88x §eGold"}),
	
	FOOD_1(Material.CAKE, 1,  "§eMuuuchen", null, null, new String[]{"§82x §7Eisen"}),
	FOOD_2(Material.APPLE, 2,  "§eApfel", null, null, new String[]{"§82x §6Bronze"}),
	FOOD_3(Material.BAKED_POTATO, 1,  "§eDeutsche Ofenkartoffel", null, null, new String[]{"§82x §6Bronze"}),
	FOOD_4(Material.COOKED_MUTTON, 4,  "§eSchnitzel", null, null, new String[]{"§82x §7Eisen"}),
	FOOD_5(Material.GOLDEN_APPLE, 1,  "§eGoldenes Leckerli", null, null, new String[]{"§82x §eGold"}),
	
	ARMOR_1(Material.LEATHER_BOOTS, 1,  "§eGamaschen Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§82x §6Bronze"}),
	ARMOR_2(Material.LEATHER_LEGGINGS, 1,  "§eWaffenrock Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§84x §6Bronze"}),
	ARMOR_3(Material.LEATHER_CHESTPLATE, 1,  "§eHarnisch Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§84x §6Bronze"}),
	ARMOR_4(Material.LEATHER_HELMET, 1,  "§eHelm Lv.1", new Enchantment[]{Enchantment.DURABILITY}, new int[]{1}, new String[]{"§82x §6Bronze"}),
	ARMOR_5(Material.IRON_BOOTS, 1,  "§eGamaschen Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FALL}, new int[]{1,1,1}, new String[]{"§82x §7Eisen"}),
	ARMOR_6(Material.CHAINMAIL_LEGGINGS, 1,  "§eWaffenrock Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}, new int[]{1,1}, new String[]{"§84x §7Eisen"}),
	ARMOR_7(Material.IRON_CHESTPLATE, 1,  "§eHarnisch Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}, new int[]{1,1}, new String[]{"§84x §7Eisen"}),
	ARMOR_8(Material.CHAINMAIL_HELMET, 1,  "§eHelm Lv.2", new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL}, new int[]{1,1}, new String[]{"§82x §7Eisen"}),
	ARMOR_9(Material.GOLD_HELMET, 1,  "§eBauhelm", new Enchantment[]{Enchantment.DURABILITY, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.THORNS}, new int[]{3,3,3}, new String[]{"§82x §eGold"}),
	
	BLOCK_1(Material.HARD_CLAY, 4,  "§eGehärteter Lehm", null, null, new String[]{"§82x §6Bronze"}),
	BLOCK_2(Material.ENDER_STONE, 1,  "§eKäse", null, null, new String[]{"§84x §6Bronze"}),
	BLOCK_3(Material.IRON_BLOCK, 1,  "§eEisenblock", null, null, new String[]{"§82x §7Eisen"}),
	BLOCK_4(Material.WEB, 1,  "§eLamafell", null, null, new String[]{"§84x §6Bronze"}),
	
	CHEST_1(Material.CHEST, 1,  "§eKiste", null, null, new String[]{"§88x §6Bronze"}),
	CHEST_3(Material.ENDER_CHEST, 1,  "§eTeam Kiste", null, null, new String[]{"§82x §eGold"}),
	
	POTION_1(Material.POTION, 1,  "§eLamagene", null, null, new String[]{"§84x §eGold"}),
	POTION_2(Material.POTION, 1,  "§eGummibärchensaft", null, null, new String[]{"§82x §7Eisen"}),
	POTION_3(Material.POTION, 1,  "§eSpeeeeeed", null, null, new String[]{"§88x §7Eisen"}),
	POTION_4(Material.POTION, 1,  "§eMedipack", null, null, new String[]{"§832x §6Bronze"}),
	POTION_5(Material.POTION, 1,  "§eSteroide", null, null, new String[]{"§88x §eGold"}),
	
	SPECIAL_1(Material.BEACON, 1,  "§eRettungsplattform", null, null, new String[]{"§84x §eGold"}),
	SPECIAL_2(Material.TNT, 1,  "§eSprengstoff", null, null, new String[]{"§832x §6Bronze"}),
	SPECIAL_3(Material.FLINT_AND_STEEL, 1,  "§eFeuerzeug", null, null, new String[]{"§82x §7Eisen"}),
	SPECIAL_4(Material.EMPTY_MAP, 1,  "§eHeimweg", null, null, new String[]{"§84x §7Eisen"}),
	SPECIAL_5(Material.FIREBALL, 1,  "§eGranate", null, null, new String[]{"§88x §eGold"}),
	SPECIAL_6(Material.ENDER_PEARL, 1,  "§eTransporter", null, null, new String[]{"§812x §eGold"});
	
	Material m;
	int a;
	public String n;
	public String[] l;
	Enchantment[] e;
	int[] i;
	
	private ItemManager(Material mat, int amount, String name, Enchantment[] ench, int[] level, String[] lore) {
		m = mat;
		a = amount;
		n = name;
		e = ench;
		l = lore;
		i = level;
	}
	
	public int getPotionId() {
		if(n.startsWith("§eLa")) return 8193;
		if(n.startsWith("§eGu")) return 8235;
		if(n.startsWith("§eSp")) return 8226;
		if(n.startsWith("§eMe")) return 8229;
		if(n.startsWith("§eSt")) return 8201;
		return -1;
	}
	
	public ItemStack getPotionStack() {
		ItemStack stack = new ItemStack(m, 1, (byte) getPotionId());
		ItemMeta meta = stack.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add("  ");
		if(l != null) for(String s : l) lore.add(s);
		meta.setLore(lore);
		meta.setDisplayName(n);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public ItemStack getGameItemStack(Player p) {
		ItemStack stack = getInvItemStack(p);
		ItemMeta meta = stack.getItemMeta();
		if(meta.getDisplayName().equalsIgnoreCase(SPECIAL_3.n)) stack.setDurability((short) 60);
		meta.setLore(null);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public ItemStack getInvItemStack(Player p) {
		ItemStack stack = new ItemStack(m, a);
		
		if(n != null && (n.equalsIgnoreCase("§eHelm Lv.1") | n.equalsIgnoreCase("§eGamaschen Lv.1") | n.equalsIgnoreCase("§eWaffenrock Lv.1") | n.equalsIgnoreCase("§eHarnisch Lv.1"))) {
			GameTeam team = GameTeam.getCurrentTeam(p);
			LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
			meta.setColor(Color.fromRGB(team.getParticleColor().getRed(), team.getParticleColor().getGreen(), team.getParticleColor().getBlue()));
			stack.setItemMeta(meta);
		}
		if(m.equals(Material.POTION) && getPotionId() != -1) stack = getPotionStack();
		
		ItemMeta meta = stack.getItemMeta();
		
		int j = 0;
		if(e != null) for(Enchantment ench : e) {meta.addEnchant(ench, i[j], true); j++;}
		
		ArrayList<String> lore = new ArrayList<>();	lore.add("  ");
		if(l != null) for(String s : l) lore.add(s);
		meta.setLore(lore);
		meta.setDisplayName(n);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public void buyItem(Player p) {
		if(hasEnoughItemsToBuy(p)) {
			int amount = Integer.valueOf(l[0].split("x ")[0].substring(2));
			String name = l[0].split("x ")[1].substring(2);
			Material mat;
			if(name.equalsIgnoreCase("eisen")) mat = Material.IRON_INGOT;
			else if(name.equalsIgnoreCase("bronze")) mat = Material.CLAY_BRICK;
			else mat = Material.GOLD_INGOT;
			
			for(ItemStack stack : p.getInventory().getContents()) {
				if(stack == null) continue;
				if(stack.getType() == Material.AIR) continue;
				if(stack.getType() == mat) {
					if(!stack.getItemMeta().hasLore()) {
						if(amount == 0) continue;
						if(stack.getAmount() > amount) {
							stack.setAmount(stack.getAmount()-amount);
							amount = 0;
							p.getInventory().addItem(getGameItemStack(p));
							p.updateInventory();
						} else if(stack.getAmount() == amount) {
							p.getInventory().remove(stack);
							amount = 0;
							p.getInventory().addItem(getGameItemStack(p));
							p.updateInventory();
						} else {
							amount -= stack.getAmount();
							p.getInventory().remove(stack);
							p.updateInventory();
						}
					}
				}
			}
			p.playSound(p.getEyeLocation(), Sound.CHICKEN_EGG_POP, 1f, 1f);
		} else {
			p.playSound(p.getEyeLocation(), Sound.NOTE_BASS, 1f, 1f);
		}
	}
	
	public boolean hasEnoughItemsToBuy(Player p) {

		int amount = Integer.valueOf(l[0].split("x ")[0].substring(2));
		String name = l[0].split("x ")[1].substring(2);
		Material mat;
		if(name.equalsIgnoreCase("eisen")) mat = Material.IRON_INGOT;
		else if(name.equalsIgnoreCase("bronze")) mat = Material.CLAY_BRICK;
		else mat = Material.GOLD_INGOT;
		
		int i = 0;
		for(ItemStack stack : p.getInventory().getContents()) {
			if(stack == null) continue;
			if(stack.getType() == Material.AIR) continue;
			if(stack.getType() == mat) {
				if(!stack.getItemMeta().hasLore()) {
					i += stack.getAmount();
				}
			}
		}
		
		if(i >= amount) return true;
		
		return false;
	}
}
