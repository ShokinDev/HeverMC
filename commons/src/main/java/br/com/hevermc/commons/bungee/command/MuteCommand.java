package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MuteCommand extends HeverCommand {

	public MuteCommand() {
		super("ban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 3) {
					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/mute <nickname> <reason>"));
				} else {
					String target = args[0];
					HeverPlayer hp = toHeverPlayer(p);
					String reason;
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++)
						sb.append(args[i]).append(" ");
					reason = sb.toString();
					ProxiedPlayer targetp = Commons.getInstance().getProxy().getPlayer(target);
					HeverPlayer targethp = PlayerLoader.getHP(target);
					if (targetp != null) {
						if (targethp.getGroup().ordinal() > hp.getGroup().ordinal()) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode mutar este jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode se auto-mutar!"));
						} else {

							targethp.setMuted(true);
							targethp.setMute_author(p.getName());
							targethp.setMute_time(0);
							targethp.setMute_reason(reason);
							targethp.mute(reason, p.getName(), 0);

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players);
								if (!t.groupIsLarger(players, Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi mutado]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este mutamento:\n§fMotivo: "
													+ reason + "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi mutado]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este mutamento:\n§fMotivo: "
													+ reason + "\n§fPor: " + p.getName()
													+ "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent.fromLegacyText("§aVocê mutou §e" + targetp.getName() + "§a com sucesso!"));
						}

					} else {
						targethp.setMuted(true);
						targethp.setMute_author(p.getName());
						targethp.setMute_time(0);
						targethp.setMute_reason(reason);
						targethp.mute(reason, p.getName(), 0);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players);
							if (!t.groupIsLarger(players, Groups.TRIAL)) {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + target + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este mutamento:\n§fMotivo: "
												+ reason + "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + target + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este mutamento:\n§fMotivo: "
												+ reason + "\n§fPor: " + p.getName()
												+ "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							}
						});
						p.sendMessage(TextComponent.fromLegacyText("§aVocê mutou §e" + target + "§a com sucesso!"));
			
					}
				}

			}
		}

	}
}
