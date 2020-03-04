package br.com.hevermc.pvp.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.pvp.api.PvPPlayer;

public class WarpCommand extends HeverCommand {

	public WarpCommand() {
		super("warp");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
			if (!pvp.isCombat()) {
				new br.com.hevermc.pvp.gui.Warps(p);
			} else {
				p.setAllowFlight(false);
				p.sendMessage("§cVocê está em combate!");
			}
		}
		return false;
	}
}
