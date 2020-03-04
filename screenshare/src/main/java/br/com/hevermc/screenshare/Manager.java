package br.com.hevermc.screenshare;

public class Manager {
	
	public void log(String log) {
		System.out.println("[SCREENSHARE] " + log);
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
