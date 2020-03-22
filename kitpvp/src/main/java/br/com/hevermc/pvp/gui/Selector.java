package br.com.hevermc.pvp.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.enums.Kits;

public class Selector {

	int[] slots = { 19, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42 };

	public Selector(Player p, int page, int selectorNumber) {
		Inventory inv = null;
		HeverPlayer hp = PlayerLoader.getHP(p.getName());

		if (page == 1) {
			inv = Bukkit.createInventory(null, 6 * 9, "§aSeletor de Kits " + selectorNumber + " §7(1/3)");
			inv.setItem(3, new ItemConstructor(new ItemStack(Material.CHEST), "§aSeletor de Kits").create());
			inv.setItem(5, new ItemConstructor(new ItemStack(Material.COMPASS), "§7Warps").create());
			inv.setItem(27,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Página anterior").create());
			inv.setItem(35,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10), "§aPróxima página").create());
			int i = 0;
			for (Kits kits : Kits.values()) {
				if (kits.getMaterial() != Material.AIR) {
					i++;
					if (!(i >= slots.length)) {
						if (hp.groupIsLarger(kits.getGroup())) {
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(kits.getMaterial()),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						} else {
						
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						}
					}
				}
			}
		} else if (page == 2) {
			inv = Bukkit.createInventory(null, 6 * 9, "§aSeletor de Kits " + selectorNumber + " §7(2/3)");
			inv.setItem(3, new ItemConstructor(new ItemStack(Material.CHEST), "§aSelector de Kits").create());
			inv.setItem(5, new ItemConstructor(new ItemStack(Material.COMPASS), "§7Warps").create());
			inv.setItem(27,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10), "§7Página anterior").create());
			inv.setItem(35,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10), "§aPróxima página").create());
			int i = 0;
			for (int a = slots.length; a < Kits.values().length; a++) {
				Kits kits = Kits.getKits(a);
				if (kits == null) {
					return;
				}
				if (kits.getMaterial() != Material.AIR) {
					i++;
					if (!(i >= slots.length)) {
						if (hp.groupIsLarger(kits.getGroup())) {
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(kits.getMaterial()),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						}  else {
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						}
					}

				}
			}
		}else if (page == 3) {
			inv = Bukkit.createInventory(null, 6 * 9, "§aSeletor de Kits " + selectorNumber + " §7(3/3)");
			inv.setItem(3, new ItemConstructor(new ItemStack(Material.CHEST), "§aSelector de Kits").create());
			inv.setItem(5, new ItemConstructor(new ItemStack(Material.COMPASS), "§7Warps").create());
			inv.setItem(27,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10), "§7Página anterior").create());
			inv.setItem(35,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§aPróxima página").create());
			int i = 0;
			for (int a = slots.length + slots.length; a < Kits.values().length; a++) {
				Kits kits = Kits.getKits(a);
				if (kits == null) {
					return;
				}
				if (kits.getMaterial() != Material.AIR) {
					i++;
					if (!(i >= slots.length)) {
						if (hp.groupIsLarger(kits.getGroup())) {
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(kits.getMaterial()),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						}  else {
					
							inv.setItem(slots[i],
									new ItemConstructor(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14),
											hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName()
													: "§cKit " + kits.getName(), // PvP
											Arrays.asList("", "§e" + kits.getDesc(), "")).create());
						}
					}

				}
			}
		}
		p.openInventory(inv);
	}

}