package br.com.hevermc.pvp.command;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.api.ItemConstructor;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.pvp.KitPvP;
import br.com.hevermc.pvp.api.PvPPlayer;
import br.com.hevermc.pvp.enums.Kits;
import br.com.hevermc.pvp.enums.Warps;
import br.com.hevermc.pvp.onevsone.Eventos1v1;
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
				p.setAllowFlight(false);
				new ScoreboardManager().build(p);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.CHEST),
						"§aSeletor de kits §7(Abra com o botão direito)").create());
				p.getInventory().setItem(1, new ItemConstructor(new ItemStack(Material.CHEST),
						"§aSeletor de kits 2 §7(Abra com o botão direito)").create());

				p.getInventory().setItem(4,
						new ItemConstructor(new ItemStack(Material.COMPASS), "§aWarps §7(Abra com o botão direito)")
								.create());
				new ScoreboardManager().build(p);
				
				p.teleport(p.getWorld().getSpawnLocation());
				p.updateInventory();
				pvp.setProtectArea(true);
				pvp.setKit(Kits.NENHUM);
				pvp.setKit2(Kits.NENHUM);
				pvp.setCombat(false);
				pvp.setWarp(Warps.SPAWN);
				new ScoreboardManager().build(p);
				p.setHealth(20);
				p.sendMessage("§3§lWARP §fVocê voltou para o spawn!");
				if (KitPvP.getManager().inEvent.contains(p)) {
					KitPvP.getManager().inEvent.remove(p);
					KitPvP.getManager().killsInEvent.remove(p);
				}
				if (Eventos1v1.firstMatch == p.getUniqueId()) {
					Eventos1v1.firstMatch = null;
				}
			}

		}
		return false;
	}
}
