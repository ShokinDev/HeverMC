package br.com.hevermc.authentication.gui;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Captcha {

	public Captcha(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, "�eClique no �bBloco de Diamante");

		int captchablock = new Random().nextInt(inv.getSize());
		ItemStack is = new ItemStack(Material.OBSIDIAN);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("�cObsidian");
		is.setItemMeta(im);

		for (int i = 0; i < inv.getSize(); i++)
			inv.setItem(i, is);

		ItemStack is2 = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta im2 = is2.getItemMeta();
		im2.setDisplayName("�aBloco de Diamante");
		is2.setItemMeta(im2);
		
		inv.setItem(captchablock, is2);

		p.openInventory(inv);
	}

}
