package br.com.hevermc.hardcoregames.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class KillMobsCommand extends HeverCommand {

	public KillMobsCommand() {
		super("killmobs");
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
					p.sendMessage("§7§o[O staffer " + p.getName() + " limpou os mobs!]");
				}
			}
			World world = Bukkit.getWorld("world");
			p.sendMessage("§eVocê limpou os §amobs§e!");
			for (LivingEntity mob : world.getEntitiesByClass(LivingEntity.class)) {
	             for (Entity entity : mob.getLocation().getChunk().getEntities()) {
	                  if (((entity instanceof LivingEntity)) && (!(entity instanceof Player))) {
	                        entity.remove();
	                  }
	             }
			}
			return true;
		}
		return false;
	}
}