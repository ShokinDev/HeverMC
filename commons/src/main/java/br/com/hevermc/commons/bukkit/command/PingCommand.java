package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;

public class PingCommand extends HeverCommand {

	public PingCommand() {
		super("ping", Arrays.asList("ms"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (args.length == 0) {
				p.sendMessage("§aSeu ping é de §e" + ReflectionAPI.getPing(p) + "ms§a!");
			} else {
				Player t = Bukkit.getPlayer(args[0]);
				if (t == null) {
					p.sendMessage("§cEste alvo está offline.");
				} else {
					p.sendMessage("§aO ping de §e" + t.getName() + " §aé de §e" + ReflectionAPI.getPing(t) + "ms§a!");
				}
			}
		}
		return false;
	}

}
