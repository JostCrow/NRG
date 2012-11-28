package energie;

import DBConnection.MySQL.ConnectMySQL;
import DBConnection.MySQL.DeviceTable;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "Verbruik")
public class Verbruik {
	
	ConnectMySQL connectMySQL;
	DeviceTable fetchDevice;
	
	public Verbruik(){
		connectMySQL = new ConnectMySQL();
		fetchDevice = new DeviceTable(connectMySQL.getConnection());
	}

	/**
	 * This is a sample web service operation
	 */
	@WebMethod(operationName = "hello")
	public String hello(@WebParam(name = "name") String txt) {
		return "Pinda " + txt + " !";
	}
}
