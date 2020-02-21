package br.com.hevermc.commons.bukkit.account.loader;

import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;

public class PlayerLoader {
	public static HeverPlayer getHP(Player p) {
		if (!Commons.getManager().heverplayer.containsKey(p.getName().toLowerCase())) {
			Commons.getManager().heverplayer.put(p.getName().toLowerCase(),
					new HeverPlayer(p.getName().toLowerCase(), p.getAddress().getHostName()));
			Commons.getManager().heverplayer.get(p.getName().toLowerCase()).load();
		}
		return Commons.getManager().heverplayer.get(p.getName().toLowerCase());
	}

	public static HeverPlayer getHP(String name) {
		if (!Commons.getManager().heverplayer.containsKey(name.toLowerCase())) {
			Commons.getManager().heverplayer.put(name.toLowerCase(), new HeverPlayer(name.toLowerCase(), "0.0.0.0"));
			Commons.getManager().heverplayer.get(name.toLowerCase()).load();
		}
		return Commons.getManager().heverplayer.get(name.toLowerCase());
	}
	
	public static void forceLoadAccount(HeverPlayer hp) {
		hp.load();
	}

	public static void unload(String name) {
		if (Commons.getManager().heverplayer.containsKey(name.toLowerCase())) {
			Commons.getManager().heverplayer.get(name.toLowerCase()).update();
			Commons.getManager().heverplayer.get(name.toLowerCase()).unload();
			Commons.getManager().heverplayer.remove(name.toLowerCase());
		}
	}

}
