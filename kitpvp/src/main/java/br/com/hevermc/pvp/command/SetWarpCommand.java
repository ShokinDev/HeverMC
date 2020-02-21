package br.com.hevermc.pvp.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.pvp.api.Warps;

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
					p.sendMessage("§aVocê deve usar §e/setwarp <fps|lava|onevsone|ovs1|ovs2>");
				} else
				if (args[0].equalsIgnoreCase("fps") || args[0].equalsIgnoreCase("lava") || args[0].equalsIgnoreCase("onevsone") 
						|| args[0].equalsIgnoreCase("ovs1") || args[0].equalsIgnoreCase("ovs2")) {
					new Warps(br.com.hevermc.pvp.enums.Warps.getWarps(args[0])).setLocation(p.getLocation());
					p.sendMessage("§aVocê definiu uma warp!");
				}
			}

		}
		return false;
	}

}
