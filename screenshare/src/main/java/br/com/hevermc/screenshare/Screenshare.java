package br.com.hevermc.screenshare;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.hevermc.screenshare.listeners.GeneralListener;
import lombok.Getter;

public class Screenshare extends JavaPlugin {

	@Getter
	public static Screenshare instance;
	@Getter
	public static Manager manager;

	@Override
	public void onEnable() {
		instance = this;
		manager = new Manager();
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		getManager().setup();
		super.onEnable();
	}

}
