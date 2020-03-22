package br.com.hevermc.pvp.listeners.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Kangaroo implements Listener {

	HeverKit kit = new HeverKit(Kits.KANGAROO);

	ArrayList<Player> kangaroo = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
		kit.setPlayer(p);

		if (kit.usingKit()) {
			if (kit.isItem()) {
				event.setCancelled(true);
				if (pvp.isCombat()) {
					p.sendMessage("§e§lKIT §fAguarde, você está em §4§lCOOLDOWN§f!");
					return;
				}
				if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
						|| event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
					event.setCancelled(true);
				if (!kangaroo.contains(p) && kit.usingKit()) {
					if (!p.isSneaking()) {
						p.setFallDistance(-5.0F);
						Vector vector = p.getEyeLocation().getDirection();
						vector.multiply(0.6F);
						vector.setY(1.2F);
						p.setVelocity(vector);
					} else {
						p.setFallDistance(-5.0F);
						Vector vector = p.getEyeLocation().getDirection();
						vector.multiply(1.8F);
						vector.setY(0.5D);
						p.setVelocity(vector);
					}
					this.kangaroo.add(p);
				}
			}

		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (this.kangaroo.contains(p)) {
			Block b = p.getLocation().getBlock();
			if (b.getType() != Material.AIR || b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				this.kangaroo.remove(p);
				return;
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player player = (Player) e;
			if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL
					&& player.getInventory().contains(Material.FIREWORK) && event.getDamage() >= 7.0D)
				event.setDamage(7.0D);
		}
	}
}
