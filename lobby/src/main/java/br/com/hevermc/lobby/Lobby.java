package br.com.hevermc.lobby;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class Lobby extends JavaPlugin {

	@Getter
	public static Manager manager = new Manager();
	@Getter
	public static Lobby instance;

	@Override
	public void onEnable() {
		instance = this;
		getManager().setup();
		super.onEnable();
	}

}
