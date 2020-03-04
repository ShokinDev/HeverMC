package br.com.hevermc.pvp.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import br.com.hevermc.pvp.KitPvP;

public class WarpsAPI {

	br.com.hevermc.pvp.enums.Warps warp;

	public WarpsAPI(br.com.hevermc.pvp.enums.Warps warp) {
		this.warp = warp;
	}

	public int getINWarp() {
		int i = 0;
		for (Player ps : Bukkit.getOnlinePlayers()) {
			PvPPlayer pvp = new PlayerLoader(ps).load().getPvPP();
			if (pvp.getWarp() == warp)
				i++;
		}
		return i;
	}

	public Location getLocation() {
		double x = KitPvP.warps.getConfig().getDouble(warp.getName() + ".x");
		double y = KitPvP.warps.getConfig().getDouble(warp.getName() + ".y");
		double z = KitPvP.warps.getConfig().getDouble(warp.getName() + ".z");
		return new Location(Bukkit.getWorld("world"), x, y, z);
	}

	public void setLocation(Location l) {
		KitPvP.warps.getConfig().set(warp.getName() + ".x", l.getX());
		KitPvP.warps.getConfig().set(warp.getName() + ".y", l.getY());
		KitPvP.warps.getConfig().set(warp.getName() + ".z", l.getZ());
		KitPvP.warps.getConfig().set(warp.getName() + ".yaw", l.getYaw());
		KitPvP.warps.getConfig().set(warp.getName() + ".pitch", l.getPitch());
		KitPvP.warps.save();
	}

	public br.com.hevermc.pvp.enums.Warps getWarp() {
		return warp;
	}

}
