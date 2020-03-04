package br.com.hevermc.commons.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class ServerInfoCommand extends HeverCommand {

	public ServerInfoCommand() {
		super("serverinfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.ADMIN, true)) {
				int a = 0;
				int b = 0;

				int c = 0;
				int d = 0;

				for (Player ps : Bukkit.getOnlinePlayers()) {
					if (toHeverPlayer(ps).getAccountType() == 1)
						c++;
					else
						d++;

					@SuppressWarnings("rawtypes")
					ViaAPI api = Via.getAPI();
					if (api.getPlayerVersion(ps) < 47)
						b++;
					else
						a++;
				}
				p.sendMessage("§aEstátisticas de jogadores no servidor atual: ");
				p.sendMessage("");
				p.sendMessage("§a1.8+ - §7" + a);
				p.sendMessage("§c1.8- - §7" + b);
				p.sendMessage("");
				p.sendMessage("§aOriginal - §7" + c);
				p.sendMessage("§cPirata - §7" + d);
				p.sendMessage("");
			}
		}
		return false;
	}
}
