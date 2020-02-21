package br.com.hevermc.commons.bungee.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ReportCommand extends HeverCommand implements TabExecutor {

	public ReportCommand() {
		super("report", "denunciar");
	}

	ArrayList<ProxiedPlayer> receive_report = new ArrayList<>();

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("silent")) {
					if (requiredGroup(p, Groups.MODPLUS, true)) {
						if (receive_report.contains(p)) {
							receive_report.remove(p);
							p.sendMessage(TextComponent.fromLegacyText(
									"�aAgora voc� recebe todos reports!"));
						} else {
							receive_report.add(p);
							p.sendMessage(TextComponent.fromLegacyText(
									"�cAgora voc� n�o recebe todos reports!"));

						}
					}
				} else {

					p.sendMessage(TextComponent
							.fromLegacyText("�aVoc� deve utilizar �e/report <acusado> <motivo>"));
				}
					
			} else
			if (args.length < 2) {
					p.sendMessage(TextComponent
							.fromLegacyText("�aVoc� deve utilizar �e/report <acusado> <motivo>"));
			} else {
				ProxiedPlayer target = Commons.getInstance().getProxy().getPlayer(args[0]);
				if (target == null) {
					p.sendMessage(TextComponent.fromLegacyText("�cEste jogador est� offline!"));
				} else if (target == p) {
					p.sendMessage(TextComponent.fromLegacyText("�cVoc� n�o pode reportar este jogador!"));
				} else {
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; ++i) {
						sb.append(args[i]).append(" ");
					}
					String reason = sb.toString();
					TextComponent msg_a = new TextComponent("�c[O jogador " + target.getName() + " foi reportado]");
					msg_a.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/connect " + p.getServer().getInfo().getName().toLowerCase()));
					msg_a.setHoverEvent(
							new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder("�cInforma��es sobre este report:\n" + "�fMotivo: �c" + reason
											+ "\n�fAutor: �c" + p.getName() + "\nServidor: �b�l"
											+ p.getServer().getInfo().getName().toUpperCase()).create()));
					Commons.getInstance().getProxy().getPlayers().forEach(all -> {
						HeverPlayer allhp = PlayerLoader.getHP(all);
						if (allhp.groupIsLarger(Groups.TRIAL) && !(receive_report.contains(all))) {
							all.sendMessage(msg_a);
						}
					});

					p.sendMessage(TextComponent.fromLegacyText(
							"�aVoc� reportou o jogador �e" + target.getName() + " �fcom sucesso!"));

				}

			}
		}
	}

	public Iterable<String> onTabComplete(final CommandSender cs, final String[] args) {
		final Set<String> match = new HashSet<String>();
		if (args.length == 1) {
			final String search = args[0].toLowerCase();
			for (ProxiedPlayer player : Commons.getInstance().getProxy().getPlayers()) {
				if (player.getName().toLowerCase().startsWith(search)) {
					match.add(player.getName());
				}
			}
		}
		return match;
	}
}
