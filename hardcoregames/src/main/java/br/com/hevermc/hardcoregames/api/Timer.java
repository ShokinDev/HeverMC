package br.com.hevermc.hardcoregames.api;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.enums.States;
import br.com.hevermc.hardcoregames.schematicutils.Schematic;
import br.com.hevermc.hardcoregames.score.ScoreboardManager;

public class Timer {

	public static Integer task = null;
	public static int tempo = 300;
	static Material[] mfnoStack = { Material.ENCHANTMENT_TABLE, Material.DIAMOND_SWORD, Material.IRON_SWORD,
			Material.DIAMOND_PICKAXE, Material.ANVIL, Material.BOW, Material.FLINT_AND_STEEL };
	static Material[] mfstack = { Material.BOWL, Material.BREAD, Material.EXP_BOTTLE, Material.CACTUS, Material.DIAMOND,
			Material.IRON_INGOT };
	boolean feast = false;

	public static void iniciarTimer() {
		task = Integer.valueOf(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(HardcoreGames.getInstance(),
				new Runnable() {
					public void run() {
						if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
							Bukkit.getWorlds().get(0).setTime(0);
							Bukkit.getWorlds().get(0).setStorm(false);
							Bukkit.getWorlds().get(0).setThundering(false);

							if (!(HardcoreGames.getManager().inGame
									.size() < HardcoreGames.getManager().minPlayerToStart)) {
								tempo--;
							} else {
								return;
							}

							if (tempo == 0) {
								HardcoreGames.getManager().setStateGame(States.INVENCIVEL);
								for (Player ons : Bukkit.getOnlinePlayers()) {
									ReflectionAPI.sendTitle(ons, "§c§lPREJOGO", "§fO pre-jogo §cacabou§f!", 8, 8, 8);
									ons.getInventory().clear();

									HGPlayer hgall = new HGPlayerLoader(ons).load().getHGP();
									ons.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
									new ScoreboardManager().build(ons);
									for (Block b : Schematic.Portoes) {
										b.setType(Material.AIR);
									}
									if (!hgall.isAdminMode()) {
										ons.setGameMode(GameMode.SURVIVAL);
										ons.setHealth(20);
										ons.setExp(0);
										ons.setFoodLevel(20);
										ons.getInventory().addItem(new ItemStack(Material.COMPASS));
										ons.getInventory().setItem(1, new ItemStack(hgall.getKit1().getItem()));
										ons.getInventory().setItem(2, new ItemStack(hgall.getKit2().getItem()));
									}
								}

								HardcoreGames.getManager().broadcast("§cO pre-jogo foi finalizado!");
							}

						} else if (HardcoreGames.getManager().getStateGame() == States.INVENCIVEL) {

							if (tempo == 0) {
								HardcoreGames.getManager().setStateGame(States.ANDAMENTO);
								tempo = 0;
								for (Player ons : Bukkit.getOnlinePlayers()) {
									ons.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
									new ScoreboardManager().build(ons);

									ons.playSound(ons.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
									if ((ons.getLocation().getBlockY() > 128)
											&& (HardcoreGames.getManager().inGame.contains(ons))) {
										ons.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 400, 4));
										ons.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 4));
										ons.setFireTicks(400);
									}
									ReflectionAPI.sendTitle(ons, "§c§lINVENCIBILIDADE",
											"§fA invencibiliade §cacabou§f!", 8, 8, 8);
								}
								HardcoreGames.getManager().broadcast("§cA invencibilidade foi finalizada!");
								return;
							}
							checkMSG();
							tempo--;
							return;
						} else if (HardcoreGames.getManager().getStateGame() == States.ANDAMENTO) {
							if (coliseu == true) {
								if (tempo == 60 || tempo == 120 || tempo == 180 || tempo == 240 || tempo == 300
										|| tempo == 360 || tempo == 420 || tempo == 480 || tempo == 540 || tempo == 600
										|| tempo == 660 || tempo == 700 || tempo == 730) {
									destroy();
								}
							}
							if (tempo % 240 == 0 && tempo >= 2) {
								Location l = new Location(Bukkit.getWorld("world"), new Random().nextInt(280),
										new Random().nextInt(20) + 50, new Random().nextInt(280));
								HardcoreGames.getManager().spawnar("minifeast", l);
								HardcoreGames.getManager()
										.broadcast("§aUm mini-feast apareceu entre §eX: "
												+ (l.getX() - new Random().nextInt(10)) + " §eZ: "
												+ (l.getZ() - new Random().nextInt(10)));

								for (Block cb : Schematic.MFBaus) {
									Chest c = (Chest) cb;
									for (int i = 0; i < new Random().nextInt(8); i++) {
										if (i == 2 || i == 1) {
											c.getInventory().addItem(
													new ItemStack(mfnoStack[new Random().nextInt(mfnoStack.length)]));
										} else {

											c.getInventory().setItem(new Random().nextInt(64),
													new ItemStack(mfstack[new Random().nextInt(mfstack.length)]));
										}
									}

								}
							}
							if (tempo == 10 * 60) {
								// Feast.prepararFeast(false, 400);
								// GameAPI.spawnar("feast", Feast.feastLoc, false);
								// Feast.sendFeastMessage();
							} else if (tempo % 60 == 0 && tempo >= 11 * 60 && tempo <= (840)) {
								// Feast.sendFeastMessage();
							} else if (tempo == (840) + 30 || tempo == (840) + 50 || tempo == (840) + 55
									|| tempo == (840) + 56 || tempo == (840) + 57 || tempo == (840) + 58
									|| tempo == (840) + 59) {
								// Feast.sendFeastMessage();
							} else if (tempo == 15 * 60) {
								// Feast.bausSpawned = true;
								// Feast.sendFeastMessage();
								// Feast.preencherBaus();
							}

							if (tempo >= 1800 && tempo < 2100 && tempo % 60 == 0) {

								HardcoreGames.getManager().broadcast("§aArena final irá spawnar em§e "
										+ HardcoreGames.getManager().formattedTime(tempo) + "§a!");

							} else if (tempo == 2100) {
								HardcoreGames.getManager().spawnarArenaFinal();

								for (Player p : Bukkit.getOnlinePlayers()) {
									p.teleport(p.getWorld().getBlockAt(0, 20, 0).getLocation());
									ReflectionAPI.sendTitle(p, "§e§lFINAL ARENA", "§fA arena §cfinal§f começou!", 5, 5,
											5);
								}
								HardcoreGames.getManager().broadcast("§fA arena §cfinal§f começou!");
							}
							if (tempo == 50 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §410 minutos.");
							} else if (tempo == 55 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §45 minutos.");
							} else if (tempo == 56 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §44 minutos§c.");
							} else if (tempo == 57 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §43 minutos.");
							} else if (tempo == 58 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §42 minutos.");
							} else if (tempo == 59 * 60) {
								HardcoreGames.getManager().broadcast("§cA partida irá acabar em §41 minuto.");
							} else if (tempo == 60 * 60) {
								for (Player on : Bukkit.getOnlinePlayers()) {
									on.sendMessage("§cA partida foi encerrada!");
								}
								HardcoreGames.getManager().stop();
								return;
							}

							tempo++;

						}

						Commons.getManager().getBackend().getRedis().set("hg-" + HardcoreGames.getManager().hgNumber,
								"status:" + HardcoreGames.getManager().getStateGame().toString() + ",timer:"
										+ HardcoreGames.getManager().formattedTime(tempo) + ",on:"
										+ HardcoreGames.getManager().inGame.size());
					}

				}, 20, 20));

	}

	public static void cancelTask() {
		if (task == null)
			return;

		Bukkit.getServer().getScheduler().cancelTask(task.intValue());
		task = null;
	}

	static boolean coliseu = true;

	public static void destroy() {
		for (int i = 0; i <= 30; i++) {
			if (Schematic.Coliseu.size() == 0) {
				coliseu = false;
				break;
			}
			Block b = Schematic.Coliseu.get(i);
			if (b != null)
				b.setType(Material.AIR);
		}
	}

	public static void checkMSG() {
		if (((tempo >= 10 ? 1 : 0) & (tempo % 60 == 0 ? 1 : 0)) != 0) {
			HardcoreGames.getManager().broadcast(getMensagem(tempo));
			Som(Sound.CLICK);
		} else if (tempo == 30) {
			HardcoreGames.getManager().broadcast(getMensagem(tempo));
			Som(Sound.CLICK);
		} else if (tempo == 15) {
			HardcoreGames.getManager().broadcast(getMensagem(tempo));
			Som(Sound.CLICK);
		} else if (tempo == 10) {
			HardcoreGames.getManager().broadcast(getMensagem(tempo));
			Som(Sound.CLICK);
		} else if (tempo <= 5) {
			HardcoreGames.getManager().broadcast(getMensagem(tempo));
			Som(Sound.NOTE_PLING);
		}
	}

	public static String getMensagem(int tempo) {
		if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
			return "§aO jogo irá começar em " + HardcoreGames.getManager().formattedTime(tempo) + ".";
		} else if (HardcoreGames.getManager().getStateGame() == States.INVENCIVEL) {
			return "§aA invencibilidade irá acabar em " + HardcoreGames.getManager().formattedTime(tempo) + ".";
		}
		return "";
	}

	public static void Som(Sound som) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), som, 1.0F, 1.0F);
		}
	}
}
