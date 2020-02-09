package br.com.hevermc.commons.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.ChatCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;

public class GeneralListeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		hp.setTag(Tags.getTags(hp.getGroup()));
		Bukkit.getOnlinePlayers().forEach(all -> {
			HeverPlayer hp_all = new PlayerLoader(all).load().getHP();
			hp.setTag_Alternative(all, hp_all.getTag());
		});
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		new PlayerLoader(e.getPlayer()).unload();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
			if (ChatCommand.chat) {
				Bukkit.broadcastMessage((hp.getTag() == Tags.MEMBRO ? hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage() : hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage()));
			} else {
				if (!hp.groupIsLarger(Groups.TRIAL)) {
					p.sendMessage("§cO chat está §c§ldesabilitado§c!");
				} else {
					Bukkit.broadcastMessage((hp.getTag() == Tags.MEMBRO ? hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage() : hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage()));
				}
			}
		}

}
