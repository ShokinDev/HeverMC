package br.com.hevermc.authentication;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.hevermc.authentication.api.File;
import br.com.hevermc.authentication.command.commons.loader.CommandLoader;
import br.com.hevermc.authentication.events.GeneralEvents;
import lombok.Getter;

	
public class Authentication extends JavaPlugin {
	
	@Getter public static Authentication instace;
	@Getter public static Manager manager;
	public static File logins = new File("logins");
	
	@Override
	public void onEnable() {
		instace = this;
		manager = new Manager();
		Bukkit.getPluginManager().registerEvents(new GeneralEvents(), this);
		new CommandLoader();
		logins.setup();
		super.onEnable();
	}

}