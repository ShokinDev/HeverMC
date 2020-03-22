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
import br.com.hevermc.pvp.api.EventoTimer;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Warps;
import br.com.hevermc.pvp.onevsone.Eventos1v1;

public class ScoreboardManager {

	int i = 0;

	public String effect(Player p) {
		PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		String cor1 = "§2§l";
		String cor2 = "§a§l";
		String cor3 = "§f§l";
		if (pvp.getWarp() == Warps.EVENTO) {
			if (i == 0) {
				i++;
				return cor1 + "EVENTO";
			}
			if (i == 1) {
				i++;
				return cor2 + "E" + cor1 + "VENTO";
			}
			if (i == 2) {
				i++;
				return cor3 + "E" + cor2 + "V" + cor1 + "ENTO";
			}
			if (i == 3) {
				i++;
				return cor3 + "EV" + cor2 + "E" + cor1 + "NTO";
			}
			if (i == 4) {
				i++;
				return cor3 + "EVE" + cor2 + "N" + cor1 + "TO";
			}
			if (i == 5) {
				i++;
				return cor3 + "EVEN" + cor2 + "T" + cor1 + "O";
			}
			if (i == 6) {
				i++;
				return cor3 + "EVENT" + cor2 + "O";
			}
			if (i == 7) {
				i++;
				return cor3 + "EVENTO";
			}
			if (i == 8) {
				i++;
				return cor3 + "EVENT" + cor2 + "O";
			}
			if (i == 9) {
				i++;
				return cor3 + "EVEN" + cor2 + "T" + cor1 + "O";
			}
			if (i == 10) {
				i++;
				return cor3 + "EVE" + cor2 + "N" + cor1 + "TO";
			}
			if (i == 11) {
				i++;
				return cor3 + "EV" + cor2 + "E" + cor1 + "NTO";
			}
			if (i == 12) {
				i++;
				return cor3 + "E" + cor2 + "V" + cor1 + "ENTO";
			}
			if (i == 13) {
				i++;
				return cor2 + "E" + cor1 + "VENTO";
			}
			if (i == 14) {
				i = 0;
				return cor1 + "EVENTO";
			}
		} else if (pvp.getWarp() == Warps.SPAWN) {

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
		obj.setDisplayName("§a§lNESTY§f§lMC");
		PvPPlayer pvpp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		LineAdder add = new LineAdder(score, obj);
		if (pvpp.getWarp() != Warps.ONEVSONE && pvpp.getWarp() != Warps.EVENTO) {

			add.addLine("  ", "§f              ", " ", 10 );
			add.addLine(" ", "§fKills: ", "§a0", 9);
			add.addLine(" ", "§fDeaths: ", "§c0", 8);
			add.addLine(" ", "§fKillStreak: ", "§e0", 7);
			add.addLine("  ", " §a  ", "", 6);
			add.addLine(" §fRank:", "§7] ", "§fUNRANKED", 5);
			add.addLine(" ", "§fOnline: ", "§e0", 4);
			add.addLine("  ", " §d  ", "", 3);
			add.addLine(" ", "§fKit: ", "§fNenhum", 2);
			add.addLine(" ", "§fKit 2: ", "§fNenhum", 1);
			add.addLine("  ", " §e  ", "", 0);
			add.addLine("§awww.", "nestymc", ".com.br ", -1);
		} else if (pvpp.getWarp() == Warps.ONEVSONE) {
			add.addLine("  ", "§f              ", " ", 9);
			add.addLine(" ", "§fKills: ", "§a0", 8);
			add.addLine(" ", "§fDeaths: ", "§c0", 7);
			add.addLine(" ", "§fKillStreak: ", "§e0", 6);
			add.addLine("  ", " §c  ", "", 5);
			add.addLine(" ", "§fOnline: ", "§e0", 4);
			add.addLine("  ", " §c  ", "", 3);
			add.addLine(" §fJogando ", "com: ", "", 2);
			add.addLine(" - ", "§a", "Ninguém", 1);
			add.addLine("  ", " §e  ", "", 0);
			add.addLine("§awww.", "nestymc", ".com.br ", -1);
		} else if (pvpp.getWarp() == Warps.EVENTO) {
			add.addLine("  ", "§f              ", " ", 6);
			add.addLine(" ", "§fKills: ", "§a0", 5);//5,4,2,1
			add.addLine(" ", "§fTempo: ", "§e00:00", 4);
			add.addLine("  ", " §c  ", "", 3);
			add.addLine(" ", "§fJogando: ", "§e0", 2);
			add.addLine(" ", "§fStatus: ", "", 1);
			add.addLine("  ", " §e  ", "", 0);
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
		}.runTaskTimer(KitPvP.getInstance(), 0, 20);
	}

	public void updateInfos(Player p) {
		if (p == null) {
			return;
		}
		PvPPlayer pvpp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
		HeverPlayer hp = PlayerLoader.getHP(p.getName());
		Scoreboard score = p.getScoreboard();
		if (hp == null) {
			return;
		}
		hp.updateRank();
		if (pvpp.getWarp() != Warps.ONEVSONE && pvpp.getWarp() != Warps.EVENTO) {
			Team kills = score.getTeam("line9");

			kills.setSuffix("§a" + hp.getPvp_kills());

			Team deaths = score.getTeam("line8");
			deaths.setSuffix("§c" + hp.getPvp_deaths());

			Team ks = score.getTeam("line7");
			ks.setSuffix("§e" + hp.getPvp_ks());

			Team rank = score.getTeam("line5");
			rank.setPrefix(" §fRank: §7[" + hp.getRank().getColor() + hp.getRank().getSymbol());
			rank.setSuffix((hp.getRank().getColor() + hp.getRank().getName()));

			Team on = score.getTeam("line4");
			on.setSuffix("§a" + KitPvP.getManager().online.size() + "/80");

			Team kit2 = score.getTeam("line1");
			kit2.setSuffix("§a" + pvpp.getKit2().getName());
			
			Team kit = score.getTeam("line2");
			kit.setSuffix("§a" + pvpp.getKit().getName());
		} else if (pvpp.getWarp() == Warps.ONEVSONE) {
			
			Team kills = score.getTeam("line8");
			kills.setSuffix("§a" + hp.getPvp_kills());

			Team deaths = score.getTeam("line7");
			deaths.setSuffix("§c" + hp.getPvp_deaths());

			Team ks = score.getTeam("line6");
			ks.setSuffix("§e" + hp.getPvp_ks());

			Team ks2 = score.getTeam("line1");
			ks2.setSuffix((Eventos1v1.batalhando.containsKey(p) ? Eventos1v1.batalhando.get(p) : "Ninguém"));

			Team on = score.getTeam("line4");
			on.setSuffix("§a" + KitPvP.getManager().online.size() + "/80");
		} else if (pvpp.getWarp() == Warps.EVENTO) {
			//5,4,2,1

			Team kills = score.getTeam("line5");
			kills.setSuffix("§a" + (KitPvP.getManager().killsInEvent.containsKey(p) ? KitPvP.getManager().killsInEvent.get(p) : 0));

			Team deaths = score.getTeam("line4");
			deaths.setSuffix("§e" + EventoTimer.formattedTime());

			Team ks = score.getTeam("line2");
			ks.setSuffix("§e" + KitPvP.getManager().inEvent.size());

			Team ks2 = score.getTeam("line1");
			ks2.setSuffix(KitPvP.getManager().eventOcurring == true ? "§cEm andamento" : "§aIniciando...");

		}
	}

}
