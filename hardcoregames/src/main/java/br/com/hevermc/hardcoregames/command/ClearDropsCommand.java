package br.com.hevermc.hardcoregames.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class ClearDropsCommand extends HeverCommand {

	public ClearDropsCommand() {
		super("cleardrops");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (!hasGroup(p, Groups.MODPLUS, true)) {
			return true;
		}
		if (args.length == 0) {
			for (Player ons : Bukkit.getOnlinePlayers()) {
				HeverPlayer hp = PlayerLoader.getHP(ons);
				if (hp.groupIsLarger(Groups.TRIAL)) {
					p.sendMessage("§7§o[O staffer " + p.getName() + " limpou o chão!]");
				}
			}
			p.sendMessage("§eVocê limpou o §achão§e!");
			for (Item item : Bukkit.getWorld("world").getEntitiesByClass(Item.class)) {
				item.remove();
			}
			return true;
		}
		return false;
	}
}