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
import br.com.hevermc.pvp.enums.Warps;

public class ScoreboardManager {

	int i = 0;

	public String effect(Player p) {
		PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		String cor1 = "§6§l";
		String cor2 = "§e§l";
		String cor3 = "§f§l";
		if (pvp.getWarp() == Warps.SPAWN) {

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
				i = 0;
				return cor1 + "PVP";
			}
		} else if (pvp.getWarp() == Warps.FPS) {
			if (i == 0) {
				i++;
				return cor1 + "FPS";
			}
			if (i == 1) {
				i++;
				return cor2 + "F" + cor1 + "PS";
			}
			if (i == 2) {
				i++;
				return cor3 + "F" + cor2 + "P" + cor1 + "S";
			}
			if (i == 3) {
				i++;
				return cor3 + "FP" + cor2 + "S";
			}
			if (i == 4) {
				i++;
				return cor3 + "FPS";
			}
			if (i == 5) {
				i++;
				return cor3 + "FP" + cor2 + "S";
			}
			if (i == 6) {
				i++;
				return cor3 + "F" + cor2 + "P" + cor1 + "S";
			}

			if (i == 7) {
				i++;
				return cor2 + "F" + cor1 + "PS";
			}
			if (i == 8) {
				i = 0;
				return cor1 + "FPS";
			}
		} else if (pvp.getWarp() == Warps.ONEVSONE) {
			if (i == 0) {
				i++;
				return cor1 + "1V1";
			}
			if (i == 1) {
				i++;
				return cor2 + "1" + cor1 + "V1";
			}
			if (i == 2) {
				i++;
				return cor3 + "1" + cor2 + "V" + cor1 + "1";
			}
			if (i == 3) {
				i++;
				return cor3 + "1V" + cor2 + "1";
			}
			if (i == 4) {
				i++;
				return cor3 + "1V1";
			}
			if (i == 5) {
				i++;
				return cor3 + "1V" + cor2 + "1";
			}
			if (i == 6) {
				i++;
				return cor3 + "1" + cor2 + "V" + cor1 + "1";
			}

			if (i == 7) {
				i++;
				return cor2 + "1" + cor1 + "V1";
			}
			if (i == 8) {
				i = 0;
				return cor1 + "1V1";
			}
		} else if (pvp.getWarp() == Warps.LAVA) {
			if (i == 0) {
				i++;
				return cor1 + "LAVA";
			}
			if (i == 1) {
				i++;
				return cor2 + "L" + cor1 + "AVA";
			}
			if (i == 2) {
				i++;
				return cor3 + "L" + cor2 + "A" + cor1 + "VA";
			}
			if (i == 3) {
				i++;
				return cor3 + "LA" + cor2 + "V" + cor1 + "A";
			}
			if (i == 4) {
				i++;
				return cor3 + "LAV" + cor2 + "A";
			}
			if (i == 5) {
				i++;
				return cor3 + "LAVA";
			}
			if (i == 6) {
				i++;
				return cor3 + "LAV" + cor2 + "A";
			}
			if (i == 7) {
				i++;
				return cor3 + "LA" + cor2 + "V" + cor1 + "A";
			}
			if (i == 8) {
				i++;
				return cor3 + "L" + cor2 + "A" + cor1 + "VA";
			}
			if (i == 9) {
				i++;
				return cor2 + "L" + cor1 + "AVA";
			}
			if (i == 10) {
				i = 0;
				return cor1 + "LAVA";
			}

		}
		i = 0;
		return "PVP";
	}

	public void build(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("pvp", "score");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lHEVER§f§lMC");
		PvPPlayer pvpp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		LineAdder add = new LineAdder(score, obj);
		if (pvpp.getWarp() != Warps.ONEVSONE) {

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
		} else {// 6,5,4,2,1
			add.addLine("  ", "§f              ", " ", 7);
			add.addLine(" ", "§fWins: ", "§a0", 6);
			add.addLine(" ", "§fLoses: ", "§c0", 5);
			add.addLine(" ", "§fWinStreak: ", "§e0", 4);
			add.addLine("  ", " §a  ", "", 3);
			add.addLine(" ", "§fRank: ", "§fUNRANKED", 2);
			add.addLine(" ", "§fOnline: ", "§e0", 1);
			add.addLine("  ", " §e  ", "", 0);
			add.addLine("§awww.", "hevermc", ".com.br ", -1);
		}
		p.setScoreboard(score);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (p == null || !p.isOnline()) {
					cancel();
					return;
				}
				obj.setDisplayName(effect(p));
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 3);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (p == null || !p.isOnline()) {
					cancel();
					return;
				}
				updateInfos(p);
			}
		}.runTaskTimer(KitPvP.getInstance(), 0, 100);
	}

	public void updateInfos(Player p) {
		PvPPlayer pvpp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		HeverPlayer hp = PlayerLoader.getHP(p);
		Scoreboard score = p.getScoreboard();
		if (hp == null || pvpp == null || score == null) {
			return;
		}

		Team kills = score.getTeam("line8");
		kills.setSuffix("§a" + hp.getPvp_kills());

		Team deaths = score.getTeam("line7");
		deaths.setSuffix("§c" + hp.getPvp_deaths());

		Team ks = score.getTeam("line6");
		ks.setSuffix("§e" + hp.getPvp_ks());

		Team rank = score.getTeam("line4");
		rank.setSuffix((hp.getRank().getColor() + hp.getRank().getName()).substring(1));

		Team on = score.getTeam("line3");
		on.setSuffix("§a" + KitPvP.getManager().online.size() + "/80");

		Team kit = score.getTeam("line1");
		kit.setSuffix("§a" + pvpp.getKit().getName());

	}

}
