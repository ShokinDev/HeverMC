package br.com.hevermc.screenshare.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.screenshare.score.ScoreboardManager;

public class GeneralListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p);
		new ScoreboardManager().build(p);
		p.teleport(p.getWorld().getSpawnLocation());
		p.setHealth(20);
		p.getInventory().clear();
		if (hp.groupIsLarger(Groups.MODGC)) {
			p.sendMessage("");
			p.sendMessage("§aVocê entrou na ScreenShare!");
			p.sendMessage("");
			p.setGameMode(GameMode.CREATIVE);
		} else {
			p.setGameMode(GameMode.SURVIVAL);
			p.sendMessage("");
			p.sendMessage(
					"§cVocê está na ScreenShare, siga todos passos da equipe e faça o download do §aAnyDesk§c(https://anydesk.com/pt/download)§c!");
			p.sendMessage("");
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Bukkit.broadcastMessage("§c" + e.getPlayer().getName() + " desconectou-se da screenshare!");
	}

	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.SURVIVAL)
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlock(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.SURVIVAL)
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
	public void onDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onSpawnEntity(EntitySpawnEvent e) {
		e.setCancelled(true);
	}

}
