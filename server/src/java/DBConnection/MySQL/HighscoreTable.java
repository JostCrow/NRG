package DBConnection.MySQL;

import Domain.Highscore;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
					+ "FROM highscore";
			
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
	
	public void addHighscore(Highscore highscore)
	{
		try {			
			String query = "INSERT INTO Highscore (score, photo_url) VALUES(?, ?)";
			
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setDouble (1, highscore.getScore());
			statement.setString (2, highscore.getFoto());
			
			statement.execute();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}
