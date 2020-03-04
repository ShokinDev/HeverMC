package br.com.hevermc.hardcoregames.command;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

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
					p.sendMessage("�aVoc� deve utilizar �e/skit criar <kit>!");
					p.sendMessage("�aVoc� deve utilizar �e/skit aplicar <kit> <blocos>!");
					return true;
				}
				if (args[0].equalsIgnoreCase("criar")) {
					if (args.length == 1) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("�aVoc� deve utilizar�e /skit criar <kit>!");
						p.sendMessage("�aVoc� deve utilizar�e /skit aplicar <kit> <blocos>!");
						return true;
					}
					final String nome = args[1];
					this.itens.put(nome, p.getInventory().getContents());
					this.armor.put(nome, p.getInventory().getArmorContents());
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
					p.sendMessage("�aO kit �e" + args[1] + " �afoi criado com sucesso!");
					return true;
				} else if (args[0].equalsIgnoreCase("aplicar")) {
					if (args.length <= 2) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("�cUse: /skit criar (kit)!");
						p.sendMessage("�cUse: /skit aplicar (kit) (blocos)!");
						return true;
					}
					final String nome = args[1];
					if (!this.itens.containsKey(nome) && !this.armor.containsKey(nome)) {
						p.playSound(p.getLocation(), Sound.CLICK, 15.0F, 1.0F);
						p.sendMessage("�cO kit �4" + nome + "�c n�o foi encontrado!");
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
								p2.sendMessage("�aO kit �e" + nome + "�a foi definido para voc�!");
								p2.setAllowFlight(false);
							}
						}
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 15.0F, 1.0F);
						p.sendMessage(
								"�aVoc� aplicou o kit �e" + nome + " �aem uma distancia de�e " + n + "�a bloco(s)!");
						return true;
					}
				}
			}
		}
		return false;
	}

}
