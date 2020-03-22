package br.com.hevermc.pvp.listeners.kits;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Anchor implements Listener {

	HeverKit kit_api = new HeverKit(Kits.ANCHOR);

	@EventHandler
	public void Kits(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player))) {
			final Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
			kit_api.setPlayer(p);
			if (kit_api.usingKit()) {
				p.setVelocity(new Vector());
				k.setVelocity(new Vector());
				k.playSound(k.getLocation(), Sound.ANVIL_BREAK, 0.5F, 0.5F);
				new BukkitRunnable() {
			
					public void run() {
						p.setVelocity(new Vector());
					}
				}.runTaskLater(KitPvP.getInstance(), 1l);
				return;
			}
			kit_api.setPlayer(k);
			if (kit_api.usingKit()) {
				k.setVelocity(new Vector());
				k.playSound(k.getLocation(), Sound.ANVIL_BREAK, 0.5F, 0.5F);
				new BukkitRunnable() {
					public void run() {
						p.setVelocity(new Vector());
						k.setVelocity(new Vector());
					}
				}.runTaskLater(KitPvP.getInstance(), 1l);
				return;
			}
		}
	}

}
