package br.com.hevermc.hardcoregames.listeners.kits;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.enums.Kits;
import br.com.hevermc.hardcoregames.enums.States;
import br.com.hevermc.hardcoregames.listeners.kits.common.HeverKit;

public class Ninja {
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
					if (HardcoreGames.getManager().getStateGame() == States.ANDAMENTO) {
						
						if (t == null) {
							p.sendMessage("§cSeu alvo está offline!");
						} else if (p.getLocation().distance(t.getLocation()) >= 50.0D) {
							p.sendMessage("§cSeu alvo está muito longe!");
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
}
