package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LobbyCommand extends HeverCommand {

	public LobbyCommand() {
		super("lobby", "l");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!p.getServer().getInfo().getName().equals("login")) {
				if (!p.getServer().getInfo().getName().equals("screenshare")
						&& !requiredGroup(p, Groups.MODGC, false)) {
					p.sendMessage(TextComponent
							.fromLegacyText("§b§lCONNECT §fVocê não pode §c§lUTILIZAR§f este comando aqui!"));

				}
				p.connect(Commons.getInstance().getProxy().getServerInfo("lobby"));
				p.sendMessage(TextComponent.fromLegacyText("§b§lCONNECT §fVocê está sendo conectado ao §b§lLOBBY§f!"));
			} else {
				p.sendMessage(
						TextComponent.fromLegacyText("§b§lCONNECT §fVocê não pode §c§lUTILIZAR§f este comando aqui!"));
			}
		}
	}

}
