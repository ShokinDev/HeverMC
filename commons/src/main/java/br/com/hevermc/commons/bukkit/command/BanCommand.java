package br.com.hevermc.commons.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class BanCommand extends HeverCommand {

	public BanCommand() {
		super("ban");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (hasGroup(p, Groups.TRIAL, true)) {
				if (args.length < 2) {
					p.sendMessage("§aVocê deve usar §e/ban <nickname> <razão>");
				} else {
					String nickname = args[0];
					HeverPlayer hp_t = new PlayerLoader(nickname.toLowerCase(), "0.0.0.0").load().getHP();
					if (!hp.groupIsLarger(hp_t.getGroup())) {
						p.sendMessage("§cEste jogador possui um cargo maior que o seu!");
					} else if (nickname.toLowerCase().equals(p.getName().toLowerCase())) {
						p.sendMessage("§cVocê não pode se banir!");
					} else {
						String reason = "Sem razão";
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++)
							sb.append(args[i]).append(" ");
						reason = sb.toString();
						hp_t.ban(p.getName(), reason, 0);
						p.sendMessage("§aO jogador §e" + nickname + "§a foi banido com sucesso!");
					}
				}
			}
		}
		return false;

	}
}
