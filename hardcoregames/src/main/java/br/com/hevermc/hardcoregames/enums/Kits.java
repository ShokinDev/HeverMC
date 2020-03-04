package br.com.hevermc.hardcoregames.enums;

import org.bukkit.Material;

import br.com.hevermc.commons.enums.Groups;
import lombok.Getter;

@Getter
public enum Kits {

	NENHUM("Nenhum", "Nenhum", Material.AIR, Material.AIR, Groups.MEMBRO),
	NINJA("Ninja", "Vire um ninja e teletransporte até as costas do seu inimigo.", Material.EMERALD, Material.AIR,Groups.MEMBRO);

	String name;
	String desc;
	Material material;
	Material item;
	Groups group;

	private Kits(String name, String desc, Material material, Material item, Groups group) {
		this.name = name;
		this.desc = desc;
		this.material = material;
		this.item = item;
		this.group = group;
	}

	public static Kits getKits(int ordinal) {
		for (Kits kits : Kits.values()) {
			if (kits.ordinal() == ordinal)
				return kits;
		}
		return null;
	}

}
