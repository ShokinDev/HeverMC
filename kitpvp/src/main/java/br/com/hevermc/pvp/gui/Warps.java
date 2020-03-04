package br.com.hevermc.pvp.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.api.WarpsAPI;

public class Warps {

	int[] slots = { 20, 21, 23, 24 };

	public Warps(Player p) {
		Inventory inv = Bukkit.createInventory(null, 4 * 9, "§eWarps");
		inv.setItem(3, new ItemConstructor(new ItemStack(Material.CHEST), "§7Seletor de Kits").create());
		inv.setItem(5, new ItemConstructor(new ItemStack(Material.COMPASS), "§aWarps").create());

		for (br.com.hevermc.pvp.enums.Warps warps : br.com.hevermc.pvp.enums.Warps.values()) {
			if (warps.getMaterial() != Material.AIR && warps != br.com.hevermc.pvp.enums.Warps.SPAWN
					&& warps != br.com.hevermc.pvp.enums.Warps.OVPOS1
					&& warps != br.com.hevermc.pvp.enums.Warps.OVPOS1) {
				inv.setItem(slots[warps.ordinal()],
						new ItemConstructor(new ItemStack(warps.getMaterial(), new WarpsAPI(warps).getINWarp()),
								"§aWarp " + warps.getName(),
								Arrays.asList("", "§e" + warps.getDesc(), "",
										"§aHá §e" + new WarpsAPI(warps).getINWarp() + " §ajogadores nesta warp"))
												.create());
			}
		}
		p.openInventory(inv);
	}

}
