package br.com.hevermc.pvp.api;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.enums.Warps;

public class OneVsOne {

	Player p1;
	Player p2;
	int dueltime;
	boolean dueling = false;

	public OneVsOne(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void hideForAll() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (dueling == false) {
					for (Player all : Bukkit.getOnlinePlayers()) {
						PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(all).load().getPvPP();
						if (!pvp.isAdminMode()) {
							p1.showPlayer(all);
							p2.showPlayer(all);
						}
					}
					cancel();
				} else {
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (all != p1 || all != p2) {
							p1.hidePlayer(all);
							p2.hidePlayer(all);
						}
						if (!p2.isOnline()) {

						}

					}
				}
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 5);
	}

	public void start() {
		dueling = true;
		hideForAll();
		p1.teleport(new WarpsAPI(Warps.OVPOS1).getLocation());
		p2.teleport(new WarpsAPI(Warps.OVPOS2).getLocation());
		p1.getInventory().clear();
		p2.getInventory().clear();
		PvPPlayer pvp1 = new PlayerLoader(p1).load().getPvPP();
		PvPPlayer pvp2 = new PlayerLoader(p2).load().getPvPP();
		pvp1.setOnevsone(true);
		pvp1.setOnevsonep(p2);
		pvp2.setOnevsone(true);
		pvp2.setOnevsonep(p1);
		pvp2.setProtectArea(false);
		pvp1.setProtectArea(false);
		pvp1.setKit(p1, Kits.ONEVSONE);
		pvp2.setKit(p2, Kits.ONEVSONE);
	}

	public void finish() {
		dueling = false;
		if (p1.isOnline() && p2.isOnline()) {
			p1.teleport(new WarpsAPI(Warps.ONEVSONE).getLocation());
			p2.teleport(new WarpsAPI(Warps.ONEVSONE).getLocation());
			PvPPlayer pvp1 = new PlayerLoader(p1).load().getPvPP();
			PvPPlayer pvp2 = new PlayerLoader(p2).load().getPvPP();
			p1.getInventory().clear();
			p2.getInventory().clear();
			pvp1.setOnevsone(false);
			pvp1.setOnevsonep(null);
			pvp2.setOnevsone(false);
			pvp2.setOnevsonep(null);
			pvp1.setCombat(false);
			pvp1.setInCombat(null);
			pvp2.setCombat(false);
			pvp2.setInCombat(null);
			pvp1.setKit(p1, Kits.NENHUM);
			pvp2.setKit(p2, Kits.NENHUM);
			pvp2.setProtectArea(true);
			pvp1.setProtectArea(true);
			p1.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
			p1.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida").create());
			p2.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
			p2.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida").create());
		} else if (p1.isOnline()) {
			p1.teleport(new WarpsAPI(Warps.ONEVSONE).getLocation());
			PvPPlayer pvp1 = new PlayerLoader(p1).load().getPvPP();
			p1.getInventory().clear();
			pvp1.setOnevsone(false);
			pvp1.setCombat(false);
			pvp1.setInCombat(null);
			pvp1.setOnevsonep(null);
			pvp1.setProtectArea(true);
			pvp1.setKit(p1, Kits.NENHUM);
			p1.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
			p1.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida").create());

		} else if (p2.isOnline()) {
			p2.teleport(new WarpsAPI(Warps.ONEVSONE).getLocation());
			PvPPlayer pvp2 = new PlayerLoader(p2).load().getPvPP();
			p2.getInventory().clear();
			pvp2.setOnevsone(false);
			pvp2.setCombat(false);
			pvp2.setInCombat(null);
			pvp2.setOnevsonep(null);
			pvp2.setProtectArea(true);
			pvp2.setKit(p2, Kits.NENHUM);
			p2.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
			p2.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida").create());

		}
	}

}
