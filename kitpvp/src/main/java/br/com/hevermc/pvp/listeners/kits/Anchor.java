package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Anchor implements Listener {

	HeverKit kit_api = new HeverKit(Kits.ANCHOR);
	ArrayList<Player> anchordamaged = new ArrayList<Player>();

	@EventHandler
	public void onPlayerVelocity(PlayerVelocityEvent e) {
		Player p = e.getPlayer();
		if (anchordamaged.contains(p))
			e.setCancelled(false);
	}

	@EventHandler
	public void onPlayerDamaged(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			kit_api.setPlayer((Player) e.getDamager());
			if (kit_api.usingKit()) {
				anchordamaged.add((Player) e.getEntity());
			}

			kit_api.setPlayer((Player) e.getEntity());
			if (kit_api.usingKit()) {
				anchordamaged.add((Player) e.getEntity());
			}
		}
	}

}
