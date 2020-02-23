package br.com.hevermc.commons.bukkit;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.commons.backend.jedis.Redis;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.BungeeChannelApi;
import br.com.hevermc.commons.bukkit.Commons;
import lombok.Getter;
import lombok.Setter;

public class Manager {
	
	@Getter @Setter
	Redis Redis;
	@Getter @Setter
	BungeeChannelApi BungeeChannel;
	public HashMap<String, HeverPlayer> heverplayer = new HashMap<String, HeverPlayer>();
	public ArrayList<Player> online = new ArrayList<Player>();
	
	public void log(String log) {
		System.out.println("[COMMONS] " + log);
	}
	

	public void setup() {
		try {
			setBungeeChannel(new BungeeChannelApi(Commons.getInstance()));
			setRedis(new Redis("191.232.247.83", 6379));
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
	

}
