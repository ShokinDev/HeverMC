package br.com.hevermc.pvp.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.AdminAPI;
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
		Bukkit.getOnlinePlayers().forEach(ps -> {
			AdminAPI.hideInAdminMode(ps);
		});
		p.setGameMode(GameMode.SURVIVAL);
		pvp.setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
		pvp.setProtectArea(true);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setAllowFlight(false);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0,
				new ItemConstructor(new ItemStack(Material.CHEST), "§aSeletor de kits §7(Abra com o botão direito)")
						.create());
		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.CHEST), "§aSeletor de kits 2 §7(Abra com o botão direito)")
						.create());
		p.getInventory().setItem(4,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§aWarps §7(Abra com o botão direito)").create());
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		KitPvP.getManager().online.add(p);
		if (KitPvP.getManager().inEvent.contains(p)) {
			KitPvP.getManager().inEvent.remove(p);
			KitPvP.getManager().killsInEvent.remove(p);
		}
	}

	ArrayList<Block> sign = new ArrayList<Block>();

	@EventHandler
	public void onSign(SignChangeEvent e) {
		if (e.getLine(0).equalsIgnoreCase("soup")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§eSopas");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("recraft")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§eRecraft");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("lfacil")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§aFacil");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("lmedio")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§eMedio");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("ldificil")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§cDificil");
			e.setLine(3, "");
			sign.add(e.getBlock());
		} else if (e.getLine(0).equalsIgnoreCase("lextremo")) {
			e.setLine(0, "");
			e.setLine(1, "§a§lNESTY§f§lMC");
			e.setLine(2, "§4Extremo");
			e.setLine(3, "");
			sign.add(e.getBlock());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		PvPPlayer hp = new PlayerLoader(p).load().getPvPP();
		if (KitPvP.getManager().online.contains(p))
			KitPvP.getManager().online.remove(p);
		Bukkit.getOnlinePlayers().forEach(ps -> {
			AdminAPI.hideInAdminMode(ps);
		});
		if (Eventos1v1.firstMatch == p.getUniqueId()) {
			Eventos1v1.firstMatch = null;
		}

		if (hp.isCombat()) {
			PvPPlayer pvp = new PlayerLoader(hp.getInCombat()).load().getPvPP();
			hp.getInCombat().sendMessage("§4§lCOMBATLOG §fSeu adversario §c§lDESLOGOU §fem combate!");

			if (KitPvP.getManager().inEvent.contains(p)
					&& KitPvP.getManager().killsInEvent.containsKey(hp.getInCombat())) {
				KitPvP.getManager().killsInEvent.put(hp.getInCombat(),
						KitPvP.getManager().killsInEvent.get(hp.getInCombat()) + 1);
			}

			HeverPlayer hp4 = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
			HeverPlayer hp3 = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader
					.getHP(hp.getInCombat().getName());
			hp3.setPvp_kills(hp3.getPvp_kills() + 1);
			hp3.setPvp_ks(hp3.getPvp_kills() + 1);
			hp4.setPvp_ks(0);
			hp4.setPvp_deaths(hp4.getPvp_deaths() + 1);

			if (Eventos1v1.firstMatch == p.getUniqueId()) {
				Eventos1v1.firstMatch = null;
			}
			int xp = 2 + new Random().nextInt(10);
			hp.getInCombat().sendMessage("§4§lKILL §fVocê matou §c" + name + "§f!");
			hp.getInCombat().sendMessage("§4§lKILL §fForam adicionados §3" + xp + " XPS §fna sua conta!");
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

				hp.getInCombat().sendMessage("§4§lKILL §fVocê venceu o duelo contra §3" + p.getName() + " §acom §3"
						+ Eventos1v1.cora(hp.getInCombat()) + " corações §fe §3" + sopsK + " §fsopas restantes!");
				hp.getInCombat().playSound(hp.getInCombat().getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				p.sendMessage("§4§lKILL §f" + hp.getInCombat().getName() + " §3venceu§f o §e1v1 §fcom §3"
						+ Eventos1v1.cora(hp.getInCombat()) + " corações§f e §3" + sopsK + " §fsopas restantes!");
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				hp.getInCombat().setHealth(20);
				pvp.setCombat(false);
				pvp.setInCombat(null);
				hp.setCombat(false);
				hp.setInCombat(null);
			}
		}
		if (KitPvP.getManager().inEvent.contains(p)) {
			KitPvP.getManager().inEvent.remove(p);
			KitPvP.getManager().killsInEvent.remove(p);
		}
		new PlayerLoader(p).unload();

	}

	public static ArrayList<Player> a = new ArrayList<Player>();

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
		if (pvpp.isAdminMode())
			return;

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
				p.sendMessage("§c§lPVP §fVocê perdeu sua §c§lPROTEÇÃO§f!");
				pvpp.setKits(p);
				if (pvpp.getKit() == Kits.NENHUM && pvpp.getKit2() == Kits.NENHUM)
					pvpp.setKit(p, Kits.PVP);
			}
		} else if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.FPS) {
			if (new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY() - 1, e.getFrom().getZ())
					.getBlock().getType() == Material.QUARTZ_BLOCK) {
				if (pvpp.isProtectArea()) {
					pvpp.setProtectArea(false);
					p.sendMessage("§c§lPVP §fVocê perdeu sua §c§lPROTEÇÃO§f!");
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
		if (e.getMaterial() == Material.DIAMOND_SWORD && pvp.isAdminMode()) {
			double distance = 5000.0D;
			Player target = null;
			for (Player all : Bukkit.getOnlinePlayers()) {
				double distancePlayerToVictm = p.getLocation().distance(all.getLocation());
				PvPPlayer pvp2 = new PlayerLoader(all).load().getPvPP();
				if (!pvp2.isProtectArea() && pvp2.isCombat()) {
					if (distancePlayerToVictm < distance && distancePlayerToVictm > 10.0D) {
						distance = distancePlayerToVictm;
						target = all;
					}
				}
			}
			if (target == null) {
				p.sendMessage("§4§lADMIN §fNão há jogadores em §3§lPVP§f!");
			} else {
				p.teleport(target);
				p.sendMessage("§4§lADMIN §fVocê foi até §b" + target.getName() + "§f!");
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
				} else if (lines.length > 0 && lines[2].equals("§aFacil")) {
					p.getInventory().clear();
					pvp.setWarp(br.com.hevermc.pvp.enums.Warps.LAVA);
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());
					pvp.setProtectArea(true);
					pvp.setKit(p, Kits.LAVA);
					HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
					hp.setCash(hp.getCash() + 5);
					p.sendMessage("§eVocê passou o desafio §aFacil§e!");
					p.sendMessage("§eVocê ganhou §35 cash's§e!");
				} else if (lines.length > 0 && lines[2].equals("§eMedio")) {
					p.getInventory().clear();
					pvp.setWarp(br.com.hevermc.pvp.enums.Warps.LAVA);
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());
					pvp.setProtectArea(true);
					pvp.setKit(p, Kits.LAVA);
					HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
					hp.setCash(hp.getCash() + 10);
					p.sendMessage("§eVocê passou o desafio §eMedio§e!");
					p.sendMessage("§eVocê ganhou §310 cash's§e!");
					hp.setCash(hp.getCash() + 10);
				} else if (lines.length > 0 && lines[2].equals("§cDificil")) {
					p.getInventory().clear();
					pvp.setWarp(br.com.hevermc.pvp.enums.Warps.LAVA);
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());
					pvp.setProtectArea(true);
					pvp.setKit(p, Kits.LAVA);
					HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
					hp.setCash(hp.getCash() + 15);
					p.sendMessage("§eVocê passou o desafio §cDificil§e!");
					p.sendMessage("§eVocê ganhou §315 cash's§e!");
				} else if (lines.length > 0 && lines[2].equals("§4Extremo")) {
					p.getInventory().clear();
					pvp.setWarp(br.com.hevermc.pvp.enums.Warps.LAVA);
					p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.LAVA).getLocation());
					pvp.setProtectArea(true);
					pvp.setKit(p, Kits.LAVA);
					HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
					hp.setCash(hp.getCash() + 20);
					p.sendMessage("§eVocê passou o desafio §4Extremo§e!");
					p.sendMessage("§eVocê ganhou §320 cash's§e!");
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
				if (e.getItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§aSeletor de kits §7(Abra com o botão direito)")) {
					new Selector(p, 1, 1);
				} else if (e.getItem().getItemMeta().getDisplayName()
						.equalsIgnoreCase("§aSeletor de kits 2 §7(Abra com o botão direito)")) {
					new Selector(p, 1, 2);
				}
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
						p.sendMessage("§3§lBUSSOLA §fNenhum jogador encontrado, apontando para o §3§lSPAWN§f!");
						p.setCompassTarget(p.getWorld().getSpawnLocation());
					} else {
						p.sendMessage("§3§lBUSSOLA §FSua bússola apontando para §3§l" + target.getName() + "§f!");
						p.setCompassTarget(target.getLocation());

					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);

		if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Player) {
			if (KitPvP.getManager().inEvent.contains(e.getEntity())) {
				new ScoreboardManager().build(e.getEntity());
				KitPvP.getManager().inEvent.remove(e.getEntity());
				KitPvP.getManager().killsInEvent.remove(e.getEntity());
				if (KitPvP.getManager().killsInEvent.containsKey(e.getEntity().getKiller())) {
					KitPvP.getManager().killsInEvent.put(e.getEntity().getKiller(),
							KitPvP.getManager().killsInEvent.get(e.getEntity().getKiller()) + 1);
				}

				PvPPlayer pvpp = new PlayerLoader(e.getEntity()).load().getPvPP();
				pvpp.setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
				new ScoreboardManager().build(e.getEntity());
			}
		} else if (e.getEntity() instanceof Player) {
			if (KitPvP.getManager().inEvent.contains(e.getEntity())) {
				KitPvP.getManager().inEvent.remove(e.getEntity());
				KitPvP.getManager().killsInEvent.remove(e.getEntity());
				Player p = e.getEntity();
				PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
				pvpp.setWarp(br.com.hevermc.pvp.enums.Warps.SPAWN);
				new ScoreboardManager().build(e.getEntity());
			}
		}
		Player p5 = e.getEntity();

		PvPPlayer pvpp5 = new PlayerLoader(p5).load().getPvPP();
		pvpp5.setCombat(false);
		pvpp5.setInCombat(null);

		if (KitPvP.getManager().inEvent.contains(e.getEntity())) {
			KitPvP.getManager().inEvent.remove(e.getEntity());
		}
		if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Player) {

			Player p = e.getEntity();
			Player killer = e.getEntity().getKiller();
			PvPPlayer pvpkiller = new PlayerLoader(killer).load().getPvPP();
			PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
			HeverPlayer hpkiller = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(killer.getName());
			HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());

			hpkiller.setPvp_kills(hpkiller.getPvp_kills() + 1);
			hpkiller.setPvp_ks(hpkiller.getPvp_ks() + 1);
			hp.setPvp_deaths(hp.getPvp_deaths() + 1);
			hp.setPvp_ks(0);

			int xp = 2 + new Random().nextInt(10);
			hpkiller.setXp(hpkiller.getXp() + xp);

			p.sendMessage("§4§lKILL §fVocê morreu para §b" + killer.getName() + "§f!");
			killer.sendMessage("§4§lKILL §fVocê matou §b" + p.getName() + "§f!");
			killer.sendMessage("§4§lKILL §fForam adicionados §b" + xp + " §fXPS na sua conta!");

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

				k2.sendMessage("§4§lKILL §fVocê venceu o duelo contra §3" + p.getName() + " §acom §3"
						+ Eventos1v1.cora(k2) + " corações §fe §3" + sopsK + " §fsopas restantes!");
				k2.playSound(k2.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				p.sendMessage("§4§lKILL §f" + k2.getName() + " §3venceu§f o §e1v1 §fcom §3" + Eventos1v1.cora(k2)
						+ " corações§f e §3" + sopsK + " §fsopas restantes!");
				p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 1.0F);
				AdminAPI.hideInAdminMode(k2);
				AdminAPI.hideInAdminMode(p);
				k2.setHealth(20);
			}
			msgks(killer, hpkiller.getPvp_ks());
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
			if (pvp.isAdminMode()) {
				if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
					p.openInventory(a.getInventory());
				} else if (e.getPlayer().getItemInHand().getType() == Material.STICK) {
					PvPPlayer tpvp = new PlayerLoader(a).load().getPvPP();
					HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(a.getName());
					p.sendMessage("  ");
					p.sendMessage("§eNickname: §a" + a.getName());
					p.sendMessage("§eGrupo: §a" + hp.getGroup().toString());
					p.sendMessage("  ");
					p.sendMessage("§eKit: §a" + tpvp.getKit().getName());
					p.sendMessage("§eWarp: §a" + tpvp.getWarp().getName());
					p.sendMessage("  ");
					p.sendMessage("§eEm combate: " + (tpvp.isCombat() ? "§aSim" : "§cNão"));
					p.sendMessage("§eEm combate com: "
							+ (tpvp.getInCombat() == null ? "§cNinguém" : "§a" + tpvp.getInCombat().getName()));
					p.sendMessage("§eEm área protegida: " + (tpvp.isProtectArea() ? "§aSim" : "§cNão"));
				}
			}
		}
	}

	@EventHandler
	public void onInteractInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (e.getCurrentItem() == null && e.getInventory().getName() == null && e.getCurrentItem().getItemMeta() == null
				&& e.getCurrentItem().getItemMeta().getDisplayName() == null) {
			e.setCancelled(true);
			return;
		}
		if (e.getInventory().getName().startsWith("§aSeletor de Kits")) {
			e.setCancelled(true);
			if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 1 §7(1/3)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					p.closeInventory();
					new Selector(p, 2, 1);
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {
						if (kits.getMaterial() == e.getCurrentItem().getType()) {

							pvp.setKit(kits);
							p.closeInventory();
							p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");

						}
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 1 §7(2/3)")) {

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					new Selector(p, 1, 1);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					p.closeInventory();
					new Selector(p, 3, 1);
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {
						if (kits.getMaterial() == e.getCurrentItem().getType()) {

							pvp.setKit(kits);
							p.closeInventory();
							p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");
						}
					}
				}

			} else if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 1 §7(3/3)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					new Selector(p, 2, 1);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {
						if (kits.getMaterial() == e.getCurrentItem().getType()) {

							pvp.setKit(kits);
							p.closeInventory();
							p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");

						}
					}
				}

			} else if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 2 §7(1/3)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					p.closeInventory();
					new Selector(p, 2, 1);
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit2() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {
						if (kits.getMaterial() == e.getCurrentItem().getType()) {
							pvp.setKit2(kits);
							p.closeInventory();
							p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");
						}
					}
				}
			} else if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 2 §7(2/3)")) {

				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					new Selector(p, 1, 1);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					p.closeInventory();
					new Selector(p, 3, 1);
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit2() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {

						if (kits.getMaterial() == e.getCurrentItem().getType()) {
							pvp.setKit2(kits);
							p.closeInventory();
							p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");
						}
					}
				}

			} else if (e.getInventory().getName().equalsIgnoreCase("§aSeletor de Kits 2 §7(3/3)")) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Página anterior")) {
					new Selector(p, 2, 1);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aPróxima página")) {
					return;
				}
				if (e.getCurrentItem().getType() != Material.AIR) {

					if (pvp.getKit2() != Kits.NENHUM) {
						p.sendMessage("§e§lKIT §fVocê já está §a§lUTILIZANDO§f um kit!");
						return;
					}
					for (Kits kits : Kits.values()) {
						if (kits.getMaterial() == e.getCurrentItem().getType()) {

								pvp.setKit2(kits);
								p.closeInventory();
								p.sendMessage("§e§lKIT §fVocê selecionou o kit §b§l" + kits.getName() + "§f!");
							
						}
					}
				}

			}

		} else if (e.getInventory().getName().equalsIgnoreCase("§aWarps")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.CHEST) {
				p.closeInventory();
				new Selector(p, 1, 1);
			}
			for (br.com.hevermc.pvp.enums.Warps warps : br.com.hevermc.pvp.enums.Warps.values()) {
				if (warps.getMaterial() == e.getCurrentItem().getType() && warps.getMaterial() != Material.AIR) {
					if (new WarpsAPI(warps).getLocation() == null) {
						e.setCancelled(true);
						p.sendMessage("§cEsta warp está em desenvolvimento!");
						return;
					}
					pvp.setKit(Kits.NENHUM);
					pvp.setKit2(Kits.NENHUM);

					if (KitPvP.getManager().inEvent.contains(p)) {
						KitPvP.getManager().inEvent.remove(p);
						KitPvP.getManager().killsInEvent.remove(p);
					}
					if (Eventos1v1.firstMatch == e.getWhoClicked().getUniqueId()) {
						Eventos1v1.firstMatch = null;
					}
					if (warps == br.com.hevermc.pvp.enums.Warps.EVENTO) {

						if (KitPvP.getManager().startedEvent == false && KitPvP.getManager().eventOcurring == false) {
							p.sendMessage("§3§lWARPS §fNão há §c§lNENHUM §fevento ativo no momento!");
							return;
						} else {
							if (KitPvP.getManager().joinInEvent == false) {
								p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.SPECEVENTO).getLocation());
								p.sendMessage(
										"§3§lWARPS §fO evento já §a§lINICIOU§f, você está na sala de espectadores!");
								pvp.setWarp(br.com.hevermc.pvp.enums.Warps.SPECEVENTO);
								p.getInventory().clear();
								new ScoreboardManager().build(p);
								p.setHealth(20);
								pvp.setCombat(false);
								pvp.setInCombat(null);
								pvp.setKit(Kits.NENHUM);
								pvp.setKit2(Kits.NENHUM);
								pvp.setProtectArea(true);
								KitPvP.getManager().specEvent.add(p);
								new ScoreboardManager().build(p);
							} else {
								p.teleport(new WarpsAPI(br.com.hevermc.pvp.enums.Warps.EVENTO).getLocation());
								p.sendMessage("§3§LWARPS §fVocê entrou na warp §e§lEVENTO§f!");
								KitPvP.getManager().inEvent.add(p);
								new ScoreboardManager().build(p);
								KitPvP.getManager().killsInEvent.put(p, 0);
								p.getInventory().clear();
								new ScoreboardManager().build(p);
								p.setHealth(20);
								pvp.setCombat(false);
								pvp.setInCombat(null);
								pvp.setKit2(Kits.NENHUM);
								pvp.setKit2(Kits.NENHUM);
								pvp.setProtectArea(true);
								pvp.setWarp(br.com.hevermc.pvp.enums.Warps.EVENTO);
								new ScoreboardManager().build(p);
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
					p.sendMessage(
							"§3§lWARPS §fVocê está sendo §a§lTELEPORTADO §faté a warp §3" + warps.getName() + "§f!");
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
		if (e.getItemDrop().getItemStack().getType() == Material.TNT) {
			e.setCancelled(false);
			return;
		}
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
		if (e.getEntity().getItemStack().getType() != Material.TNT) {
			e.getEntity().remove();
			e.setCancelled(true);
		} else {
			new BukkitRunnable() {
				@Override
				public void run() {
					e.getEntity().remove();
				}
			}.runTaskLater(KitPvP.getInstance(), 100L);
		}
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

			if (pvpp.getWarp() == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
				if (!Eventos1v1.Combate1.contains(p)) {
					e.setCancelled(true);
				} else {

					if (!Eventos1v1.batalhando.get(p).equalsIgnoreCase(d.getName()))
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
				}.runTaskLater(KitPvP.getInstance(), 120L);
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

				}.runTaskLater(KitPvP.getInstance(), 120L);
			}

			if (d.getItemInHand().getType() == Material.STONE_SWORD
					|| d.getItemInHand().getType() == Material.IRON_SWORD
					|| d.getItemInHand().getType() == Material.WOOD_SWORD
					|| d.getItemInHand().getType() == Material.GOLD_SWORD
					|| d.getItemInHand().getType() == Material.DIAMOND_SWORD) {
				p.getItemInHand().setDurability((short) 0);
				p.updateInventory();
				if (d.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
					e.setDamage(e.getDamage() - 3.5);
				} else {
					if (d.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
						e.setDamage(e.getDamage() - 5);
						return;
					}
					e.setDamage(e.getDamage() - 3);
				}
			}
		}

	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
		e.setCancelled(true);
	}

	public static void msgks(Player p, int ks) {
		if (ks == 5 || ks == 10 || ks == 20 || ks == 30 || ks == 40 || ks == 50 || ks == 60 || ks == 70 || ks == 80
				|| ks == 90 || ks == 100) {
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(
					"§3§lKILLSTREAK §fO jogador §b§l" + p.getName() + "§f atingiu um killstreak de §b§l" + ks + "§f!");
			Bukkit.broadcastMessage("");
		}
	}
}
