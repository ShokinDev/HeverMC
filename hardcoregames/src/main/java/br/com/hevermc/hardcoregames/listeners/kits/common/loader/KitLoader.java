package br.com.hevermc.hardcoregames.listeners.kits.common.loader;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.google.common.reflect.ClassPath;

import br.com.hevermc.hardcoregames.HardcoreGames;

public class KitLoader {

	public KitLoader() {
		registerAllListeners();
	}

	public void registerAllListeners() {
		try {
			ClassPath cp = ClassPath.from(getClass().getClassLoader());
			cp.getTopLevelClasses("br.com.hevermc.hardcoregames.listeners.kits").stream().forEach(listeners -> {
				try {
					registerListener((Listener) listeners.load().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, HardcoreGames.getInstance());
		HardcoreGames.getManager().log("O kit " + listener.getClass().getName() + " foi registrado!");
	}

}
