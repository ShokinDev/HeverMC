package br.com.hevermc.commons.bungee;

import br.com.hevermc.commons.bungee.events.GeneralEvents;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class Commons extends Plugin {

	@Getter
	static Manager manager = new Manager();

	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, new GeneralEvents());
		Commons.getManager().setMaintenance(true);
		getManager().setup();
		super.onEnable();
	}

}
