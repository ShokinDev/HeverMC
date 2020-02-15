package br.com.hevermc.pvp.enums;

import org.bukkit.Material;

import br.com.hevermc.commons.enums.Groups;
import lombok.Getter;

@Getter
public enum Kits {

	NENHUM("Nenhum", "Nenhum", Material.AIR, Groups.MEMBRO), PVP("PVP", "Kit sem habilidades.", Material.STONE_SWORD, Groups.MEMBRO);

	String name;
	String desc;
	Material material;
	Groups group;

	private Kits(String name, String desc, Material material, Groups group) {
		this.name = name;
		this.desc = desc;
		this.material = material;
		this.group = group;
	}

}
