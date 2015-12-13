package de.Moohsassin.LamaWars;

import java.util.Random;

public enum GameType {

	ZWEIxZWEI("2x2"),
	ZWEIxDREI("2x3"),
	ZWEIxVIER("2x4"),
	ZWEIxFUENF("2x5"),
	VIERxEINS("4x1"),
	VIERxZWEI("4x2"),
	VIERxDREI("4x3"),
	VIERxVIER("4x4"),
	ACHTxEINS("8x1"),
	ACHTxZWEI("8x2"),
	ACHTxVIER("8x4");
	
	String shorted;
	
	private GameType(String shorted) {
		this.shorted = shorted;
	}
	
	public String getShorted() {
		return this.shorted;
	}
	
	public static GameType getRandomType() {
		return GameType.values()[new Random().nextInt(GameType.values().length)];
	}
	
	public static GameType getTypeByString(String s) {
		for(GameType types : GameType.values()) {
			if(types.getShorted().equalsIgnoreCase(s)) return types;
		}
		return VIERxVIER;
	}
	
	public int getMaxPlayers() {
		return Integer.valueOf(shorted.split("x")[0]) * Integer.valueOf(shorted.split("x")[1]);
	}
	
}
