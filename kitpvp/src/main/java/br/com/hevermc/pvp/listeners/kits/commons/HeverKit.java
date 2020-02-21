package br.com.hevermc.pvp.listeners.kits.commons;

import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;

public class HeverKit {

	Kits kit;
	HashMap<Player, Date> cooldown = new HashMap<Player, Date>();
	Player p;

	public void setPlayer(Player p) {
		this.p = p;
	}

	public HeverKit(Kits kit) {
		this.kit = kit;
	}

	public boolean usingKit() {
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (pvp.getKit() == kit)
			return true;
		return false;
	}

	public boolean isItem() {
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		if (pvp.getKit().getMaterial() == p.getItemInHand().getType())
			return true;
		return false;
	}

	public boolean verifyCooldown() {
		if (cooldown.containsKey(p)) {
			if (new Date().after(cooldown.get(p))) {
				cooldown.remove(p);
				return false;
			}
			return true;
		}
		return false;
	}

	public void setCooldown(Date end) {
		if (!verifyCooldown())
			cooldown.put(p, end);
	}

}
