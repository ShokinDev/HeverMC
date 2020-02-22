package br.com.hevermc.pvp.listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.OneVsOneLoader;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;

public class OneVsOneListener implements Listener {

	ArrayList<Player> cooldown = new ArrayList<Player>();
	HashMap<Player, Player> duel = new HashMap<Player, Player>();

	@EventHandler
	public void onInteractPlayer(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player a = (Player) e.getRightClicked();
			PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();

			if (p.getItemInHand().getType() == Material.BLAZE_ROD
					&& pvp.getWarp() == br.com.hevermc.pvp.enums.Warps.ONEVSONE) {
				e.setCancelled(false);
				if (!cooldown.contains(p)) {
					if (duel.containsKey(a)) {
						if (duel.get(a) == p) {
							new OneVsOneLoader(p, a).start();
							duel.remove(a);
						} else {
							duel.put(p, a);
							a.sendMessage("§eVocê foi desafiado por §e" + p.getName() + "!");
							p.sendMessage("§eVocê desafiou §e" + a.getName() + "!");
							cooldown.add(p);
							new BukkitRunnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (cooldown.contains(p)) {
										cooldown.remove(p);
									}
								}
							}.runTaskLater(KitPvP.getInstance(), 5 * 30l);
						}
					} else {
						duel.put(p, a);
						a.sendMessage("§eVocê foi desafiado por §e" + p.getName() + "!");
						p.sendMessage("§eVocê desafiou §e" + a.getName() + "!");
						cooldown.add(p);
						new BukkitRunnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (cooldown.contains(p)) {
									cooldown.remove(p);
								}
							}
						}.runTaskLater(KitPvP.getInstance(), 5 * 30l);
					}

					cooldown.add(p);
					new BukkitRunnable() {
						@Override
						public void run() {
							cooldown.remove(p);
						}
					}.runTaskLater(KitPvP.getInstance(), 15);
				} else {
					p.sendMessage("§cAguarde para desafiar novamente!");
				}

			}

		}
	}

}
