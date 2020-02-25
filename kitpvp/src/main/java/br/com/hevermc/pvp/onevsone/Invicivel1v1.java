package br.com.hevermc.pvp.onevsone;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Invicivel1v1 {

	private static final ArrayList<Player> vanished = new ArrayList<>();

	public static final boolean isVanished(final Player bp) {
		return vanished.contains(bp);
	}

	public static final void setVanished(final Player bp) {
		if (isVanished(bp)) {
			unsetVanished(bp);
		}
		vanished.add(bp);
	}

	public static final void unsetVanished(final Player bp) {
		if (!isVanished(bp)) {
			return;
		}
		vanished.remove(bp);
	}

	public static final void playerHideShowMethod(final Player bp) {
		for (final Player all : Bukkit.getOnlinePlayers()) {
			if (isVanished(bp) && !isVanished(all)) {
				all.hidePlayer(bp);
				bp.showPlayer(all);
				if (Eventos1v1.fighting.contains(all)) {
					all.hidePlayer(bp);
				}
			} else if (isVanished(bp) && isVanished(all)) {
				all.showPlayer(bp);
				bp.showPlayer(all);
				if (Eventos1v1.fighting.contains(all)) {
					all.hidePlayer(bp);
				}
			} else if (!isVanished(bp) && isVanished(all)) {
				all.showPlayer(bp);
				bp.hidePlayer(all);
				if (Eventos1v1.fighting.contains(all)) {
					all.hidePlayer(bp);
				}
			} else if (!isVanished(bp) && !isVanished(all)) {
				all.showPlayer(bp);
				bp.showPlayer(all);
				if (Eventos1v1.fighting.contains(all)) {
					all.hidePlayer(bp);
				}
			}
		}
	}

	public static final void runVanishSystem(final Player bp) {
		for (int i = 5; i > 0; i--) {
			playerHideShowMethod(bp);
		}
	}
}