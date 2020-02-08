package br.com.hevermc.commons.bukkit;

import java.util.HashMap;

import br.com.hevermc.commons.backend.sql.SQLManager;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import lombok.Getter;
import lombok.Setter;

public class Manager {
	
	@Getter @Setter
	SQLManager SQLManager;
	public HashMap<String, HeverPlayer> heverplayer = new HashMap<String, HeverPlayer>();
	
	public void log(String log) {
		System.out.println("[COMMONS] " + log);
	}
	

	public void setup() {
		try {
			setSQLManager(new SQLManager());
			
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
	

}
