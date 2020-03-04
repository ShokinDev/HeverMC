package br.com.hevermc.lobby.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;

public class Collectables {
	
	public Collectables() {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§eColetáveis");
		inv.setItem(13, new ItemConstructor(new ItemStack(Material.SKULL_ITEM), "§aCabeças").create());
	}

}
