package br.com.hevermc.lobby.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.lobby.Lobby;

public class HologramCommand extends HeverCommand {

	public HologramCommand() {
		super("hologram");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.DIRETOR, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/hologram <tkpvp|tdpvp>");
				} else if (args[0].equalsIgnoreCase("tkpvp")) {
					Lobby.getManager().h_loc.put("tkpvp", p.getLocation());
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}else if (args[0].equalsIgnoreCase("tdpvp")) {
					Lobby.getManager().h_loc.put("tdpvp", p.getLocation());
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}
			}
		}
		return false;
	}
}
