package br.com.hevermc.commons.bungee;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.hevermc.commons.bungee.command.common.loader.CommandLoader;
import br.com.hevermc.commons.bungee.events.GeneralEvents;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Commons extends Plugin {

	@Getter
	static Manager manager = new Manager();
	@Getter
	static Commons instance;

	String[] bcs = { "", "O servidor está em fase §9§lBETA§f, bugs podem ocorrer.",
			"Entre em nosso discord para ficar atento a todas novidades: §ediscord.hevermc.com.br",
			"Acompanhe nosso servidor no Twitter: §3@_HeverMC", "Tendo problemas com hackers? Utilize §c/report§f!" };

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new GeneralEvents());
		Commons.getManager().setMaintenance(false);
		instance = this;
		new CommandLoader();
		getManager().setup();
		List<String> tk = getManager().getSQLManager().getTopKitPvP("kills");
		List<String> td = getManager().getSQLManager().getTopKitPvP("deaths");
		getManager().getRedis().set("tkpvp", tk.get(0) + "," + tk.get(1) + "," + tk.get(2) + "," + tk.get(3) + "," + tk.get(4));
		getManager().getRedis().set("tdpvp", td.get(0) + "," + td.get(1) + "," + td.get(2) + "," + td.get(3) + "," + td.get(4));
		getProxy().getScheduler().schedule(this, new Runnable() {

			@Override
			public void run() {
				List<String> tk2 = getManager().getSQLManager().getTopKitPvP("kills");
				List<String> td2 = getManager().getSQLManager().getTopKitPvP("deaths");
				getManager().getRedis().del("tkpvp");
				getManager().getRedis().del("tdpvp");
				getManager().getRedis().set("tkpvp", tk2.get(0) + "," + tk2.get(1) + "," + tk2.get(2) + "," + tk2.get(3) + "," + tk2.get(4));
				getManager().getRedis().set("tdpvp", td2.get(0) + "," + td2.get(1) + "," + td2.get(2) + "," + td2.get(3) + "," + td2.get(4));
			}
		}, 1, 3, TimeUnit.MINUTES);
		getProxy().getScheduler().schedule(this, new Runnable() {

			@Override
			public void run() {
				getManager().getRedis().del("all");
				getManager().getRedis().del("kitpvp");
				getManager().getRedis().set("all", "on:" + getProxy().getOnlineCount());
				getManager().getRedis().set("kitpvp", "on:" + (getProxy().getServerInfo("kitpvp").getPlayers() != null ? getProxy().getServerInfo("kitpvp").getPlayers().size() : 0));
			}
		}, 1, 3, TimeUnit.SECONDS);
		getProxy().getScheduler().schedule(this, new Runnable() {
			int i = 1;

			@Override
			public void run() {
				if (i == bcs.length) {
					i = 1;
					return;
				} else {
					i++;
				}
				if (bcs[i] == null)
					return;

				for (ProxiedPlayer all : Commons.getInstance().getProxy().getPlayers()) {
					all.sendMessage(TextComponent.fromLegacyText(""));
					all.sendMessage(TextComponent.fromLegacyText("§6§lHEVER§f§lMC §f» §f" + bcs[i]));
					all.sendMessage(TextComponent.fromLegacyText(""));
				}

			}
		}, 1, 1, TimeUnit.MINUTES);
		super.onEnable();
	}

}
