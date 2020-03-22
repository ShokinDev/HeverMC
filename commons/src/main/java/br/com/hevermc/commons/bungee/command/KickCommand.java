package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class KickCommand extends HeverCommand implements TabExecutor {

	public KickCommand() {
		super("kick");
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 2) {
					p.sendMessage(
							TextComponent.fromLegacyText("§4§lKICK §fVocê deve utilizar §B/kick <player> <motivo>"));
				} else {
					ProxiedPlayer target = Commons.getInstance().getProxy().getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(TextComponent.fromLegacyText("§4§lKICK §fSeu alvo está §4§lOFFLINE§f!"));
					} else if (toHeverPlayer(target).getGroup().ordinal() > toHeverPlayer(p).getGroup().ordinal()) {
						p.sendMessage(
								TextComponent.fromLegacyText("§4§lKICK §fVocê §c§lNÃO §fpode kickar este jogador!"));
					} else {
						String reason;
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++)
							sb.append(args[i]).append(" ");
						reason = sb.toString();
						target.disconnect(TextComponent
								.fromLegacyText("§4§lKICK\n\n§fPor: " + reason + "\nPelo: " + p.getName()));
						p.sendMessage(TextComponent
								.fromLegacyText("§4§lKICK §fVocê kickou §b§l" + target.getName() + " §fcom sucesso!"));
						TextComponent msg_a = new TextComponent("§7§o[O jogador " + args[0] + " foi kickado]");
						msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder(
										"§cInformações:\n§fMotivo: " + reason + "\n§fPor: " + sender.getName())
												.create()));
						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t2 = PlayerLoader.getHP(players.getName());
							if (!t2.groupIsLarger(Groups.MODPLUS)) {

								players.sendMessage(msg_a);
							}
						});
					}

				}
			}
		}
	}

	public Iterable<String> onTabComplete(final CommandSender cs, final String[] args) {
		Set<String> match = new HashSet<String>();
		if (args.length == 1) {
			String search = args[0].toLowerCase();
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				if (player.getName().toLowerCase().startsWith(search)) {
					match.add(player.getName());
				}
			}
		}
		return match;
	}

}
