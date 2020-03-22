package br.com.hevermc.commons.bungee.events;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import br.com.hevermc.commons.bukkit.api.DateUtil;
import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GeneralEvents implements Listener {

	public static HashMap<String, Integer> premium = new HashMap<String, Integer>();
	public static ArrayList<String> verificando = new ArrayList<String>();

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		ServerPing sp = e.getResponse();
		if (Commons.getManager().isMaintenance()) {

			sp.setDescription(
					"          §4◄   §a§lNESTY§f§lMC §4♦ §7(§f1.7.X §7| §f1.15.X§7)   §4►\n   §4﹃ §4﹄ §7Os §6servidores §7estão em §Cmanutenção §4﹃ §4﹄");
		} else {

			sp.setDescription(
					"           §a►  §a§lNESTY§f§lMC §a♦ §7(§f1.7.X §7| §f1.15.X§7)  §a◄\n         §a﹃ §a﹄ §7Servidor em fase §9§lBETA §a﹃ §a﹄");
		}
		sp.getPlayers().setSample(null);
		sp.getPlayers().setMax(2020);
		e.setResponse(sp);
	}

	public static HashMap<String, Date> segs = new HashMap<String, Date>();
	public static ArrayList<String> bypass = new ArrayList<String>();
	public static ArrayList<String> bypass2 = new ArrayList<String>();

	public static HashMap<String, Integer> checks = new HashMap<String, Integer>();
	public static ArrayList<String> bypass3 = new ArrayList<String>();

	public static ArrayList<String> cooldown = new ArrayList<String>();
	public static ArrayList<String> qs = new ArrayList<String>();
	public static ArrayList<String> ip = new ArrayList<String>();

	@EventHandler
	public void onJoin(LoginEvent e) {
		HeverPlayer hp = PlayerLoader.getHP(e.getConnection().getName());
		hp.load();
		hp.setLastLogin(new Date().getTime());
		if (e.getConnection().getVersion() >= 47) {
			qs.add(e.getConnection().getName());
		}
		if (Commons.getManager().isMaintenance()) {
			if (!hp.groupIsLarger(Groups.TRIAL)) {
				e.setCancelReason("§4§lWHITELIST\n\n§fEstamos em manutenção, tente novamente mais tarde!"
						+ "\n\n§fEntre em nosso §3§lDISCORD§f!\n§ahttps://discord.gg/VgbDwqS");
				e.setCancelled(true);
			}
		}
		if (hp.isBanned()) {

			if (hp.getBan_time() > 0 && new Date().after(new Date(hp.getBan_time()))) {
				Commons.getManager().getBackend().getSql().delete("bans", "name",
						e.getConnection().getName().toLowerCase());
				return;
			}
			e.setCancelReason("§4§lBANIDO\n\n§fVocê foi banido "
					+ (hp.getBan_time() > 0 ? "temporariamente" : "permanentemente") + "!\n\n§fMotivo: §c"
					+ hp.getBan_reason() + "\n§fPor: " + hp.getBan_author()
					+ (hp.getBan_time() > 0 ? "\n§fAté: " + DateUtil.formatDifference(hp.getBan_time()) : "")
					+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ahttps://discord.gg/VgbDwqS");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPreLogin(PreLoginEvent e) {
		if (e.getConnection().getName().toLowerCase().startsWith("mcdrop")) {
			e.setCancelReason("§cNickname bloqueado!");
			e.setCancelled(true);
			return;
		}
		if (!bypass.contains(e.getConnection().getName())) {
			if (!segs.containsKey(e.getConnection().getName())) {
				e.setCancelReason("§cAguarde 3 segundos e tente novamente!");
				e.setCancelled(true);
				Calendar c = Calendar.getInstance();
				c.add(Calendar.SECOND, 3);
				segs.put(e.getConnection().getName(), c.getTime());
				return;
			} else {
				if (!new Date().after(segs.get(e.getConnection().getName()))) {

					e.setCancelReason(
							"§cAguarde " + DateUtil.formatDifference(segs.get(e.getConnection().getName()).getTime())
									+ " e tente novamente!");
					e.setCancelled(true);
					return;
				} else {
					bypass.add(e.getConnection().getName());
					bypass2.add(e.getConnection().getName());
				}
			}
		}
		if (premium.containsKey(e.getConnection().getName()) && premium.get(e.getConnection().getName()) == 0) {
			if (!bypass3.contains(e.getConnection().getName())) {
				if (!bypass2.contains(e.getConnection().getName())) {
					if (!verificando.contains(e.getConnection().getName())) {
						if (checks.containsKey(e.getConnection().getName())) {
							checks.put(e.getConnection().getName(), checks.get(e.getConnection().getName()) + 1);
						} else {
							checks.put(e.getConnection().getName(), 1);
						}

						if (checks.get(e.getConnection().getName()) > 2) {
							bypass3.add(e.getConnection().getName());
							return;
						}

						verificando.add(e.getConnection().getName());

						e.setCancelReason("§a§lNESTY§f§lMC\n§f\n§aVocê foi verificado!\n§fEntre novamente!");
						e.setCancelled(true);

						return;
					}
				} else {
					bypass2.remove(e.getConnection().getName());
				}
			}
		}
		if (cooldown.contains(e.getConnection().getName().toLowerCase())) {
			e.setCancelled(true);
			e.setCancelReason(
					"§a§lNESTY§f§lMC\n§f\n§fSua conta ainda não foi descarregada da ultima vez em que você logou \n§fno servidor, tente novamente!\n§f");
			return;
		}
		if (!premium.containsKey(e.getConnection().getName())) {
			if (PlayerLoader.getHP(e.getConnection().getName()).getAccountType() != 2) {
				if (PlayerLoader.getHP(e.getConnection().getName()).getAccountType() == 1) {
					e.getConnection().setOnlineMode(true);
					premium.put(e.getConnection().getName(), 1);
				} else {
					e.getConnection().setOnlineMode(false);
					premium.put(e.getConnection().getName(), 0);
				}
			} else {
				e.getConnection().setOnlineMode(false);
			}
		} else {
			if (premium.get(e.getConnection().getName()) == 1) {
				e.getConnection().setOnlineMode(true);
			} else {
				e.getConnection().setOnlineMode(false);
			}
		}
		if (verificando.contains(e.getConnection().getName())) {
			verificando.remove(e.getConnection().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();

		String n = p.getName();
		if (qs.contains(n))
			qs.remove(n);
		PlayerLoader.unload(n);

		ProxyServer.getInstance().getScheduler().schedule(Commons.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (cooldown.contains(e.getPlayer().getName().toLowerCase())) {
					cooldown.remove(e.getPlayer().getName().toLowerCase());
				}

			}
		}, 5, TimeUnit.SECONDS);
	}

	@EventHandler
	public void onChat(ChatEvent e) {
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		HeverPlayer hp = PlayerLoader.getHP(p.getName());
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
					Commons.getManager().getBackend().getSql().delete("mutes", "name", p.getName().toLowerCase());

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
				HeverPlayer hp_all = PlayerLoader.getHP(alls.getName());
				if (hp_all.groupIsLarger(Groups.YOUTUBERPLUS))
					alls.sendMessage(TextComponent.fromLegacyText("§e§l[SC] §f"
							+ Tags.getTags(hp.getGroup()).getPrefix() + " " + Tags.getTags(hp.getGroup()).getColor()
							+ p.getName() + " §7» §f" + e.getMessage()));
			});
		}
	}

	ArrayList<String> loginBypass = new ArrayList<String>();

	@EventHandler
	public void receiveMessage(PluginMessageEvent event) {
		String message = null;

		try {
			message = new String(event.getData(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (message.contains("updateGroup(")) {

			String[] received = message.substring(14).replace(")", "").split(",");
			String name = received[0];
			String toGroup = received[1].replace("groupTo:", "");
			PlayerLoader.getHP(name).setGroup(Groups.getGroup(toGroup));
		}
//		"§6§lHEVER§f§lMC §7» §fUm evento foi iniciado no KitPvP, entre clicando aqui!"
		if (message.contains("updateAll(")) {
			String received = message.substring(12).replace(")", "");
			PlayerLoader.getHP(received).update();
		}
		if (message.contains("loginBypass(")) {
			String received = message.substring(14).replace(")", "");
			if (!loginBypass.contains(received))
				loginBypass.add(received);

		}
		if (message.contains("kitpvpEvent")) {
			TextComponent msg_a = new TextComponent(
					"§a§lNESTY§f§lMC §7» §fUm evento foi iniciado no KitPvP, entre clicando aqui e use §e/evento §fdentro do servidor!");
			msg_a.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/connect kitpvp"));
			Commons.getInstance().getProxy().getPlayers().forEach(all -> {
				all.sendMessage(msg_a);
			});
		}

	}

}
