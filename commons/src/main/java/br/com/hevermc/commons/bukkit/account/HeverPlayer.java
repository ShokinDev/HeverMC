package br.com.hevermc.commons.bukkit.account;

import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;
import br.com.hevermc.commons.enums.Tags;
import lombok.Getter;
import lombok.Setter;

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
	Groups group;
	@Setter
	Ranks rank;
	Tags tag;

	@Setter
	int pvp_kills = 0;

	@Setter
	int pvp_deaths = 0;

	@Setter
	int pvp_ks = 0;

	public HeverPlayer(Player p) {
		this.name = p.getName().toLowerCase();
		this.ip = p.getAddress().getHostName();
	}

	public HeverPlayer(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}

	public void setGroup(Groups group) {
		this.group = group;
		if (Bukkit.getOnlinePlayers().size() != 0) {
			int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
			Commons.getManager().getBungeeChannel().sendPluginMessage(Commons.getManager().online.get(random),
					"updateGroup(" + this.name + ",groupTo:" + group.toString() + ")", "BungeeCord");
		}

	}

	public void load() {
		try {
			String p_acc = Commons.getManager().getRedis().get(name.toLowerCase());
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
			if (groupExpireIn > 0) {
				if (new Date().after(new Date(groupExpireIn))) {
					setGroup(Groups.MEMBRO);
				}
			}

			Commons.getManager().log("Conta de " + this.name + " foi carregada!");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel carregar a conta de " + this.name + "!");
			e.printStackTrace();
		}
	}

	public void update() {
		try {
			Commons.getManager().getRedis().set(this.name,
					"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
							+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
							+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks);
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
			e.printStackTrace();
		}
	}

	public void updateRank() {
		if (Ranks.getRank(rank.ordinal() + 1).getXp() >= getXp()) {
			setRank(Ranks.getRank(rank.ordinal() + 1));
			setXp(getRank().getXp());
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

	public boolean groupIsLarger(Groups group) {
		if (getGroup().ordinal() >= group.ordinal())
			return true;
		return false;
	}

	public void setTag(Tags tag) {
		Player p = Bukkit.getPlayer(name);
		this.tag = tag;
		if (p != null) {
			if (tag != Tags.MEMBRO) {
				ReflectionAPI.tag(p, tag.getPrefix() + " " + tag.getColor(),
						" §7(" + getRank().getColor() + getRank().getSymbol() + "§7)", tag.getOrdem());
			} else {
				ReflectionAPI.tag(p, tag.getColor(), " §7(" + getRank().getColor() + getRank().getSymbol() + "§7)",
						tag.getOrdem());

			}
		}
	}

	public void setTag_Alternative(Player target, Tags tag) {
		Player p = Bukkit.getPlayer(name);
		if (p != null) {
			if (tag != Tags.MEMBRO) {
				ReflectionAPI.tag(target, p, tag.getPrefix() + " " + tag.getColor(),
						" §7(" + getRank().getColor() + getRank().getSymbol() + "§7)", tag.getOrdem());
			} else {
				ReflectionAPI.tag(target, p, tag.getColor(),
						" §7(" + getRank().getColor() + getRank().getSymbol() + "§7)", tag.getOrdem());

			}
		}
	}

	public String getSuffix() {
		return " §7(" + getRank().getColor() + getRank().getSymbol() + "§7)";
	}

}
