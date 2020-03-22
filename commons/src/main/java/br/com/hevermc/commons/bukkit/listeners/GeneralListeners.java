package br.com.hevermc.commons.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.help.HelpTopic;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.ChatCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class GeneralListeners implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p.getName());
		hp.setTag(Tags.getTags(hp.getGroup()));
		hp.updateRank();
		Bukkit.getOnlinePlayers().forEach(all -> {
			hp.setTag_Alternative(all);
		});
		Commons.getManager().online.add(p);

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		PlayerLoader.unload(e.getPlayer().getName());
		Commons.getManager().online.remove(e.getPlayer());
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
			@SuppressWarnings("rawtypes")
			ViaAPI api = Via.getAPI();
			if (api.getPlayerVersion(e.getEntity().getKiller()) >= 47) {
				PlayerLoader.getHP(e.getEntity().getKiller().getName())
						.setCash(PlayerLoader.getHP(e.getEntity().getKiller().getName()).getCash() + 10);
				e.getEntity().getKiller().sendMessage("§4§lKILL §fVocê ganhou §3§l10 cash's §fpor jogar na §21.8+§f!");
			}
		}
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/me") || e.getMessage().startsWith("/minecraft:") || e.getMessage().contains(":")
				|| e.getMessage().startsWith("/bukkit:") || e.getMessage().startsWith("/pl")
				|| e.getMessage().startsWith("/about") || e.getMessage().startsWith("/help")
				|| e.getMessage().startsWith("//calc") || e.getMessage().startsWith("/plugins")) {
			e.setCancelled(true);
			return;
		}
		if (!e.isCancelled()) {
			Player player = e.getPlayer();
			String cmd = e.getMessage().split(" ")[0];
			HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
			if (topic == null) {
				player.sendMessage("§4§lERRO §fEste §4§LCOMANDO §fnão existe!");
				e.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		HeverPlayer hp = PlayerLoader.getHP(p.getName());
		if (ChatCommand.chat) {
			Bukkit.broadcastMessage((hp.getTag() == Tags.MEMBRO
					? hp.getTag().getColor() + p.getName() + " §7[" + hp.getRank().getColor() + hp.getRank().getSymbol()
							+ "§7] §7»§f " + e.getMessage()
					: hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7["
							+ hp.getRank().getColor() + hp.getRank().getSymbol() + "§7]" + " §7»§f "
							+ e.getMessage().replace("&", "§")));
		} else {
			if (!hp.groupIsLarger(Groups.TRIAL)) {
				p.sendMessage("§6§lCHAT §fO chat está §c§lDESABILITADO§f!");
			} else {
				Bukkit.broadcastMessage((hp.getTag() == Tags.MEMBRO
						? hp.getTag().getColor() + p.getName() + " §7[" + hp.getRank().getColor()
								+ hp.getRank().getSymbol() + "§7] " + " §7»§f " + e.getMessage()
						: hp.getTag().getPrefix() + " " + hp.getTag().getColor() + p.getName() + " §7["
								+ hp.getRank().getColor() + hp.getRank().getSymbol() + "§7]" + " §7»§f "
								+ e.getMessage().replace("&", "§")));
			}
		}
	}

}
