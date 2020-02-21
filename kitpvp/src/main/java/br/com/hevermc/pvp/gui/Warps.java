package br.com.hevermc.pvp.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;

public class Warps {

	int[] slots = { 0,1,2, 3, 4, 5};

	public Warps(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, "§eWarps");

		for (br.com.hevermc.pvp.enums.Warps warps : br.com.hevermc.pvp.enums.Warps.values()) {
			if (warps.getMaterial() != Material.AIR) {
				inv.setItem(slots[warps.ordinal()], new ItemConstructor(new ItemStack(warps.getMaterial()),
					"§aWarp " + warps.getName(), Arrays.asList("", "§e" + warps.getDesc(), "")).create());
			}
		}
		p.openInventory(inv);
	}

}
