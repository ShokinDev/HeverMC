package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class TeleportCommand extends HeverCommand {

	public TeleportCommand() {
		super("teleport", Arrays.asList("tp", "go", "tpall"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.BUILDER, true)) {
				if (args.length == 0) {

					if (commandLabel.equalsIgnoreCase("tpall") && hasGroup(p, Groups.MODPLUS, true)) {
						Bukkit.getOnlinePlayers().forEach(ps -> ps.teleport(p));
						p.sendMessage("§aVocê teleportou todos até você");
						Bukkit.getOnlinePlayers().forEach(ps -> {
							HeverPlayer hps = toHeverPlayer(ps);
							if (hps.groupIsLarger(Groups.GERENTE)) {
								ps.sendMessage("§7§o[" + p.getName() + " teleportou todos até " + p.getName() + "]");
							}
						});
					} else {

						p.sendMessage("§aVocê deve utilizar §e/tp <target> §aou §e/tp <x> <y> <z>");
					}
				} else {

					if (hasGroup(p, Groups.TRIAL, true)) {
						if (args.length == 1) {
							if (args[0].length() > 16 || args[0].length() < 3) {
								p.sendMessage("§cO alvo informado não é valido!");
							} else {
								Player target = Bukkit.getPlayer(args[0]);
								if (target == null) {
									p.sendMessage("§cO alvo informado não está on-line!");
								} else {
									p.teleport(target);
									p.sendMessage("§aVocê se teleportou para §e" + target.getName());
									Bukkit.getOnlinePlayers().forEach(ps -> {
										HeverPlayer hps = toHeverPlayer(ps);
										if (hps.groupIsLarger(Groups.GERENTE)) {
											ps.sendMessage("§7§o[" + p.getName() + " se teleportou até "
													+ target.getName() + "]");
										}
									});
								}
							}
						}

						if (args.length == 2) {
							if (hasGroup(p, Groups.TRIAL, true)) {
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
										Bukkit.getOnlinePlayers().forEach(ps -> {
											HeverPlayer hps = toHeverPlayer(ps);
											if (hps.groupIsLarger(Groups.GERENTE)) {
												ps.sendMessage("§7§o[" + p.getName() + " teleportou " + target.getName()
														+ " até " + target2.getName() + "]");
											}
										});
									}
								}
							}
						}

						if (hasGroup(p, Groups.BUILDER, true)) {
							if (args.length == 3) {
								if (!(isInt(args[0]) || isInt(args[1]) || isInt(args[2]))) {
									p.sendMessage("§cVocê deve utilizar apenas numeros!");
								}
								double x = Double.valueOf(args[0]);
								double y = Double.valueOf(args[1]);
								double z = Double.valueOf(args[2]);
								p.teleport(new Location(p.getWorld(), x, y, z));
								p.sendMessage("§aVocê se teleportou para §eX: " + x + " Y:" + y + " Z:" + z + "§a!");
								Bukkit.getOnlinePlayers().forEach(ps -> {
									HeverPlayer hps = toHeverPlayer(ps);
									if (hps.groupIsLarger(Groups.GERENTE)) {
										ps.sendMessage("§7§o[" + p.getName() + " se teleportou até " + "X: " + x + " Y:"
												+ y + " Z:" + z + "]");
									}
								});
							}
						}

					}
				}
			}
		}
		return false;
	}
}
