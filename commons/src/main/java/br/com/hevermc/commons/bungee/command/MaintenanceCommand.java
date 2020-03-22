package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
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
						p.sendMessage(TextComponent.fromLegacyText("§4§lWHITELIST §fVocê "
								+ (Commons.getManager().isMaintenance() ? "§a§lATIVOU" : "§c§lDESATIVOU")
								+ " §fa manutenção!"));
						ProxyServer.getInstance().getPlayers().forEach(ps -> {
							if (!toHeverPlayer(ps).groupIsLarger(Groups.TRIAL)) {
								ps.disconnect(TextComponent.fromLegacyText(
										"§4§lWHITELIST\n\n§fEstamos em manutenção, tente novamente mais tarde!"
												+ "\n\n§fEntre em nosso §3§lDISCORD§f!\n§ehttps://discord.gg/VgbDwqS"));
							}
						});
					}
				}
			}
		}
	}
}
