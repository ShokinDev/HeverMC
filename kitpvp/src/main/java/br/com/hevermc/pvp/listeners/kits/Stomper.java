package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Stomper implements Listener {


	public static ArrayList<Player> stomperdano = new ArrayList<Player>();
	public static ArrayList<Player> nerfstomper = new ArrayList<Player>();

	HeverKit kit_api = new HeverKit(Kits.STOMPER);
	@EventHandler
	public void stomperEvent(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		kit_api.setPlayer(p);
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL && kit_api.usingKit()) {
		if (e.getDamage() >= 4.0D) {
			e.setDamage(4.0D);
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
		}
		for (Entity stompada : p.getNearbyEntities(1.5D, 2.0D, 1.5D)) {
			if (!(stompada instanceof Player)) {
				return;
			}
			Player t = (Player) stompada;
			PvPPlayer pvpt = new PlayerLoader(t).load().getPvPP();
			HeverKit kit_t = new HeverKit(Kits.ANTISTOMPER);
			kit_t.setPlayer(t);
			if (pvpt.isProtectArea())
				return;
			if (kit_t.usingKit()) {
				t.damage(0.0F);
				p.sendMessage("§cVocê pisoteou um AntiStomper!");
				t.sendMessage("§aSeu AntiStomper salvou sua vida!");
				return;
			}
			if (!t.isSneaking()) {
				stomperdano.add(p);
				t.damage(p.getFallDistance(), p);
				stomperdano.remove(p);
			} else {
				t.damage(4.0D);
			}
		}
		}
	}

}
