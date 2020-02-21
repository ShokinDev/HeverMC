package br.com.hevermc.commons.enums;

import lombok.Getter;

@Getter
public enum Ranks {

	UNRANKED("-", "§f", 0);

	String symbol;
	String color;
	int xp;

	private Ranks(String symbol, String color, int xp) {
		this.symbol = symbol;
		this.color = color;
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
