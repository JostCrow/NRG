package DBConnection.MySQL;

import Domain.Device;
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
	
	public HighscoreTable(Connection connection)
	{
		this.connection = connection;
	}
	
	public ArrayList<Highscore> getAllHighscores()
	{
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();		
		try {
			Statement statement = connection.createStatement();
			
			String query = "SELECT *"
					+ "FROM Highscore";
			
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				Highscore h = new Highscore(resultSet.getInt("id"), resultSet.getDouble("score"), resultSet.getString("photo_url"), resultSet.getDate("timestamp"));
				highscores.add(h);
			}			
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		return highscores;
	}
	
	public boolean addNewHighscore()
	{
		ArrayList<Highscore> highscores = new ArrayList<Highscore>();		
		try {
			Statement statement = connection.createStatement();
			
			String query = "SELECT *"
					+ "FROM Highscore";
			
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				Highscore h = new Highscore(resultSet.getInt("id"), resultSet.getDouble("score"), resultSet.getString("photo_url"), resultSet.getDate("timestamp"));
				highscores.add(h);
			}			
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}
}
