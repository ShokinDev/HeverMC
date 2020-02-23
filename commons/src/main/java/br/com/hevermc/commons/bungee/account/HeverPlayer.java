package br.com.hevermc.commons.bungee.account;

import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;
import br.com.hevermc.commons.enums.Tags;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
public class HeverPlayer {
	@Setter
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
	@Setter
	boolean banned;
	@Setter
	String ban_reason;
	@Setter
	String ban_author;
	@Setter
	long ban_time;
	@Setter
	boolean muted;
	@Setter
	String mute_reason;
	@Setter
	String mute_author;
	@Setter
	long mute_time;
	@Setter
	int pvp_kills;
	@Setter
	int pvp_deaths;
	@Setter
	int pvp_ks;

	public HeverPlayer(ProxiedPlayer p) {
		this.name = p.getName().toLowerCase();
		this.ip = p.getAddress().getHostName();
	}

	public HeverPlayer(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}

	public boolean groupIsLarger(Groups group) {
		if (getGroup().ordinal() >= group.ordinal())
			return true;

		return false;
	}

	public boolean groupIsLarger(ProxiedPlayer p, Groups group) {
		if (p.getServer().getInfo().getName().equals("login"))
			return false;
		if (getGroup().ordinal() >= group.ordinal())
			return true;

		return false;
	}

	public void load() {
		try {
			if (!Commons.getManager().getSQLManager().checkString("hever_players", "name", getName())) {
				Commons.getManager().getSQLManager().insertPlayer(name, ip);
			}
			setCash(Commons.getManager().getSQLManager().getInt("hever_players", "name", "cash", getName()));
			xp = Commons.getManager().getSQLManager().getInt("hever_ranking", "name", "xp", getName());
			groupExpireIn = Commons.getManager().getSQLManager().getLong("hever_groups", "name", "expireIn", getName());
			lastLogin = Commons.getManager().getSQLManager().getLong("hever_players", "name", "last_login", getName());
			firstLogin = Commons.getManager().getSQLManager().getLong("hever_players", "name", "first_login",
					getName());
			setGroup(Groups.getGroup(
					Commons.getManager().getSQLManager().getString("hever_groups", "name", "group", getName())));
			rank = Ranks.getRank(
					Commons.getManager().getSQLManager().getString("hever_ranking", "name", "ranking", getName()));
			banned = Commons.getManager().getSQLManager().checkString("hever_bans", "name", getName());
			pvp_kills = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "kills", getName());
			pvp_deaths = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "deaths", getName());
			pvp_ks = Commons.getManager().getSQLManager().getInt("hever_kitpvp", "name", "ks", getName());
			if (banned) {
				ban_author = Commons.getManager().getSQLManager().getString("hever_bans", "name", "author", getName());
				ban_reason = Commons.getManager().getSQLManager().getString("hever_bans", "name", "reason", getName());
				ban_time = Commons.getManager().getSQLManager().getLong("hever_bans", "name", "time", getName());
			}
			muted = Commons.getManager().getSQLManager().checkString("hever_mutes", "name", getName());
			if (muted) {
				mute_author = Commons.getManager().getSQLManager().getString("hever_mutes", "name", "author",
						getName());
				mute_reason = Commons.getManager().getSQLManager().getString("hever_mutes", "name", "reason",
						getName());
				mute_time = Commons.getManager().getSQLManager().getLong("hever_mutes", "name", "time", getName());
			}

			Commons.getManager().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks);
			Commons.getManager().log("Conta de " + this.name + " foi carregada!");
		} catch (Exception e) {
			e.printStackTrace();
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
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "kills", "name", getPvp_kills(), getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "deaths", "name", getPvp_deaths(),
					getName());
			Commons.getManager().getSQLManager().updateInt("hever_kitpvp", "ks", "name", getPvp_ks(), getName());
			Commons.getManager().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks);

			Commons.getManager().log("Conta de " + this.name + " foi atualizada!");
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
		}
	}

	public void ban(String reason, String author, long time) {
		try {
			Commons.getManager().getSQLManager().insertBan(name, author, reason, time);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("Não foi possivel banir a conta de " + this.name);
		}
	}

	public void mute(String reason, String author, long time) {
		try {
			Commons.getManager().getSQLManager().insertMute(name, author, reason, time);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("Não foi possivel mutar a conta de " + this.name);
		}
	}

	public void unload() {
		try {

			Commons.getManager().log("Conta de " + this.name + " sendo descarregada!");
			Commons.getManager().getRedis().del(this.name);
			setGroup(null);
			setCash(0);
			setXp(0);
			setRank(null);
			setName(null);
			setIp(null);
			setLastLogin(0l);
			setFirstLogin(0l);
			Commons.getManager().log("Conta descarregada!");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
		}
	}

}
