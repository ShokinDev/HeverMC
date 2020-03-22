package br.com.hevermc.authentication.listeners;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import org.bukkit.Bukkit;
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

	HashMap<String, Integer> type = new HashMap<String, Integer>();

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

	public void setAccountType(String name) {
		if (!type.containsKey(name)) {
			if (isPremium(name)) {
				type.put(name, 1);// m.getBackend().getSql().updateInt("players", "accountType", "name",
									// this.accountType, this.name)
				if (Authentication.getManager().getSQLManager().checkString("players", "name", name.toLowerCase())) {
					if (Authentication.getManager().getSQLManager().getInt("players", "name", "accountType",
							name.toLowerCase()) == 2) {
						Authentication.getManager().getSQLManager().updateInt("players", "accountType", "name", 1,
								name.toLowerCase());
						br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(name).setAccountType(1);
						br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(name).forceUpdate();
						Bukkit.getPlayer(name).kickPlayer(
								"§a§lNESTY§f§lMC\n§f\n§fSua conta foi registrada como §a§lORIGINAL§f,\n§fpor favor, relogue.\n§f\n§asite.nestymc..com.br");
					}
				}
			} else {
				type.put(name, 0);
				if (Authentication.getManager().getSQLManager().checkString("players", "name", name.toLowerCase())) {
					if (Authentication.getManager().getSQLManager().getInt("players", "name", "accountType",
							name.toLowerCase()) == 2) {
						Authentication.getManager().getSQLManager().updateInt("players", "accountType", "name", 0,
								name.toLowerCase());

						br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(name).setAccountType(0);
						br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(name).forceUpdate();
					}
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		final Player p = e.getPlayer();
		ReflectionAPI.tab(p, "\n§a§lNESTY§f§lMC\n",
				"\n§fTwitter: §a@NestyNetwork\n§fSite: §asite.nestymc.com.br\n§fLoja: §aloja.nestymc.com.br\n§fDiscord: §ahttps://discord.gg/CqqTRv6\n");
		setAccountType(p.getName());
		final LoginPlayer lp = new PlayerLoader(p).load().lp();
		for (int i = 0; i < 100; i++)
			p.sendMessage(" ");

		if (type.get(p.getName()) == 1) {
			lp.setCaptcha(true);
			lp.setLogged(true);
			p.sendMessage(
					"§e§lLOGIN §fVocê foi autenticado como jogador §a§lORIGINAL§f, portanto não foi necessário fazer login!");
			new BukkitRunnable() {

				@Override
				public void run() {
					if (!p.isOnline() || p == null) {
						cancel();
						return;
					}
					p.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lLOBBY§f!");
					new BungeeChannelApi(Authentication.getInstance()).connect(p, "lobby");
				}
			}.runTaskTimer(Authentication.getInstance(), 0, 35L);
		}

		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		p.getInventory().clear();
		p.setGameMode(GameMode.SURVIVAL);
		p.setMaxHealth(4);
		p.setFoodLevel(20);
		BarUtil.setBar(p, (lp.isRegistred() ? "§a§l§k!!!§f§l USE: §3§l/LOGIN <SENHA> §A§L§K!!!"
				: "§a§l§k!!!§f§l USE: §3§l/REGISTER <SENHA> <SENHA> §A§L§K!!!"), 100);
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
					p.kickPlayer("§4§lKICK\n§f\n§fVocê §4§lDEMOROU §fde mais para efetuar o captcha!");
				} else {
					new BukkitRunnable() {

						@Override
						public void run() {
							if (!lp.isLogged())
								p.kickPlayer("§4§lKICK\n§f\n§fVocê §4§lDEMOROU §fde mais para efetuar o login!");

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
		if (e.getInventory().getTitle().equals("§eClique no §2Bloco Verde") && !lp.isCaptcha()) {
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
		if (title.startsWith("§eClique no §bBloco Verde")) {
			if (clicked.getType() == Material.WOOL) {
				if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("§aClique aqui!")) {
					lp.setCaptcha(true);
					p.closeInventory();
					p.sendMessage("§e§lLOGIN §fVocê §a§lACERTOU§f o captcha!");

					new BukkitRunnable() {

						public void run() {
							if (!lp.isLogged()) {
								if (lp.isRegistred()) {
									p.sendMessage("§e§lLOGIN §fVocê deve se §3§lAUTENTICAR§f, use §b/login <senha>");
								} else {
									p.sendMessage(
											"§e§lLOGIN §FVocê deve se §c§lREGISTRAR§f, use §b/register <senha> <confirme sua senha> <pin de segurança>");
								}
							} else {
								cancel();
							}
						}
					}.runTaskTimer(Authentication.getInstance(), 0, 30L);

				} else {
					p.kickPlayer("§e§lLOGIN §fVocê §4§lERROU §fo captcha!");
				}
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