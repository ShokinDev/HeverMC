package br.com.hevermc.authentication.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import br.com.hevermc.authentication.Authentication;

public class File {

	String name;
	java.io.File file;
	FileConfiguration filecfg;

	public File(String name) {
		this.name = name;
	}

	public void setup() {
		file = new java.io.File(Authentication.getInstance().getDataFolder(), name + ".yml");
		if (file.exists()) {
			file.mkdir();
		} else {
			Authentication.getManager().log("Creating files...");
			try {
				file.createNewFile();
				Authentication.getManager().log("Created!");
			} catch (Exception e) {
				Authentication.getManager().log("The files could not be created!" + e.getMessage());
			}
		}
		filecfg = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() {
		return this.filecfg;
	}

	public void save() {
		Authentication.getManager().log("Saving files...");
		try {
			filecfg.save(file);
			Authentication.getManager().log("Saved");
		} catch (Exception e) {
			Authentication.getManager().log("Couldn't save files!");

		}
	}
}
