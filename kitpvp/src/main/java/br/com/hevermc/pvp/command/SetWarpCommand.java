package br.com.hevermc.pvp.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.pvp.api.WarpsAPI;

public class SetWarpCommand extends HeverCommand {

	public SetWarpCommand() {
		super("setwarp");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.BUILDER, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/setwarp <fps|lava|1v1|1v1pos1|1v1pos2>");
				} else if (args[0].equalsIgnoreCase("fps") || args[0].equalsIgnoreCase("lava")
						|| args[0].equalsIgnoreCase("1v1") || args[0].equalsIgnoreCase("1v1pos1")
						|| args[0].equalsIgnoreCase("1v1pos2") || args[0].equalsIgnoreCase("specevento")
						|| args[0].equalsIgnoreCase("evento")) {
					new WarpsAPI(br.com.hevermc.pvp.enums.Warps.getWarps(args[0])).setLocation(p.getLocation());
					p.sendMessage("§3§lWARP §fVocê definiu a §3§lWARP§f: §3" + args[0].toUpperCase() + "§f!");
				} else {
					p.sendMessage("§3§lWARP §fEsta warp §4§lNÃO§f existe!");
				}
			}

		}
		return false;
	}

}
