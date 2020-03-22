package br.com.hevermc.authentication.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.authentication.Authentication;
import br.com.hevermc.authentication.api.BarUtil;
import br.com.hevermc.authentication.api.BungeeChannelApi;
import br.com.hevermc.authentication.api.LoginPlayer;
import br.com.hevermc.authentication.api.ReflectionAPI;
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
				p.sendMessage("§e§lLOGIN §fVocê já está §a§lLOGADO§f!");
			} else if (args.length == 0) {
				p.sendMessage("§e§lLOGIN §fVocê deve usar §e/login <senha>");
			} else {
				if (!args[0].equals(lp.getPassword())) {
					p.sendMessage("§e§lLOGIN §fA senha está §c§lINCORRETA§f!");
				} else if (!lp.isRegistred()) {
					p.sendMessage("§e§lLOGIN §fVocê §4§lNÃO§f possui uma conta registrada!");
				} else {
					lp.setLogged(true);
					lp.update();
					BarUtil.updateBar(p, "§a§l§k!!!§f§l AGORA VOCÊ ESTÁ §3§lAUTENTICADO §a§l§k!!!", 50);
					p.sendMessage("§e§lLOGIN §fVocê foi §a§lAUTENTICADO§f com sucesso!");
					ReflectionAPI.sendTitle(p, "§3§lAUTENTICADO", "§fAgora você está §alogado§f!", 15, 15, 15);
		
					new BukkitRunnable() {

						@Override
						public void run() {
							if (!p.isOnline() || p == null) {
								cancel();
								return;
							}
							p.sendMessage("§b§lCONNECT §fVocê está sendo conectado ao §b§lLOBBY§f!");
							new BungeeChannelApi(Authentication.getInstance()).connect(p, "lobby");
						}
					}.runTaskTimer(Authentication.getInstance(), 0, 35L);
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
