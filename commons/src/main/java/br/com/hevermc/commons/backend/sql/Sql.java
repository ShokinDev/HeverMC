package br.com.hevermc.commons.backend.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;

public class Sql {

	String host;
	int port;
	String user;
	String password;
	String database;
	@Getter
	@Setter
	Connection connection;

	public Sql(String host, int port, String user, String password, String database) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
		setup();
	}

	public void setup() {
		try {
			setConnection(DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
					this.user, this.password));
			System.out.print("aaa mysql conectqado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
