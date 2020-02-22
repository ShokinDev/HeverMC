package br.com.hevermc.commons.bungee.events;

import java.util.Date;

import br.com.hevermc.commons.bukkit.api.DateUtil;
import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GeneralEvents implements Listener {

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		ServerPing sp = e.getResponse();
		// sp.setDescription(" §6§lHEVER§f§lMC§f » §7[1.7.x - 1.8.x] \n §f» Servidor em
		// fase §1§lBETA §f«");
		sp.setDescription(
				"              §6§lHEVER§f§lMC§f » §7(1.7.x - 1.8.x) \n             §b§l§k!!§f Servidor em fase §1§lBETA §b§l§k!!§f");

		sp.getPlayers().setSample(null);
		sp.getPlayers().setMax(2020);
		if (Commons.getManager().isMaintenance()) {
			sp.getVersion().setProtocol(999);
			sp.getVersion().setName("Manutenção");
		} else {
			sp.getVersion().setName("HeverMC > 1.7 to 1.8");
		}
		e.setResponse(sp);
	}

	@EventHandler
	public void onJoin(LoginEvent e) {
		HeverPlayer hp = PlayerLoader.getHP(e.getConnection().getName());
		hp.load();
		if (Commons.getManager().isMaintenance()) {
			if (!hp.groupIsLarger(Groups.TRIAL)) {
				System.out.print("[DEBUG] Member not in WhiteList!");
				e.setCancelled(true);
				e.setCancelReason("§4§lWHITELIST\n\n§fEstamos em manutenção, tente novamente mais tarde!"
						+ "\n\n§fEntre em nosso §3§lDISCORD§f!\n§ediscord.hevermc.com.br");
			}
		}
		if (hp.isBanned()) {
			if (hp.getBan_time() > 0 && new Date().after(new Date(hp.getBan_time()))) {
				hp.setBan_author(null);
				hp.setBan_reason(null);
				hp.setBan_time(0);
				hp.setBanned(false);
				return;
			}
			System.out.print("[DEBUG] Banned is true for " + e.getConnection().getName() + "!");
			e.setCancelled(true);
			e.setCancelReason("§4§lBANIDO\n\n§fVocê foi banido "
					+ (hp.getBan_time() > 0 ? "temporariamente" : "permanentemente") + "!\n\n§fMotivo: §c"
					+ hp.getBan_reason() + "\n§fPor: " + hp.getBan_author()
					+ (hp.getBan_time() > 0 ? "\n§fAté: " + DateUtil.formatDifference(hp.getBan_time()) : "")
					+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ediscord.hevermc.com.br");
		}
	}

	@EventHandler
	public void onQuit(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		PlayerLoader.unload(p.getName());

	}

	@EventHandler
	public void onChat(ChatEvent e) {
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		HeverPlayer hp = PlayerLoader.getHP(p);
		if (p.getServer().getInfo().getName().equals("login")) {
			if (!(e.getMessage().startsWith("/login") || e.getMessage().startsWith("/register"))) {
				e.setCancelled(true);
			}
		}
		if (hp.isMuted()) {
			if (e.getMessage().startsWith("/"))
				return;
			if (hp.getMute_time() > 0) {
				if (new Date().after(new Date(hp.getMute_time()))) {
					hp.setMute_author(null);
					hp.setMuted(false);
					hp.setMute_reason(null);
					hp.setMute_time(0);
					return;
				}
			}
			p.sendMessage(TextComponent.fromLegacyText("§cVocê está mutado "
					+ (hp.getMute_time() > 0 ? "até " + DateUtil.formatDifference(hp.getMute_time())
							: "permanentemente")
					+ "!"));
			e.setCancelled(true);
		}
		if (Commons.getManager().staffchat.contains(p)) {
			if (e.getMessage().startsWith("/"))
				return;

			e.setCancelled(true);
			Commons.getInstance().getProxy().getPlayers().forEach(alls -> {
				HeverPlayer hp_all = PlayerLoader.getHP(alls);
				if (hp_all.groupIsLarger(alls, Groups.YOUTUBERPLUS))
					alls.sendMessage(TextComponent.fromLegacyText("§e§l[STAFF-CHAT] §f"
							+ Tags.getTags(hp.getGroup()).getPrefix() + " " + Tags.getTags(hp.getGroup()).getColor()
							+ p.getName() + " §7» §f" + e.getMessage()));
			});
		}
	}

	@EventHandler
	public void receiveMessage(PluginMessageEvent event) {
		String message = null;

		try {
			message = new String(event.getData(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (message.contains("updateGroup(")) {

			System.out.println("[DEBUG] Message received by Bukkit, " + message);
			String[] received = message.substring(14).replace(")", "").split(",");
			String name = received[0];
			System.out.println("[DEBUG] Name read is " + name);
			String toGroup = received[1].replace("groupTo:", "");
			System.out.println("[DEBUG] Group read is " + toGroup);
			if (Commons.getInstance().getProxy().getPlayer(name) != null)
				PlayerLoader.getHP(name).setGroup(Groups.getGroup(toGroup));
		}
		if (message.contains("updateAll(")) {
			String received = message.substring(12).replace(")", "");
			if (Commons.getInstance().getProxy().getPlayer(received) != null)
				PlayerLoader.getHP(Commons.getInstance().getProxy().getPlayer(received)).update();
		}
		if (message.contains("messageUnban(")) {
			String received = message.substring(15).replace(")", "");
			PlayerLoader.getHP(Commons.getInstance().getProxy().getPlayer(received)).load();
		}
		if (message.contains("messageBan(")) {
			// name,author,reason,time
			System.out.println("[DEBUG] Message received by Bukkit, " + message);
			String[] received = message.substring(13).replace(")", "").split(",");
			String name = received[0];
			System.out.println("[DEBUG] Name read is " + name);
			String author = received[1];
			System.out.println("[DEBUG] Author read is " + author);
			String reason = received[2];
			System.out.println("[DEBUG] Reason read is " + reason);
			String time = received[3];
			System.out.println("[DEBUG] Time read is " + time);
			Commons.getInstance().getProxy().getPlayer(name).disconnect(TextComponent.fromLegacyText(
					"§4§lBANIDO\n\n§fVocê foi banido permanentemente!\n\n§fMotivo: §c" + reason + "\n§fPor: " + author
							+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ediscord.hevermc.com.br"));
			HeverPlayer hp2 = PlayerLoader.getHP(name);
			hp2.setBanned(true);
			hp2.setBan_author(author);
			hp2.setBan_time(Long.valueOf(time));
			hp2.setBan_reason(reason);
			Commons.getInstance().getProxy().getPlayers().forEach(players -> {
				HeverPlayer hp = PlayerLoader.getHP(players);
				if (!hp.groupIsLarger(players, Groups.MODPLUS)) {
					TextComponent msg_a = new TextComponent("§c[O jogador " + name + " foi banido]");
					msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason.substring(1)
									+ "\n§fDuração: "
									+ (Long.valueOf(time) == 0l ? "§4permanentemente" : "§ctemporariamente"))
											.create()));
					players.sendMessage(msg_a);
				} else {
					TextComponent msg_a = new TextComponent("§c[O jogador " + name + " foi banido]");
					msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason.substring(1)
									+ "\n§fPor: " + author + "\n§fDuração: "
									+ (Long.valueOf(time) == 0l ? "§4permanentemente" : "§ctemporariamente"))
											.create()));
					players.sendMessage(msg_a);
				}
			});

		}
		if (message.contains("messageMute(")) {
			// name,author,reason,time
			System.out.println("[DEBUG] Message received by Bukkit, " + message);
			String[] received = message.substring(14).replace(")", "").split(",");
			String name = received[0];
			System.out.println("[DEBUG] Name read is " + name);
			String author = received[1];
			System.out.println("[DEBUG] Author read is " + author);
			String reason = received[2];
			System.out.println("[DEBUG] Reason read is " + reason);
			String time = received[3];
			System.out.println("[DEBUG] Time read is " + time);
			HeverPlayer hp2 = PlayerLoader.getHP(name);
			hp2.setMuted(true);
			hp2.setMute_author(author);
			hp2.setMute_reason(reason);
			hp2.setMute_time(Long.valueOf(time));
			Commons.getInstance().getProxy().getPlayers().forEach(players -> {
				HeverPlayer hp = PlayerLoader.getHP(players);
				if (!hp.groupIsLarger(players, Groups.MODPLUS)) {
					TextComponent msg_a = new TextComponent("§c[O jogador " + name + " foi mutado]");
					msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason.substring(1)
									+ "\n§fDuração: "
									+ (Long.valueOf(time) == 0l ? "§4permanentemente" : "§ctemporariamente"))
											.create()));
					players.sendMessage(msg_a);
				} else {
					TextComponent msg_a = new TextComponent("§c[O jogador " + name + " foi mutado]");
					msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason.substring(1)
									+ "\n§fPor: " + author + "\n§fDuração: "
									+ (Long.valueOf(time) == 0l ? "§4permanentemente" : "§ctemporariamente"))
											.create()));
					players.sendMessage(msg_a);
				}
			});

		}
	}

}
