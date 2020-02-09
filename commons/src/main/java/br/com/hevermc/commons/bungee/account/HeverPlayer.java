package br.com.hevermc.commons.bungee.account;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.account.loader.PlayerLoader;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;
import br.com.hevermc.commons.enums.Tags;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
public class HeverPlayer {
	String name;
	@Setter
	String ip;
	@Setter
	int cash;
	@Setter
	int xp;
	@Setter
	long groupExpireIn;
	@Setter
	long firstLogin;
	@Setter
	long lastLogin;
	@Setter
	Groups group;
	@Setter
	Ranks rank;
	Tags tag;

	public HeverPlayer(ProxiedPlayer p) {
		this.name = p.getName().toLowerCase();
		this.ip = p.getAddress().getHostName();
	}

	public HeverPlayer(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}
	public boolean groupIsLarger(ProxiedPlayer p, Groups group) {
		HeverPlayer hp = new PlayerLoader(p).getHP();
		if (p.getServer().getInfo().getName().equals("login"))
			return false;
		if (hp.getGroup().ordinal() >= group.ordinal())
			return true;

		return false;

	}

	public void load() {
		try {
			if (!Commons.getManager().getSQLManager().checkString("hever_players", "name", getName())) {
				Commons.getManager().getSQLManager().insertPlayer(name, ip);
			}
			cash = Commons.getManager().getSQLManager().getInt("hever_players", "name", "cash", getName());
			xp = Commons.getManager().getSQLManager().getInt("hever_ranking", "name", "xp", getName());
			groupExpireIn = Commons.getManager().getSQLManager().getLong("hever_groups", "name", "expireIn", getName());
			lastLogin = Commons.getManager().getSQLManager().getLong("hever_players", "name", "last_login", getName());
			firstLogin = Commons.getManager().getSQLManager().getLong("hever_players", "name", "first_login",
					getName());
			group = Groups.getGroup(
					Commons.getManager().getSQLManager().getString("hever_groups", "name", "group", getName()));
			rank = Ranks.getRank(
					Commons.getManager().getSQLManager().getString("hever_ranking", "name", "ranking", getName()));
			Commons.getManager().log("Conta de " + this.name + " foi carregada!");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel carregar a conta de " + this.name);
		}
	}

	public void update() {
		try {
			Commons.getManager().getSQLManager().updateInt("hever_players", "cash", "name", getCash(), getName());
			Commons.getManager().getSQLManager().updateInt("hever_ranking", "xp", "name", getXp(), getName());
			Commons.getManager().getSQLManager().updateString("hever_groups", "group", "name", getGroup().toString(),
					getName());
			Commons.getManager().getSQLManager().updateLong("hever_players", "first_login", "name", getFirstLogin(),
					getName());
			Commons.getManager().getSQLManager().updateLong("hever_players", "last_login", "name", getLastLogin(),
					getName());
			Commons.getManager().getSQLManager().updateLong("hever_groups", "expireIn", "name", getGroupExpireIn(),
					getName());
			Commons.getManager().getSQLManager().updateString("hever_ranking", "ranking", "name", getRank().toString(),
					getName());
			Commons.getManager().log("Conta de " + this.name + " foi atualizada!");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
		}
	}
	public void unload() {
		try {

			Commons.getManager().log("Conta de " + this.name + " sendo descarregada!");
			cash = 0;
			xp = 0;
			group = null;
			rank = null;
			name = null;
			ip = null;
			lastLogin = 0l;
			firstLogin = 0l;
			Commons.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
		}
	}

}
