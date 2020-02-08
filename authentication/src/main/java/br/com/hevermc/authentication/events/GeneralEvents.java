package br.com.hevermc.authentication.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.authentication.Authentication;
import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.authentication.api.loader.PlayerLoader;
import br.com.hevermc.authentication.gui.Captcha;
import br.com.hevermc.authentication.score.ScoreboardManager;

public class GeneralEvents implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		final Player p = e.getPlayer();
		final LoginPlayer lp = new PlayerLoader(p).load().lp();
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		new BukkitRunnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				new Captcha(p);
			}
		}.runTaskLater(Authentication.getInstace(), 5l);
		new BukkitRunnable() {

			public void run() {
				if (!lp.isCaptcha()) {
					p.kickPlayer("§cVocê demorou de mais para efetuar o captcha!");
				}
			}
		}.runTaskLater(Authentication.getInstace(), 150L);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		new PlayerLoader(p).unload();
	}

	@EventHandler
	public void onCloseInventory(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		LoginPlayer lp = new PlayerLoader(p).load().lp();
		lp.getName();
		if (e.getInventory().getTitle().equals("§eClique no §bBloco de Diamante") && !lp.isCaptcha()) {
			new BukkitRunnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Captcha(p);
				}
			}.runTaskLater(Authentication.getInstace(), 3l);
		}
	}

	@EventHandler
	public void onInteract(InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		final LoginPlayer lp = new PlayerLoader(p).load().lp();
		String title = e.getInventory().getTitle();
		ItemStack clicked = e.getCurrentItem();
		if (title.startsWith("§eClique no §bBloco de Diamante")) {
			if (clicked.getType() == Material.DIAMOND_BLOCK) {
				lp.setCaptcha(true);
				p.closeInventory();
				p.sendMessage("§aVocê acertou o captcha!");

				new BukkitRunnable() {

					public void run() {
						if (!lp.isLogged()) {
							if (lp.isRegistred()) {
								p.sendMessage("§eVocê deve se autenticar, use §b/login <senha>");
							} else {
								p.sendMessage(
										"§eVocê deve se registrar, use §b/register <senha> <confirme sua senha> <pin de segurança>");
							}
						} else {
							cancel();
						}
					}
				}.runTaskTimer(Authentication.getInstace(), 0, 30L);

			} else {
				p.kickPlayer("§cVocê errou o captcha!");
			}
		}
	}

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.getWorld().setWeatherDuration(0);
		e.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		LoginPlayer lp = new PlayerLoader(p).load().lp();
		if (!lp.isLogged()) {
			p.teleport(p.getWorld().getSpawnLocation());
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onSpawnEntity(EntitySpawnEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	
	public void onCancelCommands() {
		
	}

}