package br.com.hevermc.commons.bungee.events;

import br.com.hevermc.commons.bungee.Commons;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class GeneralEvents implements Listener {

	@EventHandler
	public void onProxyPing(ProxyPingEvent e) {
		ServerPing sp = e.getResponse();
		//sp.setDescription("              §6§lHEVER§f§lMC§f » §7[1.7.x - 1.8.x] \n            §f» Servidor em fase §1§lBETA §f«");
		sp.setDescription("              §6§lHEVER§f§lMC§f » §7[1.7.x - 1.8.x] \n            §b§l§k!!§f Servidor em fase §1§lBETA §b§l§k!!§f");

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

}
