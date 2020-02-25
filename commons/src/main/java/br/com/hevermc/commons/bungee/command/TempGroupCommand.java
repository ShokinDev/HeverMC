package br.com.hevermc.commons.bungee.command;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class TempGroupCommand extends HeverCommand implements TabExecutor {

	public TempGroupCommand() {
		super("tempgroup");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (isPlayer(sender)) {
			ProxiedPlayer p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (requiredGroup(p, Groups.GERENTE, true)) {
				if (args.length < 3) {
					p.sendMessage(
							TextComponent.fromLegacyText("§aVocê deve usar §e/tempgroup <nickname> <time> <grupo>"));
				} else {
					Groups togroup = Groups.getGroup(args[2]);
					String time = args[1];
					HeverPlayer htarget;
					if (!(args[0].length() > 16)) {
						htarget = PlayerLoader.getHP(args[0]);
					} else {
						p.sendMessage(TextComponent.fromLegacyText("§cNickname inválido!"));
						return;
					}
					if (!hp.groupIsLarger(togroup)) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste grupo é maior ou igual ao seu!"));
					} else if (togroup == null) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste grupo não existe!"));
					} else if (!hp.groupIsLarger(htarget.getGroup())) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste jogador possui um cargo maior que o seu!"));
					} else if (args[0].equalsIgnoreCase(p.getName())) {
						p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode alterar seu propío grupo!"));
					} else if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
						p.sendMessage(TextComponent
								.fromLegacyText("§aVocê deve usar §e/tempgroup <nickname> <time> <grupo>"));
					} else {
						int timeint = Integer
								.valueOf(time.replace("d", "").replace("m", "").replace("s", "").replace("y", ""));
						String format = time.replace(timeint + "", "");
						Calendar c = Calendar.getInstance();
						if (format.equalsIgnoreCase("s")) {
							c.add(Calendar.SECOND, timeint);
						} else if (format.equalsIgnoreCase("d")) {
							c.add(Calendar.DAY_OF_YEAR, timeint);
						} else if (format.equalsIgnoreCase("m")) {
							c.add(Calendar.MONTH, timeint);
						} else if (format.equalsIgnoreCase("y")) {
							c.add(Calendar.YEAR, timeint);
						}
						ProxiedPlayer t = Commons.getInstance().getProxy().getPlayer(args[0]);
						if (t != null) {
							t.disconnect(TextComponent.fromLegacyText("§6§lHEVER§f§lMC\n§f\n§fSeu grupo foi alterado para " + togroup.getName() + "!\n§f\n§fEntre novamente."));
						}
						htarget.setGroup(togroup);
						htarget.setGroupExpireIn(c.getTimeInMillis());
						p.sendMessage(TextComponent.fromLegacyText("§aVocê alterou o cargo de §b" + htarget.getName()
								+ " §apara §b" + htarget.getGroup().getName() + " §acom sucesso!"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				sender.sendMessage(TextComponent.fromLegacyText("§cVocê deve usar §c§l/tempgroup <nickname> <grupo>"));
			} else {
				Calendar c = Calendar.getInstance();
				Groups togroup = Groups.getGroup(args[1]);
				HeverPlayer htarget;
				if (!(args[0].length() > 16)) {
					htarget = PlayerLoader.getHP(args[0]);
				} else {
					sender.sendMessage(TextComponent.fromLegacyText("§cNickname inválido!"));
					return;
				}
				if (togroup == null) {
					sender.sendMessage(TextComponent.fromLegacyText("§cEste grupo não existe!"));
				} else {
					ProxiedPlayer t = Commons.getInstance().getProxy().getPlayer(args[0]);
					if (t != null) {
						t.disconnect(TextComponent.fromLegacyText("§6§lHEVER§f§lMC\n§f\n§fSeu grupo foi alterado para " + togroup.getName() + "!\n§f\n§fEntre novamente."));
					}
					htarget.setGroup(togroup);
					htarget.setGroupExpireIn(c.getTimeInMillis());
					if (Commons.getInstance().getProxy().getPlayer(args[0]) != null) {
						Commons.getManager().getRedis().set("update:" + htarget.getName().toLowerCase(), "all");
					}
					sender.sendMessage(TextComponent.fromLegacyText("§aVocê alterou o cargo de §b" + htarget.getName()
							+ " §apara §b" + htarget.getGroup().getName() + " §acom sucesso!"));
				}
			}
		}
	}
	
	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		if ((args.length > 2) || (args.length == 0)) {
			return ImmutableSet.of();
		}
		Set<String> match = new HashSet<>();
		if (args.length == 1) {
			String search = args[0].toLowerCase();
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				if (player.getName().toLowerCase().startsWith(search)) {
					match.add(player.getName());
				}
			}
		} else 
			if (args.length == 3) {
				String search = args[2].toLowerCase();
				for (Groups player : Groups.values()) {
					if (player.getName().toLowerCase().startsWith(search)) {
						match.add(player.getName());
					}
				}
			}
		return match;
	}
}