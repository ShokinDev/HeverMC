package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;

public class TempGroupCommand extends HeverCommand {

	public TempGroupCommand() {
		super("tempgroup", Arrays.asList("settempgroup"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (hasGroup(p, Groups.GERENTE, true)) {
				if (args.length < 3) {
					p.sendMessage("§aVocê deve usar §e/tempgroup <nickname> <time> <grupo>");
				} else {
					Groups togroup = Groups.getGroup(args[2]);
					String time = args[1];
					HeverPlayer htarget;
					if (!(args[0].length() > 16)) {
						htarget = PlayerLoader.getHP(args[0]);
					} else {
						p.sendMessage("§cNickname inválido!");
						return true;
					}
					if (!hp.groupIsLarger(togroup)) {
						p.sendMessage("§cEste grupo é maior ou igual ao seu!");
					} else if (togroup == null) {
						p.sendMessage("§cEste grupo não existe!");
					} else if (!hp.groupIsLarger(htarget.getGroup())) {
						p.sendMessage("§cEste jogador possui um cargo maior que o seu!");
					} else if (args[0].equalsIgnoreCase(p.getName())) {
						p.sendMessage("§cVocê não pode alterar seu propío grupo!");
					} else if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
						p.sendMessage("§aVocê deve usar §e/tempgroup <nickname> <time> <grupo>");
					} else {
						int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("s", "").replace("y", ""));
						String format = time.replace(timeint + "", "");
						Calendar c = Calendar.getInstance();
						if (format.equalsIgnoreCase("s")) {
							c.add(Calendar.SECOND, timeint);
						} else if (format.equalsIgnoreCase("d")) {
							c.add(Calendar.DAY_OF_YEAR, timeint);
						} else if (format.equalsIgnoreCase("m")) {
							c.add(Calendar.MONTH, timeint);
						} else if (format.equalsIgnoreCase("y")) {
							c.add(Calendar.YEAR, timeint);
						}
						htarget.setGroup(togroup);
						htarget.setGroupExpireIn(c.getTimeInMillis());
						htarget.update();
						if (Bukkit.getPlayer(args[0]) != null) {
							htarget.setTag(Tags.getTags(togroup));
						}
						p.sendMessage("§aVocê alterou o cargo de §b" + htarget.getName() + " §apara §b"
								+ htarget.getGroup().getName() + " §acom sucesso!");
					}
				}
			}
		} else {
			if (args.length < 2) {
				sender.sendMessage("§cVocê deve usar §c§l/tempgroup <nickname> <grupo>");
			} else {
				Groups togroup = Groups.getGroup(args[1]);
				HeverPlayer htarget;
				if (!(args[0].length() > 16)) {
					htarget = PlayerLoader.getHP(args[0]);
				} else {
					sender.sendMessage("§cNickname inválido!");
					return true;
				}
				if (togroup == null) {
					sender.sendMessage("§cEste grupo não existe!");
				} else {
					htarget.setGroup(togroup);
					htarget.update();
					sender.sendMessage("§aVocê alterou o cargo de §b" + htarget.getName() + " §apara §b"
							+ htarget.getGroup().getName() + " §acom sucesso!");
				}
			}
		}
		return false;
	}

}
