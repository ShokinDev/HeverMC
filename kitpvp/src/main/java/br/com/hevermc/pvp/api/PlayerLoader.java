package br.com.hevermc.pvp.api;

import org.bukkit.entity.Player;

import br.com.hevermc.pvp.KitPvP;

public class PlayerLoader {

	String name;

	public PlayerLoader(Player p) {
		this.name = p.getName().toLowerCase();
	}

	public PlayerLoader(String name) {
		this.name = name.toLowerCase();
	}

	public PlayerLoader load() {
		if (KitPvP.getManager().pvpplayer.containsKey(name.toLowerCase())) {
			KitPvP.getManager().pvpplayer.put(name, new PvPPlayer(name.toLowerCase()));
			getPvPP().load();
		}
		return this;
	}

	public PvPPlayer getPvPP() {
		return KitPvP.getManager().pvpplayer.get(name);
	}

}
