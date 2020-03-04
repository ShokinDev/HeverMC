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

	String[] bcs = { "O servidor está em fase §9§lBETA§f, bugs podem ocorrer.",
			"Entre em nosso §3§lDISCORD§f para ficar atento a todas novidades: §ediscord.hevermc.com.br",
			"Acompanhe nosso servidor no Twitter: §3§l@_HeverMC", "Tendo problemas com §4§lHACKERS§f? Utilize §c§l/report§f!",
			"Ganhe §3§lCASH §fao jogar na §21.8§f!" };

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new GeneralEvents());
		Commons.getManager().setMaintenance(false);
		instance = this;
		new CommandLoader();
		getManager().setup();

		List<String> tk = getManager().getBackend().getSql().getTopFive("kitpvp", "kills");
		List<String> td = getManager().getBackend().getSql().getTopFive("kitpvp", "deaths");
		List<String> rankO = getManager().getBackend().getSql().getTopRank();

		getManager().getBackend().getRedis().set("tkpvp",
				tk.get(0) + "," + tk.get(1) + "," + tk.get(2) + "," + tk.get(3) + "," + tk.get(4) + "," + tk.get(5)
						+ "," + tk.get(6) + "," + tk.get(7) + "," + tk.get(8) + "," + tk.get(9));
		getManager().getBackend().getRedis().set("tdpvp",
				td.get(0) + "," 
		+ td.get(1) + "," + td.get(2) + "," + td.get(3) + "," + td.get(4) + "," 
						+ td.get(5)+ "," + 
						td.get(6) + "," + td.get(7) + "," + td.get(8) + "," + td.get(9));
		
		
		getManager().getBackend().getRedis().set("rank",
				rankO.get(0) + "," 
		+ rankO.get(1) + "," + rankO.get(2) + "," + rankO.get(3) + "," + rankO.get(4) + "," 
						+ rankO.get(5)+ "," + 
						rankO.get(6) + "," + rankO.get(7) + "," + rankO.get(8) + "," + rankO.get(9));

		List<String> tkhg = getManager().getBackend().getSql().getTopFive("hg", "kills");
		List<String> tdhg = getManager().getBackend().getSql().getTopFive("hg", "deaths");

		getManager().getBackend().getRedis().set("tkhg",
				tkhg.get(0) + "," + tkhg.get(1) + "," + tkhg.get(2) + "," + tkhg.get(3) + "," + tkhg.get(4) + ","
						+ tkhg.get(5) + "," + tkhg.get(6) + "," + tkhg.get(7) + "," + tkhg.get(8) + "," + tkhg.get(9));

		getManager().getBackend().getRedis().set("tdhg",
				tdhg.get(0) + "," + 
		tdhg.get(1) + "," + 
		tdhg.get(2) + "," +
		tdhg.get(3) + "," +
		tdhg.get(4) + "," + tdhg.get(5) + "," + 
		tdhg.get(6) + "," + 
		tdhg.get(7) + "," + 
		tdhg.get(8) + "," + 
		tdhg.get(9));
		getProxy().getScheduler().schedule(this, new Runnable() {

			@Override
			public void run() {
				List<String> tk2 = getManager().getBackend().getSql().getTopFive("kitpvp", "kills");
				List<String> td2 = getManager().getBackend().getSql().getTopFive("kitpvp", "deaths");
				List<String> rankO = getManager().getBackend().getSql().getTopRank();
				getManager().getBackend().getRedis().del("tkpvp");
				getManager().getBackend().getRedis().del("tdpvp");
				getManager().getBackend().getRedis().del("rank");

				getManager().getBackend().getRedis().set("rank",
						rankO.get(0) + "," 
				+ rankO.get(1) + "," + rankO.get(2) + "," + rankO.get(3) + "," + rankO.get(4) + "," 
								+ rankO.get(5)+ "," + 
								rankO.get(6) + "," + rankO.get(7) + "," + rankO.get(8) + "," + rankO.get(9));
				getManager().getBackend().getRedis().set("tkpvp",
						tk2.get(0) + "," + tk2.get(1) + "," + tk2.get(2) + "," + tk2.get(3) + "," + tk2.get(4) + ","
								+ tk2.get(5) + "," + tk2.get(6) + "," + tk2.get(7) + "," + tk2.get(8) + ","
								+ tk2.get(9));
				getManager().getBackend().getRedis().set("tdpvp",
						td2.get(0) + "," + td2.get(1) + "," + td2.get(2) + "," + td2.get(3) + "," + td2.get(4) + 
						","
								+ td2.get(5) + "," + td2.get(6) + "," + td2.get(7) + "," + td2.get(8) + ","
								+ td2.get(9));

				List<String> tkhg2 = getManager().getBackend().getSql().getTopFive("hg", "kills");
				List<String> tdhg2 = getManager().getBackend().getSql().getTopFive("hg", "deaths");

				getManager().getBackend().getRedis().del("tkhg");
				getManager().getBackend().getRedis().del("tdhg");

				getManager().getBackend().getRedis().set("tkhg",
						tkhg2.get(0) + "," + tkhg2.get(1) + "," + tkhg2.get(2) + "," + tkhg2.get(3) + "," + tkhg2.get(4)
								+ tkhg2.get(5) + "," + tkhg2.get(6) + "," + tkhg2.get(7) + "," + tkhg2.get(8) + ","
								+ tkhg2.get(9));
				getManager().getBackend().getRedis().set("tdhg",
						tdhg2.get(0) + "," + tdhg2.get(1) + "," + tdhg2.get(2) + "," + tdhg2.get(3) + "," + tdhg2.get(4)
								+ tdhg2.get(5) + "," + tdhg2.get(6) + "," + tdhg2.get(7) + "," + tdhg2.get(8) + ","
								+ tdhg2.get(9));
			}
		}, 1, 3, TimeUnit.MINUTES);
		getProxy().getScheduler().schedule(this, new Runnable() {

			@Override
			public void run() {
				getManager().getBackend().getRedis().set("all", "on:" + getProxy().getOnlineCount());
				getManager().getBackend().getRedis().set("kitpvp",
						"on:" + (getProxy().getServerInfo("kitpvp").getPlayers() != null
								? getProxy().getServerInfo("kitpvp").getPlayers().size()
								: 0));
			}
		}, 1, 3, TimeUnit.SECONDS);
		getProxy().getScheduler().schedule(this, new Runnable() {
			int i = 1;

			@Override
			public void run() {
				if (i >= 5) {
					i = 0;
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

	@Override
	public void onDisable() {
		getManager().getBackend().getRedis().del("tkpvp");
		getManager().getBackend().getRedis().del("all");
		getManager().getBackend().getRedis().del("kitpvp");
		getManager().getBackend().getRedis().del("tdpvp");
		super.onDisable();
	}

}
