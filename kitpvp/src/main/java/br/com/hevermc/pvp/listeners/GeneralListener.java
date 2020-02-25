package br.com.hevermc.pvp.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.util.Vector;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.api.WarpsAPI;
import br.com.hevermc.pvp.command.BuildCommand;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.gui.Selector;
import br.com.hevermc.pvp.gui.Warps;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;
import br.com.hevermc.pvp.onevsone.Eventos1v1;
import br.com.hevermc.pvp.onevsone.Invicivel1v1;
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
		pvp.setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
		pvp.setProtectArea(true);
		p.sendMessage("");
		p.sendMessage("§fSeja bem-vindo ao nosso §aKitPvP§f, esperamos que divirta-se!");
		p.sendMessage("");
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(3,
				new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits §7(Abra com o botão direito)")
						.create());
		p.getInventory().setItem(5,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§eWarps §7(Abra com o botão direito)").create());
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		KitPvP.getManager().online.add(p);
		if (KitPvP.getManager().inEvent.contains(p))
			KitPvP.getManager().inEvent.remove(p);
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
		System.out.print("rodou1");
		Player p = e.getPlayer();
		String name = p.getName();
		PvPPlayer hp = new PlayerLoader(p).load().getPvPP();
		KitPvP.getManager().online.remove(p);

		if (KitPvP.getManager().inEvent.contains(p)) {
			KitPvP.getManager().inEvent.remove(p);
			new PlayerLoader(p).load().getPvPP().setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
		}
		if (hp.isCombat()) {
			PvPPlayer pvp = new PlayerLoader(hp.getInCombat()).load().getPvPP();
			hp.getInCombat().sendMessage("§cSeu adversario deslogou em combate.");

			HeverPlayer hp4 = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p);
			HeverPlayer hp3 = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(hp.getInCombat());
			hp3.setPvp_kills(hp3.getPvp_kills() + 1);
			hp3.setPvp_ks(hp3.getPvp_kills() + 1);
			hp4.setPvp_ks(0);
			hp4.setPvp_deaths(hp4.getPvp_deaths() + 1);
			int xp = 2 + new Random().nextInt(10);
			hp.getInCombat().sendMessage("§aVocê matou " + name);
			hp.getInCombat().sendMessage("§aForam adicionados §e" + xp + " §aXPS na sua conta!");
			hp3.setXp(xp);
			if (pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
				Eventos1v1.fighting.remove(hp.getInCombat());
				Eventos1v1.fighting.remove(p);
				Eventos1v1.playerfigh.remove(p);
				Eventos1v1.playerfigh.remove(hp.getInCombat());
				Eventos1v1.Combate1.remove(p);
				Eventos1v1.Combate1.remove(hp.getInCombat());
				int sopsK = Eventos1v1.itemsInInventory(hp.getInCombat().getInventory(),
						new Material[] { Material.MUSHROOM_SOUP });
				Eventos1v1.defaultItens(hp.getInCombat());
				hp.getInCombat().teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.ONEVSONE).getLocation());
				hp.setWarp(br.com.hevermc.pvp.enums.Warps.ONEVSONE);
				for (int i = 6; i > 0; i--) {
					Invicivel1v1.playerHideShowMethod(p);
					Invicivel1v1.playerHideShowMethod(hp.getInCombat());
				}

				if (Eventos1v1.batalhando.containsKey(p)) {
					Eventos1v1.batalhando.remove(p);
				}
				if (Eventos1v1.batalhando.containsKey(hp.getInCombat())) {
					Eventos1v1.batalhando.remove(hp.getInCombat());
				}

				hp.getInCombat().sendMessage("§aVocê venceu o duelo contra §e" + p.getName() + " §acom: §e"
						+ Eventos1v1.cora(hp.getInCombat()) + " corações §ae §e" + sopsK + " §asopas restantes!");
				hp.getInCombat().playSound(hp.getInCombat().getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				p.sendMessage("§a" + hp.getInCombat().getName() + " §evenceu§a o §e1v1 §acom §e"
						+ Eventos1v1.cora(hp.getInCombat()) + " corações§a e §e" + sopsK + " §asopas restantes!");
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				hp.getInCombat().setHealth(20);
				pvp.setCombat(false);
				pvp.setInCombat(null);
				hp.setCombat(false);
				hp.setInCombat(null);
			}
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
		if (KitPvP.getManager().buildInEvent == false) {
			if (e.getMaterial() == Material.LAVA_BUCKET || e.getMaterial() == Material.WATER_BUCKET) {
				e.setCancelled(true);
			}
		}
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
		if (e.getMaterial() == Material.BLAZE_ROD || e.getMaterial() == Material.IRON_FENCE
				|| e.getMaterial() == Material.INK_SACK) {
			e.setCancelled(true);
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
				new Selector(p, 1);
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
						p.sendMessage("§eBússola apontando para §a" + target.getName());
						p.setCompassTarget(target.getLocation());

					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (KitPvP.getManager().inEvent.contains(e.getEntity())) {
			KitPvP.getManager().inEvent.remove(e.getEntity());
			new PlayerLoader(e.getEntity()).load().getPvPP().setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
		}
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
			HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p);

			hpkiller.setPvp_kills(hpkiller.getPvp_kills() + 1);
			hpkiller.setPvp_ks(hpkiller.getPvp_ks() + 1);
			hp.setPvp_deaths(hp.getPvp_deaths() + 1);
			hp.setPvp_ks(0);

			int xp = 2 + new Random().nextInt(10);

			hpkiller.setXp(hpkiller.getXp() + xp);

			p.sendMessage("§cVocê morreu para " + killer.getName());
			killer.sendMessage("§aVocê matou " + p.getName());
			killer.sendMessage("§aForam adicionados §e" + xp + " §aXPS na sua conta!");

			pvpp.setCombat(false);
			pvpkiller.setCombat(false);
			pvpp.setInCombat(null);
			pvpkiller.setInCombat(null);

			if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
				Player k2 = Bukkit.getPlayerExact(Eventos1v1.playerfigh.get(p));
				Eventos1v1.fighting.remove(k2);
				Eventos1v1.fighting.remove(p);
				Eventos1v1.playerfigh.remove(p);
				Eventos1v1.playerfigh.remove(k2);
				Eventos1v1.Combate1.remove(p);
				Eventos1v1.Combate1.remove(k2);
				new PlayerLoader(p).load().getPvPP().setProtectArea(true);
				new PlayerLoader(k2).load().getPvPP().setProtectArea(true);
				int sopsK = Eventos1v1.itemsInInventory(k2.getInventory(), new Material[] { Material.MUSHROOM_SOUP });
				Eventos1v1.defaultItens(k2);

				k2.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.ONEVSONE).getLocation());
				for (int i = 6; i > 0; i--) {
					Invicivel1v1.playerHideShowMethod(p);
					Invicivel1v1.playerHideShowMethod(k2);
				}

				if (Eventos1v1.batalhando.containsKey(p)) {
					Eventos1v1.batalhando.remove(p);
				}
				if (Eventos1v1.batalhando.containsKey(k2)) {
					Eventos1v1.batalhando.remove(k2);
				}

				k2.sendMessage("§aVocê venceu o duelo contra §e" + p.getName() + " §acom: §e" + Eventos1v1.cora(k2)
						+ " corações §ae §e" + sopsK + " §asopas restantes!");
				k2.playSound(k2.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				p.sendMessage("§a" + k2.getName() + " §evenceu§a o §e1v1 §acom §e" + Eventos1v1.cora(k2)
						+ " corações§a e §e" + sopsK + " §asopas restantes!");
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				k2.setHealth(20);
			}
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
		if (e.getCurrentItem() == null) {
			e.setCancelled(true);
			return;
		}
		if (e.getInventory().getName().startsWith("§eSeletor de Kits")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.COMPASS) {
				p.closeInventory();
				new Warps(p);
			}
			if (e.getInventory().getName().equalsIgnoreCase("§eSeletor de Kits §7(1/2)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					p.closeInventory();
					new Selector(p, 2);
				} else if (e.getCurrentItem().getType() == Material.COMPASS) {
					p.closeInventory();
					new Warps(p);
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§eSeletor de Kits §7(2/2)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					p.closeInventory();
					new Selector(p, 1);
				} else if (e.getCurrentItem().getType() == Material.COMPASS) {
					p.closeInventory();
					new Warps(p);
				}
			}
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
			if (e.getCurrentItem().getType() == Material.CHEST) {
				p.closeInventory();
				new Selector(p, 1);
			}
			for (br.com.hevermc.pvp.enums.Warps warps : br.com.hevermc.pvp.enums.Warps.values()) {
				if (warps.getMaterial() == e.getCurrentItem().getType() && warps.getMaterial() != Material.AIR) {
					if (new WarpsAPI(warps).getLocation() == null) {
						e.setCancelled(true);
						p.sendMessage("§cEsta warp está em desenvolvimento!");
						return;
					}
					if (warps == br.com.hevermc.pvp.enums.Warps.EVENTO) {

						if (KitPvP.getManager().startedEvent == false && KitPvP.getManager().eventOcurring == false) {
							p.sendMessage("§cNão há nenhum evento ativo no momento");
							return;
						} else {
							if (KitPvP.getManager().joinInEvent == false) {
								p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.SPECEVENTO).getLocation());
								p.sendMessage("§eO evento já §ainiciou§e, você está na sala de espectadores!");
								pvp.setWarp(br.com.hevermc.pvp.enums.Warps.SPECEVENTO);
								p.getInventory().clear();
								new ScoreboardManager().build(p);
								p.setHealth(20);
								pvp.setCombat(false);
								pvp.setInCombat(null);
								pvp.setKit(Kits.NENHUM);
								pvp.setProtectArea(true);
								KitPvP.getManager().specEvent.add(p);
							} else {
								p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.EVENTO).getLocation());
								p.sendMessage("§aVocê entrou na warp §eevento§a!");
								KitPvP.getManager().inEvent.add(p);
								p.getInventory().clear();
								new ScoreboardManager().build(p);
								p.setHealth(20);
								pvp.setCombat(false);
								pvp.setInCombat(null);
								pvp.setKit(Kits.NENHUM);
								pvp.setProtectArea(true);
								pvp.setWarp(br.com.hevermc.pvp.enums.Warps.EVENTO);
							}
							return;
						}
					}
					e.setCancelled(true);
					p.getInventory().clear();
					p.closeInventory();
					p.teleport(new WarpsAPI(warps).getLocation());
					pvp.setWarp(warps);
					pvp.setProtectArea(true);
					p.sendMessage("§eVocê está sendo teleportado até a warp §a" + warps.getName());
					new ScoreboardManager().build(p);
					if (warps == br.com.hevermc.pvp.enums.Warps.FPS) {
						pvp.setKit(p, Kits.FPS);
					} else if (warps == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
						Eventos1v1.defaultItens(p);
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
			pvp.setWarp(br.com.hevermc.pvp.enums.Warps.ONEVSONE);
			pvp.setProtectArea(true);
			new BukkitRunnable() {

				@Override
				public void run() {
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.ONEVSONE).getLocation());
					Eventos1v1.defaultItens(p);
				}
			}.runTaskLater(KitPvP.getInstance(), 7L);

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
		if (KitPvP.getManager().buildInEvent && KitPvP.getManager().inEvent.contains(e.getPlayer())) {
			blocks.add(e.getBlock());
			return;
		}
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	public static ArrayList<Block> blocks = new ArrayList<Block>();

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
		if (KitPvP.getManager().buildInEvent && KitPvP.getManager().inEvent.contains(e.getPlayer())
				&& e.getBlock().getType() != Material.GLASS && e.getBlock().getType() != Material.STAINED_CLAY)
			return;
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (e.getItemDrop().getItemStack().getType() == pvp.getKit().getMaterial()
				|| e.getItemDrop().getItemStack().getType() == Material.STONE_SWORD
				|| e.getItemDrop().getItemStack().getType() == Material.DIAMOND_SWORD
				|| e.getItemDrop().getItemStack().getType() == Material.COMPASS || pvp.isProtectArea()) {
			if (e.getItemDrop().getItemStack().getType() != Material.BOWL
					&& e.getItemDrop().getItemStack().getType() != Material.MUSHROOM_SOUP)
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
			if (Eventos1v1.batalhando.containsKey(d)) {
				if (!Eventos1v1.batalhando.get(d).equalsIgnoreCase(p.getName())) {
					e.setCancelled(true);
				}
			}
			if (pvpp.isProtectArea() || pvpd.isProtectArea()) {
				e.setCancelled(true);
				return;
			}
			if (!pvpp.isCombat() && pvpp.getWarp() != br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
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
			if (!pvpd.isCombat() && pvpd.getWarp() != br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
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

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
		e.setCancelled(true);
	}

}
