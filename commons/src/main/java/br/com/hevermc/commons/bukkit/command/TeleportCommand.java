package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class TeleportCommand extends HeverCommand {

	public TeleportCommand() {
		super("teleport", Arrays.asList("tp", "go"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.BUILDER, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve utilizar §e/tp <target> §aou §e/tp <x> <y> <z>");
				} else if (args.length == 1) {

					if (hasGroup(p, Groups.TRIAL, true)) {
						if (args[0].length() > 16 || args[0].length() < 3) {
							p.sendMessage("§cO alvo informado não é valido!");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							if (target == null) {
								p.sendMessage("§cO alvo informado não está on-line!");
							} else {
								p.teleport(target);
								p.sendMessage("§aVocê se teleportou para §e" + target.getName());
							}
						}

					} else if (args.length == 2) {
						if (args[0].length() > 16 || args[0].length() < 3 || args[1].length() > 16
								|| args[1].length() < 3) {
							p.sendMessage("§cUm dos alvos informado não é valido!");
						} else {
							Player target = Bukkit.getPlayer(args[0]);
							Player target2 = Bukkit.getPlayer(args[1]);
							if (target == null || target2 == null) {
								p.sendMessage("§cUm dos alvos informado não está on-line!");
							} else {
								target.teleport(target2);
								p.sendMessage("§aVocê teleportou §e" + target.getName() + " §apara §e"
										+ target2.getName() + "§a!");
							}
						}
					}
				}

				if (hasGroup(p, Groups.BUILDER, true)) {
					if (args.length == 3) {
						if (!(isInt(args[0]) || isInt(args[1]) || isInt(args[2]))) {
							p.sendMessage("§cVocê deve utiliza apenas numeros!");
						}
						double x = Double.valueOf(args[0]);
						double y = Double.valueOf(args[1]);
						double z = Double.valueOf(args[2]);
						p.teleport(new Location(p.getWorld(), x, y, z));
						p.sendMessage("§aVocê se teleportou para §eX: " + x + " Y:" + y + " Z:" + z + "§a!");

					}
				}
			}
		}
		return false;
	}
}
