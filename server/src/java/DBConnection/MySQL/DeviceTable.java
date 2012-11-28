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
	
	public DeviceTable(Connection connection){
		this.connection = connection;
	}
	
	public List<Device> getAll(){
		List<Device> devices = new ArrayList<Device>();
		try {
			Statement statement = connection.createStatement();
			
			String query = "SELECT *"
					+ "FROM device";
			
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				Device d = new Device(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("photo_url"), resultSet.getDouble("average_watt"), resultSet.getString("sensor"));
				devices.add(d);
			}
			statement.close();
			
		} catch (SQLException ex) {
			Logger.getLogger(DeviceTable.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
		return devices;
	}
}
