package de.Moohsassin.LamaWars;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

public enum GameTeam {

	Rot("§cRot", 2, new Color(255, 85, 85), 14),
	Blau("§bBlau", 2, new Color(85, 255, 255), 3),
	Grün("§aGrün", 4, new Color(85, 255, 85), 13),
	Gelb("§eGelb", 4, new Color(255, 255, 85), 4),
	
	Lila("§5Lila", 8, new Color(170, 0, 170), 10),
	Weis("§fWeis", 8, new Color(255, 255, 255), 0),
	Grau("§7Grau", 8, new Color(170, 170, 170), 9),
	Rosa("§dRosa", 8, new Color(255, 85, 255), 2);
	
	String name;
	int required;
	Color color;
	int cc;
	
	public static HashMap<String, ArrayList<String>> teams = new HashMap<>();
	public static ArrayList<String> cantRespawn = new ArrayList<>();
	
	private GameTeam(String name, int required, Color c, int clayColor) {
		this.name = name;
		this.required = required;
		this.color = c;
		this.cc = clayColor;
	}
	
	public int getClayColor() {
		return this.cc;
	}
	
	public Color getParticleColor() {
		return this.color;
	}
	
	public String getName() {
		return this.name;
	}
	 
	public String getColor() {
		String toSplit = this.name;
		return toSplit.substring(0, 2);
	}
	
	public int getSize() {
		if(teams.containsKey(this.name)) return teams.get(this.name).size();
		return 0;
	}
	
	public boolean canRespawn() {
		return !cantRespawn.contains(this.name);
	}
	
	public boolean isRequired(GameType type) {
		int teams = Integer.valueOf(type.getShorted().split("x")[0]);
		return this.required <= teams;
	}
	
	public static GameTeam getTeam(String name) {
		for(GameTeam teams : GameTeam.values()) {
			if(teams.getName().equalsIgnoreCase(name)) return teams;
		}
		return null;
	}
	
	public static GameTeam getEmptiestTeam(GameType type) {
		int size = 100;
		GameTeam toReturn = GameTeam.Rot;
		
		for(GameTeam team : GameTeam.values()) {
			if(team.isRequired(type)) {
				if(!teams.containsKey(team.getName())) return team;
				else {
					int i = teams.get(team.getName()).size();
					if(i < size) {
						size = i;
						toReturn = team;
					}
				}
			}
		}
		
		return toReturn;
	}
	
	public static boolean isInTeam(Player p) {
		for(ArrayList<String> values : teams.values()) {
			if(values.contains(p.getName())) return true;
		}
		return false;
	}
	
	public static GameTeam getCurrentTeam(Player p) {
		if(isInTeam(p)) {
			for(String team : teams.keySet()) {
				if(teams.get(team).contains(p.getName())) return getTeam(team);
			}
		}
		return null;
	}
	
	public static void removePlayerFromCurrentTeam(Player p) {
		GameTeam t = getCurrentTeam(p);
		if(t != null) {
			ArrayList<String> players = teams.get(t.getName());
			players.remove(p.getName());
			if(players.isEmpty()) teams.remove(t.getName());
			else teams.put(t.getName(), players);
		}
	}
	
	public void addPlayer(Player p) {
		
		removePlayerFromCurrentTeam(p);
		
		ArrayList<String> players = teams.get(name);
		if(players == null) players = new ArrayList<>();
		players.add(p.getName());
		teams.put(name, players);
	}
	
}
 