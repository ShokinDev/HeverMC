package br.com.hevermc.hardcoregames.listeners.kits.common;

import java.util.Date;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.hardcoregames.api.HGPlayer;
import br.com.hevermc.hardcoregames.api.HGPlayerLoader;
import br.com.hevermc.hardcoregames.enums.Kits;

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
		HGPlayer hg = new HGPlayerLoader(p).load().getHGP();
		if (hg.getKit1() == kit || hg.getKit2() == kit)
			return true;
		return false;
	}

	public boolean isItem() {
		HGPlayer hg = new HGPlayerLoader(p).load().getHGP();
		if (hg.getKit1() == kit) {
			if (hg.getKit1().getMaterial() == p.getItemInHand().getType())
				return true;
		} else if (hg.getKit2() == kit) {
			if (hg.getKit2().getMaterial() == p.getItemInHand().getType())
				return true;
			
		}
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
