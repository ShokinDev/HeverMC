package br.com.hevermc.commons.bukkit.command.commons;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;

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

	public boolean hasGroup(Player p, Groups group, boolean b) {
		HeverPlayer hp = PlayerLoader.getHP(p.getName());

		if (hp.getGroup().ordinal() >= group.ordinal()) {
			return true;
		} else if (b) {
			p.sendMessage("§cVocê precisa do grupo §c§l" + group.getName() + " §cou superior para executar este comando!");
		}
		return false;
	}

	public boolean isPlayer(CommandSender cmds) {
		if (cmds instanceof Player)
			return true;
		return false;

	}

	public Player toPlayer(CommandSender sender) {
		return (Player) sender;
	}

	public HeverPlayer toHeverPlayer(Player p) {
		return PlayerLoader.getHP(p.getName());
	}

}
