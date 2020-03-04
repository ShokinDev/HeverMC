package br.com.hevermc.lobby.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.lobby.Lobby;

public class HardcoreGames {

	int[] slots = { 11, 12, 13, 14, 15 };

	public void updateInventory(Inventory inv, Player p) {
		new BukkitRunnable() {

			@Override
			public void run() {

				if (inv == null || p.getOpenInventory() == null) {
					cancel();
					return;
				}
				for (int i = 1; i < slots.length + 1; i++) {
					if (Commons.getManager().getBackend().getRedis().get("hg-" + i) == null) {
						inv.setItem(slots[i], new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8),
								"§aSala HG-" + i, Arrays.asList("", "§cSala offline.", "")).create());
					} else {
						String[] a = Commons.getManager().getBackend().getRedis().get("hg-" + i).split(",");
						String status = a[0].replace("status:", "");
						String tempo = a[1].replace("timer:", "");
						String jogadores = a[2].replace("on:", "");
						if (status.equals("PREJOGO")) {
							inv.setItem(slots[i - 1],
									new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10),
											"§aSala HG-" + i, Arrays.asList("", "§fEstágio: §aPreJogo",
													"§fTempo: §e" + tempo, "§fJogadores: §a" + jogadores, ""))
															.create());
						} else if (status.equals("INVENCIVEL")) {
							inv.setItem(slots[i - 1],
									new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 11),
											"§aSala HG-" + i, Arrays.asList("", "§fEstágio: §eInvencibilidade",
													"§fTempo: §e" + tempo, "§fJogadores: §a" + jogadores, ""))
															.create());
						} else if (status.equals("ANDAMENTO")) {
							inv.setItem(slots[i - 1],
									new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 1),
											"§aSala HG-" + i, Arrays.asList("", "§fEstágio: §cAndamento",
													"§fTempo: §e" + tempo, "§fJogadores: §a" + jogadores, ""))
															.create());
						} else if (status.equals("FINALIZANDO")) {
							inv.setItem(slots[i - 1],
									new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8),
											"§aSala HG-" + i, Arrays.asList("", "§4Finalizando...", "")).create());
						}
					}
				}
				p.updateInventory();
			}
		}.runTaskTimer(Lobby.getInstance(), 0, 20);

	}

	public HardcoreGames(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3 * 9, "§eSalas de HardcoreGames");

		for (int i = 1; i < slots.length + 1; i++) {
			inv.setItem(slots[i - 1], new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8),
					"§aSala HG-" + i, Arrays.asList("", "§cSala offline.", "")).create());
		}
		updateInventory(inv, p);

		p.openInventory(inv);
	}

}
