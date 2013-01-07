package Service;

import DBConnection.MySQL.ConnectMySQL;
import DBConnection.MySQL.HighscoreTable;
import Domain.Highscore;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "Highscore")
public class HighscoreService {
	
	ConnectMySQL connectMySQL;
	HighscoreTable highscoreTable;
	
	public HighscoreService(){
		connectMySQL = new ConnectMySQL();
		highscoreTable = new HighscoreTable(connectMySQL.getConnection());
	}

	/**
	 * Met deze methode kan je alle highscores ophalen die in de database zitten.
	 */
	@WebMethod(operationName = "getAllHighscores")
	public List<Highscore> getAllHighscores() {
		return highscoreTable.getAllHighscores();
	}
	
	/**
	 * Met deze methode kan je een gelimiteerde hoeveelheid highscores ophalen die in de database zitten.
	 */
	@WebMethod(operationName = "getLimitedHighscores")
	public List<Highscore> getLimitedHighscores(@WebParam(name = "begin") int begin, @WebParam(name = "end") int end) {
		return highscoreTable.getLimitedHighscores(begin, end);
	}

	/**
	 * Met deze methode kan je een highscore toevoegen.
	 */
	@WebMethod(operationName = "AddHighscore")
	public int AddHighscore(@WebParam(name = "highscore") Highscore highscore) {
		return highscoreTable.addHighscore(highscore);
	}

	/**
	 * Met deze methode kan je een highscore verwijderen.
	 */
	@WebMethod(operationName = "DeleteHighscore")
	public void DeleteHighscore(@WebParam(name = "highscore") Highscore highscore) {
		highscoreTable.deleteHighscore(highscore);
	}
}
