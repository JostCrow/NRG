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
				device.setWatt_total(resultSet.getDouble("watt_total"));
				device.setDivide_by(resultSet.getInt("devide_by"));
				device.setSensor(resultSet.getString("sensor"));
				devices.add(device);
			}
			statement.close();
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(devices.size());
		return devices;
	}

	public Device getById(int id) {
		Device device;
		Statement statement;
		try {
			statement = connection.createStatement();

			String query = "SELECT * "
					+ "FROM device "
					+ "WHERE id = '" + id + "'";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();

			device = new Device(resultSet.getInt("id"));
			device.setName(resultSet.getString("name"));
			device.setPhoto_url(resultSet.getString("photo_url"));
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

	public void updateWattAmount(double watt) {
	}
}