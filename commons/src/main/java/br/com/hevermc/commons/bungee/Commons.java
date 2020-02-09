package br.com.hevermc.commons.bungee;

import br.com.hevermc.commons.bungee.command.common.loader.CommandLoader;
import br.com.hevermc.commons.bungee.events.GeneralEvents;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class Commons extends Plugin {

	@Getter
	static Manager manager = new Manager();
	@Getter
	static Commons instance;

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new GeneralEvents());
		Commons.getManager().setMaintenance(true);
		instance = this;
		new CommandLoader();
		getManager().setup();
		super.onEnable();
	}

}
