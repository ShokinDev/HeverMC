package br.com.hevermc.commons.bungee.account;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.hevermc.commons.backend.Backend;
import br.com.hevermc.commons.bungee.Commons;
import br.com.hevermc.commons.bungee.Manager;
import br.com.hevermc.commons.bungee.events.GeneralEvents;
import br.com.hevermc.commons.clan.Clans;
import br.com.hevermc.commons.clan.Hierarchy;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;
import br.com.hevermc.commons.enums.Tags;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Getter
@Setter
public class HeverPlayer {

	String name;
	String ip;
	int cash;
	int xp;
	long groupExpireIn;
	long firstLogin;
	long lastLogin;
	Groups group;
	Ranks rank;
	Tags tag;
	boolean banned;
	String ban_reason;
	String ban_author;
	long ban_time;
	boolean muted;
	String mute_reason;
	String mute_author;
	long mute_time;
	int pvp_kills = 0;
	int pvp_deaths = 0;
	int pvp_ks = 0;
	int hg_kills = 0;
	int hg_deaths = 0;
	int hg_wins = 0;
	int accountType = 0;

	Clans clan;
	Hierarchy clan_hierarchy;

	public HeverPlayer(ProxiedPlayer p) {
		this.name = p.getName().toLowerCase();
		this.ip = p.getAddress().getHostName();
	}

	public HeverPlayer(String name, String ip) {
		this.name = name.toLowerCase();
		this.ip = ip.toLowerCase();
	}

	public boolean groupIsLarger(Groups group) {
		if (Commons.getManager().getBackend().getRedis().contains("update:" + this.name.toLowerCase())) {
			load();
		}
		if (getGroup().ordinal() >= group.ordinal())
			return true;

		return false;
	}

	public boolean groupIsLarger(ProxiedPlayer p, Groups group) {
		if (Commons.getManager().getBackend().getRedis().contains("update:" + this.name.toLowerCase())) {
			load();
		}
		if (p.getServer().getInfo().getName().equals("login"))
			return false;
		if (getGroup().ordinal() >= group.ordinal())
			return true;

		return false;
	}

	public void clan() {
		Manager m = Commons.getManager();
		Backend b = m.getBackend();
		if (b.getSql().checkString("clans", "owner", name)) {
			String namec = b.getSql().getString("clans", "owner", "name", name);

			String admin1 = b.getSql().getString("clans", "owner", "admin1", name);
			String admin2 = b.getSql().getString("clans", "owner", "admin2", name);

			String member1 = b.getSql().getString("clans", "owner", "member1", name);
			String member2 = b.getSql().getString("clans", "owner", "member2", name);
			String member3 = b.getSql().getString("clans", "owner", "member3", name);
			String member4 = b.getSql().getString("clans", "owner", "member4", name);
			String member5 = b.getSql().getString("clans", "owner", "member5", name);

			int xp = b.getSql().getInt("clans", "owner", "xp", name);

			clan = new Clans(namec, name, member1, member2, member3, member4, member5, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.OWNER;
		} else if (b.getSql().checkString("clans", "admin1", name)) {
			String namec = b.getSql().getString("clans", "admin1", "name", name);

			String owner = b.getSql().getString("clans", "admin1", "owner", name);
			String admin2 = b.getSql().getString("clans", "admin1", "admin2", name);

			String member1 = b.getSql().getString("clans", "admin1", "member1", name);
			String member2 = b.getSql().getString("clans", "admin1", "member2", name);
			String member3 = b.getSql().getString("clans", "admin1", "member3", name);
			String member4 = b.getSql().getString("clans", "admin1", "member4", name);
			String member5 = b.getSql().getString("clans", "admin1", "member5", name);

			int xp = b.getSql().getInt("clans", "admin1", "xp", name);

			clan = new Clans(namec, owner, member1, member2, member3, member4, member5, name, admin2, xp);
			clan_hierarchy = Hierarchy.ADMIN;
		} else if (b.getSql().checkString("clans", "admin2", name)) {
			String namec = b.getSql().getString("clans", "admin2", "name", name);

			String owner = b.getSql().getString("clans", "admin2", "owner", name);
			String admin1 = b.getSql().getString("clans", "admin2", "admin1", name);

			String member1 = b.getSql().getString("clans", "admin2", "member1", name);
			String member2 = b.getSql().getString("clans", "admin2", "member2", name);
			String member3 = b.getSql().getString("clans", "admin2", "member3", name);
			String member4 = b.getSql().getString("clans", "admin2", "member4", name);
			String member5 = b.getSql().getString("clans", "admin2", "member5", name);

			int xp = b.getSql().getInt("clans", "admin2", "xp", name);

			clan = new Clans(namec, owner, member1, member2, member3, member4, member5, admin1, name, xp);
			clan_hierarchy = Hierarchy.ADMIN;
		} else if (b.getSql().checkString("clans", "member1", name)) {
			String namec = b.getSql().getString("clans", "member1", "name", name);

			String owner = b.getSql().getString("clans", "member1", "owner", name);
			String admin1 = b.getSql().getString("clans", "member1", "admin1", name);
			String admin2 = b.getSql().getString("clans", "member1", "admin2", name);

			String member2 = b.getSql().getString("clans", "member1", "member2", name);
			String member3 = b.getSql().getString("clans", "member1", "member3", name);
			String member4 = b.getSql().getString("clans", "member1", "member4", name);
			String member5 = b.getSql().getString("clans", "member1", "member5", name);

			int xp = b.getSql().getInt("clans", "member1", "xp", name);

			clan = new Clans(namec, owner, name, member2, member3, member4, member5, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.MEMBER;
			// 2

		} else if (b.getSql().checkString("clans", "member2", name)) {
			String namec = b.getSql().getString("clans", "member2", "name", name);

			String owner = b.getSql().getString("clans", "member2", "owner", name);
			String admin1 = b.getSql().getString("clans", "member2", "admin1", name);
			String admin2 = b.getSql().getString("clans", "member2", "admin2", name);

			String member = b.getSql().getString("clans", "member2", "member1", name);
			String member3 = b.getSql().getString("clans", "member2", "member3", name);
			String member4 = b.getSql().getString("clans", "member2", "member4", name);
			String member5 = b.getSql().getString("clans", "member2", "member5", name);

			int xp = b.getSql().getInt("clans", "member2", "xp", name);

			clan = new Clans(namec, owner, member, name, member3, member4, member5, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.MEMBER;

		} else if (b.getSql().checkString("clans", "member3", name)) {
			String namec = b.getSql().getString("clans", "member3", "name", name);

			String owner = b.getSql().getString("clans", "member3", "owner", name);
			String admin1 = b.getSql().getString("clans", "member3", "admin1", name);
			String admin2 = b.getSql().getString("clans", "member3", "admin2", name);

			String member = b.getSql().getString("clans", "member3", "member1", name);
			String member2 = b.getSql().getString("clans", "member3", "member2", name);
			String member4 = b.getSql().getString("clans", "member3", "member4", name);
			String member5 = b.getSql().getString("clans", "member3", "member5", name);

			int xp = b.getSql().getInt("clans", "member3", "xp", name);

			clan = new Clans(namec, owner, member, member2, name, member4, member5, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.MEMBER;

		} else if (b.getSql().checkString("clans", "member4", name)) {
			String namec = b.getSql().getString("clans", "member4", "name", name);

			String owner = b.getSql().getString("clans", "member4", "owner", name);
			String admin1 = b.getSql().getString("clans", "member4", "admin1", name);
			String admin2 = b.getSql().getString("clans", "member4", "admin2", name);

			String member = b.getSql().getString("clans", "member4", "member1", name);
			String member2 = b.getSql().getString("clans", "member4", "member2", name);
			String member3 = b.getSql().getString("clans", "member4", "member3", name);
			String member5 = b.getSql().getString("clans", "member4", "member5", name);

			int xp = b.getSql().getInt("clans", "member3", "xp", name);

			clan = new Clans(namec, owner, member, member2, member3, name, member5, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.MEMBER;
		} else if (b.getSql().checkString("clans", "member5", name)) {
			String namec = b.getSql().getString("clans", "member5", "name", name);

			String owner = b.getSql().getString("clans", "member5", "owner", name);
			String admin1 = b.getSql().getString("clans", "member5", "admin1", name);
			String admin2 = b.getSql().getString("clans", "member5", "admin2", name);

			String member = b.getSql().getString("clans", "member5", "member1", name);
			String member2 = b.getSql().getString("clans", "member5", "member2", name);
			String member3 = b.getSql().getString("clans", "member5", "member3", name);
			String member4 = b.getSql().getString("clans", "member5", "member4", name);

			int xp = b.getSql().getInt("clans", "member4", "xp", name);

			clan = new Clans(namec, owner, member, member2, member3, member4, name, admin1, admin2, xp);
			clan_hierarchy = Hierarchy.MEMBER;
		} else {
			clan = null;
			clan_hierarchy = null;
		}
	}

	public void load() {
		try {
			if (!Commons.getManager().getBackend().getSql().checkString("players", "name", getName())) {
				Commons.getManager().getBackend().getSql().insertPlayer(name);
			}
			Manager m = Commons.getManager();
			this.group = Groups.getGroup(m.getBackend().getSql().getString("players", "name", "group", this.name));
			this.rank = Ranks.getRank(m.getBackend().getSql().getString("players_rank", "name", "ranking", this.name));
			this.xp = m.getBackend().getSql().getInt("players_rank", "name", "xp", this.name);
			this.cash = m.getBackend().getSql().getInt("players_rank", "name", "cash", this.name);
			this.accountType = m.getBackend().getSql().getInt("players", "name", "accountType", this.name);
			this.groupExpireIn = m.getBackend().getSql().getLong("players", "name", "groupExpireIn", this.name);
			this.lastLogin = m.getBackend().getSql().getLong("players", "name", "firstlogin", this.name);
			this.firstLogin = m.getBackend().getSql().getLong("players", "name", "lastlogin", this.name);
			this.hg_kills = m.getBackend().getSql().getInt("hg", "name", "kills", this.name);
			this.hg_deaths = m.getBackend().getSql().getInt("hg", "name", "deaths", this.name);
			this.hg_wins = m.getBackend().getSql().getInt("hg", "name", "wins", this.name);
			this.pvp_kills = m.getBackend().getSql().getInt("kitpvp", "name", "kills", this.name);
			this.pvp_deaths = m.getBackend().getSql().getInt("kitpvp", "name", "deaths", this.name);
			this.pvp_ks = m.getBackend().getSql().getInt("kitpvp", "name", "ks", this.name);
			this.banned = m.getBackend().getSql().checkString("bans", "name", this.name);
			this.muted = m.getBackend().getSql().checkString("mutes", "name", this.name);
			clan();
			if (banned) {
				ban_author = Commons.getManager().getBackend().getSql().getString("bans", "name", "author", getName());
				ban_reason = Commons.getManager().getBackend().getSql().getString("bans", "name", "reason", getName());
				ban_time = Commons.getManager().getBackend().getSql().getLong("bans", "name", "time", getName());
			}
			if (muted) {
				mute_author = Commons.getManager().getBackend().getSql().getString("mutes", "name", "author",
						getName());
				mute_reason = Commons.getManager().getBackend().getSql().getString("mutes", "name", "reason",
						getName());
				mute_time = Commons.getManager().getBackend().getSql().getLong("mutes", "name", "time", getName());
			}
			if (groupExpireIn > 0) {
				if (new Date().after(new Date(groupExpireIn))) {
					setGroup(Groups.MEMBRO);
				}
			}
			Commons.getManager().getBackend().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks
							+ ",hg_kills:" + hg_kills + ",hg_deaths:" + hg_deaths + ",hg_wins:" + hg_wins
							+ ",accountType:" + accountType);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("Não foi possivel carregar a conta de " + this.name);
		}
	}

	public void quit() {
		try {
			GeneralEvents.cooldown.add(name);
			ProxyServer.getInstance().getScheduler().schedule(Commons.getInstance(), new Runnable() {
				@Override
				public void run() {
					if (name == null && Commons.getManager().getBackend().getRedis().contains(name.toLowerCase()))
						return;
					String p_acc = Commons.getManager().getBackend().getRedis().get(name.toLowerCase());
					String[] p_accl = p_acc.split(",");
					cash = Integer.parseInt(p_accl[0].replace("cash:", ""));
					xp = Integer.parseInt(p_accl[1].replace("xp:", ""));
					groupExpireIn = Long.parseLong(p_accl[2].replace("groupExpireIn:", ""));
					lastLogin = Long.parseLong(p_accl[3].replace("lastLogin:", ""));
					firstLogin = Long.parseLong(p_accl[4].replace("firstLogin:", ""));
					group = Groups.getGroup(p_accl[5].replace("group:", ""));
					rank = Ranks.getRank(p_accl[6].replace("rank:", ""));
					pvp_kills = Integer.parseInt(p_accl[7].replace("pvp_kills:", ""));
					pvp_deaths = Integer.parseInt(p_accl[8].replace("pvp_deaths:", ""));
					pvp_ks = Integer.parseInt(p_accl[9].replace("pvp_ks:", ""));
					hg_kills = Integer.parseInt(p_accl[10].replace("hg_kills:", ""));
					hg_deaths = Integer.parseInt(p_accl[11].replace("hg_deaths:", ""));
					hg_wins = Integer.parseInt(p_accl[12].replace("hg_wins:", ""));
					accountType = Integer.parseInt(p_accl[13].replace("accountType:", ""));
					Manager m = Commons.getManager();
					//tabela, grupo ,de qm
					m.getBackend().getSql().updateString("players", "group", "name", group.toString(), name);
					m.getBackend().getSql().updateString("players_rank", "ranking", "name", rank.toString(), name);
					m.getBackend().getSql().updateInt("players_rank", "xp", "name", xp, name);
					m.getBackend().getSql().updateInt("players_rank", "cash", "name", cash, name);
					m.getBackend().getSql().updateInt("players", "accountType", "name", accountType, name);
					m.getBackend().getSql().updateLong("players", "groupExpireIn", "name", groupExpireIn, name);
					m.getBackend().getSql().updateLong("players", "lastlogin", "name", lastLogin, name);
					m.getBackend().getSql().updateLong("players", "firstlogin", "name", firstLogin, name);
					m.getBackend().getSql().updateInt("hg", "kills", "name", hg_kills, name);
					m.getBackend().getSql().updateInt("hg", "deaths", "name", hg_deaths, name);
					m.getBackend().getSql().updateInt("hg", "wins", "name", hg_wins, name);
					m.getBackend().getSql().updateInt("kitpvp", "kills", "name", pvp_kills, name);
					m.getBackend().getSql().updateInt("kitpvp", "deaths", "name", pvp_deaths, name);
					m.getBackend().getSql().updateInt("kitpvp", "ks", "name", pvp_ks, name);
					m.getBackend().getSql().updateInt("players_rank", "rankOrdinal", "name", rank.ordinal(), name);
					m.getBackend().getSql().updateInt("players_rank", "xpTotal", "name", xp 
							+ m.getBackend().getSql().getInt("players_rank", "name", "xpTotal", name), name);

					GeneralEvents.cooldown.remove(name);
					unload();
				}
			}, 3, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("N�o foi possivel atualizar a conta de " + this.name);
		}
	}

	public void updateByRedis() {
		try {
			String p_acc = Commons.getManager().getBackend().getRedis().get(name.toLowerCase());
			String[] p_accl = p_acc.split(",");
			cash = Integer.parseInt(p_accl[0].replace("cash:", ""));
			xp = Integer.parseInt(p_accl[1].replace("xp:", ""));
			groupExpireIn = Long.parseLong(p_accl[2].replace("groupExpireIn:", ""));
			lastLogin = Long.parseLong(p_accl[3].replace("lastLogin:", ""));
			firstLogin = Long.parseLong(p_accl[4].replace("firstLogin:", ""));
			group = Groups.getGroup(p_accl[5].replace("group:", ""));
			rank = Ranks.getRank(p_accl[6].replace("rank:", ""));
			pvp_kills = Integer.parseInt(p_accl[7].replace("pvp_kills:", ""));
			pvp_deaths = Integer.parseInt(p_accl[8].replace("pvp_deaths:", ""));
			pvp_ks = Integer.parseInt(p_accl[9].replace("pvp_ks:", ""));
			hg_kills = Integer.parseInt(p_accl[10].replace("hg_kills:", ""));
			hg_deaths = Integer.parseInt(p_accl[11].replace("hg_deaths:", ""));
			hg_wins = Integer.parseInt(p_accl[12].replace("hg_wins:", ""));
			accountType = Integer.parseInt(p_accl[13].replace("accountType:", ""));
			Manager m = Commons.getManager();
			m.getBackend().getSql().updateString("players", "group", "name", this.group.toString(), this.name);
			m.getBackend().getSql().updateString("players_rank", "ranking", "name", this.rank.toString(), this.name);
			m.getBackend().getSql().updateInt("players_rank", "xp", "name", this.xp, this.name);
			m.getBackend().getSql().updateInt("players_rank", "cash", "name", this.cash, this.name);
			m.getBackend().getSql().updateInt("players", "accountType", "name", this.accountType, this.name);
			m.getBackend().getSql().updateLong("players", "groupExpireIn", "name", this.groupExpireIn, this.name);
			m.getBackend().getSql().updateLong("players", "lastlogin", "name", this.lastLogin, this.name);
			m.getBackend().getSql().updateLong("players", "firstlogin", "name", this.firstLogin, this.name);
			m.getBackend().getSql().updateInt("hg", "kills", "name", this.hg_kills, this.name);
			m.getBackend().getSql().updateInt("hg", "deaths", "name", this.hg_deaths, this.name);
			m.getBackend().getSql().updateInt("hg", "wins", "name", this.hg_wins, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "kills", "name", this.pvp_kills, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "deaths", "name", this.pvp_deaths, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "ks", "name", this.pvp_ks, this.name);

			Commons.getManager().getBackend().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks
							+ ",hg_kills:" + hg_kills + ",hg_deaths:" + hg_deaths + ",hg_wins:" + hg_wins
							+ ",accountType:" + accountType);

		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("N�o foi possivel atualizar a conta de " + this.name);
		}
	}

	public void update() {
		try {
			Manager m = Commons.getManager();
			m.getBackend().getSql().updateString("players", "group", "name", this.group.toString(), this.name);
			m.getBackend().getSql().updateString("players_rank", "ranking", "name", this.rank.toString(), this.name);
			m.getBackend().getSql().updateInt("players_rank", "xp", "name", this.xp, this.name);
			m.getBackend().getSql().updateInt("players_rank", "cash", "name", this.cash, this.name);
			m.getBackend().getSql().updateInt("players", "accountType", "name", this.accountType, this.name);
			m.getBackend().getSql().updateLong("players", "groupExpireIn", "name", this.groupExpireIn, this.name);
			m.getBackend().getSql().updateLong("players", "lastlogin", "name", this.lastLogin, this.name);
			m.getBackend().getSql().updateLong("players", "firstlogin", "name", this.firstLogin, this.name);
			m.getBackend().getSql().updateInt("hg", "kills", "name", this.hg_kills, this.name);
			m.getBackend().getSql().updateInt("hg", "deaths", "name", this.hg_deaths, this.name);
			m.getBackend().getSql().updateInt("hg", "wins", "name", this.hg_wins, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "kills", "name", this.pvp_kills, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "deaths", "name", this.pvp_deaths, this.name);
			m.getBackend().getSql().updateInt("kitpvp", "ks", "name", this.pvp_ks, this.name);

			Commons.getManager().getBackend().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks
							+ ",hg_kills:" + hg_kills + ",hg_deaths:" + hg_deaths + ",hg_wins:" + hg_wins
							+ ",accountType:" + accountType);

		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("N�o foi possivel atualizar a conta de " + this.name);
		}
	}

	public void ban(String reason, String author, long time) {
		try {
			banned = true;
			ban_author = author;
			ban_reason = reason;
			ban_time = time;
			Commons.getManager().getBackend().getSql().insertBan(name, author, reason, time);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("N�o foi possivel banir a conta de " + this.name);
		}
	}

	public void mute(String reason, String author, long time) {
		try {
			muted = true;
			mute_author = author;
			mute_reason = reason;
			mute_time = time;
			Commons.getManager().getBackend().getSql().insertMute(name, author, reason, time);
		} catch (Exception e) {
			e.printStackTrace();
			Commons.getManager().log("N�o foi possivel mutar a conta de " + this.name);
		}
	}

	public void unload() {
		try {

			Commons.getManager().getBackend().getRedis().del(this.name);
			cash = 0;
			xp = 0;
			group = null;
			rank = null;
			name = null;
			ip = null;
			lastLogin = 0l;
			firstLogin = 0l;
			pvp_deaths = 0;
			pvp_kills = 0;
			pvp_ks = 0;
			accountType = 0;
			hg_deaths = 0;
			hg_kills = 0;
			hg_wins = 0;

		} catch (Exception e) {
			Commons.getManager().log("N�o foi possivel atualizar a conta de " + this.name);
		}
	}



}
