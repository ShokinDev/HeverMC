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
	

}
