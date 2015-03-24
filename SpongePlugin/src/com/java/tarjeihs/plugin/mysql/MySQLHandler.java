package com.java.tarjeihs.plugin.mysql;

import com.java.tarjeihs.plugin.JPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("unused")
public class MySQLHandler {
	
	private boolean connected = false;
	
	protected Connection conn = null;
	
	private final JPlugin plugin;
	private final MySQLAccessor sqlAccessor;
	private final String sql_host;
	private final int sql_port;
	private final String sql_database;
	private final String sql_username;
	private final String sql_password;
	
	private String url = "jdbc:mysql://";
	
	private static final String driver = "com.mysql.jdbc.Driver";
	
	private Properties properties = null;

	public MySQLHandler(JPlugin instance, String sql_Host, int sql_Port, String sql_Database, String sql_Username, String sql_Password) {
		this.plugin = instance;
		this.sqlAccessor = new MySQLAccessor(this.plugin);

		this.sql_host = sql_Host;
		this.sql_database = sql_Database;
		this.sql_port = sql_Port;
		this.sql_username = sql_Username;
		this.sql_password = sql_Password;

		this.properties = new Properties();
		this.properties.put("user", sql_Username);
		this.properties.put("password", sql_Password);

		this.url = (this.url + ":" + sql_Port + "/" + sql_Database);
	}

	public synchronized void initializeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			if ((this.properties.isEmpty()) || (this.properties == null)) {
				this.conn = DriverManager.getConnection(this.url, this.sql_username, this.sql_password);
			} else {
				this.conn = DriverManager.getConnection(this.url, this.properties);
			}
		} catch (SQLException e) {
			System.out.println("(Error)" + e.getMessage());
		} catch (Exception e) {
			System.out.println("(Error)" + e.getMessage());
		}
	}

	public Connection getConnection() {
		if (this.conn == null) {
			initializeConnection();
		}
		return this.conn;
	}

	public void closeConnection() {
		if (this.conn == null) {
			System.out.println("Connection already closed.");
			return;
		}
		try {
			this.conn.close();
			System.out.println("[Server] Connection closed");
		} catch (SQLException e) {
			System.out.println("(Error)" + e.getMessage());
		}
	}

	public MySQLAccessor getSQLAccessor() {
		return this.sqlAccessor;
	}
}
