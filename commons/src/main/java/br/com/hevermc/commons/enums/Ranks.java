package br.com.hevermc.commons.enums;

import lombok.Getter;

@Getter
public enum Ranks {

    Unranked("§f", "-", "Unranked", 0),
    Primary("§a", "☰", "Primary", 500),
    Advanced("§e", "☲", "Advanced", 2000),
    Expert("§1", "☷", "Expert", 2200),
    Silver("§7", "✶", "Silver", 2500),
    Gold("§6", "✳", "Gold", 3000),
    Diamond("§b", "✦", "Diamond", 3300),
    Emerald("§2", "✥", "Emerald", 4000),
    Crystal("§9", "❉", "Crystal", 4200),
    Legend("§3", "❁", "Legend", 4500),
    Monster("§5", "✹", "Monster", 5000),
    Master("§c", "✫", "Master", 6000),
    Legendary("§4", "✪", "Legendary", 15000);

	String symbol;
	String color;
	String name;
	int xp;

	private Ranks(String color, String symbol, String name, int xp) {
		this.symbol = symbol;
		this.color = color;
		this.name = name;
		this.xp = xp;
	}

	public static Ranks getRank(String name) {
		for (Ranks ranks : Ranks.values())
			if (ranks.toString().equalsIgnoreCase(name.toLowerCase()))
				return ranks;
		return null;
	}

	public static Ranks getRank(int ordinal) {
		for (Ranks ranks : Ranks.values())
			if (ranks.ordinal() == ordinal)
				return ranks;
		return null;
	}
}
