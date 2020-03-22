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

public class MuteCommand extends HeverCommand implements TabExecutor {

	public MuteCommand() {
		super("mute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 2) {
					p.sendMessage(
							TextComponent.fromLegacyText("§3§lMUTE §fVocê deve usar §b/mute <nickname> <reason>"));
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
									TextComponent.fromLegacyText("§3§lMUTE §fVocê §4§lNÃO§f pode mutar este jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(
									TextComponent.fromLegacyText("§3§lMUTE §fVocê não pode se §4§lAUTO-MUTAR§f!"));
						} else if (targethp.isMuted()) {
							p.sendMessage(
									TextComponent.fromLegacyText("§3§lMUTE §fEste jogador já está §3§lMUTADO§f!"));
						} else {

							targethp.mute(reason, p.getName(), 0l);

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players.getName());
								if (!t.groupIsLarger(Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"§7§o[O jogador " + targetp.getName() + " foi mutado]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
													+ "\n§fDuração: §4§lPERMANENTE").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"§7§o[O jogador " + targetp.getName() + " foi mutado]");
									msg_a.setHoverEvent(
											new HoverEvent(HoverEvent.Action.SHOW_TEXT,
													new ComponentBuilder(""
															+ "§cInformações sobre este mute:\n§fMotivo: " + reason
															+ "\n§fPor: " + p.getName() + "\n§fDuração: §4§lPERMANENTE")
																	.create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent
									.fromLegacyText("§3§lMUTE §fVocê mutou §b§l" + targetp.getName() + "§f com sucesso!"));
						}

					} else {

						targethp.mute(reason, p.getName(), 0);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.TRIAL)) {
								TextComponent msg_a = new TextComponent("§7§o[O jogador " + target + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
												+ "\n§fDuração: §4§lPERMANENTE").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent("§7§o[O jogador " + target + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
												+ "\n§fPor: " + p.getName() + "\n§fDuração: §4§lPERMANENTE").create()));
								players.sendMessage(msg_a);
							}
						});
						p.sendMessage(TextComponent
								.fromLegacyText("§3§lMUTE §fVocê mutou §b§l" + target + "§f com sucesso!"));

					}
				}

			}
		} else {
			if (args.length < 3) {
				sender.sendMessage(TextComponent.fromLegacyText("§aVocê deve usar §e/mute <nickname> <reason>"));
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
					if (targethp.isMuted()) {
						sender.sendMessage(TextComponent.fromLegacyText("§cEsse jogador já está mutado!"));
					} else {

						targethp.mute(reason, sender.getName(), 0l);

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.MODPLUS)) {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
												+ "\n§fDuração: §4§lPERMANENTE").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi mutado]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("" + "§cInformações sobre este mute:\n§fMotivo: " + reason
												+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4§lPERMANENTE")
														.create()));
								players.sendMessage(msg_a);
							}
						});
						sender.sendMessage(TextComponent
								.fromLegacyText("§aVocê mutou §e" + targetp.getName() + "§a com sucesso!"));
					}

				} else {

					targethp.mute(reason, sender.getName(), 0);

					Commons.getInstance().getProxy().getPlayers().forEach(players -> {
						HeverPlayer t = PlayerLoader.getHP(players.getName());
						if (!t.groupIsLarger(Groups.TRIAL)) {
							TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi mutado]");
							msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
											+ "\n§fDuração: §4§lPERMANENTE").create()));
							players.sendMessage(msg_a);
						} else {
							TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi mutado]");
							msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
									new ComponentBuilder("§cInformações sobre este mute:\n§fMotivo: " + reason
											+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4§lPERMANENTE")
													.create()));
							players.sendMessage(msg_a);
						}
					});
					sender.sendMessage(TextComponent.fromLegacyText("§aVocê mutou §e" + target + "§a com sucesso!"));

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
