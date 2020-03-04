package br.com.hevermc.authentication.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.authentication.Authentication;
import br.com.hevermc.authentication.api.BarUtil;
import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.authentication.api.ReflectionAPI;
import br.com.hevermc.authentication.api.loader.PlayerLoader;
import br.com.hevermc.authentication.command.commons.HeverCommand;

public class RegisterCommand extends HeverCommand {

	public RegisterCommand() {
		super("register");
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			LoginPlayer lp = new PlayerLoader(p).load().lp();
			if (lp.isLogged()) {
				p.sendMessage("�cVoc� j� est� logado");
			} else if (args.length < 3) {
				p.sendMessage("�aVoc� deve usar �e/register <senha> <senha novamente> <pin de seguran�a>");
			} else {
				if (!args[0].equals(args[1])) {
					p.sendMessage("�cAs senhas n�o s�o iguais!");
				} else if (!isInt(args[2]) && args[2].length() != 4){
					p.sendMessage("�cO pin deve ter no 4 digitos e apenas numeros!");
				} else if (lp.isRegistred()) {
					p.sendMessage("�cVoc� j� est� registrado, use /login <senha>!");
				} else {
					
					lp.setPassword(args[0]);
					lp.setPin(Integer.parseInt(args[2]));
					lp.setLogged(true);
					lp.update();
					BarUtil.updateBar(p, "�a�l�k!!!�f�l AGORA VOC� EST� �e�lAUTENTICADO �a�l�k!!!", 50);
					p.sendMessage("�aVoc� foi registrado com sucesso!");
					ReflectionAPI.sendTitle(p, "�3�lAUTENTICADO", "�fAgora voc� est� �alogado�f!", 15, 15,15);

					new BukkitRunnable() {
						
						@Override
						public void run() {
							if (!p.isOnline() || p == null) {
								cancel();
								return;
							}
							p.sendMessage("�aVoc� est� sendo conectado ao �blobby�f!");
							Authentication.getManager().getBungeeChannel().connect(p, "lobby");
						}
					}.runTaskTimer(Authentication.getInstance(), 15L, 15L);
					new BukkitRunnable() {
						@Override
						public void run() {
							BarUtil.removeBar(p);
	
						}
					}.runTaskLater(Authentication.getInstance(), 30L);
				}
			}
		}
		return false;
	}

}
