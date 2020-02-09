package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffChatCommand extends HeverCommand {
	
	public StaffChatCommand() {
		super("staffchat", "sc");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (isPlayer(sender)) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.YOUTUBERPLUS, true)) {
				if (Commons.getManager().staffchat.contains(p)) {
					Commons.getManager().staffchat.remove(p);
					p.sendMessage(new TextComponent("§cVocê saiu do §estaff-chat§c!"));
				} else {
					Commons.getManager().staffchat.add(p);
					p.sendMessage(new TextComponent("§aVocê entrou no §estaff-chat§a!"));
				}
			}
		}
	}

}
