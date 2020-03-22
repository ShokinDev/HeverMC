package br.com.hevermc.commons.bungee.command;

import br.com.hevermc.commons.bungee.command.common.HeverCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DiscordCommand extends HeverCommand {

	public DiscordCommand() {
		super("discord", "aplicar");
	}

	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			p.sendMessage(TextComponent.fromLegacyText("§3§lDISCORD §fO discord do servidor é §Ahttps://discord.gg/VgbDwqS§f!"));
		}
	}

}
