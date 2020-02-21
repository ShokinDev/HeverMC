package br.com.hevermc.pvp.enums;

import org.bukkit.Material;

import br.com.hevermc.commons.enums.Groups;
import lombok.Getter;

@Getter
public enum Kits {

	NENHUM("Nenhum", "Nenhum", Material.AIR, Material.AIR, Groups.MEMBRO),
	PVP("PvP", "Kit sem habilidades.", Material.STONE_SWORD, Material.AIR, Groups.MEMBRO),
	NINJA("Ninja", "Vire um ninja e teletransporte até as costas do seu inimigo.", Material.EMERALD, Material.AIR, Groups.MEMBRO),
	BOXER("Boxer", "Vire um boxeador e bata mais forte.", Material.QUARTZ, Material.AIR, Groups.MEMBRO),
	SNAIL("Snail", "Deixe seu inimigo mais lento.", Material.WEB, Material.AIR, Groups.MEMBRO),
	VIPER("Viper", "Envenene seu inimigo.", Material.SPIDER_EYE, Material.AIR, Groups.MEMBRO),
	QUICKDROPPER("Quickdropper", "Não tenha potes em seu inventário.", Material.BOWL, Material.AIR, Groups.MEMBRO),
	NOFALL("NoFall", "Não tome dano de queda.", Material.IRON_BOOTS, Material.AIR, Groups.MEMBRO),
	KANGAROO("Kangaroo", "Vire um canguru.", Material.FIREWORK, Material.FIREWORK, Groups.MEMBRO),
	FPS("Fps", "Fps", Material.AIR, Material.AIR, Groups.MEMBRO);

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
