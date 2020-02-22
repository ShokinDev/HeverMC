package br.com.hevermc.pvp;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.pvp.api.OneVsOne;
import br.com.hevermc.pvp.api.PvPPlayer;

public class Manager {
	
	public ArrayList<Player> online = new ArrayList<Player>();
	public HashMap<String, PvPPlayer> pvpplayer = new HashMap<String, PvPPlayer>();
	public HashMap<String, OneVsOne> ovs = new HashMap<String, OneVsOne>();
	
	public void log(String log) {
		System.out.println("[KITPVP] " + log);
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
