package br.com.hevermc.pvp.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.AdminAPI;
import br.com.hevermc.pvp.api.PlayerLoader;
import br.com.hevermc.pvp.api.PvPPlayer;

public class AdminCommand extends HeverCommand {

	public AdminCommand() {
		super("admin");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.TRIAL, true)) {
				PvPPlayer pvp = new PlayerLoader(p).load().getPvPP();
				if (pvp.isAdminMode()) {
					pvp.setAdminMode(false);
					p.sendMessage("§cVocê saiu do modo ADMIN!");
					p.setGameMode(GameMode.SURVIVAL);
					KitPvP.getManager().online.add(p);
					Bukkit.getOnlinePlayers().forEach(all -> {
						HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(all);
						if (hp.groupIsLarger(Groups.TRIAL)) {
							all.sendMessage("§7[" + p.getName() + " saiu do modo ADMIN]");
						}
					});
				} else {
					pvp.setAdminMode(true);
					p.sendMessage("§aVocê entrou no modo ADMIN!");
					p.sendMessage("§dVocê está invisivel para "  + toHeverPlayer(p).getGroup().getName() + " abaixo!");
					new AdminAPI(p);
					KitPvP.getManager().online.remove(p);
					p.setGameMode(GameMode.CREATIVE);
					Bukkit.getOnlinePlayers().forEach(all -> {
						HeverPlayer hp = br.com.hevermc.commons.bukkit.account.loader.PlayerLoader.getHP(all);
						if (hp.groupIsLarger(Groups.TRIAL)) {
							all.sendMessage("§7[" + p.getName() + " entrou no modo ADMIN]");
						}
					});
				}
			}

		}
		return false;
	}

}
