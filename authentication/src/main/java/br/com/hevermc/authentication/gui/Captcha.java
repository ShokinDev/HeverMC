package br.com.hevermc.authentication.gui;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Captcha {

	public Captcha(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "§eClique no §bBloco de Diamante");

		int captchablock = new Random().nextInt(inv.getSize());
		ItemStack is = new ItemStack(Material.OBSIDIAN);
		
		for (int i = 26; i > 0;i--)
			inv.setItem(i, is);
		
		inv.setItem(captchablock, new ItemStack(Material.DIAMOND_BLOCK));

		
		p.openInventory(inv);
	}

}
