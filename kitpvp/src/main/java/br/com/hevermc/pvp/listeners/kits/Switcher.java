package br.com.hevermc.pvp.listeners.kits;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.listeners.kits.commons.HeverKit;

public class Switcher implements Listener {

	HeverKit kit_api = new HeverKit(Kits.SWITCHER);

	@EventHandler
	public void kitSwitcher(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Snowball s = (Snowball) e.getDamager();
			if ((s.getShooter() instanceof Player)) {
				Player shooter = (Player) s.getShooter();
				
				kit_api.setPlayer(shooter);
				if (kit_api.usingKit() && !new PlayerLoader(p).getPvPP().isProtectArea() && !new PlayerLoader(shooter).getPvPP().isProtectArea()) {
					Location to = p.getLocation();
					p.teleport(shooter);
					shooter.teleport(to);
					Calendar c = Calendar.getInstance();
					c.add(Calendar.SECOND, 8);
					kit_api.setCooldown(new Date(c.getTimeInMillis()));
					p.sendMessage("§aVocê trocou de lugar com §e" + shooter.getName() + "§a!");
					shooter.sendMessage("§aVocê trocou de lugar com §e" + p.getName() + "§a!");
				}
			}
		}
	}

	@EventHandler
	public void kitSwitcher(PlayerInteractEvent e) {
		kit_api.setPlayer(e.getPlayer());
		Player p = e.getPlayer();
		kit_api.setPlayer(p);
		if (e.getItem() == null) {
			return;
		}
		if (e.getMaterial() == Material.SNOW_BALL && kit_api.usingKit()) {
			if (kit_api.verifyCooldown() == true) {
				e.setCancelled(true);
				p.updateInventory();
				p.sendMessage("§cAguarde, você está em cooldown!");
			} else {
				p.getInventory().addItem(new ItemConstructor(new ItemStack(Material.SNOW_BALL), "§eSwitcher §fitem").create());
				p.updateInventory();
			}
		}
	}

}
