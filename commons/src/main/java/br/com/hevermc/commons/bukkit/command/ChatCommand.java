package br.com.hevermc.commons.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class ChatCommand extends HeverCommand {

	public ChatCommand() {
		super("chat");
	}

	public boolean chat = true;

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.TRIAL, true)) {
				if (args.length == 0) {
					p.sendMessage("�aVoc� deve usar �e/chat <on|off>");
				} else if (args[0].equalsIgnoreCase("on")) {
					if (chat) {
						p.sendMessage("�cO chat j� est� ativado!");
					} else {
						p.sendMessage("�aVoc� �bativou �fo chat!");
						chat = true;
					}

				} else if (args[0].equalsIgnoreCase("off")) {
					if (!chat) {
						p.sendMessage("�cO chat j� est� desativado!");
					} else {
						p.sendMessage("�aVoc� �cdesativou �ao chat!");
						chat = false;
					}

				}
			}
		}
		return false;
	}

}
