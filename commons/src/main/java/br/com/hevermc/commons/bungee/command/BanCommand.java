package br.com.hevermc.commons.bungee.command;

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
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BanCommand extends HeverCommand implements TabExecutor {

	public BanCommand() {
		super("ban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 2) {
					p.sendMessage(
							TextComponent.fromLegacyText("§4§lBAN §fVocê deve utilizar §b/ban <nickname> <reason>"));
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
							p.sendMessage(
									TextComponent.fromLegacyText("§4§lBAN §fVocê não pode §4§lBANIR§f este jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(TextComponent.fromLegacyText("§4§lBAN §fVocê não pode se §4§lAUTO-BANIR§f!"));
						} else if (targethp.isBanned()) {
							p.sendMessage(TextComponent.fromLegacyText("§4§lBAN §fEsse jogador já está §4§lBANIDO§f!"));
						} else {

							targetp.disconnect(TextComponent
									.fromLegacyText("§4§lBANIDO\n\n§fVocê foi banido permanentemente!\n\n§fMotivo: §c"
											+ reason + "\n§fPor: " + p.getName()
											+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ehttps://discord.gg/VgbDwqS"));

							targethp.ban(reason, p.getName(), 0l);

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players.getName());
								if (!t.groupIsLarger(Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"§7§o[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"§7§o[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fPor: " + p.getName()
													+ "\n§fDuração: §4permanentemente").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent
									.fromLegacyText("§4§lBAN §fVocê baniu §c" + targetp.getName() + "§f com sucesso!"));
						}

					} else {

						targethp.ban(reason, p.getName(), 0l);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.TRIAL)) {
								TextComponent msg_a = new TextComponent("§7§o[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent("§7§o[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fPor: " + p.getName() + "\n§fDuração: §4permanentemente")
														.create()));
								players.sendMessage(msg_a);
							}
						});
						p.sendMessage(
								TextComponent.fromLegacyText("§4§lBAN §fVocê baniu §c" + target + "§f com sucesso!"));

					}
				}

			}
		} else {
			if (args.length < 3) {
				sender.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/ban <nickname> <reason>"));
			} else {
				String target = args[0];
				String reason;
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++)
					sb.append(args[i]).append(" ");
				reason = sb.toString();
				ProxiedPlayer targetp = Commons.getInstance().getProxy().getPlayer(target);
				HeverPlayer targethp = PlayerLoader.getHP(target);
				if (targetp != null) {
					if (target.toLowerCase().equals(sender.getName().toLowerCase())) {
						sender.sendMessage(TextComponent.fromLegacyText("§cVocê não pode se auto-banir!"));
					} else if (targethp.isBanned()) {
						sender.sendMessage(TextComponent.fromLegacyText("§cEsse jogador já está banido!"));
					} else {

						targetp.disconnect(TextComponent
								.fromLegacyText("§4§lBANIDO\n\n§fVocê foi banido permanentemente!\n\n§fMotivo: §c"
										+ reason + "\n§fPor: " + sender.getName()
										+ "\n\n§fAchou sua punição injusta? Contate-nós via §3§lDISCORD§f!\n§ehttps://discord.gg/VgbDwqS"));

						targethp.ban(reason, sender.getName(), 0l);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.MODPLUS)) {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fDuração: §4permanentemente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4permanentemente")
														.create()));
								players.sendMessage(msg_a);
							}
						});
						sender.sendMessage(TextComponent
								.fromLegacyText("§aVocê baniu §e" + targetp.getName() + "§a com sucesso!"));
					}

				} else {

					targethp.ban(reason, sender.getName(), 0l);

					Commons.getInstance().getProxy().getPlayers().forEach(players -> {
						HeverPlayer t = PlayerLoader.getHP(players.getName());
						if (!t.groupIsLarger(Groups.TRIAL)) {
							TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
							msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
											+ "\n§fDuração: §4permanentemente").create()));
							players.sendMessage(msg_a);
						} else {
							TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
							msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
											+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4permanentemente")
													.create()));
							players.sendMessage(msg_a);
						}
					});
					sender.sendMessage(TextComponent.fromLegacyText("§aVocê baniu §e" + target + "§a com sucesso!"));
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
		}
		return match;
	}
}
