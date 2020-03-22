package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class ClearChatCommand extends HeverCommand {

	public ClearChatCommand() {
		super("clearchat", Arrays.asList("cc"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.TRIAL, true)) {
				for (int i = 0; i < 100; i++) 
					Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(""));
				p.sendMessage("§6§lCHAT §fVocê §a§lLIMPOU §fo chat!");
			}
		}
		return false;
	}
}
