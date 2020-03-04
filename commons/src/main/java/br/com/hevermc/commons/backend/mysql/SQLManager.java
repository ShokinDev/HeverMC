package br.com.hevermc.commons.backend.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hevermc.commons.enums.Groups;
import br.com.hevermc.commons.enums.Ranks;

public class SQLManager extends SQL {

	public SQLManager() {
		super("191.232.235.215", 3306, "u37_gl4UEFeVDB", "9twa2TibYW9vGv7TpeoJ5tCC", "s37_gta");
		tables();
	}

	public void tables() {
		PreparedStatement stm = null;
		try {
			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `players` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL, "
							+ "`group` VARCHAR(999) NULL, `accountType` INT NULL, `groupExpireIn` LONG NULL, `lastlogin` LONG NULL, `firstlogin` LONG NULL, `clan` VARCHAR(999) NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `players_rank` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`ranking` VARCHAR(999) NULL, `xp` INT NULL, `cash` INT NULL,`rankOrdinal` INT NULL,`xpTotal` INT NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `bans` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`author` VARCHAR(16) NULL, `reason` VARCHAR(999) NULL, `time` LONG NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `mutes` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`author` VARCHAR(16) NULL, `reason` VARCHAR(999) NULL, `time` LONG NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `kitpvp` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`kills` INT NULL, `deaths` INT NULL, `ks` INT NULL, `1v1_wins` INT NULL, `1v1_loses` INT NULL, `1v1_ws` INT NULL, `kitList` VARCHAR(999) NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hg` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`kills` INT NULL, `deaths` INT NULL, `wins` INT NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `clans` (`id` INT NOT NULL AUTO_INCREMENT,`clan` VARCHAR(90) NULL, `owner` VARCHAR(16) NULL"
							+ ", `admin1` VARCHAR(16) NULL, `admin2` VARCHAR(16) NULL, "
							+ "`member1` VARCHAR(16) NULL, `member2` VARCHAR(16) NULL, `member3` VARCHAR(16) NULL, `member4` VARCHAR(16) NULL, `member5` VARCHAR(16) NULL,"
							+ " `xp` INT NULL, `tag` VARCHAR(6) NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createClan(String name, String owner, String tag, int xp) {
		PreparedStatement stm = null;
		try {

			stm = getConnection().prepareStatement("INSERT INTO `clans`(`name`, `owner`, `admin1`, "
					+ "`admin2`, `member1`, `member2`, `member3`, `member4`, `member5`, `xp`, `tag`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			stm.setString(1, name);
			stm.setString(2, owner);
			stm.setString(3, "Ninguem");
			stm.setString(4, "Ninguem");
			stm.setString(5, "Ninguem");
			stm.setString(6, "Ninguem");
			stm.setString(7, "Ninguem");
			stm.setString(8, "Ninguem");
			stm.setString(9, "Ninguem");
			stm.setInt(10, xp);
			stm.setString(11, tag);
			stm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getTopRank() {
		PreparedStatement stm = null;
		List<String> tops = new ArrayList<String>();
		try {
			stm = getConnection().prepareStatement("SELECT * FROM `players_rank` ORDER BY `xpTotal` DESC LIMIT 10");
			ResultSet rs = stm.executeQuery();
			int i = 0;
			while (rs.next()) {
				if (i <= 10) {
					i++;
					Ranks r = Ranks.getRank(rs.getInt("rankOrdinal"));
					tops.add("§f" + i + "° §a" + rs.getString("name") + " - " + "§7[" + r.getColor() + r.getSymbol()
							+ "§7] " + r.getColor() + r.getName());
				}
			}
			while (i != 10) {
				i++;
				tops.add("§f" + i + "° §aNinguém - §7[§f-§7] §fUnranked");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tops;
	}

	public List<String> getTopFive(String table, String top) {
		PreparedStatement stm = null;
		List<String> tops = new ArrayList<String>();
		try {
			stm = getConnection().prepareStatement("SELECT * FROM `" + table + "` ORDER BY " + top + " DESC LIMIT 10");
			ResultSet rs = stm.executeQuery();
			int i = 0;
			while (rs.next()) {
				if (i <= 10) {
					i++;
					tops.add("§f" + i + "° §a" + rs.getString("name") + " - §e" + rs.getInt(top) + " "
							+ top.toUpperCase());
				}
			}
			while (i != 10) {
				i++;
				tops.add("§f" + i + "° §aNinguém - §e0 " + top.toUpperCase());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tops;
	}

	public void delete(String table, String where, String where_args) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection().prepareStatement("DELETE FROM `" + table + "` WHERE `" + where + "` = ?");
			stm_1.setString(1, where_args);
			stm_1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
	}

	public void insertMute(String name, String author, String reason, long time) {
		PreparedStatement stm = null;
		try {

			stm = getConnection()
					.prepareStatement("INSERT INTO `mutes`(`name`, `author`, `reason`, `time`) VALUES (?,?,?,?)");
			stm.setString(1, name);
			stm.setString(2, author);
			stm.setString(3, reason);
			stm.setLong(4, time);
			stm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertBan(String name, String author, String reason, long time) {
		PreparedStatement stm = null;
		try {

			stm = getConnection()
					.prepareStatement("INSERT INTO `bans`(`name`, `author`, `reason`, `time`) VALUES (?,?,?,?)");
			stm.setString(1, name);
			stm.setString(2, author);
			stm.setString(3, reason);
			stm.setLong(4, time);
			stm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPlayer(String name) {
		PreparedStatement stm = null;
		try {

			if (!checkString("players", "name", name)) {
				stm = getConnection().prepareStatement(
						"INSERT INTO `players`(`name`, `group`, `accountType`, `groupExpireIn`, `lastlogin`, `firstlogin`) VALUES (?,?,?,?,?,?)");
				stm.setString(1, name);
				stm.setString(2, Groups.MEMBRO.toString());
				stm.setInt(3, 2);
				stm.setLong(4, 0l);
				stm.setLong(5, new Date().getTime());
				stm.setLong(6, new Date().getTime());
				stm.executeUpdate();
			}

			if (!checkString("players_rank", "name", name)) {
				stm = getConnection().prepareStatement(
						"INSERT INTO `players_rank`(`name`, `ranking`, `xp`, `cash`, `rankOrdinal`, `xpTotal`) VALUES (?,?,?,?,?,?)");
				stm.setString(1, name);
				stm.setString(2, Ranks.Unranked.toString());
				stm.setLong(3, 0);
				stm.setLong(4, 0);
				stm.setInt(5, 0);
				stm.setInt(6, 0);
				stm.executeUpdate();
			}
			if (!checkString("kitpvp", "name", name)) {
				stm = getConnection().prepareStatement(
						"INSERT INTO `kitpvp`(`name`, `kills`, `deaths`, `ks`, `1v1_wins`, `1v1_loses`, `1v1_ws`) VALUES (?,?,?,?,?,?,?)");
				stm.setString(1, name);
				stm.setInt(2, 0);
				stm.setInt(3, 0);
				stm.setInt(4, 0);
				stm.setInt(5, 0);
				stm.setInt(6, 0);
				stm.setInt(7, 0);
				stm.executeUpdate();
			}

			if (!checkString("hg", "name", name)) {
				stm = getConnection()
						.prepareStatement("INSERT INTO `hg`(`name`, `kills`, `deaths`, `wins`) VALUES (?,?,?,?)");
				stm.setString(1, name);
				stm.setInt(2, 0);
				stm.setInt(3, 0);
				stm.setInt(4, 0);
				stm.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkString(String table, String where, String arg) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = ?");
			stm_1.setString(1, arg);
			ResultSet rs = stm_1.executeQuery();

			if (rs.next())
				return true;

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}

		return false;
	}

	public String getString(String table, String where, String string, String where_args) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = ?");
			stm_1.setString(1, where_args);
			ResultSet rs = stm_1.executeQuery();
			if (rs.next())
				return rs.getString(string);

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
		return null;
	}

	public int getInt(String table, String where, String integer, String where_args) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = ?");
			stm_1.setString(1, where_args);
			ResultSet rs = stm_1.executeQuery();

			if (rs.next())
				return rs.getInt(integer);

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
		return 0;
	}

	public long getLong(String table, String where, String Long, String where_args) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection().prepareStatement("SELECT * FROM `" + table + "` WHERE `" + where + "` = ?");
			stm_1.setString(1, where_args);
			ResultSet rs = stm_1.executeQuery();

			if (rs.next())
				return rs.getLong(Long);

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
		return 0;
	}

	public void updateString(String table, String set, String where, String args1, String args2) {
		PreparedStatement stm_1;
		try {
			;
			stm_1 = getConnection()
					.prepareStatement("UPDATE `" + table + "` SET `" + set + "` = ? WHERE `" + where + "` = ?");
			stm_1.setString(1, args1);
			stm_1.setString(2, args2);
			stm_1.executeUpdate();
		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
	}

	public void updateInt(String table, String set, String where, int args1, String args2) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection()
					.prepareStatement("UPDATE `" + table + "` SET `" + set + "` = ? WHERE `" + where + "` = ?");
			stm_1.setInt(1, args1);
			stm_1.setString(2, args2);
			stm_1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
	}

	public void updateLong(String table, String set, String where, long args1, String args2) {
		PreparedStatement stm_1;
		try {
			stm_1 = getConnection()
					.prepareStatement("UPDATE `" + table + "` SET `" + set + "` = ? WHERE `" + where + "` = ?");
			stm_1.setLong(1, args1);
			stm_1.setString(2, args2);
			stm_1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("MYSQL - " + e.getMessage());
		}
	}

}