package br.com.hevermc.lobby.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.lobby.Lobby;

public class NPCCommand extends HeverCommand {

	public NPCCommand() {
		super("setnpc");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.DIRETOR, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/setnpc <kitpvp>");
				} else if (args[0].equalsIgnoreCase("kitpvp")) {
					Lobby.getManager().npc_loc.put("kitpvp", p.getLocation());
					p.sendMessage("§aVocê definiu a localização de um npc!");
				}
			}
		}
		return false;
	}
}
