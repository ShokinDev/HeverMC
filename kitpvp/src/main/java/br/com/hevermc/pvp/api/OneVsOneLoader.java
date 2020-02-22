package br.com.hevermc.pvp.api;

import org.bukkit.entity.Player;

import br.com.hevermc.pvp.KitPvP;

public class OneVsOneLoader {

	Player p;
	Player p2;
	OneVsOne ovs;

	public OneVsOneLoader(Player p, Player p2) {
		this.p = p;
		this.p2 = p2;
		if (!KitPvP.getManager().pvpplayer.containsKey(p.getName().toLowerCase())) {
			ovs = new OneVsOne(p, p2);
		} else {
			ovs = KitPvP.getManager().ovs.get(p.getName().toLowerCase());
		}
	}

	public void start() {
		if (!(KitPvP.getManager().ovs.containsKey(p.getName().toLowerCase()))
				|| KitPvP.getManager().ovs.containsKey(p2.getName().toLowerCase())) {
			ovs = new OneVsOne(p, p2);
			KitPvP.getManager().ovs.put(p.getName().toLowerCase(), ovs);
			KitPvP.getManager().ovs.put(p2.getName().toLowerCase(), ovs);
		}
	}

	public void finish() {
		if (!(KitPvP.getManager().ovs.containsKey(p.getName().toLowerCase()))
				|| KitPvP.getManager().ovs.containsKey(p2.getName().toLowerCase())) {
			ovs.finish();
			KitPvP.getManager().ovs.remove(p.getName().toLowerCase());
			KitPvP.getManager().ovs.remove(p2.getName().toLowerCase());
		}
	}

}
