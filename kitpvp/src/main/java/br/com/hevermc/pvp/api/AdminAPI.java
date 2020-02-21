package br.com.hevermc.pvp.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.pvp.KitPvP;

public class AdminAPI {

	public AdminAPI(Player p) {
		new BukkitRunnable() {
			PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
			
			HeverPlayer ph = PlayerLoader.getHP(p);

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Bukkit.getOnlinePlayers().forEach(all -> {
					if (pvp.isAdminMode()) {
						HeverPlayer hp = PlayerLoader.getHP(all);
						if (!hp.groupIsLarger(ph.getGroup()))
							all.hidePlayer(p);
					} else {
						all.showPlayer(p);
						cancel();
					}
				});
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 5);
	}

}
