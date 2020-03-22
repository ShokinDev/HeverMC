package br.com.hevermc.commons.enums;

import lombok.Getter;

@Getter
public enum Tags {

	MEMBRO("§7", "§7", "t", Groups.MEMBRO, false), 
	PRO("§6§lPRO", "§6", "s", Groups.PRO, false),
	GANSO("§e§lGANSO", "§e", "p", Groups.GANSO, false),
	NESTY("§5§lNESTY", "§5", "q", Groups.NESTY, false), 
	BETA("§1§LBETA", "§1", "o", Groups.BETA, true),
	YOUTUBER("§b§lYT", "§b", "m", Groups.YOUTUBER, true),
	YOUTUBERPLUS("§3§lYT+", "§3", "l", Groups.YOUTUBERPLUS, true),
	DESIGNER("§2§lDESIGNER", "§2", "k", Groups.DESIGNER, true),
	BUILDER("§e§lBUILDER", "§e", "j", Groups.BUILDER, false),
	TRIAL("§d§lTRIAL", "§d", "i", Groups.TRIAL, false),
	MOD("§5§lMOD", "§5", "h", Groups.MOD, false),
	MODGC("§5§lMODGC", "§5", "g", Groups.MODGC, true), 
	MODPLUS("§5§LMOD+", "§5", "f", Groups.MODPLUS, false),
	GERENTE("§c§lGERENTE", "§c", "e", Groups.GERENTE, false),
	ADMIN("§c§lADMIN", "§c", "d", Groups.ADMIN, false),
	DIRETOR("§4§LDIRETOR", "§4", "c", Groups.DIRETOR, false),
	DEV("§3§lDEV", "§3", "b", Groups.DEV, false),
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
