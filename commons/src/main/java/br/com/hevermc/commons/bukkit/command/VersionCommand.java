package br.com.hevermc.commons.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;

public class VersionCommand extends HeverCommand {

	public VersionCommand() {
		super("vs");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.ADMIN, true)) {
				int a = 0;
				int b = 0;
				for (Player ps : Bukkit.getOnlinePlayers()) {
					@SuppressWarnings("rawtypes")
					ViaAPI api = Via.getAPI();
					if (api.getPlayerVersion(ps) < 47)
						b++;
					else
						a++;
				}
				p.sendMessage("§aEstátisticas de versões dos jogadores no servidor atual: \n§2> 47 (maior que ou 1.8): "
						+ a + "\n§c< 47 (menor que 1.8): " + b);
			}
		}
		return false;
	}
}
