package br.com.hevermc.hardcoregames.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.hardcoregames.HardcoreGames;
import br.com.hevermc.hardcoregames.api.Timer;

public class TempoCommand extends HeverCommand {

	public TempoCommand() {
		super("tempo");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (!hasGroup(p, Groups.MODPLUS, true)) {
			return true;
		}
		if (args.length == 0) {
			p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
			p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
			p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
			return true;
		} else if (args.length == 3) {
			String fazer = args[0], time = args[1], formato = args[2];
			if (!formato.equalsIgnoreCase("minutos") && !formato.equalsIgnoreCase("segundos")) {
				p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
				return true;
			}
			if (isInt(time)) {
				p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
				return true;
			}
			if (time.contains("-") || time.contains("+")) {
				p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
				p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
				return true;
			}
			int valor = Integer.valueOf(time);
			if (fazer.equalsIgnoreCase("set")) {
				if (valor < 0) {
					p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
					p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
					p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
					return true;
				}
				if (formato.equalsIgnoreCase("minutos")) {
					Timer.tempo = valor *60;
				} else {
					Timer.tempo = valor;
				}
			} else if (fazer.equalsIgnoreCase("add")) {
				if (formato.equalsIgnoreCase("minutos")) {
					Timer.tempo = Timer.tempo + (valor * 60);
				} else {
					Timer.tempo = Timer.tempo + valor;
				}
			} else if (fazer.equalsIgnoreCase("remove")) {
				int newTempo = 0;
				if (formato.equalsIgnoreCase("minutos")) {
					newTempo = Timer.tempo - (valor * 60);
				} else {
					newTempo = Timer.tempo - valor;
				}
				if (newTempo < 0)
					newTempo = 0;
				Timer.tempo = newTempo;
			}
			for (Player ons : Bukkit.getOnlinePlayers()) {
				HeverPlayer hp = PlayerLoader.getHP(ons);
				if (hp.groupIsLarger(Groups.TRIAL)) {
					p.sendMessage("§7§o[O staffer " + p.getName() + " alterou o tempo da partida!]");
				}
			}
			p.sendMessage("§eVocê mudou o §atempo§e da partida!");
			Bukkit.broadcastMessage("§aO tempo foi alterado para: " + HardcoreGames.getManager().formattedTimeForScoreboard(Timer.tempo) + "!");
		} else {
			p.sendMessage("§aVocê deve usar §e/tempo set <valor> <minutos ou segundos>");
			p.sendMessage("§aVocê deve usar §e/tempo add <valor> <minutos ou segundos>");
			p.sendMessage("§aVocê deve usar §e/tempo remove <valor> <minutos ou segundos>");
		}
		return false;
	}
}
