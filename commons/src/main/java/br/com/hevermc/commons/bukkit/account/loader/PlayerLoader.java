package br.com.hevermc.commons.bukkit.account.loader;

import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;

public class PlayerLoader {

	String name;
	String ip;

	public PlayerLoader(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}

	public PlayerLoader(Player p) {
		this.name = p.getName();
		this.ip = p.getAddress().getHostName();
	}

	public PlayerLoader load() {
		if (!(Commons.getManager().heverplayer.containsKey(name.toLowerCase()))) {
			Commons.getManager().heverplayer.put(name.toLowerCase(), new HeverPlayer(name, ip));
			Commons.getManager().heverplayer.get(name.toLowerCase()).load();
		}
		return this;
	}

	public HeverPlayer getHP() {
		return Commons.getManager().heverplayer.get(name.toLowerCase());
	}

	public void unload() {
		getHP().update();
		getHP().unload();
		Commons.getManager().heverplayer.remove(name.toLowerCase());
	}

}
