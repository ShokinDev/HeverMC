package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Spiderman implements Listener {

	HeverKit kit = new HeverKit(Kits.SPIDERMAN);
	public static HashMap<String, Snowball> teias = new HashMap<>();

	@EventHandler
	public void lancar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		kit.setPlayer(p);
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (kit.usingKit()) && (p.getItemInHand().getType() == Material.STRING)) {
			e.setCancelled(true);
			p.updateInventory();
			if (kit.verifyCooldown() == false) {
				Snowball teia = (Snowball) p.launchProjectile(Snowball.class);
				teias.put(p.getName(), teia);
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, 10);
				kit.setCooldown(c.getTime());
				p.sendMessage("§eVocê usou sua §ateia§e!");
				p.playSound(p.getLocation(), Sound.IRONGOLEM_THROW, 1.0F, 1.0F);
				return;
			} else {
				p.sendMessage("§cAguarde, você está em cooldown!");
			}
		}
	}

	@EventHandler
	public void teia(ProjectileHitEvent e) {
		if (((e.getEntity() instanceof Snowball)) && ((e.getEntity().getShooter() instanceof Player))) {
			Snowball teia = (Snowball) e.getEntity();
			Player p = (Player) e.getEntity().getShooter();
			if (new PlayerLoader(p).load().getPvPP().isProtectArea()) {
				if ((teias.containsKey(p.getName())) && (teia == teias.get(p.getName()))) {
					teias.remove(p.getName());
					final Block b = teia.getLocation().getBlock();
					b.setType(Material.WEB);
					new BukkitRunnable() {
						public void run() {
							if (b.getType() == Material.WEB)
								b.setType(Material.AIR);
						}
					}.runTaskLater(KitPvP.getInstance(), 200L);
					return;
				}
			}
		}
	}
}