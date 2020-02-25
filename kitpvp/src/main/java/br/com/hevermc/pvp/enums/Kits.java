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
	NOFALL("NoFall", "Não tome dano de queda.", Material.FEATHER, Material.AIR, Groups.MEMBRO),
	KANGAROO("Kangaroo", "Vire um canguru.", Material.FIREWORK, Material.FIREWORK, Groups.MEMBRO),
	MONK("Monk", "Desarme seus inimigos.", Material.BLAZE_ROD, Material.BLAZE_ROD, Groups.MEMBRO),
	FISHERMAN("Fisherman", "Fisgue seus inimigos.", Material.FISHING_ROD, Material.FISHING_ROD, Groups.MEMBRO),
	ANCHOR("Anchor", "Não se mova ao ser hitado.", Material.ANVIL, Material.AIR, Groups.MEMBRO),
	CRITICAL("Critical", "Dê danos criticos em seu adversário.", Material.REDSTONE_BLOCK, Material.AIR, Groups.MEMBRO),
	GLADIATOR("Gladiator", "Duele em uma jaula de vidro.", Material.IRON_FENCE, Material.IRON_FENCE, Groups.MEMBRO),
	STOMPER("Stomper", "Pisoteie seus inimigos.", Material.IRON_BOOTS, Material.AIR, Groups.MEMBRO),
	ANTISTOMPER("AntiStomper", "Seja imune aos stompers.", Material.DIAMOND_BOOTS, Material.AIR, Groups.MEMBRO),
	SWITCHER("Switcher", "Troque de lugar com seus inimigos.", Material.SNOW_BALL, Material.SNOW_BALL, Groups.MEMBRO),
	AJNIN("Ajnin", "Agache para trazer seus inimigos até você.", Material.NETHER_STAR, Material.AIR, Groups.MEMBRO),
	AVATAR("Avatar", "Domine todos os 4 elementos.", Material.LAPIS_BLOCK, Material.BEACON, Groups.MEMBRO),
	REAPER("Reaper", "De efeito de wither em seus inimigos.", Material.WOOD_HOE, Material.WOOD_HOE, Groups.MEMBRO),
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
