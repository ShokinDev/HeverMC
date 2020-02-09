package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;

public class GroupSetCommand extends HeverCommand {

	public GroupSetCommand() {
		super("groupset", Arrays.asList("setgroup"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {

		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (hasGroup(p, Groups.GERENTE, true)) {
				if (args.length < 2) {
					p.sendMessage("�aVoc� deve usar �e/groupset <nickname> <grupo>");
				} else {
					Groups togroup = Groups.getGroup(args[1]);
					HeverPlayer htarget;
					if (!(args[0].length() > 16)) {
						htarget = new PlayerLoader(args[0], "0.0.0.0").load().getHP();
					} else {
						p.sendMessage("�cNickname inv�lido!");
						return true;
					}
					if (!hp.groupIsLarger(togroup)) {
						p.sendMessage("�cEste grupo � maior ou igual ao seu!");
					} else if (togroup == null) {
						p.sendMessage("�cEste grupo n�o existe!");
					} else if (!hp.groupIsLarger(htarget.getGroup())) {
						p.sendMessage("�cEste jogador possui um cargo maior que o seu!");
					} else if (args[0].equalsIgnoreCase(p.getName())) {
						p.sendMessage("�cVoc� n�o pode alterar seu prop�o grupo!");
					} else {
						htarget.setGroup(togroup);
						if (Bukkit.getPlayer(args[0]) != null) {
							htarget.setTag(Tags.getTags(togroup));
						} else {

						}
						p.sendMessage("�aVoc� alterou o cargo de �b" + htarget.getName() + " �apara �b"
								+ htarget.getGroup().getName() + " �acom sucesso!");
					}
				}
			}
		} else {
			if (args.length < 2) {
				sender.sendMessage("�cVoc� deve usar �c�l/groupset <nickname> <grupo>");
			} else {
				Groups togroup = Groups.getGroup(args[1]);
				HeverPlayer htarget;
				if (!(args[0].length() > 16)) {
					htarget = new PlayerLoader(args[0], "0.0.0.0").load().getHP();
				} else {
					sender.sendMessage("�cNickname inv�lido!");
					return true;
				}
				if (togroup == null) {
					sender.sendMessage("�cEste grupo n�o existe!");
				} else {
					htarget.setGroup(togroup);
					sender.sendMessage("�aVoc� alterou o cargo de �b" + htarget.getName() + " �apara �b"
							+ htarget.getGroup().getName() + " �acom sucesso!");
				}
			}
		}
		return false;
	}

}
