package br.com.hevermc.hardcoregames.score;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.api.HGPlayer;
import br.com.hevermc.hardcoregames.api.HGPlayerLoader;
import br.com.hevermc.hardcoregames.api.Timer;
import br.com.hevermc.hardcoregames.enums.States;

public class ScoreboardManager {

	int i = 0;

	public String effect(Player p) {
		String cor1 = "§6§l";
		String cor2 = "§e§l";
		String cor3 = "§f§l";
		if (i == 0) {
			i++;
			return cor1 + "HARDCOREGAMES";
		}
		if (i == 1) {
			i++;
			return cor2 + "H" + cor1 + "ARDCOREGAMES";
		}
		if (i == 2) {
			i++;
			return cor3 + "H" + cor2 + "A" + cor1 + "RDCOREGAMES";
		}
		if (i == 3) {
			i++;
			return cor3 + "HA" + cor2 + "R" + cor1 + "DCOREGAMES";
		}
		if (i == 4) {
			i++;
			return cor3 + "HAR" + cor2 + "E" + cor1 + "COREGAMES";
		}
		if (i == 5) {
			i++;
			return cor3 + "HARD" + cor2 + "C" + cor1 + "OREGAMES";
		}
		if (i == 6) {
			i++;
			return cor3 + "HARDC" + cor2 + "O" + cor1 + "REGAMES";
		}
		if (i == 7) {
			i++;
			return cor3 + "HARDCO" + cor2 + "R" + cor1 + "EGAMES";
		}
		if (i == 8) {
			i++;
			return cor3 + "HARDCOR" + cor2 + "E" + cor1 + "GAMES";
		}
		if (i == 9) {
			i++;
			return cor3 + "HARDCORE" + cor2 + "G" + cor1 + "AMES";
		}
		if (i == 10) {
			i++;
			return cor3 + "HARDCOREG" + cor2 + "A" + cor1 + "MES";
		}
		if (i == 11) {
			i++;
			return cor3 + "HARDCOREGA" + cor2 + "M" + cor1 + "ES";
		}
		if (i == 12) {
			i++;
			return cor3 + "HARDCOREGAM" + cor2 + "E" + cor1 + "S";
		}
		if (i == 13) {
			i++;
			return cor3 + "HARDCOREGAME" + cor2 + "S";
		}
		if (i == 14) {
			i++;
			return cor3 + "HARDCOREGAMES";
		}
		if (i == 15) {
			i++;
			return cor3 + "HARDCOREGAME" + cor2 + "S";
		}
		if (i == 16) {
			i++;
			return cor3 + "HARDCOREGAM" + cor2 + "E" + cor1 + "S";
		}
		if (i == 17) {
			i++;
			return cor3 + "HARDCOREGA" + cor2 + "M" + cor1 + "ES";
		}
		if (i == 18) {
			i++;
			return cor3 + "HARDCOREG" + cor2 + "A" + cor1 + "MES";
		}
		if (i == 19) {
			i++;
			return cor3 + "HARDCORE" + cor2 + "G" + cor1 + "AMES";
		}
		if (i == 20) {
			i++;
			return cor3 + "HARDCOR" + cor2 + "E" + cor1 + "GAMES";
		}
		if (i == 21) {
			i++;
			return cor3 + "HARDCO" + cor2 + "R" + cor1 + "EGAMES";
		}
		if (i == 22) {
			i++;
			return cor3 + "HARDC" + cor2 + "O" + cor1 + "REGAMES";
		}
		if (i == 23) {
			i++;
			return cor3 + "HARD" + cor2 + "C" + cor1 + "OREGAMES";
		}
		if (i == 24) {
			i++;
			return cor3 + "HAR" + cor2 + "D" + cor1 + "COREGAMES";
		}
		if (i == 25) {
			i++;
			return cor3 + "HA" + cor2 + "R" + cor1 + "DCOREGAMES";
		}
		if (i == 26) {
			i++;
			return cor2 + "H" + cor1 + "ARDCOREGAMES";
		}
		if (i == 27) {
			i=0;
			return cor3 + "HARDCOREGAMES";
		}
		i = 0;
		return "HARDCOREGAMES";
	}

	public void build(Player p) {
		Scoreboard score = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = score.registerNewObjective("pvp", "score");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lHEVER§f§lMC");
		LineAdder add = new LineAdder(score, obj);
		
		if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
			
			add.addLine(" ", "§1 ", " ", 6);
			add.addLine(" ", "§fKit1: ", "§aNenhum", 5);
			add.addLine(" ", "§fKit2: ", "§aNenhum", 4);
			add.addLine(" ", "§2  ", " ", 3);
			add.addLine(" ", "§fIniciando em: ", "§a0", 2);
			add.addLine(" ", "§fJogadores: ", "§a0", 1);
			add.addLine("  ", "§4    ", "", 0);
			add.addLine("§awww.", "hevermc", ".com.br ", -1);
			
		} else if (HardcoreGames.getManager().getStateGame() == States.INVENCIVEL) {
			
			add.addLine(" ", "§1 ", " ", 6);
			add.addLine(" ", "§fKit1: ", "§aNenhum", 5);
			add.addLine(" ", "§fKit2: ", "§aNenhum", 4);
			add.addLine(" ", "§2  ", " ", 3);
			add.addLine(" §fInv", "encibilidade: ", "§a0", 2);
			add.addLine(" ", "§fJogadores: ", "§a0", 1);
			add.addLine("  ", "§4    ", "", 0);
			add.addLine("§awww.", "hevermc", ".com.br ", -1);
			
		} else if (HardcoreGames.getManager().getStateGame() == States.ANDAMENTO) {
			
			add.addLine(" ", "§1 ", " ", 8);
			add.addLine(" ", "§fKit1: ", "§aNenhum", 7);
			add.addLine(" ", "§fKit2: ", "§aNenhum", 6);
			add.addLine(" ", "§1 ", " ", 5);
			add.addLine(" ", "§fKills: ", "§a0", 4);
			add.addLine(" ", "§2  ", " ", 3);
			add.addLine(" ", "§fTempo: ", "§a0", 2);
			add.addLine(" ", "§fJogadores: ", "§a0", 1);
			add.addLine("  ", "§4    ", "", 0);
			add.addLine("§awww.", "hevermc", ".com.br ", -1);
			
		} else if (HardcoreGames.getManager().getStateGame() == States.FINALIZANDO) {
			
			add.addLine(" ", "§1 ", " ", 9);
			add.addLine(" ", "§fKit1: ", "§aNenhum", 8);
			add.addLine(" ", "§fKit2: ", "§aNenhum", 7);
			add.addLine(" ", "§1 ", " ", 6);
			add.addLine(" ", "§fKills: ", "§a0", 5);
			add.addLine(" ", "§fGanhador: ", "§a" + HardcoreGames.getManager().getWinner(), 4);
			add.addLine(" ", "§2  ", " ", 3);
			add.addLine(" ", "§fTempo: ", "§a0", 2);
			add.addLine(" ", "§fJogadores: ", "§a0", 1);
			add.addLine("  ", "§4    ", "", 0);
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
		}.runTaskTimer(HardcoreGames.getInstance(), 0, 3);

		new BukkitRunnable() {

			@Override
			public void run() {
				if (p == null || !p.isOnline()) {
					cancel();
					return;
				}
				updateInfos(p);
			}
		}.runTaskTimer(HardcoreGames.getInstance(), 0, 20);
	}

	public void updateInfos(Player p) {
		if (p == null) {
			return;
		}
		HeverPlayer hp = PlayerLoader.getHP(p);
		HGPlayer hg = new HGPlayerLoader(p).load().getHGP();
		Scoreboard score = p.getScoreboard(); 
		if (hp == null) {
			return;
		}
		hp.updateRank();
		if (HardcoreGames.getManager().getStateGame() == States.PREJOGO) {
			
			Team kit1 = score.getTeam("line5");
			kit1.setSuffix("§a" + hg.getKit1().getName());
			
			Team kit2 = score.getTeam("line4");
			kit2.setSuffix("§a" +   hg.getKit2().getName());
			
			Team tempo = score.getTeam("line2");
			tempo.setSuffix("§a" + HardcoreGames.getManager().formattedTimeForScoreboard(Timer.tempo));
			
			Team on = score.getTeam("line1");
			on.setSuffix("§a" + HardcoreGames.getManager().inGame.size());
			
		} else if (HardcoreGames.getManager().getStateGame() == States.INVENCIVEL) {
			
			Team kit1 = score.getTeam("line5");
			kit1.setSuffix("§a" + hg.getKit1().getName());
			
			Team kit2 = score.getTeam("line4");
			kit2.setSuffix("§a" +   hg.getKit2().getName());
			
			Team tempo = score.getTeam("line2");
			tempo.setSuffix("§a" + HardcoreGames.getManager().formattedTimeForScoreboard(Timer.tempo));
			
			Team on = score.getTeam("line1");
			on.setSuffix("§a" + HardcoreGames.getManager().inGame.size());
			
		} else if (HardcoreGames.getManager().getStateGame() == States.ANDAMENTO) {
			
			Team kit1 = score.getTeam("line7");
			kit1.setSuffix("§a" + hg.getKit1().getName());
			
			Team kit2 = score.getTeam("line6");
			kit2.setSuffix("§a" +  hg.getKit2().getName());
			
			Team kills = score.getTeam("line4");
			kills.setSuffix("§e" + hg.getKills());
			
			Team tempo = score.getTeam("line2");
			tempo.setSuffix("§a" + HardcoreGames.getManager().formattedTimeForScoreboard(Timer.tempo));
			
			Team on = score.getTeam("line1");
			on.setSuffix("§a" + HardcoreGames.getManager().inGame.size());
			
		} else if (HardcoreGames.getManager().getStateGame() == States.FINALIZANDO) {
			
			Team kit1 = score.getTeam("line7");
			kit1.setSuffix("§a" + hg.getKit1().getName());
			
			Team kit2 = score.getTeam("line6");
			kit2.setSuffix("§a" +  hg.getKit2().getName());
			
			Team kills = score.getTeam("line5");
			kills.setSuffix("§e" + hg.getKills());
			
			Team tempo = score.getTeam("line2");
			tempo.setSuffix("§a" + HardcoreGames.getManager().formattedTimeForScoreboard(Timer.tempo));
			
			Team on = score.getTeam("line1");
			on.setSuffix("§a" + HardcoreGames.getManager().inGame.size());

		}
	}

}
