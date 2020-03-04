package br.com.hevermc.hardcoregames;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.hevermc.hardcoregames.listeners.GeneralListener;
import lombok.Getter;

public class HardcoreGames extends JavaPlugin {

	@Getter
	public static HardcoreGames instance;
	@Getter
	public static Manager manager;

	@Override
	public void onLoad() {
		manager = new Manager();
		manager.deleteDir(new File("world"));
		super.onLoad();
	}
	
	@Override
	public void onEnable() {
		//manager.loadChunks();
		//manager.criarBordas();
		instance = this;
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		getManager().setup();
		super.onEnable();
	}
}
