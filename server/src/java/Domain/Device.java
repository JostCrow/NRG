package Domain;

public class Device {
	
	private int id;
	private String name;
	private String location;
	private String background_url;
	private String logo_url;
	private String device_url;
	private double watt_total;
	private int divide_by;
	private String sensor;
	
	public Device(){
	}

	public Device(int id) {
		this.id = id;
	}

	public Device(String name, String location, String background_url, String logo_url, String device_url, double watt_total, int divide_by, String sensor) {
		this.name = name;
		this.background_url = background_url;
		this.watt_total = watt_total;
		this.divide_by = divide_by;
		this.sensor = sensor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBackground_url() {
		return background_url;
	}

	public void setBackground_url(String background_url) {
		this.background_url = background_url;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getDevice_url() {
		return device_url;
	}

	public void setDevice_url(String device_url) {
		this.device_url = device_url;
	}

	public double getWatt_total() {
		return watt_total;
	}

	public void setWatt_total(double watt_total) {
		this.watt_total = watt_total;
	}

	public int getDivide_by() {
		return divide_by;
	}

	public void setDivide_by(int divide_by) {
		this.divide_by = divide_by;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public String toString(){
		return "id = "+ id + "; Name = " + name + "; Watt total = " + watt_total + "; devide_by = " + divide_by + "; Sensor" + sensor;
	}
}
