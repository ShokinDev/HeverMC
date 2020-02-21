package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Servers;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class SendCommand extends HeverCommand implements TabExecutor {

	public SendCommand() {
		super("send");
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (requiredGroup(p, Groups.MODGC, true)) {
				if (args.length < 2) {
					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve utilizar §e/send <player> <server>"));
				} else {
					ProxiedPlayer target = Commons.getInstance().getProxy().getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(TextComponent.fromLegacyText("§cSeu alvo está offline!"));
					} else if (Commons.getInstance().getProxy().getServerInfo(args[1]) == null) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste servidor não existe!"));
					} else {
						target.connect(Commons.getInstance().getProxy().getServerInfo(args[1]));
						p.sendMessage(TextComponent.fromLegacyText("§aVocê enviou " + target.getName() + " §apara §b"
								+ Commons.getInstance().getProxy().getServerInfo(args[1]).getName().toUpperCase()
								+ " §acom sucesso!"));
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
		} else if (args.length == 2) {
			String search = args[1].toLowerCase();
			for (Servers servers : Servers.values()) {
				if (servers.toString().toLowerCase().startsWith(search)) {
					match.add(servers.toString());
				}
			}
		}
		return match;
	}

}
