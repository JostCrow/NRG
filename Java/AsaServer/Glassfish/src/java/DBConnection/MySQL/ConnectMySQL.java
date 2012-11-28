package DBConnection.MySQL;

import java.sql.*;

public class ConnectMySQL {

	Connection conn;

	public void connect() {
		dbConnect("jdbc:mysql://localhost:3306/asa", "root", "root");
	}

	public void dbConnect(String db_connect_string, String db_userid, String db_password) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
			System.out.println("connected");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
