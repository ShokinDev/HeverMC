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
					
					Lobby.getInstance().getLocations().getConfig().set("holo.tkpvp.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("holo.tkpvp.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("holo.tkpvp.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					
					p.sendMessage("§aVocê definiu a localização de um holograma!");
					
				}else if (args[0].equalsIgnoreCase("tdpvp")) {
					
					Lobby.getInstance().getLocations().getConfig().set("holo.tdpvp.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdpvp.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdpvp.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}else if (args[0].equalsIgnoreCase("tdhg")) {
					
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}else if (args[0].equalsIgnoreCase("tdhg")) {
					
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("holo.tdhg.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}else if (args[0].equalsIgnoreCase("rank")) {
					
					Lobby.getInstance().getLocations().getConfig().set("holo.rank.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("holo.rank.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("holo.rank.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					
					p.sendMessage("§aVocê definiu a localização de um holograma!");
				}
			}
		}
		return false;
	}
}
