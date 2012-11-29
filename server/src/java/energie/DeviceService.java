package energie;

import DBConnection.MySQL.ConnectMySQL;
import DBConnection.MySQL.DeviceTable;
import Domain.Device;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "Verbruik")
public class DeviceService {
	
	ConnectMySQL connectMySQL;
	DeviceTable deviceTable;
	
	public DeviceService(){
		connectMySQL = new ConnectMySQL();
		deviceTable = new DeviceTable(connectMySQL.getConnection());
	}

	/**
	 * Met deze methode kan je alle devices ophalen die in de database zitten.
	 */
	
	@WebMethod(operationName = "getAllDevices")
	public List<Device> getAllDevices() {
		return deviceTable.getAll();
	}

	/**
	 * Met deze methode kan je 1 devices ophalen die in de database zit door middel van het id.
	 */
	
	@WebMethod(operationName = "getDeviceById")
	public Device getById(@WebParam(name = "deviceId") int id) {
		return deviceTable.getById(id);
	}
}
