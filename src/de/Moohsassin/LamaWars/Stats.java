package de.Moohsassin.LamaWars;

import java.util.UUID;

public class Stats {

	static MySQL sql;
	
	public static void openConnection() {
		try {
			sql = new MySQL("Games");
			sql.openConnection();
			
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS LamaWars (Uuid VARCHAR(36), Wins INT, Looses INT, Kills INT, Deaths INT, Broken INT)");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void closeConnection() {
		sql.closeConnection();
	}
	
	public static int getWins(UUID u) {
		return sql.getInt("LamaWars", "Uuid", u, "Wins");
	}

	public static int getLooses(UUID u) {
		return sql.getInt("LamaWars", "Uuid", u, "Looses");
	}
	
	public static int getKills(UUID u) {
		return sql.getInt("LamaWars", "Uuid", u, "Kills");
	}
	
	public static int getDeaths(UUID u) {
		return sql.getInt("LamaWars", "Uuid", u, "Deaths");
	}
	
	public static int getBrokenBeds(UUID u) {
		return sql.getInt("LamaWars", "Uuid", u, "Broken");
	}
	
	public static void addStats(UUID u, int wins, int looses, int kills, int deaths, int broken) {
		
		if(getWins(u) < 0) sql.queryUpdate("INSERT INTO LamaWars (Uuid,Wins,Looses,Kills,Deaths,Broken) VALUES ('" + u + "','" + wins + "','" + looses + "','" + kills + "','" + deaths + "','" + broken + "')");
		
		wins += getWins(u);
		looses += getLooses(u);
		kills += getKills(u);
		deaths += getDeaths(u);
		broken += getBrokenBeds(u);
		
		sql.queryUpdate("DELETE FROM LamaWars WHERE Uuid='" + u + "'");
		sql.queryUpdate("INSERT INTO LamaWars (Uuid,Wins,Looses,Kills,Deaths,Broken) VALUES ('" + u + "','" + wins + "','" + looses + "','" + kills + "','" + deaths + "','" + broken + "')");
		
	}
}
