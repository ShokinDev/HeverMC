package br.com.hevermc.lobby.command;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class BuildCommand extends HeverCommand {

	public BuildCommand() {
		super("build");
	}

	public static HashMap<Player, Boolean> build = new HashMap<Player, Boolean>();

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.DIRETOR, true)) {

				if (build.containsKey(p) ? !build.get(p) : !build.containsKey(p)) {
					build.put(p, true);
					p.sendMessage("§aVocê ativou o build!");
				} else {
					build.put(p, false);
					p.sendMessage("§cVocê desativou o build!");
				}
			}

		}
		return false;
	}
}
