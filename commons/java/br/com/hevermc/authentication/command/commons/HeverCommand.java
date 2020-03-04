package br.com.hevermc.authentication.command.commons;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class HeverCommand extends Command {

	public HeverCommand(String cmd, List<String> aliases) {
		super(cmd);
		setAliases(aliases);
	}

	public HeverCommand(String cmd) {
		super(cmd);
	}

	@Override
	public abstract boolean execute(CommandSender sender, String commandLabel, String[] args);

	public boolean isInt(String args) {
		try {
			Integer.parseInt(args);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isPlayer(CommandSender cmds) {
		if (cmds instanceof Player)
			return true;
		return false;

	}

	public Player toPlayer(CommandSender sender) {
		return (Player) sender;
	}

}
