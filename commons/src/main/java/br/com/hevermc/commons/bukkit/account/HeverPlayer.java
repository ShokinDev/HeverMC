package br.com.hevermc.commons.bukkit.account;

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
		Commons.getManager().getBungeeChannel().sendPluginMessage(Bukkit.getPlayer("Shokiin"),
				"updateGroup(" + this.name + ",groupTo:" + group.toString() + ")", "BungeeCord");
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
			Commons.getManager().log("Não foi possivel carregar a conta de " + this.name + "!");
			e.printStackTrace();
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

			Commons.getManager().getBungeeChannel().sendPluginMessage(Bukkit.getPlayer("Shokiin"),
					"updateAll(" + this.name + ")", "BungeeCord");
		} catch (Exception e) {
			Commons.getManager().log("Não foi possivel atualizar a conta de " + this.name);
		}
	}
	
	public void ban(String author, String reason, long time) {

		Commons.getManager().getSQLManager().insertBan(getName(), author, reason, time);
		Commons.getManager().getBungeeChannel().sendPluginMessage(Bukkit.getPlayer("Shokiin"),
				"messageBan(" + this.name + "," + author + "," + reason + "," + time + ")", "BungeeCord");
		
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
