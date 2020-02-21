package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ScreenShareCommand extends HeverCommand implements TabExecutor {

	public ScreenShareCommand() {
		super("screenshare", "ss");
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (requiredGroup(p, Groups.MODGC, true)) {
				if (args.length < 2) {
					p.sendMessage(TextComponent.fromLegacyText("�aVoc� deve utilizar �e/ss <player>"));
				} else {
					ProxiedPlayer target = Commons.getInstance().getProxy().getPlayer(args[0]);
					if (target == null) {
						p.sendMessage(TextComponent.fromLegacyText("�cSeu alvo est� offline!"));
					} else if (Commons.getInstance().getProxy().getServerInfo("ss") == null) {
						p.sendMessage(TextComponent.fromLegacyText("�cEste servidor n�o existe!"));
					} else {
						target.connect(Commons.getInstance().getProxy().getServerInfo("ss"));
						p.sendMessage(TextComponent.fromLegacyText("�aVoc� enviou " + target.getName() + " �apara �b"
								+ Commons.getInstance().getProxy().getServerInfo("ss").getName().toUpperCase()
								+ " �acom sucesso!"));
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
