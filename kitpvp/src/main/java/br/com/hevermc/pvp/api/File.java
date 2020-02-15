package br.com.hevermc.pvp.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import br.com.hevermc.pvp.KitPvP;

public class File {

	String name;
	java.io.File file;
	FileConfiguration filecfg;

	public File(String name) {
		this.name = name;
	}

	public void setup() {
		file = new java.io.File(KitPvP.getInstance().getDataFolder(), name + ".yml");
		if (file.exists()) {
			file.mkdir();
		} else {
			KitPvP.getManager().log("Creating files...");
			try {
				file.createNewFile();
				KitPvP.getManager().log("Created!");
			} catch (Exception e) {
				KitPvP.getManager().log("The files could not be created!" + e.getMessage());
			}
		}
		filecfg = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() {
		return this.filecfg;
	}

	public void save() {
		KitPvP.getManager().log("Saving files...");
		try {
			filecfg.save(file);
			KitPvP.getManager().log("Saved");
		} catch (Exception e) {
			KitPvP.getManager().log("Couldn't save files!");

		}
	}
}
