package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Ninja implements Listener {

	HeverKit kit_api = new HeverKit(Kits.NINJA);
	HashMap<Player, Player> ninja = new HashMap<Player, Player>();

	@EventHandler
	public void kitNinja(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			kit_api.setPlayer(d);
			if (kit_api.usingKit()) {
				Player p = (Player) e.getEntity();
				ninja.put(d, p);
			}

		}
	}

	@EventHandler
	public void kitNinja(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		kit_api.setPlayer(p);
		if (kit_api.usingKit() && ninja.containsKey(p)) {
			if (kit_api.verifyCooldown() == true) {
				p.sendMessage("§cAguarde, você está em cooldown!");
			} else {
				if (p.isSneaking()) {
					Player t = ninja.get(p);
					PvPPlayer tpvp = new PlayerLoader(t).load().getPvPP();
					if (tpvp.isProtectArea()) {
						p.sendMessage("§cSeu alvo está em uma área protegida!");
					} else if (t == null) {
						p.sendMessage("§cSeu alvo está offline!");
					} else if (p.getLocation().distance(t.getLocation()) >= 50.0D) {
						p.sendMessage("§cSeu alvo está muito longe!");
					} else if (Gladiator.lutando.containsKey(t) && Gladiator.lutando.get(t) != p) {
						p.sendMessage("§cSeu alvo está batalhando em um gladiator com outra pessoa!");
					} else {
						p.teleport(t);
						Calendar c = Calendar.getInstance();
						c.add(Calendar.SECOND, 6);
						Date d = new Date(c.getTimeInMillis());
						kit_api.setCooldown(d);
						p.sendMessage("§aVocê se teleportou para §e" + t.getName() + "§a!");
						ninja.remove(p);
					}

				}
			}
		}
	}

}
