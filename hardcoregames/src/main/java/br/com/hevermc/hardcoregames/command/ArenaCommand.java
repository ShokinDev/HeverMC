package br.com.hevermc.hardcoregames.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.hardcoregames.HardcoreGames;

public class ArenaCommand extends HeverCommand {

	public ArenaCommand() {
		super("arena");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (!hasGroup(p, Groups.MODPLUS, true)) {
			return true;
		}
		if (args.length == 0) {
			p.sendMessage("§aVocê deve usar §e/arena final");
			p.sendMessage("§aVocê deve usar §e/arena limpar");
			p.sendMessage("§aVocê deve usar §e/arena <largura> <altura>");
			return true;
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("limpar")) {
				limpararena(p);
			} else if (args[0].equalsIgnoreCase("final")) {
				for (Player ons : Bukkit.getOnlinePlayers()) {
					HeverPlayer hp = PlayerLoader.getHP(ons);
					if (hp.groupIsLarger(Groups.TRIAL)) {
						p.sendMessage("§7§o[O staffer " + p.getName() + " spawnou a arena final!]");
					}
				}
				p.teleport(new Location(Bukkit.getWorld("world"), 0, 60, 0));
				HardcoreGames.getManager().spawnarArenaFinal();
				HardcoreGames.getManager().broadcast("§fA arena §cfinal§f começou!");
			} else {
				p.sendMessage("§aVocê deve usar §e/arena final");
				p.sendMessage("§aVocê deve usar §e/arena limpar");
				p.sendMessage("§aVocê deve usar §e/arena <largura> <altura>");
			}
		} else if (args.length == 2) {
			String largura = args[0], altura = args[1];
			if (!isInt(altura) || !isInt(largura)) {
				p.sendMessage("§aVocê deve usar §e/arena final");
				p.sendMessage("§aVocê deve usar §e/arena limpar");
				p.sendMessage("§aVocê deve usar §e/arena <largura> <altura>");
				return true;
			}
			criarArena(p, Integer.valueOf(largura), Integer.valueOf(altura));
			p.sendMessage("§eVocê criou uma §aarena§e!");
		} else {
			p.sendMessage("§aVocê deve usar §e/arena final");
			p.sendMessage("§aVocê deve usar §e/arena limpar");
			p.sendMessage("§aVocê deve usar §e/arena <largura> <altura>");
		}

		return false;
	}

	private Location ponto_baixo, ponto_alto;

	public List<Location> getLocationsFromTwoPoints(Location location1, Location location2) {
		List<Location> locations = new ArrayList<>();
		int topBlockX = (location1.getBlockX() < location2.getBlockX() ? location2.getBlockX() : location1.getBlockX());
		int bottomBlockX = (location1.getBlockX() > location2.getBlockX() ? location2.getBlockX()
				: location1.getBlockX());
		int topBlockY = (location1.getBlockY() < location2.getBlockY() ? location2.getBlockY() : location1.getBlockY());
		int bottomBlockY = (location1.getBlockY() > location2.getBlockY() ? location2.getBlockY()
				: location1.getBlockY());
		int topBlockZ = (location1.getBlockZ() < location2.getBlockZ() ? location2.getBlockZ() : location1.getBlockZ());
		int bottomBlockZ = (location1.getBlockZ() > location2.getBlockZ() ? location2.getBlockZ()
				: location1.getBlockZ());
		for (int x = bottomBlockX; x <= topBlockX; x++)
			for (int z = bottomBlockZ; z <= topBlockZ; z++)
				for (int y = bottomBlockY; y <= topBlockY; y++)
					locations.add(new Location(location1.getWorld(), x, y, z));
		return locations;
	}

	public List<Block> getBlocks(Location location1, Location location2) {
		List<Block> blocks = new ArrayList<>();
		for (Location loc : getLocationsFromTwoPoints(location1, location2)) {
			Block b = loc.getBlock();
			if (!b.getType().equals(Material.AIR))
				blocks.add(b);
		}
		return blocks;
	}

	public void limpararena(Player p) {
		if (ponto_alto == null) {
			p.sendMessage("§eVocê não criou uma §aarena§e!");
			return;
		}
		for (Block b : getBlocks(ponto_baixo, ponto_alto))
			b.setType(Material.AIR);
		p.sendMessage("§eVocê limpou a §aarena§e!");
	}

	public void criarArena(Player p, int largura, int altura) {
		Location loc = p.getLocation();
		List<Location> cuboid = new ArrayList<>();
		cuboid.clear();
		for (int bX = -largura; bX <= largura; bX++) {
			for (int bZ = -largura; bZ <= largura; bZ++) {
				for (int bY = -1; bY <= altura; bY++) {
					if (bY == -1) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					} else if ((bX == -largura) || (bZ == -largura) || (bX == largura) || (bZ == largura)) {
						cuboid.add(loc.clone().add(bX, bY, bZ));
					}
				}
			}
		}

		for (Location loc1 : cuboid)
			loc1.getBlock().setType(Material.BEDROCK);
		Location PB = loc.clone().add(largura - 1, 0, largura - 1);
		ponto_baixo = PB;
		Location PA = loc.clone().subtract(largura - 1, 0, largura - 1);
		PA.add(0, altura, 0);
		ponto_alto = PA;
		for (Player ons : Bukkit.getOnlinePlayers()) {
			HeverPlayer hp = PlayerLoader.getHP(ons);
			if (hp.groupIsLarger(Groups.TRIAL)) {
				p.sendMessage("§7§o[O staffer " + p.getName() + " spawnou a arena final!]");
			}
		}
	}

}
