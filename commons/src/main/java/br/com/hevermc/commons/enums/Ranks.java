package br.com.hevermc.commons.enums;

import lombok.Getter;

@Getter
public enum Ranks {

    UNRANKED("§f", "-", "Unranked", 0),
    PRIMARY("§a", "•", "Primary", 2000),
    ADVANCED("§e", "••", "Advanced", 4200),
    EXPERT("§1", "•••", "Expert", 6500),
    SILVER("§7", "‡", "Silver", 8000),
    LEGENDARY("§4", "†", "Legendary", 15000);

	String symbol;
	String color;
	String name;
	int xp;

	private Ranks(String symbol, String color, String name, int xp) {
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
