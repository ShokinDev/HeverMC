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
	Kits kit2 = Kits.NENHUM;
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
			kit2 = null;
			warp = null;
			KitPvP.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			KitPvP.getManager().log("Conta de " + name + " não foi descarregada!");
		}
	}

	public void setKit2(Player p, Kits kit) {
		this.kit2 = kit;
		if (kit2 != Kits.NENHUM) {
			p.getInventory().clear();

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
					if (this.kit.getMaterial() == Material.AIR) {
						p.getInventory().setItem(1,
								new ItemConstructor(new ItemStack(kit.getMaterial()), "§e" + kit.getName() + " §fitem")
										.create());
					} else {

						p.getInventory().setItem(2,
								new ItemConstructor(new ItemStack(kit.getMaterial()), "§e" + kit.getName() + " §fitem")
										.create());
					}
				} else {
					p.getInventory().setItem(0,
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra").create());
				}
			}

			if (kit == Kits.SWITCHER) {
				p.getInventory().setItem(1,
						new ItemConstructor(new ItemStack(kit.getItem(), 2), "§e" + kit.getName() + " §fitem")
								.create());
			}

			if (getWarp() == Warps.SPAWN)
				p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.COMPASS), "§fBussola").create());
			p.updateInventory();
		}
	}

	public void setKits(Player p) {
		p.getInventory().clear();
		if (getKit() == Kits.PVP || getKit2() == Kits.PVP) {
			p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra",
					Enchantment.DAMAGE_ALL, 1).create());
		} else {

			p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§fEspada de Pedra").create());
		}
		if (getKit().getItem() != Material.AIR) {
			p.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(getKit().getMaterial()), "§a" + getKit().getName()).create());
			if (getKit2().getItem() != Material.AIR) {
				p.getInventory().setItem(2,
						new ItemConstructor(new ItemStack(getKit2().getMaterial()), "§a" + getKit2().getName()).create());
			}
		} else {
			if (getKit2().getItem() != Material.AIR) {
				p.getInventory().setItem(1,
						new ItemConstructor(new ItemStack(getKit2().getMaterial()), "§a" + getKit2().getName()).create());
			}

		}
		for (int i = 0; i < 36; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
		if (getWarp() == Warps.SPAWN)
			p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.COMPASS), "§fBussola").create());
	}

	public void setKit(Player p, Kits kit) {
		this.kit = kit;
		if (kit != Kits.NENHUM) {
			p.getInventory().clear();
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

			if (kit == Kits.SWITCHER) {
				p.getInventory().setItem(1,
						new ItemConstructor(new ItemStack(kit.getItem(), 2), "§e" + kit.getName() + " §fitem")
								.create());
			}
			if (getWarp() == Warps.SPAWN)
				p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.COMPASS), "§fBussola").create());
			p.updateInventory();
		}
	}

}
