package br.com.hevermc.pvp.listeners.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Fisherman implements Listener {

	HeverKit kit_api = new HeverKit(Kits.FISHERMAN);

	@EventHandler
	public void onHook(PlayerFishEvent e) {
		Player p = e.getPlayer();
		kit_api.setPlayer(p);
		if (e.getCaught() instanceof Player) {
			if (kit_api.usingKit()) {
				Player target = (Player) e.getCaught();
				if (!new PlayerLoader(p).load().getPvPP().isProtectArea()) {
					target.teleport(p);
				}
			}
		}
	}

}
