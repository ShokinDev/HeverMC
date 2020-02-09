package br.com.hevermc.commons.bukkit.api;

import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConstructor {

	ItemStack is;
	ItemMeta im;
	String display = null;
	List<String> lore= null;
	Enchantment enchant= null;
	int enchant_level;

	public ItemConstructor(ItemStack is, String display, List<String> lore, Enchantment enchant, int enchant_level) {
		this.is = is;
		this.im = is.getItemMeta();
		this.display = display;
		this.lore = lore;
		this.enchant = enchant;
		this.enchant_level = enchant_level;
	}

	public ItemConstructor(ItemStack is, String display, Enchantment enchant, int enchant_level) {
		this.is = is;
		this.im = is.getItemMeta();
		this.display = display;
		this.enchant = enchant;
		this.enchant_level = enchant_level;
	}

	public ItemConstructor(ItemStack is, String display, List<String> lore) {
		this.is = is;
		this.im = is.getItemMeta();
		this.display = display;
		this.lore = lore;
	}
	public ItemConstructor(ItemStack is, String display) {
		this.is = is;
		this.im = is.getItemMeta();
		this.display = display;
	}
	
	public ItemStack create() {
		im.setDisplayName(display);
		if (lore != null)
			im.setLore(lore);
		if (enchant != null)
			im.addEnchant(enchant, enchant_level, true);
		is.setItemMeta(im);
		return is;
		
	}

}
