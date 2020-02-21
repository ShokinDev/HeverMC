package br.com.hevermc.commons.bukkit.command;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class UnmuteCommand extends HeverCommand {

	public UnmuteCommand() {
		super("umute");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.GERENTE, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/umute <nickname>");
				} else {
					String nickname = args[0];
					if (!Commons.getManager().getSQLManager().checkString("hever_mutes", "name",
							nickname.toLowerCase())) {
						p.sendMessage("§cEste jogador não está mutado!");
					} else {
						Commons.getManager().getSQLManager().delete("hever_mutes", "name", nickname.toLowerCase());
						p.sendMessage("§aO jogador §e" + nickname + "§a foi desmutado com sucesso!");
						if (Bukkit.getOnlinePlayers().size() != 0) {
							int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
							Commons.getManager().getBungeeChannel().sendPluginMessage(Commons.getManager().online.get(random),
									"messageUnban(" + nickname + ")", "BungeeCord");
						}
					}
				}

			}
		} else {
			if (args.length == 0) {
				sender.sendMessage("§aVocê deve usar §e/umute <nickname>");
			} else {
				String nickname = args[0];
				if (!Commons.getManager().getSQLManager().checkString("hever_mutes", "name", nickname.toLowerCase())) {
					sender.sendMessage("§cEste jogador não está banido!");
				} else {
					Commons.getManager().getSQLManager().delete("hever_mutes", "name", nickname.toLowerCase());
					sender.sendMessage("§aO jogador §e" + nickname + "§a foi desmutado com sucesso!");
					if (Bukkit.getOnlinePlayers().size() != 0) {
						int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
						Commons.getManager().getBungeeChannel().sendPluginMessage(Commons.getManager().online.get(random),
								"messageUnban(" + nickname + ")", "BungeeCord");
					}
				}
			}
		}
		return false;
	}
}
