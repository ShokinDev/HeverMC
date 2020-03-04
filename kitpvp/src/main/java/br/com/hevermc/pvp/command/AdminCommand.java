package br.com.hevermc.pvp.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.api.ItemConstructor;
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

	HashMap<Player, ItemStack[]> items = new HashMap<Player, ItemStack[]>();

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
					p.getInventory().clear();
					if (items.containsKey(p)) {
						p.getInventory().setContents(items.get(p));
						items.remove(p);
					}
					
					Bukkit.getOnlinePlayers().forEach(all -> {
						HeverPlayer hp = toHeverPlayer(all);
						all.showPlayer(p);
						if (hp.groupIsLarger(toHeverPlayer(p).getGroup())) {
							all.sendMessage("§7[" + p.getName() + " saiu do modo ADMIN]");
						}
					});
				} else {
					pvp.setAdminMode(true);
					p.sendMessage("§aVocê entrou no modo ADMIN!");
					p.sendMessage("§dVocê está invisivel para " + toHeverPlayer(p).getGroup().getName() + " abaixo!");
					AdminAPI.hideInAdminMode(p);
					items.put(p, p.getInventory().getContents());
					p.getInventory().clear();
					p.getInventory().setItem(0, new ItemConstructor(new ItemStack(Material.DIAMOND_SWORD),
							"§eJogadores em combate §7(Clique com o botão direito)").create());
					p.getInventory().setItem(1, new ItemConstructor(new ItemStack(Material.STICK),
							"§eInformações sobre o jogador §7(Interaja com o jogador)").create());
					KitPvP.getManager().online.remove(p);
					p.setGameMode(GameMode.CREATIVE);
					Bukkit.getOnlinePlayers().forEach(all -> {
						HeverPlayer hp = toHeverPlayer(all);
						if (hp.groupIsLarger(toHeverPlayer(p).getGroup())) {
							all.sendMessage("§7[" + p.getName() + " entrou no modo ADMIN]");
						}
					});
				}
			}

		}
		return false;
	}

}
