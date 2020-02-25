package br.com.hevermc.pvp;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import br.com.hevermc.pvp.api.PvPPlayer;

public class Manager {
	
	public ArrayList<Player> online = new ArrayList<Player>();
	public HashMap<String, PvPPlayer> pvpplayer = new HashMap<String, PvPPlayer>();
	public boolean eventOcurring = false;
	public boolean startedEvent = false;
	public boolean joinInEvent = false;
	public boolean buildInEvent = false;
	public ArrayList<Player> inEvent = new ArrayList<Player>();
	public ArrayList<Player> specEvent = new ArrayList<Player>();
	
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
