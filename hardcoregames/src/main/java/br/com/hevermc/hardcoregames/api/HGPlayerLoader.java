package br.com.hevermc.hardcoregames.api;

import org.bukkit.entity.Player;

import br.com.hevermc.hardcoregames.HardcoreGames;

public class HGPlayerLoader {

	String name;

	public HGPlayerLoader(Player p) {
		this.name = p.getName().toLowerCase();
	}

	public HGPlayerLoader(String name) {
		this.name = name.toLowerCase();
	}

	public HGPlayerLoader load() {
		if (!HardcoreGames.getManager().hgplayer.containsKey(name.toLowerCase())) {
			HardcoreGames.getManager().hgplayer.put(name.toLowerCase(), new HGPlayer(name.toLowerCase()));
		}
		return this;
		
	}	public HGPlayerLoader unload() {
		if (HardcoreGames.getManager().hgplayer.containsKey(name.toLowerCase())) {
			getHGP().unload();
			HardcoreGames.getManager().hgplayer.remove(name.toLowerCase());
		}
		return this;
	}

	public HGPlayer getHGP() {
		return HardcoreGames.getManager().hgplayer.get(name);
	}

}
