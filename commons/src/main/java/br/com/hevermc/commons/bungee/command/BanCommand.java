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

public class BanCommand extends HeverCommand {

	public BanCommand() {
		super("ban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 3) {
					p.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/ban <nickname> <reason>"));
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
							p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode banir este jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(TextComponent.fromLegacyText("§cVocê não pode se auto-banir!"));
						} else {

							targetp.disconnect(TextComponent
									.fromLegacyText("§4§lBANIDO\n\n§fVocê foi banido permanentemente!\n\n§fMotivo: §c"
											+ reason + "\n§fPor: " + p.getName()
											+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ediscord.hevermc.com.br"));

							targethp.setBanned(true);
							targethp.setBan_author(p.getName());
							targethp.setBan_time(0);
							targethp.setBan_reason(reason);
							targethp.ban(reason, p.getName(), 0);

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players);
								if (!t.groupIsLarger(players, Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fPor: " + p.getName()
													+ "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent.fromLegacyText("§aVocê baniu §e" + targetp.getName() + "§a com sucesso!"));
						}

					} else {
						targethp.setBanned(true);
						targethp.setBan_author(p.getName());
						targethp.setBan_time(0);
						targethp.setBan_reason(reason);
						targethp.ban(reason, p.getName(), 0);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players);
							if (!t.groupIsLarger(players, Groups.TRIAL)) {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
												+ reason + "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
												+ reason + "\n§fPor: " + p.getName()
												+ "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							}
						});
						p.sendMessage(TextComponent.fromLegacyText("§aVocê baniu §e" + target + "§a com sucesso!"));
			
					}
				}

			}
		}

	}
}
