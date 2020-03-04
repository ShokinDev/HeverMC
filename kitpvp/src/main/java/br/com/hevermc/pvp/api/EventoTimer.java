package br.com.hevermc.pvp.api;

import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.pvp.KitPvP;

public class EventoTimer {

	static int timer = 0;

	public static int getTime() {
		return timer;
	}

	public static String formattedTime() {
		int timer = getTime();
		int min = timer / 60;
		int segs = timer % 60;
		return ((min >= 10) ? Integer.valueOf(min) : ("0" + min)) + ":"
				+ ((segs >= 10) ? Integer.valueOf(segs) : ("0" + segs));
	}

	public EventoTimer() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (KitPvP.getManager().eventOcurring == false) {
					cancel();
					return;
				}
				timer++;
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 30L);
	}

}
