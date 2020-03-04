package br.com.hevermc.authentication.listeners;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.authentication.Authentication;
import br.com.hevermc.authentication.api.BarUtil;
import br.com.hevermc.authentication.api.BungeeChannelApi;
import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.authentication.api.ReflectionAPI;
import br.com.hevermc.authentication.api.loader.PlayerLoader;
import br.com.hevermc.authentication.gui.Captcha;
import br.com.hevermc.authentication.score.ScoreboardManager;

public class GeneralEvents implements Listener {

	private String getUrlContent(String url) {
		try {
			InputStream connection = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection));
			String line = null;
			String content = "";
			while ((line = reader.readLine()) != null) {
				content = content + line;
			}
			return content;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public boolean isPremium(String player) {
		return getUrlContent("https://api.mojang.com/users/profiles/minecraft/" + player).length() > 0;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		final Player p = e.getPlayer();
		ReflectionAPI.tab(p, "\n§6§lHEVER§f§lMC\n",
				"\n§fSite: §ehevermc.com.br\n§fTwitter: §e@HeverNetwork_\n§fDiscord: §ediscord.hevermc.com.br\n§fCaso tenha algum problema visite nosso §eDiscord§f!\n");

		final LoginPlayer lp = new PlayerLoader(p).load().lp();
		for (int i = 0; i < 100; i++)
			p.sendMessage(" ");
		if (!Authentication.getManager().getSQLManager().checkString("hever_accounts", "name", p.getName())) {
			if (isPremium(p.getName())) {
				Authentication.getManager().getSQLManager().insertOriginal(p.getName(), 1);

				System.out.print("[DEBUG] entrou primeira vez - original");
				Authentication.getManager().getBungeeChannel().sendPluginMessage(p, "loginBypass(" + p.getName() +")", "BungeeCord");
				p.kickPlayer("§6§lHEVER§f§lMC\n§7\n§aSua conta foi registrada.\n§7Entre novamente!");
				return;
			} else {
				System.out.print("[DEBUG] entrou primeira vez - pirata");
				Authentication.getManager().getSQLManager().insertOriginal(p.getName(), 0);
			}
		} else {
			if (Authentication.getManager().getSQLManager().getInt("hever_accounts", "name", "original",
					p.getName()) == 1) {
				lp.setCaptcha(true);
				lp.setLogged(true);
				System.out.print("[DEBUG] entrou como original");
				p.sendMessage("§aVocê foi autenticado como jogador original, portanto não foi necessário fazer login!");
				new BukkitRunnable() {

					@Override
					public void run() {
						if (!p.isOnline() || p == null) {
							cancel();
							System.out.println("[DEBUG] redirect to lobby canceled");
							return;
						}
						new BungeeChannelApi(Authentication.getInstance()).sendPluginMessage(p, "loginBypass(" + p.getName() +")", "BungeeCord");
						p.sendMessage("§aVocê está sendo conectado ao §blobby§a!");
						new BungeeChannelApi(Authentication.getInstance()).connect(p, "lobby");
					}
				}.runTaskTimer(Authentication.getInstance(), 0, 35L);
			} else {
				System.out.print("[DEBUG] entrou como pirtata");
			}
		}

		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		p.getInventory().clear();
		p.setGameMode(GameMode.SURVIVAL);
		p.setMaxHealth(4);
		p.setFoodLevel(20);
		BarUtil.setBar(p, (lp.isRegistred() ? "§a§l§k!!!§f§l USE: §e§l/LOGIN <SENHA> §A§L§K!!!"
				: "§a§l§k!!!§f§l USE: §e§l/REGISTER <SENHA> <SENHA> §A§L§K!!!"), 100);
		for (Entity entitys : p.getWorld().getEntities()) {
			if (!(entitys instanceof Player)) {
				entitys.remove();
			}
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				if (!lp.isCaptcha())
					new Captcha(p);
			}
		}.runTaskLater(Authentication.getInstance(), 5l);
		new BukkitRunnable() {

			public void run() {
				if (!lp.isCaptcha()) {
					p.kickPlayer("§cVocê demorou de mais para efetuar o captcha!");
				} else {
					new BukkitRunnable() {

						@Override
						public void run() {
							if (!lp.isLogged())
								p.kickPlayer("§cVocê demorou de mais para logar!");

						}
					}.runTaskLater(Authentication.getInstance(), 250L);
				}
			}
		}.runTaskLater(Authentication.getInstance(), 250L);
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
					new Captcha(p);
				}
			}.runTaskLater(Authentication.getInstance(), 3l);
		}
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
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
				}.runTaskTimer(Authentication.getInstance(), 0, 30L);

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
	public void onChat(AsyncPlayerChatEvent e) {
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
	public void onCancelCommands(PlayerCommandPreprocessEvent e) {
		if (!(e.getMessage().toLowerCase().startsWith("/register")
				|| e.getMessage().toLowerCase().startsWith("/login"))) {
			e.getPlayer().sendMessage("§cVocê não pode executar este tipo de ação aqui!");
			e.setCancelled(true);
		}
	}

}