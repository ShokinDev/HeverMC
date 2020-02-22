package br.com.hevermc.commons.bukkit.command;

import java.util.Calendar;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class TempMuteCommand extends HeverCommand {

	public TempMuteCommand() {
		super("tempmute");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (hasGroup(p, Groups.TRIAL, true)) {
				if (args.length < 3) {
					p.sendMessage("§aVocê deve usar §e/tempmute <nickname> <time> <razão>");
				} else {
					String nickname = args[0];
					String time = args[1];
					HeverPlayer hp_t = PlayerLoader.getHP(nickname);
					if (!hp.groupIsLarger(hp_t.getGroup())) {
						p.sendMessage("§cEste jogador possui um cargo maior que o seu!");
					} else if (nickname.toLowerCase().equals(p.getName().toLowerCase())) {
						p.sendMessage("§cVocê não pode se mute!");
					} else if (!isInt(time.replace("d", "").replace("m", "").replace("y", "").replace("s", ""))) {
						p.sendMessage("§aVocê deve usar §e/tempmute <nickname> <time> <razão>");
					} else {
						int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("s", "").replace("y", ""));
						String format = time.replace(timeint + "", "");
						Calendar c = Calendar.getInstance();
						if (format.equalsIgnoreCase("s")) {
							c.add(Calendar.SECOND, timeint);
						} if (format.equalsIgnoreCase("d")) {
							c.add(Calendar.DAY_OF_YEAR, timeint);
						} else if (format.equalsIgnoreCase("m")) {
							c.add(Calendar.MONTH, timeint);
						} else if (format.equalsIgnoreCase("y")) {
							c.add(Calendar.YEAR, timeint);
						}

						String reason = "Sem razão";
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++)
							sb.append(args[i]).append(" ");
						reason = sb.toString();

						hp_t.mute(p.getName(), reason, c.getTimeInMillis());
						p.sendMessage("§aO jogador §e" + nickname + "§a foi mutado com sucesso!");
					}
				}
			}
		}
		return false;

	}
}
