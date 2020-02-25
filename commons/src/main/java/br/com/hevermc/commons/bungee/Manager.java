package br.com.hevermc.commons.bungee;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.hevermc.commons.backend.jedis.Redis;
import br.com.hevermc.commons.backend.sql.SQLManager;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Manager {

	@Getter
	@Setter
	SQLManager SQLManager;
	@Getter
	@Setter
	Redis redis;
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
			setSQLManager(new SQLManager());
			setRedis(new Redis("191.232.247.83", 6379));

			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}

}
