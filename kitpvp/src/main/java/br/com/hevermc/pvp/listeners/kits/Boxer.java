package br.com.hevermc.pvp.listeners.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Boxer implements Listener {

	HeverKit kit_api = new HeverKit(Kits.BOXER);
	
	@EventHandler
	public void kitBoxer(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			kit_api.setPlayer(d);
			if (kit_api.usingKit()) {
				e.setDamage(e.getDamage() + 1);
			} else {
				kit_api.setPlayer(p);
				if (kit_api.usingKit()) {
					e.setDamage(e.getDamage() - 1);
				}
			}
		}
		
	}
	
}
