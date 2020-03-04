package br.com.hevermc.hardcoregames.api;

import org.bukkit.entity.Player;

import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.enums.Kits;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HGPlayer {

	String name;
	boolean invencible;
	Kits kit1 = Kits.NENHUM;
	Kits kit2 = Kits.NENHUM;
	int kills = 0;
	boolean combat = false;
	boolean adminMode = false;
	Player inCombat;
	boolean specting = false;

	public HGPlayer(String name) {
		this.name = name.toLowerCase();
	}

	public void unload() {
		try {
			HardcoreGames.getManager().log("Conta de " + name + " sendo descarregada!");
			name = null;
			invencible = false;
			adminMode = false;
			inCombat = null;
			kit1 = null;
			kit2 = null;
			HardcoreGames.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			HardcoreGames.getManager().log("Conta de " + name + " não foi descarregada!");
		}
	}
}
