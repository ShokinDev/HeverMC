package br.com.hevermc.authentication.api.loader;

import org.bukkit.entity.Player;

import br.com.hevermc.authentication.Authentication;
import br.com.hevermc.authentication.api.LoginPlayer;

public class PlayerLoader {

	Player p;

	public PlayerLoader(Player p) {
		this.p = p;
	}

	public PlayerLoader unload() {
		if (Authentication.getManager().loginplayer.containsKey(p)) {
			lp().unload();
			Authentication.getManager().loginplayer.remove(p);
		}
		return this;
	}

	public PlayerLoader load() {
		if (!Authentication.getManager().loginplayer.containsKey(p))
			Authentication.getManager().loginplayer.put(p,
					new LoginPlayer(p.getName(), p.getAddress().getHostName()).load());
		return this;
	}

	public LoginPlayer lp() {
		return Authentication.getManager().loginplayer.get(p);
	}

}
