package br.com.hevermc.authentication;

import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.authentication.api.BungeeChannelApi;
import br.com.hevermc.authentication.api.LoginPlayer;
import lombok.Getter;
import lombok.Setter;

public class Manager {
	
	public HashMap<Player, LoginPlayer> loginplayer = new HashMap<Player, LoginPlayer>();
	@Getter @Setter
	public BungeeChannelApi BungeeChannel;
	
	public void log(String log) {
		System.out.println("[AUTHENTICATION] " + log);
	}

	public void setup() {
		try {
			setBungeeChannel(new BungeeChannelApi(Authentication.getInstance()));
			log("Initialization completed successfully!");
		} catch (Exception e) {
			log("Initialization completed unsuccessfully!!");
			e.printStackTrace();
		}
	}
}
