package br.com.hevermc.lobby.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.enums.Tags;

public class Profile {

	public Profile(Player p) {
		Inventory inv = Bukkit.createInventory(null, 4 * 9, "§ePerfil");
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		@SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(397, 1, (short) 3);
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(p.getName());
		skullm.setDisplayName(Tags.getTags(hp.getGroup()).getPrefix() + " " + Tags.getTags(hp.getGroup()).getColor()
				+ p.getName() + hp.getSuffix());
		skull.setItemMeta((ItemMeta) skullm);

		inv.setItem(13, skull);
		inv.setItem(19,
				new ItemConstructor(new ItemStack(Material.DIAMOND_SWORD), "§a§lKitPvP",
						Arrays.asList(" ", "§fSuas estatísticas nesse modo de jogo são: ", " ", " §aKills: §20 ",
								" §cDeaths: §c0 ", " §eKillStreak: §60 ", " ", "§apvp.hevermc.com.br")).create());
		inv.setItem(20, new ItemConstructor(new ItemStack(Material.PAPER), "§c§k§lHunger-Games").create());
		inv.setItem(21, new ItemConstructor(new ItemStack(Material.PAPER), "§c§k§lGladiator").create());
		inv.setItem(25,
				new ItemConstructor(new ItemStack(Material.BOOK), "§e§lInformações sobre sua conta",
						Arrays.asList(" ", "§fTodas informações sobre sua conta: ", " ",
								" §fGrupo: " + Tags.getTags(hp.getGroup()).getColor() + hp.getGroup().getName(),
								" §fRank: " + hp.getRank().getColor() + hp.getRank().toString(), " ",
								" §fExperiência: §e" + hp.getXp(), " §fCash: §3" + hp.getCash(), " ")).create());

		p.openInventory(inv);
	}

}
