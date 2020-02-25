package br.com.hevermc.pvp.onevsone;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.com.hevermc.pvp.KitPvP;

public final class Menu1v1Custom implements Listener {

	public static final HashMap<Player, String> playername = new HashMap<>();
	public static final HashMap<Player, String> armaduras = new HashMap<>();
	public static final HashMap<Player, Material> espada = new HashMap<>();
	public static final HashMap<Player, String> recrafttype = new HashMap<>();
	public static final HashMap<Player, Boolean> recraft = new HashMap<>();
	public static final HashMap<Player, Boolean> sharpness = new HashMap<>();
	public static final HashMap<Player, Boolean> fullsoup = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static final void loadItensCustom(final Player bp1, final Player bp2) {
		bp1.getInventory().clear();
		bp2.getInventory().clear();
		bp1.getInventory().setArmorContents(null);
		bp2.getInventory().setArmorContents(null);
		if (armaduras.containsKey(bp1) && armaduras.get(bp1) != "SEM") {
			if (armaduras.get(bp1) == "LEATHER") {
				bp1.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));

				bp2.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			} else if (armaduras.get(bp1) == "IRON") {
				bp1.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

				bp2.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			} else if (armaduras.get(bp1) == "DIAMOND") {
				bp1.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

				bp2.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			}
		}
		if (fullsoup.containsKey(bp1)) {
			if (fullsoup.get(bp1)) {
				for (int i = 1; i < 36; i++) {
					bp1.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
					bp2.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
				}
			} else {
				for (int i = 1; i < 9; i++) {
					bp1.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
					bp2.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
				}
			}
		}
		if (espada.containsKey(bp1)) {
			if (espada.get(bp1) == Material.WOOD_SWORD) {
				if (sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaMadeira(true));
					bp2.getInventory().setItem(0, espadaMadeira(true));
				} else {
					bp1.getInventory().setItem(0, espadaMadeira(false));
					bp2.getInventory().setItem(0, espadaMadeira(false));
				}
			}
			if (espada.get(bp1) == Material.STONE_SWORD) {
				if (sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaPedra(true));
					bp2.getInventory().setItem(0, espadaPedra(true));
				} else {
					bp1.getInventory().setItem(0, espadaPedra(false));
					bp2.getInventory().setItem(0, espadaPedra(false));
				}
			}
			if (espada.get(bp1) == Material.IRON_SWORD) {
				if (sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaFerro(true));
					bp2.getInventory().setItem(0, espadaFerro(true));
				} else {
					bp1.getInventory().setItem(0, espadaFerro(false));
					bp2.getInventory().setItem(0, espadaFerro(false));
				}
			}
			if (espada.get(bp1) == Material.DIAMOND_SWORD) {
				if (sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaDiamante(true));
					bp2.getInventory().setItem(0, espadaDiamante(true));
				} else {
					bp1.getInventory().setItem(0, espadaDiamante(false));
					bp2.getInventory().setItem(0, espadaDiamante(false));
				}
			}
		}
		if (recraft.containsKey(bp1)) {
			if (recraft.get(bp1)) {
				if (recrafttype.get(bp1) == "COGUMELO") {
					bp1.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (byte) 0));
					bp1.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64, (byte) 0));
					bp1.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64, (byte) 0));

					bp2.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (byte) 0));
					bp2.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64, (byte) 0));
					bp2.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64, (byte) 0));
				} else if (recrafttype.get(bp1) == "COCOABEAN") {

					bp1.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (byte) 0));
					bp1.getInventory().setItem(14, new ItemStack(Material.getMaterial(351), 64, (byte) 3));

					bp2.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (byte) 0));
					bp2.getInventory().setItem(14, new ItemStack(Material.getMaterial(351), 64, (byte) 3));
				}
			}
		}
		bp1.updateInventory();
		bp2.updateInventory();
	}

	public static final void removeDefaultCustoms(final Player bp) {
		if (playername.containsKey(bp)) {
			playername.remove(bp);
		}
		if (armaduras.containsKey(bp)) {
			armaduras.remove(bp);
		}
		if (espada.containsKey(bp)) {
			espada.remove(bp);
		}
		if (recrafttype.containsKey(bp)) {
			recrafttype.remove(bp);
		}
		if (sharpness.containsKey(bp)) {
			sharpness.remove(bp);
		}
		if (fullsoup.containsKey(bp)) {
			fullsoup.remove(bp);
		}
	}

	public static final ItemStack espadaDiamante(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaFerro(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.IRON_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaPedra(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.STONE_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaMadeira(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.WOOD_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final void setDefaultCustoms(final Player bp, final String playerName) {
		removeDefaultCustoms(bp);
		playername.put(bp, playerName);
		armaduras.put(bp, "LEATHER");
		espada.put(bp, Material.WOOD_SWORD);
		recrafttype.put(bp, "COGUMELO");
		recraft.put(bp, Boolean.valueOf(false));
		sharpness.put(bp, Boolean.valueOf(true));
		fullsoup.put(bp, Boolean.valueOf(false));
	}

	public static final void openCustomInventory(final Player bp, final Player customer) {
		final Inventory custom = Bukkit.createInventory(bp, 54, "§a1v1 contra: " + customer.getName());

		custom.setItem(45, Itens1v1.newItem(Material.INK_SACK, "§cCancelar desafio", 1, (byte) 1));
		custom.setItem(53, Itens1v1.newItem(Material.INK_SACK, "§aDesafiar Jogador", 1, (byte) 10));

		if (espada.containsKey(bp)) {
			if (espada.get(bp) == Material.WOOD_SWORD) {
				custom.setItem(11, Itens1v1.newItem(Material.WOOD_SWORD, "§7Espada: §bMadeira",
						new String[] { "§7- Clique para mudar!"}));
			} else if (espada.get(bp) == Material.STONE_SWORD) {
				custom.setItem(11, Itens1v1.newItem(Material.STONE_SWORD, "§7Espada: §bPedra",
						new String[] { "§7- Clique para mudar!"}));
			} else if (espada.get(bp) == Material.IRON_SWORD) {
				custom.setItem(11, Itens1v1.newItem(Material.IRON_SWORD, "§7Espada: §bFerro",
						new String[] { "§7- Clique para §bmudar!"}));
			} else if (espada.get(bp) == Material.DIAMOND_SWORD) {
				custom.setItem(11, Itens1v1.newItem(Material.DIAMOND_SWORD, "§7Espada: §bDiamante",
						new String[] { "§7- Clique para mudar!"}));
			}
		}

		if (armaduras.containsKey(bp)) {
			if (armaduras.get(bp) == "LEATHER") {
				custom.setItem(12, Itens1v1.newItem(Material.LEATHER_CHESTPLATE, "§7Armadura: §bCouro",
						new String[] { "§7- Clique para mudar!"}));
			} else if (armaduras.get(bp) == "IRON") {
				custom.setItem(12, Itens1v1.newItem(Material.IRON_CHESTPLATE, "§7Armadura: §bFerro",
						new String[] { "§7- Clique para mudar!"}));
			} else if (armaduras.get(bp) == "DIAMOND") {
				custom.setItem(12, Itens1v1.newItem(Material.DIAMOND_CHESTPLATE, "§7Armadura: §bDiamante",
						new String[] { "§7- Clique para mudar!"}));
			} else if (armaduras.get(bp) == "SEM") {
				custom.setItem(12, Itens1v1.newItem(Material.GOLD_HELMET, "§7Armadura: §bNenhuma",
						new String[] { "§7- Clique para mudar!"}));
			}
		}

		if (recrafttype.containsKey(bp)) {
			if (recrafttype.get(bp) == "COGUMELO") {
				custom.setItem(30, Itens1v1.newItem(Material.RED_MUSHROOM, "§7Recraft: §bCogumelo",
						new String[] { "§7- Clique para mudar!"}));
			} else if (recrafttype.get(bp) == "COCOABEAN") {
				final ItemStack Cacau = new ItemStack(Material.INK_SACK);
				final ItemMeta Cacaum = Cacau.getItemMeta();
				Cacau.setDurability((short)3);
				Cacaum.setDisplayName("§7Recraft: §bCocoabean");
				ArrayList<String> CacauDesc = new ArrayList<String>();
				CacauDesc.add("§7- Clique para mudar!");
				Cacaum.setLore(CacauDesc);
				Cacau.setItemMeta((ItemMeta)Cacaum);
				custom.setItem(30, Cacau);
			}
		}

		if (recraft.containsKey(bp)) {
			if (recraft.get(bp)) {
				custom.setItem(32, Itens1v1.newItem(Material.SLIME_BALL, "§7Recraft: §bSim",
						new String[] { "§7- Clique para mudar!"}));
			} else if (!recraft.get(bp)) {
				custom.setItem(32, Itens1v1.newItem(Material.MAGMA_CREAM, "§7Recraft: §bNão",
						new String[] { "§7- Clique para mudar!"}));
			}
		}

		if (sharpness.containsKey(bp)) {
			if (sharpness.get(bp)) {
				custom.setItem(15, Itens1v1.newItem(Material.ENCHANTED_BOOK, "§7Afiação: §bSim",
						new String[] { "§7- Clique para mudar!"}));
			} else if (!sharpness.get(bp)) {
				custom.setItem(15, Itens1v1.newItem(Material.BOOK, "§7Afiação: §bNão",
						new String[] { "§7- Clique para mudar§7!"}));
			}
		}

		if (fullsoup.containsKey(bp)) {
			if (fullsoup.get(bp)) {
				custom.setItem(14, Itens1v1.newItem(Material.MUSHROOM_SOUP, "§7FullSopa: §bSim",
						new String[] { "§7- Clique para mudar!"}));
			} else if (!fullsoup.get(bp)) {
				custom.setItem(14, Itens1v1.newItem(Material.BOWL, "§7FullSopa: §bNão",
						new String[] { "§7- Clique para mudar!"}));
			}
		}

		bp.openInventory(custom);
	}

	@EventHandler
	public final void onCustomItensChange(final InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			final Player bp = (Player) e.getWhoClicked();
			if (e.getInventory().getName().equalsIgnoreCase("§a1v1 contra: " + playername.get(bp)) && e.getCurrentItem() != null) {
				
				if (e.getCurrentItem().getType() == Material.AIR) {
					e.setCancelled(true);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCancelar desafio")) {
					bp.closeInventory();
					e.setCancelled(true);
					return;
				}
				if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					espada.put(bp, Material.STONE_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					espada.put(bp, Material.IRON_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					espada.put(bp, Material.DIAMOND_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					espada.put(bp, Material.WOOD_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				// armaduras //
				if (e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					armaduras.put(bp, "IRON");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					armaduras.put(bp, "DIAMOND");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					armaduras.put(bp, "SEM");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.GOLD_HELMET) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					armaduras.put(bp, "LEATHER");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				// tipo de recraft //
				if (e.getCurrentItem().getType() == Material.RED_MUSHROOM) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					recrafttype.put(bp, "COCOABEAN");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Recraft: §bCocoabean")) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					recrafttype.put(bp, "COGUMELO");
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				// recraft ativado/desativado //
				if (e.getCurrentItem().getType() == Material.SLIME_BALL) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					recraft.put(bp, Boolean.valueOf(false));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.MAGMA_CREAM) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					recraft.put(bp, Boolean.valueOf(true));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				// sharpness ativado/desativado //
				if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					sharpness.put(bp, Boolean.valueOf(false));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.BOOK) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					sharpness.put(bp, Boolean.valueOf(true));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				// fullsopa ativado/desativado //
				if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					fullsoup.put(bp, Boolean.valueOf(false));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.BOWL) {
					e.setCancelled(true);
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					fullsoup.put(bp, Boolean.valueOf(true));
					openCustomInventory(bp, Bukkit.getPlayer(playername.get(bp)));
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aDesafiar Jogador")) {
					e.setCancelled(true);
					bp.closeInventory();
					if (Bukkit.getPlayer(playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("");
						return;
					}
					bp.sendMessage("§aVocê desafiou o jogador: " + playername.get(bp) + " para um 1v1 custom!");
					Bukkit.getPlayer(playername.get(bp)).sendMessage("§aVocê foi desafiado pelo jogador: " + bp.getName() + " para um 1v1 custom!");
					Eventos1v1.cooldown.add(bp);
					Eventos1v1.challengec.put(bp, Bukkit.getPlayer(playername.get(bp)));
					Bukkit.getScheduler().runTaskLater(KitPvP.getInstance(), new Runnable() {
						@Override
						public void run() {
							if (Eventos1v1.cooldown.contains(bp)) {
								Eventos1v1.cooldown.remove(bp);
							}
							if (Eventos1v1.challengec.containsKey(bp)) {
								Eventos1v1.challengec.remove(bp);
							}
						}
					}, 200);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
}
