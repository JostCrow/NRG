package DBConnection.MySQL;

import Domain.Highscore;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighscoreTable {

	Connection connection;

	public HighscoreTable(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 
	 */
	public ArrayList<Highscore> getAllHighscores() {
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();
		try {
			Statement statement = connection.createStatement();

			String query = "SELECT * "
					+ "FROM highscore "
					+ "ORDER BY score DESC";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Highscore h = new Highscore(resultSet.getInt("id"), resultSet.getDouble("score"), resultSet.getString("photo_url"), resultSet.getDate("timestamp"));
				highscores.add(h);
			}
			statement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		return highscores;
	}

	/**
	 * 
	 */
	public ArrayList<Highscore> getLimitedHighscores(int begin, int end) {
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();
		try {
			Statement statement = connection.createStatement();

			String query = "SELECT * "
					+ "FROM highscore "
					+ "ORDER BY score DESC "
					+ "LIMIT " + begin + ", " + end + ";";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Highscore h = new Highscore(resultSet.getInt("id"), resultSet.getDouble("score"), resultSet.getString("photo_url"), resultSet.getDate("timestamp"));
				highscores.add(h);
			}
			statement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		return highscores;
	}

	/**
	 * 
	 */
	public void addHighscore(Highscore highscore) {
		try {
			String query = "INSERT INTO highscore (score, photo_url) "
					+ "VALUES("
					+ highscore.getScore() + ", "
					+ "'" + highscore.getFoto() + "')";
			System.out.println(query);
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * 
	 */
	public void deleteHighscore(Highscore highscore) {
		try {
			String query = "DELETE FROM highscore "
					+ "WHERE id = " + highscore.getId() + ";";
			System.out.println(query);
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
