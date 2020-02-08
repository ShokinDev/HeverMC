package br.com.hevermc.commons.enums;

import lombok.Getter;

public enum Groups {

	MEMBRO("Membros", null), PRO("Pro", null), LEGEND("Legend", null), CRYSTAL("Crystal", null), BETA("Beta", null),
	YEAR("2020", null), YOUTUBER("Youtuber", "Yt"), YOUTUBERPLUS("Ytplus", "Yt+"), DESIGNER("Designer", "Dzn"),
	BUILDER("Builder", null), TRIAL("Trial", null), MOD("Mod", null), MODGC("ModGC", null), MODPLUS("Mod+", "ModPlus"),
	GERENTE("Gerente", null), ADMIN("Admin", "adm"), COORDENADOR("Coordenador", "Coord"), DONO("Dono", "owner");

	@Getter
	String name;
	String alternate_name;

	private Groups(String name, String alternate_name) {
		this.name = name;
		this.alternate_name = alternate_name;
	}

	public static Groups getGroup(String name) {
		for (Groups groups : Groups.values())
			if (groups.toString().equalsIgnoreCase(name.toLowerCase()))
				return groups;
		return null;
	}

}
