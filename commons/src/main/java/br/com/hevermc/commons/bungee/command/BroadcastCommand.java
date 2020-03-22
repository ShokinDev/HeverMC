package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BroadcastCommand extends HeverCommand {

	public BroadcastCommand() {
		super("broadcast", "bc");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!p.getServer().getInfo().getName().equals("login")) {

				if (requiredGroup(p, Groups.ADMIN, true)) {
					if (args.length == 0) {
						p.sendMessage(TextComponent
								.fromLegacyText("§4§lBROADCAST §fVocê deve utilizar §b/broadcast <mensagem>"));
					} else {
						String message;
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < args.length; i++)
							sb.append(args[i]).append(" ");
						message = sb.toString();
						Commons.getInstance().getProxy().getPlayers().forEach(all -> {
							all.sendMessage(TextComponent.fromLegacyText(""));
							all.sendMessage(
									TextComponent.fromLegacyText("§a§lNESTY§f§lMC §7» §f" + message.replace("&", "§")));
							all.sendMessage(TextComponent.fromLegacyText(""));
						});
					}
				}
			}
		}
	}
}
