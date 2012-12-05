package DBConnection.MySQL;

import Domain.Device;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceTable {

	Connection connection;

	public DeviceTable(Connection connection) {
		this.connection = connection;
	}

	public List<Device> getAll() {
		List<Device> devices = new ArrayList<Device>();
		try {
			Statement statement = connection.createStatement();

			String query = "SELECT *"
					+ "FROM device";

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Device device = new Device(resultSet.getInt("id"));
				device.setName(resultSet.getString("name"));
				device.setPhoto_url(resultSet.getString("photo_url"));
				device.setLogo_url(resultSet.getString("logo_url"));
				device.setWatt_total(resultSet.getDouble("watt_total"));
				device.setDivide_by(resultSet.getInt("devide_by"));
				device.setSensor(resultSet.getString("sensor"));
				devices.add(device);
			}
			statement.close();
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return devices;
	}

	public Device getById(int id){
		Device device;
		try {
			Statement statement = connection.createStatement();

			String query = "SELECT * "
					+ "FROM device "
					+ "WHERE id = '" + id + "'";
			
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();
			
			device = new Device(resultSet.getInt("id"));
			device.setName(resultSet.getString("name"));
			device.setPhoto_url(resultSet.getString("photo_url"));
			device.setLogo_url(resultSet.getString("logo_url"));
			device.setWatt_total(resultSet.getDouble("watt_total"));
			device.setDivide_by(resultSet.getInt("devide_by"));
			device.setSensor(resultSet.getString("sensor"));
			
			statement.close();
		} catch (SQLException ex) {
			device = new Device();
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		return device;
	}

	public void updateWattAmount(int id, double watt){
		Device device = getById(id);
		
		try {
			Statement statement = connection.createStatement();
			
			String query = "UPDATE device "
					+ "SET watt_total = " + (device.getWatt_total() + watt) + " "
					+ ", devide_by = " + (device.getDivide_by()+1) + " "
					+ "WHERE id = " + device.getId();
			
			statement.executeUpdate(query);
			statement.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void addDevice(Device device){
		
		try {
			Statement statement = connection.createStatement();
			
			String query = "INSERT INTO device "
					+ "VALUES (null, "
					+ "'" + device.getName() + "', "
					+ "'" + device.getPhoto_url() + "', "
					+ "'" + device.getLogo_url() + "', "
					+ device.getWatt_total() + ", "
					+ device.getDivide_by() + ", "
					+ "'" + device.getSensor() + "');";
			
			statement.executeUpdate(query);
			statement.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void removeDevice(Device device){
		
		try {
			Statement statement = connection.createStatement();
			
			String query = "DELETE FROM device "
					+ "WHERE id = " + device.getId() + ";";
			
			statement.executeUpdate(query);
			statement.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Not tested Yet
	 * @param device 
	 */
	public void updateDevice(Device device){
		
		try {
			Statement statement = connection.createStatement();
			
			String query = "UPDATE device SET "
					+ "name = '" + device.getName() + "', "
					+ "photo_url = '" + device.getPhoto_url() + "', "
					+ "logo_url = '" + device.getLogo_url() + "', "
					+ "watt_total = " + device.getWatt_total() + ", "
					+ "devide_by = " + device.getDivide_by() + ", "
					+ "sensor = '" + device.getSensor() + "' "
					+ "WHERE id = " + device.getId() + ";";
			System.out.println(query);
			statement.executeUpdate(query);
			statement.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}