package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UnbanCommand extends HeverCommand {

	public UnbanCommand() {
		super("unban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.GERENTE, true)) {
				if (args.length == 0) {
					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/unban <nickname>"));
				} else {
					HeverPlayer targethp = PlayerLoader.getHP(args[0]);
					if (!targethp.isBanned()) {
						p.sendMessage(TextComponent.fromLegacyText("§cEsse jogador não está banido!"));
					} else {
						Commons.getManager().getBackend().getSql().delete("bans", "name", args[0].toLowerCase());
						targethp.load();
						p.sendMessage(TextComponent.fromLegacyText("§aVocê desbaniu §e" + targethp.getName() + "!"));
					}
				}

			}
		}
	}
}
