package br.com.hevermc.commons.backend.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLManager extends Sql {
//host, port, user, pw, db
	public SQLManager() {
		super("191.232.235.215", 3306, "u37_gl4UEFeVDB", "9twa2TibYW9vGv7TpeoJ5tCC", "s37_gta");
		tables();
	}

	public void tables() {
		PreparedStatement stm = null;
		PreparedStatement stm1 = null;
		PreparedStatement stm2 = null;
		PreparedStatement stm3 = null;
		PreparedStatement stm4 = null;
		try {
			stm = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_players` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`ip` VARCHAR(999) NULL, `cash` INT NULL, `last_login` LONG NULL, `first_login` LONG NULL, PRIMARY KEY (`id`));");
			stm.executeUpdate();

			stm1 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_groups` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`group` VARCHAR(99) NULL, `expireIn` LONG NULL, PRIMARY KEY (`id`));");
			stm1.executeUpdate();

			stm2 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_ranking` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`ranking` VARCHAR(999) NULL, `xp` INT NULL, PRIMARY KEY (`id`));");
			stm2.executeUpdate();

			stm3 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_bans` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`author` VARCHAR(16) NULL, `reason` VARCHAR(999) NULL, `time` LONG NULL, PRIMARY KEY (`id`));");
			stm3.executeUpdate();

			stm3 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_mutes` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`author` VARCHAR(16) NULL, `reason` VARCHAR(999) NULL, `time` LONG NULL, PRIMARY KEY (`id`));");
			stm3.executeUpdate();

			stm4 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_kitpvp` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`kills` INT NULL, `deaths` INT NULL, `ks` INT NULL, `1v1_loses` INT NULL, `1v1_wins` INT NULL, `1v1_ws` INT NULL, `kitList` VARCHAR(999) NULL, PRIMARY KEY (`id`));");
			stm4.executeUpdate();
			stm3 = getConnection().prepareStatement(
					"CREATE TABLE IF NOT EXISTS `hever_discord` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(16) NULL,"
							+ "`dc_id` VARCHAR(999) NULL, `pin` VARCHAR(999) NULL, `status`  VARCHAR(999) NULL, PRIMARY KEY (`id`));");
			stm3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getTopKitPvP(String top) {
		PreparedStatement stm = null;
		List<String> tops = new ArrayList<String>();
		try {
			stm = getConnection().prepareStatement("SELECT * FROM `hever_kitpvp` ORDER BY `"+top+"` DESC");
			ResultSet rs = stm.executeQuery();
			int i = 0;
			while (rs.next()) {
				if (i <= 10) {
					i++;
					tops.add("§f" + i + "º §a" + rs.getString("name") + " §e" + rs.getInt(top));
				} else {
					break;
				}
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
			System.out.println("Erro ao inserir player");
		}
	}

	public void insertMute(String name, String author, String reason, long time) {
		PreparedStatement stm = null;
		try {

			stm = getConnection()
					.prepareStatement("INSERT INTO `hever_mutes`(`name`, `author`, `reason`, `time`) VALUES (?,?,?,?)");
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
					.prepareStatement("INSERT INTO `hever_bans`(`name`, `author`, `reason`, `time`) VALUES (?,?,?,?)");
			stm.setString(1, name);
			stm.setString(2, author);
			stm.setString(3, reason);
			stm.setLong(4, time);
			stm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertPlayer(String name, String ip) {
		PreparedStatement stm = null;
		try {
			stm = getConnection().prepareStatement(
					"INSERT INTO `hever_players`(`name`, `ip`, `cash`, `last_login`, `first_login`) VALUES (?,?,?,?,?)");
			stm.setString(1, name);
			stm.setString(2, ip);
			stm.setInt(3, 0);
			stm.setLong(4, new Date().getTime());
			stm.setLong(5, new Date().getTime());
			stm.executeUpdate();

			stm = getConnection()
					.prepareStatement("INSERT INTO `hever_groups`(`name`, `group`, `expireIn`) VALUES (?,?,?)");
			stm.setString(1, name);
			stm.setString(2, "MEMBRO");
			stm.setLong(3, 0);
			stm.executeUpdate();

			stm = getConnection()
					.prepareStatement("INSERT INTO `hever_ranking`(`name`, `ranking`, `xp`) VALUES (?,?,?)");
			stm.setString(1, name);
			stm.setString(2, "UNRANKED");
			stm.setInt(3, 0);
			stm.executeUpdate();

			stm = getConnection().prepareStatement(
					"INSERT INTO `hever_kitpvp`(`name`, `kills`, `deaths`, `ks`, `kitList`) VALUES (?,?,?,?,?)");
			stm.setString(1, name);
			stm.setInt(2, 0);
			stm.setInt(3, 0);
			stm.setInt(4, 0);
			stm.setString(5, "nenhum,pvp,kangaroo,viper");
			stm.executeUpdate();

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
			System.out.println("Erro ao verificar uma string");
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
			System.out.println("MYSQL - UPDATED STRING");
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
			System.out.println("MYSQL - UPDATED INT");

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
