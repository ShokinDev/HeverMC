package br.com.hevermc.pvp.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.util.Vector;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.OneVsOneLoader;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.api.WarpsAPI;
import br.com.hevermc.pvp.command.BuildCommand;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.gui.Selector;
import br.com.hevermc.pvp.gui.Warps;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;
import br.com.hevermc.pvp.score.ScoreboardManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class GeneralListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		pvp.setProtectArea(true);
		for (int i = 0; i < 100; i++)
			p.sendMessage("");
		p.sendMessage("§6§lHEVER§f§lMC");
		p.sendMessage("");
		p.sendMessage("§fSeja bem-vindo ao nosso §aKitPvP§f, esperamos que divirta-se!");
		p.sendMessage("");
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0,
				new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits §7(Abra com o botão direito)")
						.create());
		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§eWarps §7(Abra com o botão direito)").create());
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		KitPvP.getManager().online.add(p);
	}

	ArrayList<Block> sign = new ArrayList<Block>();

	@EventHandler
	public void onSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("soup")) {
			e.setLine(0, "");
			e.setLine(1, "§6§lHEVER§f§lMC");
			e.setLine(2, "§eSopas");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("recraft")) {
			e.setLine(0, "");
			e.setLine(1, "§6§lHEVER§f§lMC");
			e.setLine(2, "§eRecraft");
			e.setLine(3, "");
			sign.add(e.getBlock());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		PvPPlayer hp = new PlayerLoader(p).load().getPvPP();
		KitPvP.getManager().online.remove(p);
		if (hp.isCombat()) {
			if (hp.getInCombat() == null)
				return;
			hp.getInCombat().sendMessage("§cSeu adversario deslogou em combate.");
			PvPPlayer pvp = new PlayerLoader(hp.getInCombat()).load().getPvPP();
			if (pvp.isOnevsone()) {
				pvp.setOvs_wins(pvp.getOvs_wins() + 1);
				pvp.setOvs_ws(pvp.getOvs_ws() + 1);
				hp.setOvs_loses(hp.getOvs_loses() + 1);
				new OneVsOneLoader(p, hp.getInCombat()).finish();
			}
			hp.setCombat(false);
			hp.setInCombat(null);
			pvp.setCombat(false);
			pvp.setInCombat(null);
		}
		new PlayerLoader(p).unload();

	}

	public static ArrayList<Player> a = new ArrayList<Player>();

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
		if (new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY() - 1, e.getFrom().getZ())
				.getBlock().getType() == Material.SPONGE) {
			p.setVelocity(new Vector().setY(10));
			if (!a.contains(p))
				a.add(p);

		}
		if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.SPAWN) {
			if (new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY() - 1, e.getFrom().getZ())
					.getBlock().getType() == Material.GRASS && pvpp.isProtectArea()) {
				pvpp.setProtectArea(false);
				p.sendMessage("§cVocê perdeu sua proteção.");
				if (pvpp.getKit() == Kits.NENHUM)
					pvpp.setKit(p, Kits.PVP);
			}
		} else if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.FPS) {
			if (new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY() - 1, e.getFrom().getZ())
					.getBlock().getType() == Material.QUARTZ_BLOCK) {
				if (pvpp.isProtectArea()) {
					pvpp.setProtectArea(false);
					p.sendMessage("§cVocê perdeu sua proteção.");
					if (pvpp.getKit() != Kits.FPS)
						pvpp.setKit(p, Kits.FPS);
				}
			}

		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getType() == Material.WALL_SIGN
					|| e.getClickedBlock().getType() == Material.SIGN_POST
					|| e.getClickedBlock().getType() == Material.SIGN) {
				Sign s = (Sign) e.getClickedBlock().getState();
				String[] lines = s.getLines();
				if (lines.length > 0 && lines[2].equals("§eSopas")) {
					Inventory inv = Bukkit.createInventory(null, 5 * 9, "§eMushroom soups");
					for (int i = 0; i < inv.getSize(); i++)
						inv.addItem(new ItemStack(Material.MUSHROOM_SOUP));
					p.openInventory(inv);
				} else if (lines.length > 0 && lines[2].equals("§eRecraft")) {
					Inventory inv = Bukkit.createInventory(null, 9, "§eRecrafts");

					inv.setItem(0, new ItemStack(Material.BOWL, 64));
					inv.setItem(1, new ItemStack(Material.RED_MUSHROOM, 64));
					inv.setItem(2, new ItemStack(Material.BROWN_MUSHROOM, 64));

					p.openInventory(inv);
				}
			}
		}
		if (e.getMaterial() != Material.AIR && e.getMaterial() != null) {

			if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
					&& e.getMaterial() == Material.MUSHROOM_SOUP) {
				HeverKit kit = new HeverKit(Kits.QUICKDROPPER);
				kit.setPlayer(p);

				if (p.getHealth() != p.getMaxHealth()) {
					if (p.getHealth() > p.getMaxHealth() - 7.0D) {
						p.setHealth(p.getMaxHealth());
					} else {
						p.setHealth(p.getHealth() + 7.0D);
					}
					p.getItemInHand().setType(Material.BOWL);
					p.getItemInHand().setAmount(1);

					if (kit.usingKit())
						p.getInventory().remove(Material.BOWL);
				}
			} else if (e.getMaterial() == Material.CHEST) {
				new Selector(p);
			} else if (e.getMaterial() == Material.COMPASS && pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.SPAWN) {
				if (pvp.isProtectArea() && pvp.getKit() == Kits.NENHUM) {
					new Warps(p);
				} else {
					double distance = 500.0D;
					Player target = null;
					for (Player all : Bukkit.getOnlinePlayers()) {
						double distancePlayerToVictm = p.getLocation().distance(all.getLocation());
						PvPPlayer pvp2 = new PlayerLoader(all).load().getPvPP();
						if (!pvp2.isProtectArea()) {
							if (distancePlayerToVictm < distance && distancePlayerToVictm > 10.0D) {
								distance = distancePlayerToVictm;
								target = all;
							}
						}
					}
					if (target == null) {
						p.sendMessage("§cNenhum jogador encontrado!");
						p.setCompassTarget(p.getWorld().getSpawnLocation());
					} else {
						p.sendMessage("§eBussola apontando para §a" + target.getName());
						p.setCompassTarget(target.getLocation());

					}
				}
			}

		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (e.getEntity().getKiller() instanceof Player && e.getEntity().getKiller() == null) {
			Player p = e.getEntity();
			p.sendMessage("§cVocê morreu sozinho!");
		}
		if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			Player killer = e.getEntity().getKiller();
			PvPPlayer pvpkiller = new PlayerLoader(killer).load().getPvPP();
			PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
			HeverPlayer hpkiller = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(killer);
			if (pvpp.getWarp() != br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
				pvpp.setDeaths(pvpp.getDeaths() + 1);
				pvpp.setKs(0);
				pvpkiller.setKills(pvpkiller.getKills() + 1);
				pvpkiller.setKs(pvpkiller.getKs() + 1);
			} else {
				pvpp.setOvs_loses(pvpp.getOvs_loses() + 1);
				pvpkiller.setOvs_wins(pvpkiller.getOvs_wins() + 1);
				pvpkiller.setOvs_ws(pvpkiller.getOvs_ws() + 1);
			}
			int xp = 2 + new Random().nextInt(10);

			hpkiller.setXp(hpkiller.getXp() + xp);
			p.sendMessage("§cVocê morreu para " + killer.getName());
			killer.sendMessage("§aVocê matou " + p.getName());
			killer.sendMessage("§aForam adicionados §e" + xp + " §aXPS na sua conta!");
			hpkiller.update();
			pvpkiller.update();
			pvpp.setCombat(false);
			pvpkiller.setCombat(false);
			pvpp.setInCombat(null);
			pvpkiller.setInCombat(null);
			pvpp.update();
		}
		if (e.getEntity() instanceof Player) {

			new BukkitRunnable() {

				@Override
				public void run() {
					CraftPlayer cp = (CraftPlayer) e.getEntity();
					EntityPlayer ep = cp.getHandle();
					PlayerConnection c = ep.playerConnection;
					PacketPlayInClientCommand pk = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);

					c.a(pk);

				}
			}.runTaskLater(KitPvP.getInstance(), 5);
		}
	}

	@EventHandler
	public void onInteractPlayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player a = (Player) e.getRightClicked();
			PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
			if (pvp.isAdminMode())
				p.openInventory(a.getInventory());
		}
	}

	@EventHandler
	public void onInteractInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (e.getInventory().getName().equalsIgnoreCase("§eSeletor de Kits")) {
			e.setCancelled(true);
			for (Kits kits : Kits.values()) {
				if (kits.getMaterial() != Material.AIR) {
					if (kits.getMaterial() == e.getCurrentItem().getType()) {
						p.closeInventory();
						e.setCancelled(true);
						pvp.setKit(p, kits);
					}
				}
			}
		} else if (e.getInventory().getName().equalsIgnoreCase("§eWarps")) {
			e.setCancelled(true);
			for (br.com.hevermc.pvp.enums.Warps warps : br.com.hevermc.pvp.enums.Warps.values()) {
				if (warps.getMaterial() == e.getCurrentItem().getType() && warps.getMaterial() != Material.AIR) {
					if (warps == br.com.hevermc.pvp.enums.Warps.ONEVSONE || new WarpsAPI(warps).getLocation() == null) {
						p.sendMessage("§cEsta warp está em desenvolvimento!");
						return;
					}
					e.setCancelled(true);
					p.getInventory().clear();
					p.closeInventory();
					p.teleport(new WarpsAPI(warps).getLocation());
					pvp.setWarp(warps);
					pvp.setProtectArea(true);
					p.sendMessage("§eVocê está sendo teleportado até a warp §a" + warps.getName());

					if (warps == br.com.hevermc.pvp.enums.Warps.FPS) {
						pvp.setKit(p, Kits.FPS);
					} else if (warps == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						new ScoreboardManager().build(p);
						p.getInventory().setItem(0,
								new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
						p.getInventory().setItem(1,
								new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida")
										.create());
					} else if (warps == br.com.hevermc.pvp.enums.Warps.LAVA) {
						pvp.setKit(p, Kits.LAVA);
					}

				}
			}
		}

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		if (pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.SPAWN) {
			p.chat("/spawn");
		} else if (pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.FPS) {
			p.getInventory().clear();
			p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.FPS).getLocation());
			pvp.setWarp(br.com.hevermc.pvp.enums.Warps.FPS);
			pvp.setProtectArea(true);
			pvp.setKit(p, Kits.FPS);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.FPS).getLocation());

				}
			}.runTaskLater(KitPvP.getInstance(), 7L);
		} else if (pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
			p.getInventory().clear();
			p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.ONEVSONE).getLocation());
			pvp.setWarp(br.com.hevermc.pvp.enums.Warps.ONEVSONE);
			pvp.setProtectArea(true);
			p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.BLAZE_ROD), "§6Desafie").create());
			p.getInventory().setItem(1,
					new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8), "§7Fila rápida").create());
		} else if (pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.LAVA) {
			p.getInventory().clear();
			pvp.setWarp(br.com.hevermc.pvp.enums.Warps.LAVA);
			p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());
			pvp.setProtectArea(true);
			pvp.setKit(p, Kits.LAVA);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());

				}
			}.runTaskLater(KitPvP.getInstance(), 7L);
		}
	}

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (e.getItemDrop().getItemStack().getType() == pvp.getKit().getMaterial()
				|| e.getItemDrop().getItemStack().getType() == Material.STONE_SWORD
				|| e.getItemDrop().getItemStack().getType() == Material.COMPASS || pvp.isProtectArea()) {
			if (e.getItemDrop().getItemStack().getType() != Material.BOWL)
				e.setCancelled(true);
		} else {
			e.getItemDrop().remove();

		}
	}

	@EventHandler
	public void onDrop(ItemSpawnEvent e) {
		e.getEntity().remove();
	}

	@EventHandler
	public void onWheather(WeatherChangeEvent e) {
		e.getWorld().setWeatherDuration(0);
		e.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
			if (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE
					|| e.getCause() == DamageCause.FIRE_TICK) {
				if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.LAVA)
					return;
			}
			if (e.getCause() == DamageCause.FALL) {

				if (a.contains(p)) {
					a.remove(p);
					e.setCancelled(true);
				}
				if (pvpp.getKit() == Kits.NOFALL)
					e.setCancelled(true);
			}
			if (pvpp.isProtectArea())
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void buffDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
			PvPPlayer pvpd = new PlayerLoader(d).load().getPvPP();
			if (KitPvP.getManager().ovs.containsKey(p.getName().toLowerCase())) {
				if (!KitPvP.getManager().ovs.containsKey(d.getName().toLowerCase()))
					e.setCancelled(true);
			}
			if (pvpp.isProtectArea() || pvpd.isProtectArea()) {
				e.setCancelled(true);
			} else {
				if (!pvpp.isCombat()) {
					pvpp.setCombat(true);
					pvpd.setInCombat(d);
					new BukkitRunnable() {

						@Override
						public void run() {
							pvpp.setCombat(false);
							pvpp.setInCombat(null);
						}
					}.runTaskLater(KitPvP.getInstance(), 60L);
				}
				if (!pvpd.isCombat()) {
					pvpd.setCombat(true);
					pvpd.setInCombat(p);
					new BukkitRunnable() {

						@Override
						public void run() {
							pvpd.setCombat(false);
							pvpd.setInCombat(null);
						}

					}.runTaskLater(KitPvP.getInstance(), 60L);
				}

				if (d.getItemInHand().getType() == Material.STONE_SWORD) {
					p.getItemInHand().setDurability((short) 0);
					p.updateInventory();
					e.setDamage(e.getDamage() - 3.5D);
				}
			}
		}
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
		e.setCancelled(true);
	}

}
