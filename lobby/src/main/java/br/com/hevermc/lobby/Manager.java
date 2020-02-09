package br.com.hevermc.lobby;

public class Manager {

	
	public void log(String log) {
		System.out.println("[AUTHENTICATION] " + log);
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
