package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Thor implements Listener{
	
	HeverKit kit = new HeverKit(Kits.THOR);

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteracts(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit.setPlayer(p);
		if (!kit.usingKit()) {
			return;
		}
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.RIGHT_CLICK_AIR)) {
			if (!kit.isItem()) {
				return;
			}
			if (kit.verifyCooldown() == true) {
				p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 13);
			kit.setCooldown(c.getTime());
			HashSet<Byte> a = null;
			Location thored = p.getTargetBlock(a, 15).getLocation();
			p.getWorld().strikeLightning(thored);
		}
	}

	@EventHandler
	public void onThor(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player)e.getEntity();
			if (new PlayerLoader(p).load().getPvPP().isProtectArea())
				return;
			kit.setPlayer(p);
			if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
				if (kit.usingKit()) {
					e.setCancelled(true);
					return;
				}
				p.setFireTicks(150);
				p.damage(1.0D);
			}
		}
	}
	
	@EventHandler
	public void onBlockFire(BlockIgniteEvent e) {
		e.setCancelled(true);
	}
}