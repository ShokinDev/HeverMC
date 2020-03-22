package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UnmuteCommand extends HeverCommand {

	public UnmuteCommand() {
		super("unmute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.GERENTE, true)) {
				if (args.length == 0) {
					p.sendMessage(TextComponent.fromLegacyText("§3§lMUTE §fVocê deve usar §b/unmute <nickname>"));
				} else {
					HeverPlayer targethp = PlayerLoader.getHP(args[0]);
					if (!targethp.isMuted()) {
						p.sendMessage(TextComponent.fromLegacyText("§3§lMUTE §fEste jogador não está §3§lMUTADO§f!"));
					} else {
						Commons.getManager().getBackend().getSql().delete("mutes", "name", args[0].toLowerCase());
						targethp.load();
						p.sendMessage(TextComponent
								.fromLegacyText("§3§lMUTE §fVocê §3§lDESMUTOU §b" + targethp.getName() + "§f!"));
					}
				}

			}
		} else {

		}
	}
}
