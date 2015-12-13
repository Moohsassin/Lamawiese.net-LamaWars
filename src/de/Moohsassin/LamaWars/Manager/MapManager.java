package de.Moohsassin.LamaWars.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import de.Moohsassin.LamaWars.GameType;
import de.Moohsassin.LamaWars.LamaWars;

public class MapManager {

	public static HashMap<String, Location> locations = new HashMap<>();
	public static HashMap<String, ArrayList<Location>> drops = new HashMap<>();
	public static String mapName = "";
	
	public static void loadMap() {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("/minecraft/Games/LamaWars", "maps.yml"));
		ArrayList<String> gTypes = new ArrayList<>();
		for(String s : cfg.getConfigurationSection("Type").getKeys(true)) {
			gTypes.add(s);
		}
		
		LamaWars.type = GameType.getTypeByString(gTypes.get(new Random().nextInt(gTypes.size())));
		
		if(LamaWars.type == null) {
			Bukkit.spigot().restart();
		}
		
		List<String> maps = cfg.getStringList("Type." + LamaWars.type.getShorted());
		mapName = maps.get(new Random().nextInt(maps.size()));
		
		try {
			FileUtils.copyDirectory(new File("/minecraft/Games/LamaWars/Maps/" + mapName), new File("MAP"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		loadLocations();
		
	}
	
	static void loadLocations() {
		File file = new File("/minecraft/Games/LamaWars/" + LamaWars.instance.getServer().getMotd() + "/MAP", "locations.yml");
		
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		World w = Bukkit.createWorld(new WorldCreator("MAP"));
		
		// Spectator
		String[] spec = cfg.getString("Spectator").split(";");
		locations.put("Spectator", new Location(w, Integer.valueOf(spec[0]), Integer.valueOf(spec[1]), Integer.valueOf(spec[2])).add(new Vector(0.5, 0, 0.5)));
		
		// Teams
		for(String teams : cfg.getStringList("Teams")) {
			String[] team = teams.split(";");
			locations.put(team[0], new Location(w, Integer.valueOf(team[1]), Integer.valueOf(team[2]), Integer.valueOf(team[3])).add(new Vector(0.5, 0, 0.5)));
		}
		
		// Drops
		for(String paths : cfg.getConfigurationSection("Items").getKeys(true)) {
			
			for(String locations : cfg.getStringList("Items." + paths)) {
				
				String[] coords = locations.split(";");
				ArrayList<Location> locs = drops.get(paths);
				if(locs == null) locs = new ArrayList<>();
				
				locs.add(new Location(w, Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2])).add(new Vector(0.5, 0, 0.5)));
				drops.put(paths, locs);
				
			}
			
		}
		
	}
	
}
