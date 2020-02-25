package br.com.hevermc.pvp.command;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.pvp.KitPvP;

public class SkitCommand extends HeverCommand {

	public SkitCommand() {
		super("skit");
	}

	public HashMap<String, ItemStack[]> itens = new HashMap<String, ItemStack[]>();
	public HashMap<String, ItemStack[]> armor = new HashMap<String, ItemStack[]>();

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.MODPLUS, true)) {
				if (args.length == 0) {
					p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
					p.sendMessage("§cUse: /Skit Criar (kit)!");
					p.sendMessage("§cUse: /Skit Aplicar (kit) (blocos)!");
					return true;
				}
				if (args[0].equalsIgnoreCase("criar")) {
					if (args.length == 1) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("§cUse: /Skit Criar (kit)!");
						p.sendMessage("§cUse: /Skit Aplicar (kit) (blocos)!");
						return true;
					}
					final String nome = args[1];
					this.itens.put(nome, p.getInventory().getContents());
					this.armor.put(nome, p.getInventory().getArmorContents());
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
					p.sendMessage("§aO kit: " + args[1] + " foi criado com sucesso!");
					return true;
				} else if (args[0].equalsIgnoreCase("aplicar")) {
					if (args.length <= 2) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("§cUse: /Skit Criar (kit)!");
						p.sendMessage("§cUse: /Skit Aplicar (kit) (blocos)!");
						return true;
					}
					final String nome = args[1];
					if (!this.itens.containsKey(nome) && !this.armor.containsKey(nome)) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("§cO kit: " + nome + " não foi encontrado!");
						return true;
					}
					if (this.isInt(args[2])) {
						final int n = Integer.parseInt(args[2]);
						for (final Entity e : p.getNearbyEntities((double) n, (double) n, (double) n)) {
							if (e instanceof Player) {
								final Player p2 = (Player) e;
								p2.getInventory().setArmorContents((ItemStack[]) this.armor.get(nome));
								p2.getInventory().setContents((ItemStack[]) this.itens.get(nome));
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
								p2.sendMessage("§aO kit: " + nome + " foi definido para você!");
								p2.setAllowFlight(false);
							}
						}
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
						p.sendMessage("§aVocê aplicou o kit: " + nome + " em uma distancia de: " + n + " bloco(s)!");
						return true;
					} else if (args[2].equalsIgnoreCase("evento")) {
						for (Player p2 : KitPvP.getManager().inEvent) {
							p2.getInventory().setArmorContents((ItemStack[]) this.armor.get(nome));
							p2.getInventory().setContents((ItemStack[]) this.itens.get(nome));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
							p2.sendMessage("§aO kit: " + nome + " foi definido para você!");
							p2.setAllowFlight(false);
						}
					}
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
					p.sendMessage("§aVocê aplicou o kit: " + nome + " em todos jogadores no evento!");
					return true;
				}
			}
		}
		return false;
	}

}
