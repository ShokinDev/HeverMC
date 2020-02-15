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

	public Selector(Player p) {
		Inventory inv = Bukkit.createInventory(null, 44, "§eSeletor de Kits");
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		int[] slots = { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39,
				40, 41, 42, 43, 44 };
		for (Kits kits : Kits.values()) {
			if (kits == Kits.NENHUM)
				return;
			if (inv.getItem(slots[kits.ordinal()]).getType() == Material.AIR
					|| inv.getItem(slots[kits.ordinal()]).getType() == null) {
				if (hp.groupIsLarger(kits.getGroup())) {
					inv.setItem(slots[kits.ordinal()],
							new ItemConstructor(new ItemStack(Material.STONE_SWORD), "§a" + kits.getName(),
									Arrays.asList("", "§a" + kits.getDesc(), "", "§aVocê possui este kit")).create());
				} else {
					inv.setItem(slots[kits.ordinal()],
							new ItemConstructor(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14), "§c" + kits.getName(),
									Arrays.asList("", "§a" + kits.getDesc(), "", "§cVocê não possui este kit")).create());
				}
			}
		}

	}

}
