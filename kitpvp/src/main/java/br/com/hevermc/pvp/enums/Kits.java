package br.com.hevermc.pvp.enums;

import org.bukkit.Material;

import br.com.hevermc.commons.enums.Groups;
import lombok.Getter;

@Getter
public enum Kits {

	NENHUM("Nenhum", "Nenhum", Material.AIR, Material.AIR, Groups.MEMBRO),
	PVP("PvP", "Kit sem habilidades.", Material.STONE_SWORD, Material.AIR, Groups.MEMBRO),
	NINJA("Ninja", "Vire um ninja e teletransporte at� as costas do seu inimigo.", Material.EMERALD, Material.AIR, Groups.MEMBRO),
	BOXER("Boxer", "Vire um boxeador e bata mais forte.", Material.QUARTZ, Material.AIR, Groups.MEMBRO),
	SNAIL("Snail", "Deixe seu inimigo mais lento.", Material.WEB, Material.AIR, Groups.MEMBRO),
	VIPER("Viper", "Envenene seu inimigo.", Material.SPIDER_EYE, Material.AIR, Groups.MEMBRO),
	QUICKDROPPER("Quickdropper", "N�o tenha potes em seu invent�rio.", Material.BOWL, Material.AIR, Groups.MEMBRO),
	NOFALL("NoFall", "N�o tome dano de queda.", Material.IRON_BOOTS, Material.AIR, Groups.MEMBRO),
	KANGAROO("Kangaroo", "Vire um canguru.", Material.FIREWORK, Material.FIREWORK, Groups.MEMBRO),
	MONK("Monk", "Desarme seus inimigos.", Material.BLAZE_ROD, Material.BLAZE_ROD, Groups.MEMBRO),
	FISHERMAN("Fisherman", "Fisgue seus inimigos.", Material.FISHING_ROD, Material.FISHING_ROD, Groups.MEMBRO),
	ANCHOR("Anchor", "N�o se mova ao ser hitado.", Material.ANVIL, Material.AIR, Groups.MEMBRO),
	FPS("Fps", "Fps", Material.AIR, Material.AIR, Groups.MEMBRO),
	ONEVSONE("1v1", "1v1", Material.AIR, Material.AIR, Groups.MEMBRO),
	LAVA("Lava", "Lava", Material.AIR, Material.AIR, Groups.MEMBRO);

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
