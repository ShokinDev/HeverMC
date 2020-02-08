package br.com.hevermc.authentication.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.authentication.api.loader.PlayerLoader;
import br.com.hevermc.authentication.command.commons.HeverCommand;

public class LoginCommand extends HeverCommand {

	public LoginCommand() {
		super("login");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			LoginPlayer lp = new PlayerLoader(p).load().lp();
			if (lp.isLogged()) {
				p.sendMessage("�cVoc� j� est� logado");
			} else if (args.length == 0) {
				p.sendMessage("�aVoc� deve usar �e/login <senha>");
			} else {
				if (!args[0].equals(lp.getPassword())) {
					p.sendMessage("�cA senha est� incorreta!");
				} else if (!lp.isRegistred()) {
					p.sendMessage("�cVoc� n�o possui uma conta registrada!");
				} else {
					lp.setLogged(true);
					lp.update();
					p.sendMessage("�aVoc� foi autenticado com sucesso!");
				}
			}
		}
		return false;
	}

}
