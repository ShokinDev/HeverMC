package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LobbyCommand extends HeverCommand {

	public LobbyCommand() {
		super("lobby", "hub");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!p.getServer().getInfo().getName().equals("login")) {
				p.connect(Commons.getInstance().getProxy().getServerInfo("lobby"));
			}
		}
	}

}
