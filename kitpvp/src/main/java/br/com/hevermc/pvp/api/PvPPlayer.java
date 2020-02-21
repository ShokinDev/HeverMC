package br.com.hevermc.pvp.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.enums.Warps;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PvPPlayer {

	String name;
	int kills;
	int deaths;
	int ks;
	String[] kitList;
	Kits kit = Kits.NENHUM;
	boolean protectArea = true;
	boolean combat = false;
	boolean adminMode = false;
	Player inCombat;
	Warps warp = Warps.SPAWN;
	
	public PvPPlayer(Player p) {
		this.name = p.getName();
	}

	public PvPPlayer(String name) {
		this.name = name;
	}

	public void load() {
		try {
			kills = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "kills", getName());
			deaths = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "deaths", getName());
			ks = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "ks", getName());
			kitList = Commons.getManager().getSQLManager().getString("hever_kitpvp", "name", "kitList", getName())
					.split(",");
			KitPvP.getManager().log("Conta de " + name + " carregada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " n�o foi carregada!");
		}
	}

	public void unload() {
		try {
			KitPvP.getManager().log("Conta de " + name + " sendo descarregada!");
			kills = 0;
			deaths = 0;
			ks = 0;
			kitList = null;
			KitPvP.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " n�o foi descarregada!");
		}
	}

	public void setKit(Player p, Kits kit) {
		this.kit = kit;
		if (kit != Kits.NENHUM) {
			p.getInventory().clear();
			ReflectionAPI.sendTitle(p, "�6�l" + kit.getName().toUpperCase(),
					"�fVoc� selecionou o kit �e" + kit.getName() + "�f!", 10, 5, 10);

			for (int i = 0; i < p.getInventory().getSize(); i++) {
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
			}
			if (kit == Kits.PVP) {
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD),
						"�fEspada de Pedra", Enchantment.DAMAGE_ALL, 1).create());
			} else {
				if (kit.getItem() != Material.AIR) {
					p.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "�fEspada de Pedra").create());
					p.getInventory().setItem(1,
							new ItemConstructor(new ItemStack(kit.getItem()), "�e" + kit.getName() + " �fitem")
									.create());
				} else {
					p.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "�fEspada de Pedra").create());
				}
			}
			p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.COMPASS), "�fBussola").create());
			p.updateInventory();
		}
	}

	public void update() {
		try {
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "kills", "name", getKills(), getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "deaths", "name", getDeaths(), getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "ks", "name", getKs(), getName());
			KitPvP.getManager().log("Conta de " + name + " atualizada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " n�o foi atualizada!");
		}
	}
}
