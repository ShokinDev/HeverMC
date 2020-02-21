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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.BarUtil;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import br.com.hevermc.lobby.Lobby;
import br.com.hevermc.lobby.api.Holograms;
import br.com.hevermc.lobby.api.NPC;
import br.com.hevermc.lobby.api.PacketReader;
import br.com.hevermc.lobby.command.BuildCommand;
import br.com.hevermc.lobby.gui.Profile;
import br.com.hevermc.lobby.gui.Servers;
import br.com.hevermc.lobby.score.ScoreboardManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;

public class GeneralListener implements Listener {

	ArrayList<Player> inCooldown = new ArrayList<Player>();

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p);
		p.getInventory().clear();
		Bukkit.getOnlinePlayers().forEach(allPlayers -> {
			if (Lobby.getManager().hide_players.contains(allPlayers))
				hidePlayers(allPlayers, p);
		});

		for (int i = 0; i < 100; i++)
			p.sendMessage("");
		p.getInventory().setItem(0,
				new ItemConstructor(new ItemStack(Material.COMPASS), "§eServidores §7(Abra com o botão direito)")
						.create());

		p.getInventory().setItem(1,
				new ItemConstructor(new ItemStack(Material.ENDER_CHEST), "§eColetáveis §7(Abra com o botão direito)")
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
		skullm.setDisplayName("§ePerfil §7(Abra com o botão direito)");
		skull.setItemMeta((ItemMeta) skullm);

		p.getInventory().setItem(7, skull);

		p.getInventory().setItem(8, new ItemConstructor(new ItemStack(Material.INK_SACK, 1, (short) 10),
				"§7Jogadores: §aON", Enchantment.WATER_WORKER, 5).create());

		p.sendMessage(" ");
		p.sendMessage("§6§lHEVER§F§LMC");
		p.sendMessage(" ");
		p.sendMessage(
				"§fSeja §ebem-vindo§f ao servidor, qualquer problema consulte a §eequipe§f via Discord: §ediscord.hevermc.com.br§f, caso tenha algum problema");
		p.sendMessage("§fcom hackers utilize o comando §c/report§f");
		p.sendMessage(" ");
		p.setAllowFlight(true);

		ReflectionAPI.sendTitle(p, "§e§lLOBBY", "§fSeja bem-vindo ao §elobby§f da rede §6Hever§fMC§f!", 10, 10, 10);
		ReflectionAPI.tab(p, "\n§6§lHEVER§f§lMC\n",
				"\n§fTwitter: §e@HeverNetwork_ §7| §fDiscord: §ediscord.hevermc.com.br §7| §fSite: §ewww.hevermc.com.br\n§fCaso tenha algum problema visite nosso §eDiscord§f!\n");

		p.teleport(p.getWorld().getSpawnLocation());
		p.setGameMode(GameMode.SURVIVAL);
		new ScoreboardManager().build(p);
		if (Lobby.getManager().npc_loc.containsKey("kitpvp")) {
			Location l = Lobby.getManager().npc_loc.get("kitpvp");
			NPC npc = new NPC("§aClique aqui", l);
			new Holograms("§a§lKITPVP", new Location(l.getWorld(), l.getX(), l.getY() + 1, l.getZ())).showPlayer(p);
			npc.spawn(p);
			npc.headRotation(-178.7f, 1.6f);
			npc.changeSkin(
					"eyJ0aW1lc3RhbXAiOjE1ODEzODE2NjgxNDQsInByb2ZpbGVJZCI6Ijk0YzI1OThhMzA4MjRiMTU4M2RlNDlhZTMzMGNkNDU2IiwicHJvZmlsZU5hbWUiOiJCbGFhYWNrb3V0WiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q0NDg1NDUzNmZlZTcxNGI0MDFlMDU3MGU4ZmEzYTJiYzM5NWMyNjA5MGI1ODU1YzYxZjNkZGJmOGE4OGEyOCJ9fX0=",
					"DfgUfogh2qx02hoGKCIkF6BxgqLh9KaaM5l92FGx1otlMgYhS0LN4HmwHQkI3+83qZCvPWShGEPx5vO2ylYk8yRmpzrlp/OedDxCqWxejLzTmSfFAg/MsY5nytPXaWLfSFEWBnS6w/DQmWfoRbMCL54AF4tBSqI6cVcMiM7WKMhTlv6yUGhhazs2yuDjgZM1wnla50z4i/HhlXFk5Fj2zAjjRg+zNP35S0l1xfEPag96Gx/NeKkCxD6iGJs6irb3ZoNaYAfnzsg58BUFfZxww9P+T7XjlZ8XlygLiTq4E4Z8AgI3+WT3TjOEK1V78t/TjoNSg7nT3+slN0Wch3bMb9GYYc45x5QPzZp5NEvG53xw9T4K0QhKbyZIXMKDt13pTf0uaxmBQh9qfMLGDcdcFrRoXaF7p358Gefy9s+8VWsUdjWuLVqb8ssrblykiEoJgaBtVxRsdxWqyj9rR9Du2HNFV/OyG6BN1AYVf6guDzrjUALonEkuG4gZHSefSw9eUBEy6YIzmi4vLe5SP5qxPtEy4cFwLpHQTWnD19UDxCTLW9hHslFPbW6zZoJYx9o1FOayo3aYzfjZc2BXRI/VKnoFLA0UkgErTdD7KzZ/9Br/vwuzHGV5/Aa53f57Id+cqq+GFshkntTK/5MPCc1+E/tRMbHSuGxyy8dFf/T8W1M=");
			new BukkitRunnable() {
				String htext = "§fJogando: §a0";
				Holograms h = new Holograms(htext,
						new Location(l.getWorld(), l.getX(), l.getY() + 0.5, l.getZ())).showPlayer(p);
				@Override
				public void run() {
					if (!p.isOnline() || p == null) {
						cancel();
					} else {
						Commons.getManager().getBungeeChannel().getPlayerCount("kitpvp")
								.whenComplete((result, error) -> {
									if (Integer.parseInt(htext.replace("§fJogando: §a", "")) != result.intValue()) {
										htext = "§fJogando: §a" + result.intValue();
										h.hidePlayer(p);
										h.setName(htext);
										h.showPlayer(p);
									}
				
								});
					}
				}
			}.runTaskTimer(Lobby.getInstance(), 0, 10);
			new BukkitRunnable() {
				@Override
				public void run() {
					npc.rmvFromTablist();
				}
			}.runTaskLater(Lobby.getInstance(), 25L);
			Lobby.getManager().npc.put(p, npc.getEntityID());
			new PacketReader(p).inject();
		}
		if (hp.groupIsLarger(Groups.PRO))
			Bukkit.broadcastMessage(Tags.getTags(hp.getGroup()).getPrefix() + " "
					+ Tags.getTags(hp.getGroup()).getColor() + p.getName() + " §fentrou neste §a§nlobby§f!");

		BarUtil.setBar(p, "§a§l§k!!!§f§l VOCÊ ESTÁ CONECTADO AO §E§LLOBBY §A§L§K!!!", 100);
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
			if (title.startsWith("§eServidores")) {
				if (clicked.getType() == Material.DIAMOND_SWORD) {
					p.sendMessage("§aVocê está sendo conectado ao KitPvP!");
					ReflectionAPI.sendTitle(p, "§b§lCONNECT", "§fVocê está se conectando ao §aKitPvP§f!", 10, 10, 10);
					Commons.getManager().getBungeeChannel().connect(p, "kitpvp");
					p.closeInventory();
				}
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
			if (p.getItemInHand().getType() == Material.COMPASS) {
				e.setCancelled(true);
				new Servers(p);
			} else if (p.getItemInHand().getItemMeta().getDisplayName()
					.equalsIgnoreCase("§ePerfil §7(Abra com o botão direito)")) {
				e.setCancelled(true);
				new Profile(p);
			} else if (p.getItemInHand().getType() == Material.ENDER_CHEST) {
				p.sendMessage("§cOs coletáveis estão em desenvolvimento!");
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
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onSpawnEntity(EntitySpawnEvent e) {
		e.setCancelled(true);
	}

}
