package br.com.hevermc.pvp.command;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.enums.Warps;
import br.com.hevermc.pvp.score.ScoreboardManager;

public class SpawnCommand extends HeverCommand {

	public SpawnCommand() {
		super("spawn");
	}

	public static HashMap<Player, Boolean> build = new HashMap<Player, Boolean>();

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			PvPPlayer pvp = new br.com.hevermc.pvp.api.PlayerLoader(p).load().getPvPP();
			if (pvp.isCombat()) {
				p.sendMessage("§cVocê está em combate!");
			} else {
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setItem(0,
						new ItemConstructor(new ItemStack(Material.CHEST), "§eSeletor de kits §7(Abra com o botão direito)")
								.create());
				p.getInventory().setItem(1,
						new ItemConstructor(new ItemStack(Material.COMPASS), "§eWarps §7(Abra com o botão direito)").create());
				p.teleport(p.getWorld().getSpawnLocation());
				p.updateInventory();
				pvp.setProtectArea(true);
				pvp.setKit(Kits.NENHUM);
				pvp.setCombat(false);
				pvp.setWarp(Warps.SPAWN);
				new ScoreboardManager().build(p);
				p.setHealth(20);
				p.sendMessage("§aVocê voltou para o spawn!");
			}

		}
		return false;
	}
}
