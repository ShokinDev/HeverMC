package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Ranks;

public class RankCommand extends HeverCommand {

	public RankCommand() {
		super("rank", Arrays.asList("liga"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			p.sendMessage("§c§lRANK §fOs ranks do servidor são os seguintes:");
			for (Ranks rank : Ranks.values()) {
				p.sendMessage(rank.getColor() + rank.getSymbol() + " " + rank.getName() + " - " + rank.getXp() + "XP");
			}
			p.sendMessage("§fSeu rank: §e" + hp.getRank().getName());
			p.sendMessage("§aSeu proximo rank é "
					+ (Ranks.getRank(hp.getRank().ordinal() + 1) == null ? "Nenhum"
							: Ranks.getRank(hp.getRank().ordinal() + 1).getName())
					+ " §ae faltam §e" + (Ranks.getRank(hp.getRank().ordinal() + 1).getXp() - hp.getXp()) + "XPS!");
		}
		return false;
	}

}
