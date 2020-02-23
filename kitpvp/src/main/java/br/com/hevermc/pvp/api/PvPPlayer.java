package br.com.hevermc.pvp.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

	public void unload() {
		try {
			KitPvP.getManager().log("Conta de " + name + " sendo descarregada!");
			name = null;
			protectArea = false;
			adminMode = false;
			inCombat = null;
			kit = null;
			warp = null;
			KitPvP.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " não foi descarregada!");
		}
	}

	public void setKit(Player p, Kits kit) {
		this.kit = kit;
		if (kit != Kits.NENHUM) {
			p.getInventory().clear();
			if (kit != Kits.FPS && kit != Kits.LAVA && kit != Kits.ONEVSONE)
				ReflectionAPI.sendTitle(p, "§6§l" + kit.getName().toUpperCase(),
						"§fVocê selecionou o kit §e" + kit.getName() + "§f!", 10, 5, 10);
			if (kit == Kits.ONEVSONE) {
				for (int i = 0; i < 8; i++) {
					p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
				}
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD),
						"§fEspada de Pedra", Enchantment.DAMAGE_ALL, 1).create());
				return;
			}
			for (int i = 0; i < p.getInventory().getSize(); i++) {
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
			}
			if (kit == Kits.PVP) {
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD),
						"§fEspada de Pedra", Enchantment.DAMAGE_ALL, 1).create());
			} else {
				if (kit.getItem() != Material.AIR) {
					p.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra").create());
					p.getInventory().setItem(1,
							new ItemConstructor(new ItemStack(kit.getItem()), "§e" + kit.getName() + " §fitem")
									.create());
				} else {
					p.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra").create());
				}
			}
			if (getWarp() == Warps.SPAWN)
				p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.COMPASS), "§fBussola").create());
			p.updateInventory();
		}
	}

}
