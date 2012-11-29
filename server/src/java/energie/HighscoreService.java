package energie;

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
	 * Met deze methode kan je alle devices ophalen die in de database zitten.
	 */
	
	@WebMethod(operationName = "getAllHighscores")
	public List<Highscore> getAllHighscores() {
		return highscoreTable.getAllHighscores();
	}

	/**
	 * Web service operation
	 */
	@WebMethod(operationName = "AddHighscore")
	public void AddHighscore(@WebParam(name = "highscore") Highscore highscore) {
		//TODO write your implementation code here:
	}

	/**
	 * Met deze methode kan je 1 devices ophalen die in de database zit door middel van het id.
	 */
	
//	@WebMethod(operationName = "getDeviceById")
//	public Device getById(@WebParam(name = "deviceId") int id) {
//		return highscoreTable.getById(id);
//	}
}
