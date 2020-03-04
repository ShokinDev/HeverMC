package br.com.hevermc.hardcoregames.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.api.HGPlayer;
import br.com.hevermc.hardcoregames.api.HGPlayerLoader;
import br.com.hevermc.hardcoregames.api.Timer;
import br.com.hevermc.hardcoregames.enums.States;
import br.com.hevermc.hardcoregames.score.ScoreboardManager;

public class GeneralListener implements Listener {

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p);
		if (e.getResult() != PlayerLoginEvent.Result.ALLOWED)
			return;

		if (HardcoreGames.getManager().getStateGame() == States.FINALIZANDO) {
			e.disallow(Result.KICK_OTHER, "§cEssa partida está acabando.");
			return;
		}

		if (HardcoreGames.getManager().inGame.contains(p)) {
			e.allow();
			return;
		} else {
			if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
				e.allow();
				return;
			} else {
				if (!hp.groupIsLarger(Groups.LEGEND)) {
					e.disallow(Result.KICK_OTHER, "§ca partida já começou!");
					return;
				} else {
					e.allow();
					return;
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p);
		HGPlayer hgp = new HGPlayerLoader(p).load().getHGP();

		new ScoreboardManager().build(p);
		if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
			HardcoreGames.getManager().inGame.add(p);
			p.getInventory().clear();
			p.teleport(new Location(p.getWorld(), 49, 168, 53));
			p.getInventory().setItem(3,
					new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits 1").create());

			p.getInventory().setItem(5,
					new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits 2").create());
			p.setHealth(20);
		} else {
			if (HardcoreGames.getManager().inGame.contains(p)) {
				HardcoreGames.getManager().broadcast("§a" + p.getName() + " voltou ao jogo!");
			} else {
				if (hp.groupIsLarger(Groups.LEGEND)) {
					hgp.setSpecting(true);
				}
			}

		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (HardcoreGames.getManager().getStateGame() != States.PREJOGO) {
			new BukkitRunnable() {
				@Override
				public void run() {
					if (!e.getPlayer().isOnline())
						HardcoreGames.getManager().inGame.remove(e.getPlayer());
				}
			}.runTaskLater(HardcoreGames.getInstance(), 80 * 20);
		} else {
			HardcoreGames.getManager().inGame.remove(e.getPlayer());
		}
		e.setQuitMessage(null);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (e.getEntity() instanceof Player) {
			Player morreu = e.getEntity();
			if (morreu.getKiller() instanceof Player) {
				Player matou = morreu.getKiller();
				HGPlayer hgmorreu = new HGPlayerLoader(morreu).load().getHGP();
				HGPlayer hgmatou = new HGPlayerLoader(matou).load().getHGP();
				HardcoreGames.getManager()
						.broadcast("§c" + morreu.getName() + "(" + hgmorreu.getKit1() + " | " + hgmorreu.getKit2()
								+ ") foi morto por " + matou.getName() + "(" + hgmatou.getKit1() + " | "
								+ hgmatou.getKit2() + ") usando "
								+ HardcoreGames.getManager().getItemInHand(matou.getItemInHand().getType()));
				hgmatou.setKills(hgmatou.getKills() + 1);
				if (PlayerLoader.getHP(morreu).groupIsLarger(Groups.LEGEND)) {
					if (Timer.tempo < 300) {
						if (PlayerLoader.getHP(morreu).groupIsLarger(Groups.CRYSTAL)) {
						} else {
							hgmorreu.setSpecting(true);
						}
					}
				}
			} else {
				HGPlayer hgmorreu = new HGPlayerLoader(morreu).load().getHGP();
				HardcoreGames.getManager()
						.broadcast("§c" + morreu.getName() + "(" + hgmorreu.getKit1() + " | " + hgmorreu.getKit2()
								+ ") morreu "
								+ HardcoreGames.getManager().getCause(morreu.getLastDamageCause().getCause()));
			}
		}
		if (HardcoreGames.getManager().inGame.size() != 1) {
			HardcoreGames.getManager()
					.broadcast("§cFaltam " + HardcoreGames.getManager().inGame.size() + " jogadores.");
		}
	}

}
