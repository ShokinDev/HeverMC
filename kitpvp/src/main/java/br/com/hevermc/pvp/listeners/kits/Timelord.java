package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Timelord implements Listener {

	HeverKit kit = new HeverKit(Kits.TIMELORD);
	ArrayList<Player> istimelord = new ArrayList<>();

	@EventHandler
	public void Clicar(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (kit.usingKit() && kit.isItem()) {
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 20);
			kit.setCooldown(c.getTime());
			p.sendMessage("§e§lKIT §fVocê usou seu §6§lTIMELORD§f!");
			p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
			p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
			for (Entity ent : p.getNearbyEntities(5D, 3D, 5D)) {
				if (ent instanceof Player) {
					final Player t = (Player) ent;
					if (new PlayerLoader(t).load().getPvPP().isProtectArea()) {
						return;
					}
					istimelord.add(t);
					t.sendMessage("§fVocê foi congelado por um §6§lTIMELORD§f!");
					t.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
					t.setAllowFlight(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.getInstance(), new Runnable() {
						@Override
						public void run() {
							if (istimelord.contains(t)) {
								t.setAllowFlight(false);
								istimelord.remove(t);
								t.sendMessage("§FVocê foi §6§DESCONGELADO§F!");
							}

						}
					}, 100L);
				}
			}
		}
	}

	@EventHandler
	public void aoAndarTimeLord(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (istimelord.contains(p)) {
			e.setCancelled(true);
		}
	}
}
