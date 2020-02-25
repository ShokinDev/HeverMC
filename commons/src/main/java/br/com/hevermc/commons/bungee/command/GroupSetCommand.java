package br.com.hevermc.commons.bungee.command;

import java.util.HashSet;
import java.util.Set;

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

public class GroupSetCommand extends HeverCommand implements TabExecutor {

	public GroupSetCommand() {
		super("groupset", "setgroup");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if (isPlayer(sender)) {
			ProxiedPlayer p = toPlayer(sender);
			HeverPlayer hp = toHeverPlayer(p);
			if (requiredGroup(p, Groups.GERENTE, true)) {
				if (args.length < 2) {

					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/groupset <nickname> <grupo>"));
				} else {
					Groups togroup = Groups.getGroup(args[1]);
					HeverPlayer htarget;
					if (!(args[0].length() > 16)) {
						htarget = PlayerLoader.getHP(args[0]);
					} else {
						p.sendMessage(TextComponent.fromLegacyText("§cNickname inválido!"));
						return;
					}
					if (!hp.groupIsLarger(togroup)) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste grupo é maior que o seu!"));
					} else if (togroup == null) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste grupo não existe!"));
					} else if (!hp.groupIsLarger(htarget.getGroup())) {
						p.sendMessage(TextComponent.fromLegacyText("§cEste jogador possui um cargo maior que o seu!"));
					} else if (args[0].equalsIgnoreCase(p.getName())) {
						p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode alterar seu propío grupo!"));
					} else {
						ProxiedPlayer t = Commons.getInstance().getProxy().getPlayer(args[0]);
						if (t != null) {
							t.disconnect(
									TextComponent.fromLegacyText("§6§lHEVER§f§lMC\n§f\n§fSeu grupo foi alterado para "
											+ togroup.getName() + "!\n§f\n§fEntre novamente."));
							htarget.setGroup(togroup);
						} else {
							htarget.setGroup(togroup);
							htarget.update();
						}
						

						p.sendMessage(TextComponent.fromLegacyText("§aVocê alterou o cargo de §b" + htarget.getName()
								+ " §apara §b" + htarget.getGroup().getName() + " §acom sucesso!"));
					}
				}
			}
		} else {
			if (args.length < 2) {
				sender.sendMessage(TextComponent.fromLegacyText("Voce deve usar /groupset <nickname> <grupo>"));
			} else {
				Groups togroup = Groups.getGroup(args[1]);
				HeverPlayer htarget;
				if (!(args[0].length() > 16)) {
					htarget = PlayerLoader.getHP(args[0]);
				} else {
					sender.sendMessage(TextComponent.fromLegacyText("Nickname inválido!"));
					return;
				}
				if (togroup == null) {
					sender.sendMessage(TextComponent.fromLegacyText("Este grupo não existe!"));
				} else {
					htarget.setGroup(togroup);
					ProxiedPlayer t = Commons.getInstance().getProxy().getPlayer(args[0]);
					if (t != null) {
						t.disconnect(TextComponent.fromLegacyText("§6§lHEVER§f§lMC\n§f\n§fSeu grupo foi alterado para "
								+ togroup.getName() + "!\n§f\n§fEntre novamente."));
					}
					sender.sendMessage(TextComponent.fromLegacyText("Voce alterou o cargo de " + htarget.getName()
							+ " para " + htarget.getGroup().getName() + " com sucesso!"));
				}
			}
		}
	}
	
	public Iterable<String> onTabComplete(CommandSender cs, String[] args) {
		Set<String> match = new HashSet<>();
		if (args.length == 1) {
			String search = args[0].toLowerCase();
			for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
				if (player.getName().toLowerCase().startsWith(search)) {
					match.add(player.getName());
				}
			}
		} else 
			if (args.length == 2) {
				String search = args[1].toLowerCase();
				for (Groups player : Groups.values()) {
					if (player.getName().toLowerCase().startsWith(search)) {
						match.add(player.getName());
					}
				}
			}
		return match;
	}
}
