package br.com.hevermc.screenshare.score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.screenshare.Screenshare;

public class ScoreboardManager {

	int i = 0;

	public String effect() {
		String cor1 = "§b§l";
		String cor2 = "§1§l";
		String cor3 = "§f§l";
		if (i == 0) {
			i++;
			return cor1 + "SCREENSHARE";
		}
		if (i == 1) {
			i++;
			return cor2 + "S" + cor1 + "CREENSHARE";
		}
		if (i == 2){
			i++;
			return cor3 + "S" + cor2 + "C" + cor1 + "REENSHARE";
		}
		if (i == 3) {
			i++;
			return cor3 + "SC" + cor2 + "R" + cor1 + "EENSHARE";
		}
		if (i == 4) {
			i++;
			return cor3 + "SCR" + cor2 + "E" + cor1 + "ENSHARE";
		}
		if (i == 5) {
			i++;
			return cor3 + "SCRE" + cor2 + "E" + cor1 + "NSHARE";
		}
		if (i == 6) {
			i++;
			return cor3 + "SCREE" + cor2 + "N" + cor1 + "SHARE";
		}
		if (i == 7) {
			i++;
			return cor3 + "SCREEN" + cor2 + "S" + cor1 + "HARE";
		}
		if (i == 8) {
			i++;
			return cor3 + "SCREENS" + cor2 + "H" + cor1 + "ARE";
		}
		if (i == 9) {
			i++;
			return cor3 + "SCREENSH" + cor2 + "A" + cor1 + "RE";
		}
		if (i == 10) {
			i++;
			return cor3 + "SCREENSHA" + cor2 + "R" + cor1 + "E";
		}
		if (i == 11) {
			i++;
			return cor3 + "SCREENSHAR" + cor2 + "E";
		}
		if (i == 12) {
			i++;
			return cor3 + "SCREENSHARE";
		}
		if (i == 13) {
			i++;
			return cor3 + "SCREENSHAR" + cor2 + "E";
		}
		if (i == 14) {
			i++;
			return cor3 + "SCREENSHA" + cor2 + "R" + cor1 + "E";
		}
		if (i == 15) {
			i++;
			return cor3 + "SCREENSH" + cor2 + "A" + cor1 + "RE";
		}
		if (i == 16) {
			i++;
			return cor3 + "SCREENS" + cor2 + "H" + cor1 + "ARE";
		}
		if (i == 17) {
			i++;
			return cor3 + "SCREEN" + cor2 + "S" + cor1 + "HARE";
		}
		if (i == 18) {
			i++;
			return cor3 + "SCRE" + cor2 + "E" + cor1 + "NSHARE";
		}
		if (i == 19) {
			i++;
			return cor3 + "SCR" + cor2 + "E" + cor1 + "ENSHARE";
		}
		if (i == 20) {
			i++;
			return cor3 + "SC" + cor2 + "R" + cor1 + "EENSHARE";
		}
		if (i == 21) {
			i++;
			return cor3 + "S" + cor2 + "C" + cor1 + "REENSHARE";
		}
		if (i == 22) {
			i++;
			return cor2 + "S" + cor1 + "CREENSHARE";
		}
		if (i == 23) {
			i = 0;
			return cor1 + "SCREENSHARE";
		}
		i = 0;
		return cor1 + "SCREENSHARE";
	}

	public void build(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("lobby", "score");
		HeverPlayer hp = PlayerLoader.getHP(p);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§a§lNESTY§f§lMC");
		LineAdder add = new LineAdder(score, obj);
		if (!hp.groupIsLarger(Groups.MODGC)) {
			add.addLine("  ", "§f              ", " ", 6);
			add.addLine("  ", " §fVocê está no servidor  ", " ", 5);
			add.addLine("  ", " §fde screenshare, para  ", " ", 4);
			add.addLine("  ", " §fvoltar ao §alobby  ", " ", 3);
			add.addLine("  ", " §fsiga os passos descritos ", " ", 2);
			add.addLine("  ", " §fpelos membros da equipe! ", " ", 1);
			add.addLine("  ", " §a  ", " ", 0);
			add.addLine("§awww.", "hevermc", ".com.br ", -1);
		} else {
			add.addLine("  ", "§f              ", " ", 3);
			add.addLine("  ", " §fVocê está no servidor  ", " ", 2);
			add.addLine("  ", " §fde screenshare!  ", " ", 1);
			add.addLine("  ", " §a  ", " ", 0);
			add.addLine("§awww.", "nestymc", ".com.br ", -1);
		}

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
		}.runTaskTimer(Screenshare.getInstance(), 0, 3);
	}

}
