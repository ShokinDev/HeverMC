package br.com.hevermc.commons.bungee.events;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
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
				"              §6§lHEVER§f§lMC§f » §7[1.7.x - 1.8.x] \n            §b§l§k!!§f Servidor em fase §1§lBETA §b§l§k!!§f");

		sp.getPlayers().setSample(null);
		sp.getPlayers().setMax(2020);
		if (Commons.getManager().isMaintenance()) {
			sp.getVersion().setProtocol(999);
			sp.getVersion().setName("Manutenção");
		} else {

			sp.getVersion().setProtocol(5);
			sp.getVersion().setName("HeverMC > 1.7 to 1.8");
		}
		e.setResponse(sp);
	}

	@EventHandler
	public void onChat(ChatEvent e) {
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		if (Commons.getManager().staffchat.contains(p)) {
			if (e.getMessage().startsWith("/")) {
				return;
			}
			e.setCancelled(true);
			Commons.getInstance().getProxy().getPlayers().forEach(alls -> {
				HeverPlayer hp_all = new PlayerLoader(alls).load().getHP();
				if (hp_all.groupIsLarger(alls, Groups.YOUTUBERPLUS))
					alls.sendMessage(TextComponent.fromLegacyText("§e§l[SC] §f"
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
		System.out.println("aaaaa messsage =) " + message);
		if (message.contains("updateGroup(")) {

			System.out.println("[DEBUG] Message received by Bukkit, " + message);
			String[] received = message.substring(14).replace(")", "").split(",");
			String name = received[0];
			System.out.println("[DEBUG] Name read is " + name);
			String toGroup = received[1].replace("groupTo:", "");
			System.out.println("[DEBUG] Group read is " + toGroup);
			if (Commons.getInstance().getProxy().getPlayer(name) != null)
				new PlayerLoader(Commons.getInstance().getProxy().getPlayer(name)).load().getHP().setGroup(Groups.getGroup(toGroup));
		}
		if (message.startsWith("updateAll(")) {
			String received = message.substring(12).replace(")", "");
			if (Commons.getInstance().getProxy().getPlayer(received) != null)
				new PlayerLoader(Commons.getInstance().getProxy().getPlayer(received)).load().getHP().update();
		}
	}

}
