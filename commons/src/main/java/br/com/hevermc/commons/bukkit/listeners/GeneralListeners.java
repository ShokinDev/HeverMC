package br.com.hevermc.commons.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
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
		HeverPlayer hp = PlayerLoader.getHP(p);
		hp.setTag(Tags.getTags(hp.getGroup()));
		Bukkit.getOnlinePlayers().forEach(all -> {
			HeverPlayer hp_all = PlayerLoader.getHP(all);
			hp.setTag_Alternative(all, hp_all.getTag());
		});
		Commons.getManager().online.add(p);
		new BukkitRunnable() {

			@Override
			public void run() {
				PlayerLoader.forceLoadAccount(hp);
			}
		}.runTaskLater(Commons.getInstance(), 15);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Commons.getManager().online.remove(e.getPlayer());
		PlayerLoader.unload(e.getPlayer().getName());
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/me") || e.getMessage().startsWith("/pl") || e.getMessage().startsWith("/about")
				|| e.getMessage().startsWith("/help") || e.getMessage().startsWith("//calc")
				|| e.getMessage().startsWith("/plugins")) 
			e.setCancelled(true);
		
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p);
		if (ChatCommand.chat) {
			Bukkit.broadcastMessage(
					(hp.getTag() == Tags.MEMBRO ? hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage()
							: hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7» §f"
									+ e.getMessage().replace("&", "§")));
		} else {
			if (!hp.groupIsLarger(Groups.TRIAL)) {
				p.sendMessage("§cO chat está §c§ldesabilitado§c!");
			} else {
				Bukkit.broadcastMessage(
						(hp.getTag() == Tags.MEMBRO ? hp.getTag().getColor() + p.getName() + " §7» §f" + e.getMessage()
								: hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7» §f"
										+ e.getMessage().replace("&", "§")));
			}
		}
	}

}
