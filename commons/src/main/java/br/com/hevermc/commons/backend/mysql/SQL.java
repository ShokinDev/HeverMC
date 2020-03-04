package br.com.hevermc.commons.backend.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;

public class SQL {

	String host;
	int port;
	String user;
	String password;
	String database;
	@Getter
	@Setter
	Connection connection;

	public SQL(String host, int port, String user, String password, String database) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.database = database;
		setup();
	}

	public void setup() {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			setConnection(DriverManager.getConnection(
					"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password));
			System.out.print("[SQL] Conectado.");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}