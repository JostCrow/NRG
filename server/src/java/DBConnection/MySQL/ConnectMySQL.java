package DBConnection.MySQL;

import java.sql.*;

public class ConnectMySQL {
	
	Connection connection;

	public ConnectMySQL() {
		connection = dbConnect("jdbc:mysql://localhost:3306/asa", "root", "root");
	}
	
	public Connection getConnection(){
		return connection;
	}

	private Connection dbConnect(String db_connect_string, String db_userid, String db_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(db_connect_string, db_userid, db_password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
