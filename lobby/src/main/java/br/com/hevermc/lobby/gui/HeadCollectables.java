package br.com.hevermc.lobby.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;

public class HeadCollectables {
	
	public HeadCollectables() {
		Inventory inv = Bukkit.createInventory(null, 3*9, "�eColet�veis �f- Head");
		inv.setItem(13, new ItemConstructor(new ItemStack(Material.SKULL_ITEM), "�aCabe�as").create());
	}

}
