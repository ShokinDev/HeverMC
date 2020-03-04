package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Gladiator implements Listener {

	private List<Block> gladiatorbloco = new ArrayList<Block>();
	private HashMap<Block, Player> gladblock= new HashMap<Block, Player>();
	public static HashMap<Player, Player> lutando= new HashMap<Player, Player>();
	private HashMap<Player, Location> lugar = new HashMap<Player, Location>();
	private int glad1;
	private int glad2;
	HeverKit kit_api = new HeverKit(Kits.GLADIATOR);
	Plugin plugin = KitPvP.getInstance();

	@EventHandler
	void KitGladiator(final PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			PvPPlayer rpvp = new PlayerLoader(r).load().getPvPP();
			PvPPlayer ppvp = new PlayerLoader(p).load().getPvPP();
			kit_api.setPlayer(p);
			if (kit_api.isItem() && kit_api.usingKit() && !rpvp.isProtectArea() && !ppvp.isProtectArea()) {
				final Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 70.0,
						p.getLocation().getZ());
				final Location loc2 = new Location(p.getWorld(), (double) (p.getLocation().getBlockX() + 8),
						(double) (p.getLocation().getBlockY() + 73), (double) (p.getLocation().getBlockZ() + 8));
				final Location loc3 = new Location(p.getWorld(), (double) (p.getLocation().getBlockX() - 8),
						(double) (p.getLocation().getBlockY() + 73), (double) (p.getLocation().getBlockZ() - 8));
				if (Gladiator.lutando.containsKey(p) || Gladiator.lutando.containsKey(r)) {
					p.sendMessage("§cVocê já esta no gladiator!");
					return;
				}
				final List<Location> cuboid = new ArrayList<Location>();
				for (int bX = -10; bX <= 10; ++bX) {
					for (int bZ = -10; bZ <= 10; ++bZ) {
						for (int bY = -1; bY <= 10; ++bY) {
							final Block b = loc.clone().add((double) bX, (double) bY, (double) bZ).getBlock();
							if (!b.isEmpty()) {
								p.sendMessage("§cVocê não pode utilizar este kit aqui!");
								return;
							}
							if (bY == 10) {
								cuboid.add(loc.clone().add((double) bX, (double) bY, (double) bZ));
							} else if (bY == -1) {
								cuboid.add(loc.clone().add((double) bX, (double) bY, (double) bZ));
							} else if (bX == -10 || bZ == -10 || bX == 10 || bZ == 10) {
								cuboid.add(loc.clone().add((double) bX, (double) bY, (double) bZ));
							}
						}
					}
				}
				for (final Location loc4 : cuboid) {
					loc4.getBlock().setType(Material.GLASS);
					this.gladiatorbloco.add(loc4.getBlock());
					this.gladblock.put(loc4.getBlock(), p);
					this.gladblock.put(loc4.getBlock(), r);
				}
				this.lugar.put(p, p.getLocation());
				this.lugar.put(r, r.getLocation());
				p.teleport(loc2);
				r.teleport(loc3);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
				r.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
				Gladiator.lutando.put(p, r);
				Gladiator.lutando.put(r, p);
				this.glad1 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable) new Runnable() {
					@Override
					public void run() {
					}
				}, 4800L);
				this.glad2 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, (Runnable) new Runnable() {
					@SuppressWarnings("unlikely-arg-type")
					@Override
					public void run() {
						if (Gladiator.lutando.containsKey(p) && Gladiator.lutando.containsKey(r)) {
							Gladiator.lutando.remove(p);
							Gladiator.lutando.remove(r);
							p.teleport((Location) Gladiator.this.lugar.get(p));
							r.teleport((Location) Gladiator.this.lugar.get(r));
							for (final Block glad : Gladiator.this.gladiatorbloco) {
								if ((Gladiator.this.gladblock.get(glad) == r || Gladiator.this.gladblock.get(glad) == p)
										&& glad.getType() == Material.GLASS) {
									glad.setType(Material.AIR);
								}
							}
							Gladiator.this.lugar.remove(p);
							Gladiator.this.lugar.remove(r);
							Gladiator.this.gladblock.remove(p);
							Gladiator.this.gladblock.remove(r);
						}
					}
				}, 6000L);
			}
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	void KitGladiatorInteragir(final BlockBreakEvent e) {
		final Player p = e.getPlayer();
		if (this.gladiatorbloco.contains(p) && p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	@EventHandler
	void KitGladiatorSair(final PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		if (Gladiator.lutando.containsKey(p)) {
			final String nome = Gladiator.lutando.get(p).getName();
			final Player q = Bukkit.getPlayer(nome);
			Gladiator.lutando.remove(p);
			Gladiator.lutando.remove(q);
			q.sendMessage("§cO jogador: " + p.getDisplayName() + " deslogou no gladiator!");
			q.teleport((Location) this.lugar.get(q));
			Bukkit.getScheduler().cancelTask(this.glad1);
			Bukkit.getScheduler().cancelTask(this.glad2);
			for (final Block glad : this.gladiatorbloco) {
				if ((this.gladblock.get(glad) == q || this.gladblock.get(glad) == p)
						&& glad.getType() == Material.GLASS) {
					glad.setType(Material.AIR);
				}
			}
			this.gladblock.remove(p);
			this.gladblock.remove(q);
		}
	}

	@EventHandler
	void KitGladiatorMorrer(final PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if (Gladiator.lutando.containsKey(p)) {
			final String nome = Gladiator.lutando.get(p).getName();
			final Player m = Bukkit.getPlayer(nome);
			m.sendMessage("§aVocê ganhou a batalha no gladiator!");
			p.sendMessage("§cVocê perdeu a batalha no gladiator!");
			Gladiator.lutando.remove(p);
			Gladiator.lutando.remove(m);
			m.teleport((Location) this.lugar.get(m));
			m.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
			Bukkit.getScheduler().cancelTask(this.glad1);
			Bukkit.getScheduler().cancelTask(this.glad2);
			for (final Block glad : this.gladiatorbloco) {
				if ((this.gladblock.get(glad) == m || this.gladblock.get(glad) == p)
						&& glad.getType() == Material.GLASS) {
					glad.setType(Material.AIR);
				}
			}

		}
	}

	@EventHandler
	void KitGladiatorComando(final PlayerCommandPreprocessEvent e) {
		final Player p = e.getPlayer();
		if (Gladiator.lutando.containsKey(p)) {
			e.setCancelled(true);
			p.sendMessage("§cSem comandos no gladiator!");
		}
	}
}
