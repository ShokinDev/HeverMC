package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.bungee.events.GeneralEvents;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class InfoCommand extends HeverCommand {

	public InfoCommand() {
		super("info");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if (isPlayer(sender)) {
			if (requiredGroup(toPlayer(sender), Groups.ADMIN, true)) {
				int a = 0;
				int b = 0;

				int c = 0;
				int d = 0;

				for (ProxiedPlayer ps : ProxyServer.getInstance().getPlayers()) {
					if (toHeverPlayer(ps).getAccountType() == 1)
						c++;
					else
						d++;

					if (!GeneralEvents.qs.contains(ps.getName()))
						b++;
					else
						a++;
				}
				sender.sendMessage(TextComponent.fromLegacyText("§aEstátisticas de jogadores no servidor: "));
				sender.sendMessage(TextComponent.fromLegacyText(""));
				sender.sendMessage(TextComponent.fromLegacyText("§a1.8+ §7- " + a));
				sender.sendMessage(TextComponent.fromLegacyText("§c1.7 §7- " + b));
				sender.sendMessage(TextComponent.fromLegacyText(""));
				sender.sendMessage(TextComponent.fromLegacyText("§aOriginal §7- " + c));
				sender.sendMessage(TextComponent.fromLegacyText("§cPirata §7- " + d));
				sender.sendMessage(TextComponent.fromLegacyText(""));
			}
		} else {
			int a = 0;
			int b = 0;

			int c = 0;
			int d = 0;

			for (ProxiedPlayer ps : ProxyServer.getInstance().getPlayers()) {
				if (toHeverPlayer(ps).getAccountType() == 1)
					c++;
				else
					d++;

				if (!GeneralEvents.qs.contains(ps.getName()))
					b++;
				else
					a++;
			}
			sender.sendMessage(TextComponent.fromLegacyText("§aEstátisticas de jogadores no servidor: "));
			sender.sendMessage(TextComponent.fromLegacyText(""));
			sender.sendMessage(TextComponent.fromLegacyText("§a1.8+ §7- " + a));
			sender.sendMessage(TextComponent.fromLegacyText("§c1.7 §7- " + b));
			sender.sendMessage(TextComponent.fromLegacyText(""));
			sender.sendMessage(TextComponent.fromLegacyText("§aOriginal §7- " + c));
			sender.sendMessage(TextComponent.fromLegacyText("§cPirata §7- " + d));
			sender.sendMessage(TextComponent.fromLegacyText(""));
		}
	}

}
