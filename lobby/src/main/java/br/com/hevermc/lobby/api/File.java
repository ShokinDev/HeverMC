package br.com.hevermc.lobby.api;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import br.com.hevermc.lobby.Lobby;

public class File {

	String name;
	java.io.File file;
	FileConfiguration filecfg;

	public File(String name) {
		this.name = name;
	}

	public void setup() {
		file = new java.io.File(Lobby.getInstance().getDataFolder(), name + ".yml");
		if (file.exists()) {
			file.mkdir();
		} else {
			Lobby.getManager().log("Creating files...");
			try {
				file.createNewFile();
				Lobby.getManager().log("Created!");
			} catch (Exception e) {
				Lobby.getManager().log("The files could not be created!" + e.getMessage());
			}
		}
		filecfg = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getConfig() {
		return this.filecfg;
	}

	public void save() {
		Lobby.getManager().log("Saving files...");
		try {
			filecfg.save(file);
			Lobby.getManager().log("Saved");
		} catch (Exception e) {
			Lobby.getManager().log("Couldn't save files!");

		}
	}
}
