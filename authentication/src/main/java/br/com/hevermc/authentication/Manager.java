package br.com.hevermc.authentication;

import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.authentication.api.LoginPlayer;

public class Manager {
	
	public HashMap<Player, LoginPlayer> loginplayer = new HashMap<Player, LoginPlayer>();
	
	public void log(String log) {
		System.out.println("[AUTHENTICATION] " + log);
	}

	public void setup() {
		try {

			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
}
