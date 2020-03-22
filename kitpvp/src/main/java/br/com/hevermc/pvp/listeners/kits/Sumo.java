package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Sumo implements Listener {

	public long cooldownLenght = 0L;
	HeverKit kit_api = new HeverKit(Kits.SUMO);
	ArrayList<Player> usokitnefdp = new ArrayList<Player>();
	@EventHandler
	public void Jump(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		kit_api.setPlayer(p);
		if (kit_api.usingKit()) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (p.getItemInHand().getType() == Material.APPLE) {
					e.setCancelled(true);
					if (kit_api.verifyCooldown() == true) {
						p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
						return;
					}
					p.setVelocity(p.getVelocity().setY(2.0D));
					Calendar c = Calendar.getInstance();
					c.add(Calendar.SECOND, 30);
					kit_api.setCooldown(c.getTime());
					usokitnefdp.add(p);
				}
			}
		}
	}

	@EventHandler
	public void stomperEvent(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() != EntityDamageEvent.DamageCause.FALL) {
			e.getDamage();
			return;
		}
		kit_api.setPlayer(p);
		if (!kit_api.usingKit()) {
			return;
		}
		for (Entity stompada : p.getNearbyEntities(5.0D, 1.0D, 5.0D)) {
			if (!(stompada instanceof Player)) {
				return;
			}
			Player st = (Player) stompada;
			if (new PlayerLoader(st).load().getPvPP().isProtectArea()) {
				return;
			}
			if (!usokitnefdp.contains(p))
				return;
			st.setVelocity(st.getVelocity().setY(1.5D));
			usokitnefdp.remove(p);

		}
		if (e.getDamage() >= 6.0D) {
			World w = p.getWorld();
			Double x = Double.valueOf(p.getLocation().getX());
			Double y = Double.valueOf(p.getLocation().getY());
			Double z = Double.valueOf(p.getLocation().getZ());
			p.getWorld().playEffect(new Location(w, x + 1, y - 1, z), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x - 1, y - 1, z), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x + 2, y - 1, z), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x - 2, y - 1, z), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x, y - 1, z + 1), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x, y - 1, z - 1), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x, y - 1, z + 2), Effect.MOBSPAWNER_FLAMES, 100, 100);
			p.getWorld().playEffect(new Location(w, x, y - 1, z - 2), Effect.MOBSPAWNER_FLAMES, 100, 100);
			e.setDamage(4.0D);
		}
	}
}