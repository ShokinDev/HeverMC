package br.com.hevermc.commons.bungee;

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

	String[] bcs = { "", "O servidor está em fase §3§lBETA§f, bugs podem ocorrer.",
			"Entre em nosso discord para ficar atento a todas novidades: §ediscord.hevermc.com.br",
			"Acompanhe nosso servidor no Twitter: §3@_HeverMC", "Tendo problemas com hackers? Utilize §c/report§f!" };

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new GeneralEvents());
		Commons.getManager().setMaintenance(false);
		instance = this;
		new CommandLoader();
		getManager().setup();
		getProxy().getScheduler().schedule(this, new Runnable() {
			int i = 1;

			@Override
			public void run() {
				if (i >= bcs.length) {
					i = 1;
					return;
				} else {
					i++;
				}
				if (bcs[i] == null)
					return;

				for (ProxiedPlayer all : Commons.getInstance().getProxy().getPlayers()) {
					all.sendMessage(TextComponent.fromLegacyText(""));
					all.sendMessage(TextComponent.fromLegacyText("§6§LHEVER§F§LMC §7»§f " + bcs[i]));
					all.sendMessage(TextComponent.fromLegacyText(""));
				}

			}
		}, 1, 1, TimeUnit.MINUTES);
		super.onEnable();
	}

}
