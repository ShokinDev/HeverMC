package br.com.hevermc.commons.bungee.command;

import java.util.Calendar;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.DateUtil;
import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TempBanCommand extends HeverCommand {

	public TempBanCommand() {
		super("tempban");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 3) {
					p.sendMessage(TextComponent
							.fromLegacyText("§4§lBAN §fVocê deve utilizar §b/tempban <nickname> <time> <reason>"));
				} else {
					String target = args[0];
					HeverPlayer hp = toHeverPlayer(p);
					String reason;
					String time = args[1].toLowerCase();
					StringBuilder sb = new StringBuilder();
					for (int i = 2; i < args.length; i++)
						sb.append(args[i]).append(" ");
					reason = sb.toString();
					ProxiedPlayer targetp = Commons.getInstance().getProxy().getPlayer(target);
					HeverPlayer targethp = PlayerLoader.getHP(target);
					if (targetp != null) {
						if (targethp.getGroup().ordinal() > hp.getGroup().ordinal()) {
							p.sendMessage(
									TextComponent.fromLegacyText("§4§lBAN §fVocê não pode §4§lBANIR §feste jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(TextComponent.fromLegacyText("§4§lBAN §fVocê não pode se §4§lAUTO-BANIR§f!"));
						} else if (!isInt(time.replace("d", "").replace("h", "").replace("s", "").replace("m", "")
								.replace("y", ""))) {
							p.sendMessage(TextComponent.fromLegacyText(
									"§4§lBAN §fVocê deve utilizar §b/tempban <nickname> <time> <reason>"));
						} else {
							int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("h", "")
									.replace("s", "").replace("y", ""));
							String format = time.replace(timeint + "", "");
							Calendar c = Calendar.getInstance();
							if (format.equalsIgnoreCase("s")) {
								c.add(Calendar.SECOND, timeint);
							} else if (format.equalsIgnoreCase("d")) {
								c.add(Calendar.DAY_OF_YEAR, timeint);
							} else if (format.equalsIgnoreCase("h")) {
								c.add(Calendar.DAY_OF_YEAR, timeint);
							} else if (format.equalsIgnoreCase("m")) {
								c.add(Calendar.MONTH, timeint);
							} else if (format.equalsIgnoreCase("y")) {
								c.add(Calendar.YEAR, timeint);
							}

							targetp.disconnect(TextComponent
									.fromLegacyText("§4§lBANIDO\n\n§fVocê foi banido temporariamente!\n\n§fMotivo: §c"
											+ reason + "\n§fPor: " + p.getName() + "\n§fPor: " + p.getName() + "\nAté: "
											+ DateUtil.formatDifference(c.getTimeInMillis())
											+ "\n\n§fAchou sua punição injustas Contate-nós via §3§lDISCORD§f!\n§ehttps://discord.gg/VgbDwqS"));

							targethp.ban(reason, p.getName(), c.getTimeInMillis());

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players.getName());
								if (!t.groupIsLarger(Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fDuração: §4temporariamente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"§c[O jogador " + targetp.getName() + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fPor: " + p.getName()
													+ "\n§fDuração: §4temporariamente §c("
													+ DateUtil.formatDifference(c.getTimeInMillis()) + ")").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent.fromLegacyText("§4§lBAN §fVocê baniu §b§l" + targetp.getName()
									+ "§f temporariamente com sucesso!"));
						}

					} else {
						if (targethp.getGroup().ordinal() > hp.getGroup().ordinal()) {
							p.sendMessage(
									TextComponent.fromLegacyText("§4§lBAN §fVocê §4§lNÃO §fpode banir este jogador!"));
						} else if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
							p.sendMessage(TextComponent.fromLegacyText(
									"§4§lBAN §fVocê deve utilizar §b/tempban <nickname> <time> <reason>"));
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

							targethp.ban(reason, p.getName(), c.getTimeInMillis());

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players.getName());
								if (!t.groupIsLarger(Groups.TRIAL)) {
									TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fDuração: §4temporariamente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: "
													+ reason + "\n§fPor: " + p.getName()
													+ "\n§fDuração: §4temporariamente §c("
													+ DateUtil.formatDifference(c.getTimeInMillis()) + ")").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent.fromLegacyText(
									"§4§lBAN §fVocê baniu §b§l" + target + "§f temporariamente com sucesso!"));
						}
					}
				}

			}
		} else {
			if (args.length < 3) {
				sender.sendMessage(
						TextComponent.fromLegacyText("§aVocê deve usar §e/tempban <nickname> <time> <reason>"));
			} else {
				String target = args[0];
				String reason;
				String time = args[1].toLowerCase();
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++)
					sb.append(args[i]).append(" ");
				reason = sb.toString();
				ProxiedPlayer targetp = Commons.getInstance().getProxy().getPlayer(target);
				HeverPlayer targethp = PlayerLoader.getHP(target);
				if (targetp != null) {
					if (!isInt(time.replace("d", "").replace("h", "").replace("s", "").replace("m", "").replace("y",
							""))) {
						sender.sendMessage(
								TextComponent.fromLegacyText("§aVocê deve usar §e/tempban <nickname> <time> <reason>"));
					} else {
						int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("h", "")
								.replace("s", "").replace("y", ""));
						String format = time.replace(timeint + "", "");
						Calendar c = Calendar.getInstance();
						if (format.equalsIgnoreCase("s")) {
							c.add(Calendar.SECOND, timeint);
						} else if (format.equalsIgnoreCase("d")) {
							c.add(Calendar.DAY_OF_YEAR, timeint);
						} else if (format.equalsIgnoreCase("h")) {
							c.add(Calendar.DAY_OF_YEAR, timeint);
						} else if (format.equalsIgnoreCase("m")) {
							c.add(Calendar.MONTH, timeint);
						} else if (format.equalsIgnoreCase("y")) {
							c.add(Calendar.YEAR, timeint);
						}

						targetp.disconnect(TextComponent
								.fromLegacyText("§4§lBANIDO\n\n§fVocê foi banido temporariamente!\n\n§fMotivo: §c"
										+ reason + "\n§fPor: " + sender.getName() + "\n§fPor: " + sender.getName()
										+ "\nAté: " + DateUtil.formatDifference(c.getTimeInMillis())
										+ "\n\n§fAchou sua punição injustas Contate-nós via §3§lDISCORD§f!\n§ehttps://discord.gg/VgbDwqS"));

						targethp.ban(reason, sender.getName(), c.getTimeInMillis());

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.MODPLUS)) {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fDuração: §4temporariamente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent(
										"§c[O jogador " + targetp.getName() + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4temporariamente §c("
												+ DateUtil.formatDifference(c.getTimeInMillis()) + ")").create()));
								players.sendMessage(msg_a);
							}
						});
						sender.sendMessage(TextComponent.fromLegacyText(
								"§aVocê baniu §e" + targetp.getName() + "§a temporariamente com sucesso!"));
					}

				} else {
					if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
						sender.sendMessage(
								TextComponent.fromLegacyText("§aVocê deve usar §e/tempban <nickname> <time> <reason>"));
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

						targethp.ban(reason, sender.getName(), c.getTimeInMillis());

						Commons.getInstance().getProxy().getPlayers().forEach(players -> {
							HeverPlayer t = PlayerLoader.getHP(players.getName());
							if (!t.groupIsLarger(Groups.TRIAL)) {
								TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fDuração: §4temporariamente").create()));
								players.sendMessage(msg_a);
							} else {
								TextComponent msg_a = new TextComponent("§c[O jogador " + target + " foi banido]");
								msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
										new ComponentBuilder("§cInformações sobre este banimento:\n§fMotivo: " + reason
												+ "\n§fPor: " + sender.getName() + "\n§fDuração: §4temporariamente §c("
												+ DateUtil.formatDifference(c.getTimeInMillis()) + ")").create()));
								players.sendMessage(msg_a);
							}
						});
						sender.sendMessage(TextComponent
								.fromLegacyText("§aVocê baniu §e" + target + "§a temporariamente com sucesso!"));
					}
				}
			}

		}

	}
}
