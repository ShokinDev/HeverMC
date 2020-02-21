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

	int[] slots = { 9, 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39,
			40, 41, 42, 43, 44 };

	public Selector(Player p) {
		Inventory inv = Bukkit.createInventory(null, 5 * 9, "§eSeletor de Kits");
		HeverPlayer hp = PlayerLoader.getHP(p);
		
		for (Kits kits : Kits.values()) {
			if (kits.getMaterial() != Material.AIR) {
				inv.setItem(slots[kits.ordinal()], new ItemConstructor(new ItemStack(kits.getMaterial()),
						hp.groupIsLarger(kits.getGroup()) ? "§aKit " + kits.getName() : "§cKit " + kits.getName(), // PvP
						Arrays.asList("", "§e" + kits.getDesc(), "")).create());
			}
		}
		p.openInventory(inv);
	}

}
