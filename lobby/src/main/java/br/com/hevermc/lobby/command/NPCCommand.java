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
					p.sendMessage("§a§lNPC §fVocê deve utilizar §b/setnpc <npc>");
				} else if (args[0].equalsIgnoreCase("kitpvp")) {
					Lobby.getInstance().getLocations().getConfig().set("npc.pvp.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("npc.pvp.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("npc.pvp.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					p.sendMessage("§a§lNPC §fVocê definiu a §3§lLOCALIZAÇÃO §fde um NPC!");
				} else if (args[0].equalsIgnoreCase("hg")) {
					Lobby.getInstance().getLocations().getConfig().set("npc.hg.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("npc.hg.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("npc.hg.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					p.sendMessage("§a§lNPC §fVocê definiu a §3§lLOCALIZAÇÃO §fde um NPC!");
				} else if (args[0].equalsIgnoreCase("glad")) {
					Lobby.getInstance().getLocations().getConfig().set("npc.glad.x", p.getLocation().getX());
					Lobby.getInstance().getLocations().getConfig().set("npc.glad.y", p.getLocation().getY());
					Lobby.getInstance().getLocations().getConfig().set("npc.glad.z", p.getLocation().getZ());
					Lobby.getInstance().getLocations().save();
					p.sendMessage("§a§lNPC §fVocê definiu a §3§lLOCALIZAÇÃO §fde um NPC!");
				}
			}
		}
		return false;
	}
}
