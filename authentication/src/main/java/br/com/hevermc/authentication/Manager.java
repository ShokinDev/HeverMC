package br.com.hevermc.authentication;

import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.authentication.api.BungeeChannelApi;
import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.commons.backend.mysql.SQLManager;
import lombok.Getter;
import lombok.Setter;

public class Manager {

	public HashMap<Player, LoginPlayer> loginplayer = new HashMap<Player, LoginPlayer>();
	@Getter
	@Setter
	public BungeeChannelApi BungeeChannel;
	@Getter
	@Setter
	SQLManager SQLManager;

	public void log(String log) {
		System.out.println("[AUTHENTICATION] " + log);
	}

	public void setup() {
		try {
			setBungeeChannel(new BungeeChannelApi(Authentication.getInstance()));
			setSQLManager(new SQLManager());
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
}
