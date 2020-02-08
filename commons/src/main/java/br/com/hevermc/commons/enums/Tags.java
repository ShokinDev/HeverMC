package br.com.hevermc.commons.enums;

import lombok.Getter;

@Getter
public enum Tags {

	MEMBRO("§7", "§7", "z", Groups.MEMBRO, false), 
	PRO("§6§lPRO", "§6", "q", Groups.PRO, false),
	LEGEND("§e§lLEGEND", "§E", "p", Groups.LEGEND, false), 
	CRYSTAL("§3§LCRYSTAL", "§3", "o", Groups.CRYSTAL, false),
	BETA("§1§LBETA", "§1", "m", Groups.BETA, true),
	YEAR("§C§L2020", "§C", "n", Groups.YEAR, false),
	YOUTUBER("§b§lYT", "§7", "l", Groups.YOUTUBER, true),
	YOUTUBERPLUS("§3§lYT+", "§3", "k", Groups.YOUTUBERPLUS, true),
	DESIGNER("§2§lDESIGNER", "§2", "j", Groups.DESIGNER, true),
	BUILDER("§e§lBUILDER", "§e", "i", Groups.BUILDER, false),
	TRIAL("§d§lTRIAL", "§d", "h", Groups.BUILDER, false),
	MOD("§5§lMOD", "§5", "g", Groups.MOD, false),
	MODGC("§5§lMODGC", "§5", "f", Groups.MODGC, true), 
	MODPLUS("§5§LMOD+", "§5", "e", Groups.MODPLUS, false),
	GERENTE("§c§lGERENTE", "§5", "d", Groups.GERENTE, false),
	ADMIN("§c§lGERENTE", "§c", "c", Groups.ADMIN, false),
	COORDENADOR("§b§LCOORD", "§B", "b", Groups.COORDENADOR, false),
	DONO("§4§LDONO", "§4", "a", Groups.DONO, false);

	String prefix;
	String color;
	String ordem;
	Groups group;
	boolean exclusive;

	private Tags(String prefix, String color, String ordem, Groups group, boolean exclusive) {
		this.prefix = prefix;
		this.color = color;
		this.ordem = ordem;
		this.group = group;
		this.exclusive = exclusive;
	}

	public static Tags getTags(String tag) {
		for (Tags tags : Tags.values())
			if (tags.toString().equalsIgnoreCase(tag))
				return tags;
		return null;
	}
	
	public static Tags getTags(Groups group) {
		for (Tags tags : Tags.values())
			if (tags.getGroup() == group)
				return tags;
		return null;
	}
}
