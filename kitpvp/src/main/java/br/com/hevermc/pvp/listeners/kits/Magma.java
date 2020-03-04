package br.com.hevermc.pvp.listeners.kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Magma implements Listener {

	HeverKit kit_api = new HeverKit(Kits.MAGMA);

	@EventHandler
	public void kitSnail(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			kit_api.setPlayer(d);
			if (kit_api.usingKit()) {
				if (new Random().nextInt(100) > 60) {
					PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
					if (pvpp.isProtectArea()) {
						return;
					}
					p.setFireTicks(50);
				}
			}
		}
	}

	@EventHandler
	public void kitMagma(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		kit_api.setPlayer(p);
		if (kit_api.usingKit() && (p.getLocation().getBlock().getType() == Material.WATER
				|| p.getLocation().getBlock().getType() == Material.STATIONARY_WATER)) {
			p.damage(2.0D);
		}
		HeverKit kit = new HeverKit(Kits.POSEIDON);
		kit.setPlayer(p);
		if (kit.usingKit()) {
			if (kit_api.usingKit() && p.getLocation().getBlock().getType() == Material.WATER
					|| p.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
			}
		}
	}

	@EventHandler
	public void kitMagma(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			kit_api.setPlayer(p);
			if (kit_api.usingKit()) {
				if (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
					e.setCancelled(true);
				}
				return;
			}

			HeverKit kit2 = new HeverKit(Kits.FIREMAN);
			kit2.setPlayer(p);
			if (kit2.usingKit()) {
				if (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK) {
					e.setCancelled(true);
				}

			}
		}
	}

}
