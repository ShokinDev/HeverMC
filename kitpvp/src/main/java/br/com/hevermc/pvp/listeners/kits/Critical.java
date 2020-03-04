package br.com.hevermc.pvp.listeners.kits;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Critical implements Listener {

	HeverKit kit_api = new HeverKit(Kits.CRITICAL);
	
	@EventHandler
	public void kitBoxer(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			kit_api.setPlayer(d);
			if (kit_api.usingKit() && !new br.com.hevermc.pvp.api.PlayerLoader((Player)e.getEntity()).load().getPvPP().isProtectArea()) {
				if (new Random().nextInt(100) > 80) {
					e.setDamage(e.getDamage() + 1.5);
					d.getWorld().playEffect(e.getEntity().getLocation(), Effect.STEP_SOUND, Material.LAVA, 100);
					d.sendMessage("§aVocê deu um dano §ecrítico§a!");
				}
			} 
		}
	}
}
