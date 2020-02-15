package br.com.hevermc.pvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.gui.Selector;
import br.com.hevermc.pvp.score.ScoreboardManager;

public class GeneralListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		// HeverPlayer hp = new PlayerLoader(p).load().getHP();
		new PlayerLoader(p).load().getPvPP();
		for (int i = 0; i < 100; i++)
			p.sendMessage("");
		p.sendMessage("§6§lHEVER§f§lMC");
		p.sendMessage("");
		p.sendMessage("§fSeja bem-vindo ao nosso §aKitPvP§f, esperamos que divirta-se!");
		p.sendMessage("");
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(0,
				new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits §7(Abra com o botão direito)")
						.create());
		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§eWarps §7(Abra com o botão direito)").create());
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();
		if (e.getFrom().getBlock().getType() == Material.GRASS && pvpp.isProtectArea()) {
			pvpp.setProtectArea(false);
			p.sendMessage("§cVocê perdeu sua proteção.");
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
			if (p.getItemInHand().getType() == Material.CHEST) {
				new Selector(p);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PvPPlayer pvpp = new PlayerLoader(p).load().getPvPP();

			if (pvpp.isProtectArea())
				e.setCancelled(true);
		}

	}

}
