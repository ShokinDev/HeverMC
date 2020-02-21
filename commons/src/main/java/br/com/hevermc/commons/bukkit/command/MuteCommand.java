package br.com.hevermc.commons.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class MuteCommand extends HeverCommand {

	public MuteCommand() {
		super("mute");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (hasGroup(p, Groups.TRIAL, true)) {
				if (args.length < 2) {
					p.sendMessage("§aVocê deve usar §e/mute <nickname> <razão>");
				} else {
					String nickname = args[0];
					HeverPlayer hp_t = PlayerLoader.getHP(nickname);
					if (!hp.groupIsLarger(hp_t.getGroup())) {
						p.sendMessage("§cEste jogador possui um cargo maior que o seu!");
					} else if (nickname.toLowerCase().equals(p.getName().toLowerCase())) {
						p.sendMessage("§cVocê não pode se mutar!");
					} else {
						String reason = "Sem razão";
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++)
							sb.append(args[i]).append(" ");
						reason = sb.toString();
						hp_t.mute(p.getName(), reason, 0);
						p.sendMessage("§aO jogador §e" + nickname + "§a foi mutado com sucesso!");
					}
				}
			}
		}
		return false;

	}
}
