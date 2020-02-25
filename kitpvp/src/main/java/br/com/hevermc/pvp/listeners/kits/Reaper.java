package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Reaper implements Listener {

	HeverKit kit_api = new HeverKit(Kits.REAPER);

	@EventHandler
	public void kitSnail(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player d = (Player) e.getRightClicked();
			Player p = e.getPlayer();
			kit_api.setPlayer(d);
			if (kit_api.usingKit() && kit_api.isItem() && kit_api.verifyCooldown() == false) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 8, 1));
				Calendar c= Calendar.getInstance();
				c.add(Calendar.SECOND, 7);
				kit_api.setCooldown(new Date(c.getTimeInMillis()));

			}
		}

	}

}
