package br.com.hevermc.commons.backend;

import br.com.hevermc.commons.backend.jedis.Redis;
import br.com.hevermc.commons.backend.mysql.SQLManager;

public class Backend {

	public static boolean bukkit = false;
	public static boolean bungee = false;

	Redis redis;
	SQLManager sql;

	private void log(String msg) {

		System.out.println("[BACKEND] " + msg);
	}

	public Backend() {
		if (bukkit == true) {
			try {
				redis = new Redis("191.232.247.83", 6379);
				log("Redis conectado!");
			} catch (Exception e) {
				log("Redis não conectado!");
			}
		} else if (bungee = true) {
			try {
				sql = new SQLManager();
				redis = new Redis("191.232.247.83", 6379);
				log("Redis & SQL conectados!");
			} catch (Exception e) {
				log("Redis & SQL não conectados!");
			}
		}
	}

	public Redis getRedis() {
		return redis;
	}

	public SQLManager getSql() {
		return sql;
	}

}
