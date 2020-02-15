package br.com.hevermc.pvp.score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PvPPlayer;

public class ScoreboardManager {

	int i = 0;

	public String effect() {
		String cor1 = "§6§l";
		String cor2 = "§e§l";
		String cor3 = "§f§l";
		if (i == 0) {
			i++;
			return cor1 + "PVP";
		}
		if (i == 1) {
			i++;
			return cor2 + "P" + cor1 + "VP";
		}
		if (i == 2) {
			i++;
			return cor3 + "P" + cor2 + "V" + cor1 + "P";
		}
		if (i == 3) {
			i++;
			return cor3 + "PV" + cor2 + "P";
		}
		if (i == 4) {
			i++;
			return cor3 + "PVP";
		}
		if (i == 5) {
			i++;
			return cor3 + "PV" + cor2 + "P";
		}
		if (i == 6) {
			i++;
			return cor3 + "P" + cor2 + "V" + cor1 + "P";
		}

		if (i == 7) {
			i++;
			return cor2 + "P" + cor1 + "VP";
		}
		if (i == 8) {
			i=0;
			return cor1 + "PVP";
		}

		return "PVP";
	}

	public void build(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("pvp", "score");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lHEVER§f§lMC");
		LineAdder add = new LineAdder(score, obj);

		add.addLine("  ", "§f              ", " ", 9);
		add.addLine(" ", "§fKills: ", "§a0", 8);
		add.addLine(" ", "§fDeaths: ", "§c0", 7);
		add.addLine(" ", "§fKillStreak: ", "§e0", 6);
		add.addLine("  ", " §a  ", "", 5);
		add.addLine(" ", "§fRank: ", "§fUNRANKED", 4);
		add.addLine(" ", "§fOnline: ", "§e0", 3);
		add.addLine("  ", " §d  ", "", 2);
		add.addLine(" ", "§fKit: ", "§fNenhum", 1);
		add.addLine("  ", " §e  ", "", 0);
		add.addLine("§awww.", "hevermc", ".com.br ", -1);

		p.setScoreboard(score);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (p == null || !p.isOnline()) {
					cancel();
					return;
				}

				obj.setDisplayName(effect());
				updateInfos(p);
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 3);
	}

	public void updateInfos(Player p) {
		PvPPlayer pvpp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		HeverPlayer hp = new PlayerLoader(p).load().getHP();
		Scoreboard score = p.getScoreboard();

		Team kills = score.getTeam("line7");
		kills.setSuffix("§a" + pvpp.getKills());
		
		Team deaths = score.getTeam("line6");
		deaths.setSuffix("§c" + pvpp.getDeaths());
		
		Team ks = score.getTeam("line5");
		ks.setSuffix("§e" + pvpp.getKs());
		
		Team rank = score.getTeam("line3");
		rank.setSuffix(hp.getRank().getColor() + hp.getRank().toString());
		
		Team kit = score.getTeam("line3");
		kit.setSuffix("§a" + pvpp.getKit().getName());


	}

}
