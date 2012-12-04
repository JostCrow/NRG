package Service;

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
	public Device getById(@WebParam(name = "deviceId") int id){
		return deviceTable.getById(id);
	}

	/**
	 * Met deze methode kan je van een device het totaal verbruikte watt updaten voor een mooi gemiddelde.
	 */
	@WebMethod(operationName = "updateWattTotal")
	public boolean updateWattTotal(@WebParam(name = "deviceId") int id, @WebParam(name = "watt") double watt){
		deviceTable.updateWattAmount(id, watt);
		return true;
	}
	
	/**
	 * Met deze methode kan je een device toevoegen.
	 */
	@WebMethod(operationName = "addDevice")
	public void addDevice(@WebParam(name = "device") Device device) {
		deviceTable.addDevice(device);
	}
	
	/**
	 * Met deze methode kan je een device verwijderen.
	 */
	@WebMethod(operationName = "removeDevice")
	public void removeDevice(@WebParam(name = "device") Device device) {
		deviceTable.removeDevice(device);
	}
	
	/**
	 * Met deze methode kan je live data opvragen van een device naar keuze.
	 */
	@WebMethod(operationName = "getLiveData")
	public boolean getLiveData(@WebParam(name = "deviceId") int id){		
		return true;
	}
	
	/**
	 * Met deze methode kan je een device in zijn geheel updaten.
	 */
	@WebMethod(operationName = "updateDevice")
	public void updateDevice(@WebParam(name = "device") Device device) {
		deviceTable.updateDevice(device);
	}
}
