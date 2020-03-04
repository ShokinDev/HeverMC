package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Tags;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffListCommand extends HeverCommand {

	public StaffListCommand() {
		super("stafflist", "sl");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (isPlayer(sender)) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.MOD, true)) {
				p.sendMessage(TextComponent.fromLegacyText("§aLista de staffers online: "));
				Commons.getInstance().getProxy().getPlayers().forEach(ps -> {
					HeverPlayer hps = PlayerLoader.getHP(ps.getName());
					if (hps.groupIsLarger(Groups.TRIAL)) {
						p.sendMessage(TextComponent.fromLegacyText(
								Tags.getTags(hps.getGroup()).getPrefix() + " " + Tags.getTags(hps.getGroup()).getColor()
										+ ps.getName() + " §7- §a" + ps.getServer().getInfo().getName().toUpperCase() + "§"));
					}
				});
			}
		}
	}
}
