package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UnloadCommand extends HeverCommand {

	public UnloadCommand() {
		super("unload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if (isPlayer(sender)) {
			if (requiredGroup(toPlayer(sender), Groups.ADMIN, true)) {
				if (args.length == 0) {
					sender.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/unload <nickname>!"));
				}
				ProxiedPlayer p = toPlayer(sender);
				PlayerLoader.unload(args[0].toLowerCase());
				p.sendMessage(TextComponent.fromLegacyText("§aVocê descarregou a conta de §e" + args[0] + "§a!"));
				
			}
		} else {
			if (args.length == 0) {
				sender.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/unload <nickname>!"));
			}
			PlayerLoader.unload(args[0].toLowerCase());
			sender.sendMessage(TextComponent.fromLegacyText("§aVocê descarregou a conta de §e" + args[0] + "§a!"));
		
		}
	}

}
