package br.com.hevermc.pvp.enums;

import org.bukkit.Material;

import br.com.hevermc.commons.enums.Groups;
import lombok.Getter;

@Getter
public enum Kits {

	NENHUM("Nenhum", "Nenhum", Material.AIR, Material.AIR, Groups.MEMBRO),
	PVP("PvP", "Kit sem habilidades.", Material.STONE_SWORD, Material.AIR, Groups.MEMBRO),
	NINJA("Ninja", "Vire um ninja e teletransporte até as costas do seu inimigo.", Material.EMERALD, Material.AIR,Groups.MEMBRO),
	BOXER("Boxer", "Vire um boxeador e bata mais forte.", Material.QUARTZ, Material.AIR, Groups.MEMBRO),
	SNAIL("Snail", "Deixe seu inimigo mais lento.", Material.WEB, Material.AIR, Groups.MEMBRO),
	VIPER("Viper", "Envenene seu inimigo.", Material.SPIDER_EYE, Material.AIR, Groups.MEMBRO),
	QUICKDROPPER("Quickdropper", "Não tenha potes em seu inventário.", Material.BOWL, Material.AIR, Groups.MEMBRO),
	NOFALL("NoFall", "Não tome dano de queda.", Material.FEATHER, Material.AIR, Groups.MEMBRO),
	KANGAROO("Kangaroo", "Vire um canguru.", Material.FIREWORK, Material.FIREWORK, Groups.MEMBRO),
	MONK("Monk", "Desarme seus inimigos.", Material.BLAZE_ROD, Material.BLAZE_ROD, Groups.MEMBRO),
	FISHERMAN("Fisherman", "Fisgue seus inimigos.", Material.FISHING_ROD, Material.FISHING_ROD, Groups.MEMBRO),
	ANCHOR("Anchor", "Não se mova ao ser hitado.", Material.ANVIL, Material.AIR, Groups.MEMBRO),
	CRITICAL("Critical", "Dê danos criticos em seu adversário.", 
			Material.GLOWSTONE, Material.AIR, Groups.NESTY),
	GLADIATOR("Gladiator", "Duele em uma jaula de vidro.", Material.IRON_FENCE, Material.IRON_FENCE, Groups.MEMBRO),
	STOMPER("Stomper", "Pisoteie seus inimigos.", Material.IRON_BOOTS, Material.AIR, Groups.BETA),
	ANTISTOMPER("AntiStomper", "Seja imune aos stompers.", Material.DIAMOND_BOOTS, Material.AIR, Groups.MEMBRO),
	SWITCHER("Switcher", "Troque de lugar com seus inimigos.", Material.SNOW_BALL, Material.SNOW_BALL, Groups.NESTY),
	AJNIN("Ajnin", "Agache para trazer seus inimigos até você.", Material.NETHER_STAR, Material.AIR, Groups.GANSO),
	AVATAR("Avatar", "Domine todos os 4 elementos.", Material.LAPIS_BLOCK, Material.BEACON, Groups.BETA),
	REAPER("Reaper", "De efeito de wither em seus inimigos.", Material.WOOD_HOE, Material.WOOD_HOE, Groups.MEMBRO),
	SPIDERMAN("SpiderMan", "Vire o spiderman.", Material.STRING, Material.STRING, Groups.GANSO),
	DESHFIRE("Deshfire", "Incendeie todos abaixo de você.", Material.REDSTONE_BLOCK, Material.REDSTONE_BLOCK,Groups.GANSO),
	MAGMA("Magma", "Incendeie seu inimigo.", Material.MAGMA_CREAM, Material.AIR, Groups.NESTY),
	FIREMAN("Fireman", "Seja imune a fogo.", Material.LAVA_BUCKET, Material.AIR, Groups.NESTY),
	C4("C4", "Entre para ALQaeda.", Material.SLIME_BALL, Material.SLIME_BALL, Groups.NESTY),
	SUMO("Sumo", "Lance seus inimigos para o céu.", Material.APPLE, Material.APPLE, Groups.MEMBRO),
	HOTPOTATO("HotPotato", "Exploda seu inimigo com sua batata.", Material.BAKED_POTATO, Material.BAKED_POTATO, Groups.GANSO),
	TIMELORD("TimeLord", "Controle o tempo.", Material.WATCH, Material.WATCH, Groups.BETA),
	PHANTOM("Phantom", "Sejá um passaro por 5 segundos.", Material.FEATHER, Material.FEATHER, Groups.MEMBRO),
	POSEIDON("Poseidon", "Sejá o deus dos mares.", Material.WATER_BUCKET, Material.AIR, Groups.BETA),
	THOR("Thor", "Sejá o deus dos raios.", Material.WOOD_AXE, Material.WOOD_AXE, Groups.MEMBRO),
	LINHA("Linha", "Linha", Material.GLASS, Material.AIR, Groups.MEMBRO),
	COOKIEMONSTER("CookieMonster", "Seu cookie lhe dá força!", Material.COOKIE, Material.COOKIE, Groups.DONO),
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
