package br.com.hevermc.pvp.enums;

import org.bukkit.Material;

import lombok.Getter;

@Getter
public enum Warps {

	SPAWN("Spawn", "Local de spawn", Material.AIR), LAVA("Lava", "Local para treinamento!", Material.LAVA_BUCKET),
	FPS("Fps", "Arena otimizada para melhor desempenho do computador", Material.GLASS),
	ONEVSONE("1V1", "Arena para duelo entre jogadores", Material.BLAZE_ROD);

	String name;
	String desc;
	Material material;

	private Warps(String name, String desc, Material material) {
		this.name = name;
		this.desc = desc;
		this.material = material;
	}

	public static Warps getWarps(int ordinal) {
		for (Warps warps : Warps.values()) {
			if (warps.ordinal() == ordinal)
				return warps;
		}
		return null;
	}

	public static Warps getWarps(String warp) {
		for (Warps warps : Warps.values()) {
			if (warps.getName().equalsIgnoreCase(warp))
				return warps;
		}
		return null;
	}

}
