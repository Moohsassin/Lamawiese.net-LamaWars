package de.Moohsassin.LamaWars.Manager;

import org.bukkit.Bukkit;

import de.Moohsassin.LamaWars.LamaWars;
import de.Moohsassin.LamaWars.MySQL;

public class ServerManager {

	public static MySQL sql = null;
	public static GameStatus status = GameStatus.LOBBY;
	
	public static void update() {
		
		if(sql == null) {
			try {
				sql = new MySQL("GeneralDatas");
				sql.openConnection();
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
		
		try {
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS Server (Name VARCHAR(40), Status INT, Current INT, Max INT, Map VARCHAR(50))");
			sql.queryUpdate("DELETE FROM Server WHERE Name ='" + Bukkit.getServer().getMotd() + "'");
			sql.queryUpdate("INSERT INTO Server (Name,Status,Current,Max,Map) VALUES ('" + Bukkit.getServer().getMotd() + "','" + status.getValue() + "','" + Bukkit.getOnlinePlayers().size() + "','" + LamaWars.type.getMaxPlayers() + "','" + MapManager.mapName + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setGameStatus(GameStatus s) {
		status = s;
		update();
	}
	
	public static void closeConnection() {
		sql.closeConnection();
	}
	
	public enum GameStatus {
		LOBBY(1),
		WARMPUP(2),
		INGAME(3),
		DEATHMATCH(4),
		ENDING(5);
		
		public int value;
		
		private GameStatus(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static GameStatus fromInt(int i) {
			for(GameStatus gs : GameStatus.values()) {
				if(gs.getValue() == i) return gs;
			}
			return null;
		}
	}
}
