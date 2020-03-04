package br.com.hevermc.commons.bungee;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.hevermc.commons.backend.Backend;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Manager {

	@Setter
	@Getter
	public Backend backend;
	@Getter
	@Setter
	boolean maintenance;
	public HashMap<String, HeverPlayer> heverplayer = new HashMap<String, HeverPlayer>();
	public ArrayList<ProxiedPlayer> staffchat = new ArrayList<ProxiedPlayer>();
	public HashMap<ProxiedPlayer, ProxiedPlayer> reply = new HashMap<ProxiedPlayer, ProxiedPlayer>();

	public void log(String log) {
		System.out.println("[COMMONS] " + log);
	}

	public void setup() {
		try {
			Backend.bungee = true;
			setBackend(new Backend());
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}

}
