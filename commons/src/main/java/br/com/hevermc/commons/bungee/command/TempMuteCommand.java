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

public class TempMuteCommand extends HeverCommand {

	public TempMuteCommand() {
		super("tempmute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = toPlayer(sender);
			if (requiredGroup(p, Groups.TRIAL, true)) {
				if (args.length < 3) {
					p.sendMessage(
							TextComponent.fromLegacyText("�aVoc� deve usar �e/tempmute <nickname> <time> <reason>"));
				} else {
					String target = args[0];
					HeverPlayer hp = toHeverPlayer(p);
					String reason;
					String time = "";
					StringBuilder sb = new StringBuilder();
					for (int i = 1; i < args.length; i++)
						sb.append(args[i]).append(" ");
					reason = sb.toString();
					ProxiedPlayer targetp = Commons.getInstance().getProxy().getPlayer(target);
					HeverPlayer targethp = PlayerLoader.getHP(target);
					if (targetp != null) {
						if (targethp.getGroup().ordinal() > hp.getGroup().ordinal()) {
							p.sendMessage(TextComponent.fromLegacyText("�cVoc� n�o pode mutar este jogador!"));
						} else if (target.toLowerCase().equals(p.getName().toLowerCase())) {
							p.sendMessage(TextComponent.fromLegacyText("�cVoc� n�o pode se auto-mutar!"));
						} else if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
							p.sendMessage(TextComponent.fromLegacyText("�aVoc� deve usar �e/tempmute <nickname> <time> <reason>"));
						} else {
							int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("s", "").replace("y", ""));
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

							
							targethp.setMuted(true);
							targethp.setMute_author(p.getName());
							targethp.setMute_time(c.getTimeInMillis());
							targethp.setMute_reason(reason);
							targethp.mute(reason, p.getName(), c.getTimeInMillis());

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players);
								if (!t.groupIsLarger(players, Groups.MODPLUS)) {
									TextComponent msg_a = new TextComponent(
											"�c[O jogador " + targetp.getName() + " foi mutado]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("�cInforma��es sobre este mutamento:\n�fMotivo: "
													+ reason + "\n�fDura��o: �4temporariamente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"�c[O jogador " + targetp.getName() + " foi mumtado]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("�cInforma��es sobre este mutamento:\n�fMotivo: "
													+ reason + "\n�fPor: " + p.getName()
													+ "\n�fDura��o: �4temporariamente").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent
									.fromLegacyText("�aVoc� mutou �e" + targetp.getName() + "�a temporariamente com sucesso!"));
						}

					} else {
						if (targethp.getGroup().ordinal() > hp.getGroup().ordinal()) {
							p.sendMessage(TextComponent.fromLegacyText("�cVoc� n�o pode mutar este jogador!"));
						} else if (!isInt(time.replace("d", "").replace("s", "").replace("m", "").replace("y", ""))) {
							p.sendMessage(TextComponent.fromLegacyText("�aVoc� deve usar �e/tempmute <nickname> <time> <reason>"));
						} else {
							int timeint = Integer.valueOf(time.replace("d", "").replace("m", "").replace("s", "").replace("y", ""));
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

							targethp.setBanned(true);
							targethp.setBan_author(p.getName());
							targethp.setBan_time(c.getTimeInMillis());
							targethp.setBan_reason(reason);
							targethp.ban(reason, p.getName(), c.getTimeInMillis());

							Commons.getInstance().getProxy().getPlayers().forEach(players -> {
								HeverPlayer t = PlayerLoader.getHP(players);
								if (!t.groupIsLarger(players, Groups.TRIAL)) {
									TextComponent msg_a = new TextComponent(
											"�c[O jogador " + target + " foi mutar]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("�cInforma��es sobre este mutamento:\n�fMotivo: "
													+ reason + "\n�fDura��o: �4temporariamente").create()));
									players.sendMessage(msg_a);
								} else {
									TextComponent msg_a = new TextComponent(
											"�c[O jogador " + target + " foi mutar]");
									msg_a.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
											new ComponentBuilder("�cInforma��es sobre este mutamento:\n�fMotivo: "
													+ reason + "\n�fPor: " + p.getName()
													+ "\n�fDura��o: �4temporariamente �c(" + DateUtil.formatDifference(c.getTimeInMillis()) + ")").create()));
									players.sendMessage(msg_a);
								}
							});
							p.sendMessage(TextComponent
									.fromLegacyText("�aVoc� mutou �e" + target + "�a temporariamente com sucesso!"));
						}
					}
				}

			}
		}

	}
}
