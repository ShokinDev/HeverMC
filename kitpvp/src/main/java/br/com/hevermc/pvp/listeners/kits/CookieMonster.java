package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class CookieMonster implements Listener {

	HeverKit kit = new HeverKit(Kits.COOKIEMONSTER);
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && kit.isItem()) {
			if (kit.verifyCooldown()) {
				p.updateInventory();
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				e.setCancelled(true);
				return;
			} else {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, 20);
				kit.setCooldown(c.getTime());
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 0));
				p.sendMessage("§aSeu cookie lhe deu força!");
			}
		}
	}

}
