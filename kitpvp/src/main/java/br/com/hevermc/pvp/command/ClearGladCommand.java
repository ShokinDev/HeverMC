package br.com.hevermc.pvp.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class ClearGladCommand extends HeverCommand {

	public ClearGladCommand() {
		super("clearglad");
	}

	public static List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					blocks.add(location.getWorld().getBlockAt(x, y, z));
				}
			}
		}
		return blocks;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.BUILDER, true)) {
				if (args.length == 0) {
					p.sendMessage("§3§lCLEARGLAD §fVocê deve utilizar §b/clearglad <raio>");
				} else {
					if (!isInt(args[0])) {
						p.sendMessage("§3§lCLEARGLAD §fVocê deve utilizar §b/clearglad <raio>");
					} else {
						for (Block b : getNearbyBlocks(p.getLocation(), Integer.parseInt(args[0]))) {
							if (b.getType() == Material.GLASS) {
								b.setType(Material.AIR);
							}
						}
					}
				}
			}

		}
		return false;
	}

}
