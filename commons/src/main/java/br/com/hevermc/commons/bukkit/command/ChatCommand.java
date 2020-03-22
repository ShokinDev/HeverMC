package br.com.hevermc.commons.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class ChatCommand extends HeverCommand {

	public ChatCommand() {
		super("chat");
	}

	public static boolean chat = true;

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.TRIAL, true)) {
				if (args.length == 0) {
					p.sendMessage("§6§lCHAT §fVocê deve utilizar §b/chat <on|off>");
				} else if (args[0].equalsIgnoreCase("on")) {
					if (chat) {
						p.sendMessage("§6§lCHAT §fO chat já está §a§lATIVADO§f!");
					} else {
						p.sendMessage("§6§lCHAT §fVocê §a§lATIVOU §fo chat!");
						chat = true;
					}

				} else if (args[0].equalsIgnoreCase("off")) {
					if (!chat) {
						p.sendMessage("§6§lCHAT §fO chat já está §4§lDEASATIVADO§f!");
					} else {
						p.sendMessage("§6§lCHAT §fVocê §4§lDESATIVOU §fo chat!");
						chat = false;
					}

				}
			}
		}
		return false;
	}

}
