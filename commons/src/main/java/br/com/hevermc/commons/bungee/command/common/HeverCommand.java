package br.com.hevermc.commons.bungee.command.common;

import br.com.hevermc.commons.bungee.account.HeverPlayer;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public abstract class HeverCommand extends Command {

	public HeverCommand(String command) {
		super(command);
	}

	public HeverCommand(String command, String aliases) {
		super(command, null, aliases);
	}

	public ProxiedPlayer toPlayer(CommandSender sender) {
		return (ProxiedPlayer) sender;
	}

	public boolean isPlayer(CommandSender sender) {
		if (sender instanceof ProxiedPlayer)
			return true;
		return false;
	}

	@Override
	public abstract void execute(CommandSender sender, String[] args);

	public boolean containsLetter(String a) {
		try {
			Integer.parseInt(a);
			return false;
		} catch (Exception e) {
		}
		return true;
	}

	public boolean requiredGroup(HeverPlayer hp, Groups group) {
		if (hp.getGroup().ordinal() >= group.ordinal())
			return true;
		return false;

	}

	public HeverPlayer toHeverPlayer(ProxiedPlayer p) {
		return PlayerLoader.getHP(p.getName());
	}

	public boolean isInt(String args) {
		try {
			Integer.parseInt(args);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean requiredGroup(ProxiedPlayer p, Groups group, boolean b) {
		HeverPlayer hp = PlayerLoader.getHP(p.getName());
		if (p.getServer().getInfo().getName().equals("login"))
			return false;

		if (hp.getGroup().ordinal() >= group.ordinal())
			return true;

		if (b)
			p.sendMessage(TextComponent.fromLegacyText(
					"§cVocê precisa do grupo §c§l" + group.getName() + " §cou superior para executar este comando!"));
		return false;

	}

}
