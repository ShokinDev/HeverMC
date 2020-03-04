package br.com.hevermc.pvp.listeners.kits;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Viper implements Listener {

	HeverKit kit_api = new HeverKit(Kits.VIPER);

	@EventHandler
	public void kitSnail(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			kit_api.setPlayer(d);
			if (kit_api.usingKit()) {
				if (new Random().nextInt(100) > 60) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
				}
			}
		}

	}

}
