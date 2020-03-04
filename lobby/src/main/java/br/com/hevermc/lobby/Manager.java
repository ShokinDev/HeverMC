package br.com.hevermc.lobby;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Manager {
	
	public ArrayList<Player> dj = new ArrayList<Player>();
	public ArrayList<Player> hide_players = new ArrayList<Player>();
	public HashMap<Player, Integer> npc = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> npc2 = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> npc3 = new HashMap<Player, Integer>();
	public HashMap<String, Location> npc_loc = new HashMap<String, Location>();
	public HashMap<String, Location> h_loc = new HashMap<String, Location>();
	
	public void log(String log) {
		System.out.println("[LOBBY] " + log);
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
