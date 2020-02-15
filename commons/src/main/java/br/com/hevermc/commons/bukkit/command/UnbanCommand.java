package br.com.hevermc.commons.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class UnbanCommand extends HeverCommand {

	public UnbanCommand() {
		super("unban");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.GERENTE, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/unban <nickname>");
				} else {
					String nickname = args[0];
					if (!Commons.getManager().getSQLManager().checkString("hever_bans", "name",
							nickname.toLowerCase())) {
						p.sendMessage("§cEste jogador não está banido!");
					} else {
						Commons.getManager().getSQLManager().delete("hever_bans", "name", nickname.toLowerCase());
						p.sendMessage("§aO jogador §e" + nickname + "§a foi desbanido com sucesso!");
					}
				}

			}
		} else {
			if (args.length == 0) {
				sender.sendMessage("§aVocê deve usar §e/unban <nickname>");
			} else {
				String nickname = args[0];
				if (!Commons.getManager().getSQLManager().checkString("hever_bans", "name", nickname.toLowerCase())) {
					sender.sendMessage("§cEste jogador não está banido!");
				} else {
					Commons.getManager().getSQLManager().delete("hever_bans", "name", nickname.toLowerCase());
					sender.sendMessage("§aO jogador §e" + nickname + "§a foi desbanido com sucesso!");
				}
			}
		}
		return false;
	}
}
