package br.com.hevermc.authentication.score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.hevermc.authentication.Authentication;

public class ScoreboardManager {

	int i = 0;

	public String effect() {
		String cor1 = "§6§l";
		String cor2 = "§e§l";
		String cor3 = "§f§l";
		if (i == 0) {
			i++;
			return cor1 + "LOGIN";
		}
		if (i == 1) {
			i++;
			return cor2 + "L" + cor1 + "OGIN";
		}
		if (i == 2) {
			i++;
			return cor3 + "L" + cor2 + "O" + cor1 + "GIN";
		}
		if (i == 3) {
			i++;
			return cor3 + "LO" + cor2 + "G" + cor1 + "IN";
		}
		if (i == 4) {
			i++;
			return cor3 + "LOG" + cor2 + "I" + cor1 + "N";
		}
		if (i == 5) {
			i++;
			return cor3 + "LOGI" + cor2 + "N";
		}
		if (i == 6) {
			i++;
			return cor3 + "LOGIN";
		}
		if (i == 7) {
			i++;
			return cor3 + "LOGI" + cor2 + "N";
		}
		if (i == 8) {
			i++;
			return cor3 + "LOG" + cor2 + "I" + cor1 + "N";
		}

		if (i == 9) {
			i++;
			return cor3 + "LO" + cor2 + "G" + cor1 + "IN";
		}
		if (i == 10) {
			i++;
			return cor3 + "L" + cor2 + "O" + cor1 + "GIN";
		}
		if (i == 11) {
			i++;
			return cor2 + "L" + cor1 + "OGIN";
		}
		if (i == 12) {
			i = 0;
			return cor1 + "LOGIN";
		}
		return cor1 + "LOGIN";
	}

	public void build(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("lobby", "score");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lHEVER§f§lMC");
		LineAdder add = new LineAdder(score, obj);

		add.addLine("  ", "§f              ", " ", 6);
		add.addLine("  ", " §fVocê está no servidor  ", " ", 5);
		add.addLine("  ", " §fde autenticação, para  ", " ", 4);
		add.addLine("  ", " §fprosseguir ao §alobby  ", " ", 3);
		add.addLine("  ", " §fsiga os passos descritos ", " ", 2);
		add.addLine("  ", " §fem seu chat! ", " ", 1);
		add.addLine("  ", " §a  ", " ", 0);
		add.addLine("", " §ahevermc", ".com.br ", -1);

		p.setScoreboard(score);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (p == null || !p.isOnline()) {
					cancel();
					return;
				}
				
				obj.setDisplayName(effect());
			}
		}.runTaskTimer(Authentication.getInstance(), 0, 3);
	}

}
