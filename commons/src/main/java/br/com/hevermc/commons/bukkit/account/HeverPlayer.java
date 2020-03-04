package br.com.hevermc.commons.bukkit.account;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.hevermc.commons.bukkit.Commons;
import br.com.hevermc.commons.bukkit.account.loader.PlayerLoader;
import br.com.hevermc.commons.bukkit.api.ReflectionAPI;
import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;
import br.com.hevermc.commons.enums.Tags;
import lombok.Getter;
import lombok.Setter;

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
	int pvp_kills = 0;
	int pvp_deaths = 0;
	int pvp_ks = 0;
	int hg_kills = 0;
	int hg_deaths = 0;
	int hg_wins = 0;
	int accountType = 0;
	boolean atualized = false;

	public HeverPlayer(Player p) {
		this.name = p.getName().toLowerCase();
		this.ip = p.getAddress().getHostName();
	}

	public HeverPlayer(String name, String ip) {
		this.name = name;
		this.ip = ip;
	}

	public void load() {
		try {
			if (Commons.getManager().getBackend().getRedis().contains(name.toLowerCase())) {

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
				new BukkitRunnable() {

					@Override
					public void run() {
						if (name == null)
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
						atualized = true;

					}
				}.runTaskLater(Commons.getInstance(), 30L);
			} else {
				cash = 0;
				xp = 0;
				groupExpireIn = 0l;
				lastLogin = 0;
				firstLogin = 0;
				group = Groups.MEMBRO;
				rank = Ranks.Unranked;
				pvp_kills = 0;
				pvp_deaths = 0;
				pvp_ks = 0;
				hg_kills = 0;
				hg_deaths = 0;
				hg_wins = 0;
				accountType = 2;
				System.out.println("[ACCOUNT] A conta de " + name + " não foi encontrada no Redis!");
			}
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel carregar a conta de " + this.name + "!");
			e.printStackTrace();
		}
	}

	public void forceUpdate() {
		Commons.getManager().getBackend().getRedis().set(this.name,
				"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
						+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:" + rank.toString()
						+ ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:" + pvp_ks + ",hg_kills:"
						+ hg_kills + ",hg_deaths:" + hg_deaths + ",hg_wins:" + hg_wins + ",accountType:" + accountType);
	}

	public void update() {
		try {
			if (atualized == true) {
				Commons.getManager().getBackend().getRedis().del(name);
				Commons.getManager().getBackend().getRedis().set(this.name,
						"cash:" + cash + ",xp:" + xp + ",groupExpireIn:" + groupExpireIn + ",lastLogin:" + lastLogin
								+ ",firstLogin:" + firstLogin + ",group:" + group.toString() + ",rank:"
								+ rank.toString() + ",pvp_kills:" + pvp_kills + ",pvp_deaths:" + pvp_deaths + ",pvp_ks:"
								+ pvp_ks + ",hg_kills:" + hg_kills + ",hg_deaths:" + hg_deaths + ",hg_wins:" + hg_wins
								+ ",accountType:" + accountType);
			}
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
			e.printStackTrace();
		}
	}

	public void updateRank() {
		if (atualized == true && Ranks.getRank(rank.ordinal() + 1) != null) {
			if (xp >= Ranks.getRank(rank.ordinal() + 1).getXp()) {
				setRank(Ranks.getRank(rank.ordinal() + 1));
				setXp(0);
				setTag(getTag());
				if (Bukkit.getPlayer(name) != null) {
					ReflectionAPI.sendTitle(Bukkit.getPlayer(name),
							rank.getColor() + "§l" + rank.getName().toUpperCase(),
							"§fParabéns, você upou para " + rank.getColor() + rank.getName(), 5, 5, 5);
					Bukkit.getPlayer(name)
							.sendMessage("§aParabéns, você upou para " + rank.getColor() + rank.getName() + "§a!");
				}
			}
		}
	}

	public void unload() {
		try {
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
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
			e.printStackTrace();
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
						" §7[" + getRank().getColor() + getRank().getSymbol() + "§7]", tag.getOrdem());
			} else {
				ReflectionAPI.tag(p, tag.getColor(), " §7[" + getRank().getColor() + getRank().getSymbol() + "§7]",
						tag.getOrdem());

			}
		}
	}

	public void setTag_Alternative(Player target) {
		Player p = Bukkit.getPlayer(name);
		HeverPlayer hp = PlayerLoader.getHP(target);
		if (p != null) {
			if (hp.getTag() != Tags.MEMBRO) {
				ReflectionAPI.tag(target, p, hp.getTag().getPrefix() + " " + hp.getTag().getColor(),
						" §7[" + hp.getRank().getColor() + hp.getRank().getSymbol() + "§7]", hp.getTag().getOrdem());
			} else {
				ReflectionAPI.tag(target, p, hp.getTag().getColor(),
						" §7[" + hp.getRank().getColor() + hp.getRank().getSymbol() + "§7]", hp.getTag().getOrdem());

			}
		}
	}

	public String getSuffix() {
		return " §7[" + getRank().getColor() + getRank().getSymbol() + "§7]";
	}

}
