package energie;

import DBConnection.MySQL.ConnectMySQL;
import DBConnection.MySQL.DeviceTable;
import Domain.Device;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "Device")
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

	/**
	 * Bla Bla Bla
	 */
	@WebMethod(operationName = "updateWattTotal")
	public boolean updateWattTotal(@WebParam(name = "deviceId") int id, @WebParam(name = "watt") double watt) {
		deviceTable.updateWattAmount(id, watt);
		return true;
	}
	
	/**
	 * Bla Bla Bla
	 */
	@WebMethod(operationName = "addDevice")
	public boolean addDevice(@WebParam(name = "device") Device device) {
		deviceTable.addDevice(device);
		return true;
	}
	
	/**
	 * Bla Bla Bla
	 */
	@WebMethod(operationName = "removeDevice")
	public boolean removeDevice(@WebParam(name = "device") Device device) {
		deviceTable.removeDevice(device);
		return true;
	}
	
	/**
	 * Bla Bla Bla
	 */
	@WebMethod(operationName = "getLiveData")
	public boolean getLiveData(@WebParam(name = "deviceId") int id) {
		Device device = deviceTable.getById(id);
		
		return true;
	}
	
	@WebMethod(operationName = "updateDevice")
	public boolean updateDevice(@WebParam(name = "device") Device device) {
//		Device device = deviceTable.getById(id);
		
		return true;
	}
}
