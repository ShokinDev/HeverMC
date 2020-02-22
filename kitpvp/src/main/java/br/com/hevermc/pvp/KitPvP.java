package br.com.hevermc.pvp;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.hevermc.pvp.api.File;
import br.com.hevermc.pvp.command.commons.loader.CommandLoader;
import br.com.hevermc.pvp.listeners.GeneralListener;
import br.com.hevermc.pvp.listeners.OneVsOneListener;
import br.com.hevermc.pvp.listeners.kits.commons.loader.KitLoader;
import lombok.Getter;

public class KitPvP extends JavaPlugin {

	@Getter
	public static KitPvP instance;
	@Getter
	public static Manager manager = new Manager();
	@Getter
	public static File warps = new File("warps");

	@Override
	public void onEnable() {
		instance = this;
		warps.setup();
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		Bukkit.getPluginManager().registerEvents(new OneVsOneListener(), this);
		new CommandLoader();
		new KitLoader();
		getManager().setup();
		super.onEnable();
	}

}
