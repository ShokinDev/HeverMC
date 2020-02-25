package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Servers;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ServerCommand extends HeverCommand implements TabExecutor {

	public ServerCommand() {
		super("server", "connect");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!p.getServer().getInfo().getName().equals("login")
					&& !p.getServer().getInfo().getName().equals("screenshare")) {
				if (args.length == 0) {
					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve utilizar §e/server <servidor>"));
				} else {
					if (Commons.getInstance().getProxy().getServerInfo(args[0]) == null) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste servidor não existe!"));
					} else if (args[0].equalsIgnoreCase("screenshare") && !requiredGroup(p, Groups.MODGC, false)) {
					} else {
						if (Commons.getInstance().getProxy().getServerInfo(args[0]) == p.getServer().getInfo()) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê já está conectado neste servidor!"));
						} else {
							p.sendMessage(TextComponent
									.fromLegacyText("§aTe conectando até §e" + args[0].toUpperCase() + " §a!"));
							p.connect(Commons.getInstance().getProxy().getServerInfo(args[0]));

						}
					}
				}
			} else {
				p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode executar esta ação neste servidor!"));
			}
		}
	}

	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		Set<String> match = new HashSet<String>();
		if (args.length == 1) {
			String search = args[0].toLowerCase();
			for (Servers servers : Servers.values()) {
				if (servers.toString().toLowerCase().startsWith(search)) {
					match.add(servers.toString());
				}
			}
		}
		return match;
	}
}
