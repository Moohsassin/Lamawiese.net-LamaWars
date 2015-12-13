package de.Moohsassin.LamaWars;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import de.Moohsassin.LamaWars.Commands.Command_stats;
import de.Moohsassin.LamaWars.Manager.MapManager;
import de.Moohsassin.LamaWars.Manager.ServerManager;
import de.Moohsassin.LamaWars.Manager.ServerManager.GameStatus;

public class LamaWars extends JavaPlugin {

	public static Plugin instance;
	public static GameType type;
	public static String pr = "§6LamaWars §8┃ §7";
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		registerEvents();
		registerCommands();

		PlayerDatas.openConnection();
		Stats.openConnection();

		MapManager.loadMap();
		
		ServerManager.setGameStatus(GameStatus.LOBBY);
	}
	
	@Override
	public void onDisable() {
		ServerManager.closeConnection();
		PlayerDatas.closeConnection();
		Stats.closeConnection();
	}
	
	private void registerEvents() {
		try {
			PluginManager pm = Bukkit.getPluginManager();
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses("de.Moohsassin.LamaWars.Events")) {
				Class<?> clazz = Class.forName(classInfo.getName());
				if(Listener.class.isAssignableFrom(clazz)) {
					pm.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
			for(ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader()).getTopLevelClasses("de.Moohsassin.LamaWars.SpecialItems")) {
				Class<?> clazz = Class.forName(classInfo.getName());
				if(Listener.class.isAssignableFrom(clazz)) {
					pm.registerEvents((Listener) clazz.newInstance(), this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void registerCommands() {
		getCommand("stats").setExecutor(new Command_stats());
	}
	
}
