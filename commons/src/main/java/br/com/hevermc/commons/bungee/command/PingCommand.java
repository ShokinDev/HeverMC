package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class PingCommand extends HeverCommand implements TabExecutor {

	public PingCommand() {
		super("ping");
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if (args.length == 0) {
				sender.sendMessage(TextComponent.fromLegacyText("§aSeu ping é de §e" + player.getPing() + " §ams!"));
			} else if (args.length >= 1) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage(TextComponent.fromLegacyText("§cEste jogador se encontra offline!"));
					return;
				}
				sender.sendMessage(TextComponent.fromLegacyText(
						"§aO ping do jogador §e" + target.getName() + "§a é de §e" + target.getPing() + " §ams!"));
			}
		}
	}
	
	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		if ((args.length > 2) || (args.length == 0)) {
			return ImmutableSet.of();
		}
		Set<String> match = new HashSet<>();
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
