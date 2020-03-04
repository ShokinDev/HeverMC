package br.com.hevermc.commons.bukkit;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.commons.backend.Backend;
import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.BungeeChannelApi;
import lombok.Getter;
import lombok.Setter;

public class Manager {

	@Getter @Setter
	Backend backend;
	@Getter @Setter
	BungeeChannelApi BungeeChannel;
	public HashMap<String, HeverPlayer> heverplayer = new HashMap<String, HeverPlayer>();
	public ArrayList<Player> online = new ArrayList<Player>();
	
	public void log(String log) {
		System.out.println("[COMMONS] " + log);
	}
	

	public void setup() {
		try {
			Backend.bukkit = true;
			setBackend(new Backend());
			setBungeeChannel(new BungeeChannelApi(Commons.getInstance()));
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
	

}
