package br.com.hevermc.pvp.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;

public class AdminAPI {

	public static void hideInAdminMode(Player p) {
		PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		HeverPlayer ph = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(p.getName());
		if (pvp.isAdminMode() == false) {
			return;
		}
		Bukkit.getOnlinePlayers().forEach(all -> {
			if (pvp.isAdminMode()) {
				HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(all.getName());
				if (!hp.groupIsLarger(ph.getGroup()))
					all.hidePlayer(p);
			}
		});
	}

}
