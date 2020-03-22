package br.com.hevermc.lobby.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import br.com.hevermc.lobby.Lobby;
import br.com.hevermc.lobby.api.NPC;
import br.com.hevermc.lobby.api.PacketReader;
import br.com.hevermc.lobby.command.BuildCommand;
import br.com.hevermc.lobby.gui.Profile;
import br.com.hevermc.lobby.gui.Servers;
import br.com.hevermc.lobby.score.ScoreboardManager;
import net.md_5.bungee.api.chat.TextComponent;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class GeneralListener implements Listener {

	ArrayList<Player> inCooldown = new ArrayList<Player>();

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		HeverPlayer hp = PlayerLoader.getHP(p.getName());
		p.getInventory().clear();
		Bukkit.getOnlinePlayers().forEach(allPlayers -> {
			if (Lobby.getManager().hide_players.contains(allPlayers))
				hidePlayers(allPlayers, p);
		});

		p.getInventory().setItem(0,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§aServidores §7(Abra com o botão direito)")
						.create());

		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.ENDER_CHEST), "§aColetáveis §7(Abra com o botão direito)")
						.create());
		@SuppressWarnings("rawtypes")
		ViaAPI api = Via.getAPI();
		if (api.getPlayerVersion(p) < 47)
			p.sendMessage(
					"§cDetectamos que você está em uma versão §c§linferior §ca §c§l1.8§c, para melhor experiência em nosso servidor use a versão §c§l1.8§c!");

		@SuppressWarnings("deprecation")
		ItemStack skull = new ItemStack(397, 1, (short) 3);
		SkullMeta skullm = (SkullMeta) skull.getItemMeta();
		skullm.setOwner(p.getName());
		skullm.setDisplayName("§aPerfil §7(Abra com o botão direito)");
		skull.setItemMeta((ItemMeta) skullm);

		p.getInventory().setItem(7, skull);

		p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10),
				"§7Jogadores: §aON", Enchantment.WATER_WORKER, 5).create());

		p.sendMessage(" ");
		p.setAllowFlight(true);

		ReflectionAPI.sendTitle(p, "§a§lLOBBY", "§fSeja bem-vindo ao §a§lNESTY§f§lMC§f!", 10, 10, 10);

		p.teleport(p.getWorld().getSpawnLocation());
		p.setGameMode(GameMode.SURVIVAL);
		new ScoreboardManager().build(p);

		//

		Location l = new Location(Bukkit.getWorld("world"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.x"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.y"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.pvp.z"));
		NPC npc;

		npc = new NPC("§aClique aqui", l);

		npc.spawn(p);
		npc.headRotation(0, 0);
		npc.changeSkin(
				"eyJ0aW1lc3RhbXAiOjE1ODI5OTk1MjM2MjEsInByb2ZpbGVJZCI6ImJhOTcxY2EwZjcyZjRiOWRiMmZlNDM0MGU2NzY3OTZiIiwicHJvZmlsZU5hbWUiOiJpTHVjYXNVUyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjlhYjgwODcyMDI5N2FhMjI4YmEyYzUzNWFhMzZmYmI2NDI5MTY3YTdkMjM2MmJjM2JlOTIyMTBmNjA0YTc4NCJ9fX0=",
				"MmuK79hTlRHjxzWODt4cpHgea8KOcB94oZJLUjviyM42PJfix1sKhvPKUDclXP9K99zBQeUQ0qNoZZIOnmheIbTXfvlF4iCIFW5RdRR2gUhqEL4fVB2Ydn8q4994BdNsqttDwiku8SOmIf4mzTjbbEFROudWkA5yGs7E1+2s3N3FqeYQPMYM15/3iUhgXXJodP60tW8dGJ7qzsUSOX5wJPkXk6G+iQXQOIo67cw2iBnJGvk0RzbkM3BKi1dCtEchAx3vdigltZRk60hCo/74pvvzVvzkNNxYjGwQ6g+EskB/GmgvV4E3mmwR9hjb8iv26kSKwBS3zE8VTIhF34PIAQ828WJWjVdi0xk+RFuNCd7l4Pd/NBLTl0lrb+eDiClucU2quPY42+6sF7B9DXFiuw7oOxXhLOj+O0Jw22iSFru9816VXdVVGu70aEZFaCw266rqRjOIycivwU3T7SVYsyxpv1kAVWRx6eqETVETg6onkhRmflHaSxtkWXxuHkFfnNB667mmcVrP43d6OIT6kdB98vbrIEJE74vLEh6HPwZy2c1cyeREpTv0ehqcvSzTfX0WkaRW8BqKpMX/IVjLu3C8c+RfANXvPSQvbEC+iG1uuON2Z6B8Q2ZF91GxEIXkz4tlSNtU3Kr3qHHCuieIEBpPt6mN1Nc0lkrqisXhNTQ=");

		Location l2 = new Location(Bukkit.getWorld("world"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.x"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.y"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.hg.z"));
		NPC npc2;

		npc2 = new NPC("§aClique aqui", l2);

		npc2.spawn(p);
		npc2.headRotation(-180.0f, 1.6f);
		npc2.changeSkin(
				"eyJ0aW1lc3RhbXAiOjE1ODI5OTk2NzEzMTMsInByb2ZpbGVJZCI6IjU3NDVkYmM2ZWVkMjQ3YzBiNjQ5MmI0YjU0ZDU4ZjlhIiwicHJvZmlsZU5hbWUiOiJTaG9raWluIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9kMjUyNWUwZTZlNGJiMTkzM2JjYTY5OWUzNjQ4ZTQ5M2U3YzBlYTIzMjIyMTI3ODc3YWI4NmUwNDNmYjA5NTEwIn19fQ==",
				"EqK9MvQ+XBfd0wZS36bItgrl7sXubnUCGNp4eYi3r/NzKQH7He5cxVBSnbtUBlOw1HbUJp79iQ6Kexufm77HBs+dx70cgoS+pRoQfckSNoB3G05ZA4EYLSswWytgiqX67eUbkbQZJaKClcr+hMvKRne5+DZUO6J6zblcwDcT5xUiCQqG/yaBjF/MpzvJVI9iGUEKxh+D2h3gkXujGxlbK54/gZFwS9Eg5le49Jl/aRlzWYfUw6TgMFQf4w4WKSew1+vZu88MIu0rW3xUVXz0uJrY/3mD5FWwuYiF0J3KUyE9l6XuMaGtEH+ZngR/Q+aKAmMZN0nbRLGIC9Vwk/PbThDML8sTO6Bn6+6okjJZFDKlAUTC0rmopMjBglu4XBBOtJZkE1pCS1PIi/pNQCeh5oojvXyUfE3H7rF1btfqduZMubyDPCf9x2doMko/rpY6Cwl5mXjkwnReV0gufp9n5Tgeq3y8Noaw5L0lfT52yZXEwzdnypwVwWxuJS0ilcTicf2ucZTSWIw21Sh9HfNLlIMDNSP3ONUXmFmPE2EVwo+gYP1vHPRq3Jy0E3dOUbETPSuTVwxSz5McR0JUjXt9wodN4SiseGJK7DdQ3xKcAmyyF/wrSRIwDvUbVOXpyo11E+J2uMzosfbWCxibTQjBirXmy/5Isa5cckOUuDyyfPk=");

		Location l3 = new Location(Bukkit.getWorld("world"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.x"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.y"),
				Lobby.getInstance().getLocations().getConfig().getDouble("npc.glad.z"));
		NPC npc3;

		npc3 = new NPC("§aClique aqui", l3);

		npc3.spawn(p);
		npc3.headRotation(-180.0f, 1.6f);
		npc3.changeSkin(
				"yJ0aW1lc3RhbXAiOjE1ODM1MzE4ODMyMjMsInByb2ZpbGVJZCI6ImM3ZTE3NDY1ZDkzODQyNTBiM2IxYjg3ZjE0ODBmOGMxIiwicHJvZmlsZU5hbWUiOiJtb3phaWNvNDA0Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83MGY4OGJiM2ZlMGIwYmQ1YmY4MzMwNDQ1ZjU2MmU4ODIwOWIwYmI2YmVmN2VkYjQwN2ZiOTFmMTlhNDQ5NmU1In19fQ==",
				"MxbgYDvo7jyEgsspJyrxABH7xmIVYBqNNqnlwyZCa7o6jIZTaX553olCGRni1ORLOLroo/53ZU3qeaqY8GQ8uYsacCN9smciRpnL4TiTWXA/sSMld1XX26g4v7fcjEkfVzwQAWhY6uya/8Vs/OTawVYPQeoRk4CPerFbc6MgUED2PMODTAmJn3cS2hNRhYckTNB0jj+ypiaF05RLYitbei5VXEsnwoUUTAMNtIstN//0DNXDbU6ATRnzlbfllTVq8Y5F0vMx/FiSv6UvfNmUCZgH8B6xY1ZwpBgQC+xcrJC7aE6PIMZIN84e5Q7v6fKLbJQ0QbBLX0J/jlrnJAa13Uwz2f0BpP8saaYgwahP5Y5MwCOeOHAweiocNYsQSTNh+/w+hsF2xPZGW+sqfeEtLOFIDaxQ+Y8YpMvnvXziDpBJAV/OSdNgnmMWc8U9okz6adcI+VAenLGfZpoJuBASc/Ib+0BJ9Km4Xoe8Nz8WM2EDoMEoEzE85p5LRE9VVwf38v0nkIqodf7IhAl/fdc3uXz8c7G+1Bvq13uv+aqFoLVHgtdv2aMjTON+SWRyJ8BygH+4sDyxJ2xK3OodZRtTzOnzetBfCH963mJeoNgwDSjzZHc46WOfd2kgHFN7rgvgral8giHNqet6ymLiozMlPJFl8Y9pv4O/0Mv1KXQhxcg=");

		new BukkitRunnable() {
			@Override
			public void run() {
				npc.rmvFromTablist();
				npc2.rmvFromTablist();
				npc3.rmvFromTablist();
			}
		}.runTaskLater(Lobby.getInstance(), 45L);
		Lobby.getManager().npc.put(p, npc.getEntityID());
		Lobby.getManager().npc2.put(p, npc2.getEntityID());
		Lobby.getManager().npc3.put(p, npc3.getEntityID());
		new PacketReader(p).inject();

		if (hp.groupIsLarger(Groups.PRO))
			Bukkit.broadcastMessage(Tags.getTags(hp.getGroup()).getPrefix() + " "
					+ Tags.getTags(hp.getGroup()).getColor() + p.getName() + " §fentrou neste §a§nLOBBY§f!");

	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInteractInInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		String title = inv.getTitle();
		ItemStack clicked = e.getCurrentItem();
		if (clicked != null) {
			if (p.getGameMode() != GameMode.CREATIVE)
				e.setCancelled(true);
			if (title.startsWith("§a§lServers")) {
				e.setCancelled(true);
				if (clicked.getType() == Material.DIAMOND_SWORD) {
					p.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lKITPVP§f!");
					Commons.getManager().getBungeeChannel().connect(p, "kitpvp");
					p.closeInventory();
				} else if (clicked.getType() == Material.MUSHROOM_SOUP) {
					p.closeInventory();
					if (!PlayerLoader.getHP(p).groupIsLarger(Groups.MOD)) {

						p.sendMessage("§4§lPERMISSÃO§f Você não possui §c§lPERMISSÃO§F!");
					} else {
						// new HardcoreGames(p);
					}
				}
			} else if (title.startsWith("§3§lServers §a§l> Salas de HardcoreGames")) {
				if (e.getCurrentItem() != null) {
					if (e.getCurrentItem().getType() == Material.INK_SACK) {
						p.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§LHG-1§f!");
						p.closeInventory();
						Commons.getManager().getBungeeChannel().connect(p,
								e.getCurrentItem().getItemMeta().getDisplayName().replace("§aSala ", "").toLowerCase());
					}
				}
				e.setCancelled(true);
			}
		}
	}

	public void hidePlayers(Player forPlayer) {
		Bukkit.getOnlinePlayers().forEach(allPlayers -> {
			forPlayer.hidePlayer(allPlayers);
		});
	}

	public void hidePlayers(Player forPlayer, Player toHide) {
		forPlayer.hidePlayer(toHide);
	}

	public void showPlayers(Player forPlayer) {
		Bukkit.getOnlinePlayers().forEach(allPlayers -> {
			forPlayer.showPlayer(allPlayers);
		});

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		// HeverPlayer hp = new PlayerLoader(p).load().getHP();
		if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
			if (p.getItemInHand().getType() == Material.LAVA_BUCKET) {
				e.setCancelled(true);
				return;
			}
			if (p.getItemInHand().getType() == Material.COMPASS) {
				e.setCancelled(true);
				new Servers(p);
			} else if (p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§aPerfil §7(Abra com o botão direito)")) {
				e.setCancelled(true);
				new Profile(p);
			} else if (p.getItemInHand().getType() == Material.ENDER_CHEST) {
				p.sendMessage("§c§lERRO §fOs coletáveis estão em §3§lDESENVOLVIMENTO§f!");
				e.setCancelled(true);
			} else if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7Jogadores: §aON")) {
				if (inCooldown.contains(p)) {
					p.sendMessage(
							"§cVocê ainda não pode utilizar esta função pois utilizou muito recentemente, aguarde.");
					return;
				}
				inCooldown.add(p);
				new BukkitRunnable() {

					@Override
					public void run() {
						inCooldown.remove(p);
					}
				}.runTaskLater(Lobby.getInstance(), 120L);
				e.setCancelled(true);
				p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 8),
						"§7Jogadores: §cOFF", Enchantment.WATER_WORKER, 5).create());
				hidePlayers(p);
				Lobby.getManager().hide_players.add(p);
			} else if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§7Jogadores: §cOFF")) {
				if (inCooldown.contains(p)) {
					p.sendMessage(
							"§cVocê ainda não pode utilizar esta função pois utilizou muito recentemente, aguarde.");
					return;
				}
				e.setCancelled(true);
				p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10),
						"§7Jogadores: §aON", Enchantment.WATER_WORKER, 5).create());
				showPlayers(p);
				Lobby.getManager().hide_players.remove(p);
			}
		}
	}

	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.SURVIVAL)
			return;
		if (!Lobby.getManager().dj.contains(p)) {
			e.setCancelled(true);
			p.getVelocity().setY(1);
			p.setVelocity(p.getLocation().getDirection().multiply(3).setY(1));
			p.getVelocity().multiply(2);
			Lobby.getManager().dj.add(p);
			p.setAllowFlight(false);
			new BukkitRunnable() {

				@Override
				public void run() {
					Lobby.getManager().dj.remove(p);
					p.setAllowFlight(true);
				}
			}.runTaskLater(Lobby.getInstance(), 25l);
		}
	}

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
		if (!BuildCommand.build.containsKey(e.getPlayer()) || !BuildCommand.build.get(e.getPlayer()))
			e.setCancelled(true);
	}

	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		e.getWorld().setWeatherDuration(0);
		e.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onSpawnEntity(EntitySpawnEvent e) {
		e.setCancelled(true);
	}

}
