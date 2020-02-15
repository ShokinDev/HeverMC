package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MaintenanceCommand extends HeverCommand {

	public MaintenanceCommand() {
		super("maintenance", "whitelist");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (!p.getServer().getInfo().getName().equals("login")) {
				if (args.length == 0) {
					if (requiredGroup(p, Groups.DIRETOR, true)) {
						Commons.getManager().setMaintenance(!Commons.getManager().isMaintenance() ? true : false);
						p.sendMessage(TextComponent.fromLegacyText("§eVocê " + (Commons.getManager().isMaintenance() ? "§aativou" : "§cdesativou") + " §ea manutenção!"));
					}
				} 
			}
		}
	}
}
