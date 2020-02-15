package br.com.hevermc.pvp.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.enums.Kits;
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
			KitPvP.getManager().log("Conta de " + name + " não foi carregada!");
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
			KitPvP.getManager().log("Conta de " + name + " não foi descarregada!");
		}
	}

	public void setKit(Player p, Kits kit) {
		this.kit = kit;
		if (kit != Kits.NENHUM) {
			for (int i = 0; i < p.getInventory().getSize(); i++) {
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
			}
			if (kit == Kits.PVP) {
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra", Enchantment.DAMAGE_ALL, 1).create());
			} else {
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra").create());
				p.getInventory().setItem(1, new ItemConstructor(new ItemStack(kit.getMaterial()), "§e" + kit.getName() + " §fitem").create());
			}
		}
	}
	 
	public void update() {
		try {
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "name", "kills", 0, getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "name", "deaths", 0, getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "name", "ks", 0, getName());
			Commons.getManager().getSQLManager().updateString("hever_kitpvp", "name", "kitList", "Nenhum", getName());
			KitPvP.getManager().log("Conta de " + name + " atualizada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " não foi atualizada!");
		}
	}
}
