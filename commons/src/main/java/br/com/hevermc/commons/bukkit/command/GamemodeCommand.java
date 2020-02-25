package br.com.hevermc.commons.bukkit.command;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.account.HeverPlayer;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.command.commons.HeverCommand;
import br.com.hevermc.commons.enums.Groups;

public class GamemodeCommand extends HeverCommand {

	public GamemodeCommand() {
		super("gamemode", Arrays.asList("gm"));
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (isPlayer(sender)) {
			Player p = toPlayer(sender);
			if (hasGroup(p, Groups.BUILDER, true)) {
				if (args.length == 0) {
					p.sendMessage("§aVocê deve usar §e/gamemode <survival|creative>!");
				} else {
					GameMode gamemode = null;
					switch (args[0].toLowerCase()) {
					case "1":
						gamemode = GameMode.CREATIVE;
						break;
					case "s":
						gamemode = GameMode.SURVIVAL;
						break;
					case "c":
						gamemode = GameMode.CREATIVE;
						break;
					case "creative":
						gamemode = GameMode.CREATIVE;
						break;
					case "criativo":
						gamemode = GameMode.CREATIVE;
						break;
					case "0":
						gamemode = GameMode.SURVIVAL;
						break;
					case "survival":
						gamemode = GameMode.SURVIVAL;
						break;
					case "sobrevivencia":
						gamemode = GameMode.SURVIVAL;
						break;
					}
					if (gamemode == null) {
						p.sendMessage("§aVocê deve usar §e/gamemode <survival|creative>!");
					} else if (gamemode == p.getGameMode()) {
						p.sendMessage("§cVocê já está utilizando este gamemode.");
					} else {
						p.setGameMode(gamemode);
						p.sendMessage("§aVocê alterou seu gamemode para §b" + gamemode.toString() + "§a!");
						Bukkit.getOnlinePlayers().forEach(online_players -> {
							HeverPlayer hp = PlayerLoader.getHP(online_players);
							if (hp.groupIsLarger(Groups.GERENTE))
								online_players.sendMessage("§c[O staffer " + p.getName() + " alterou seu gamemode para "
										+ p.getGameMode().toString() + "]");

						});
					}
				}
			}
		}
		return false;
	}

}
