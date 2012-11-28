package Domain;

public class Device {
	private int id;
	private String name;
	private String photo_url;
	private double average_watt;
	private String sensor;
	
	public Device(){
	}

	public Device(int id, String name, String photo_url, double average_watt, String sensor) {
		this.id = id;
		this.name = name;
		this.photo_url = photo_url;
		this.average_watt = average_watt;
		this.sensor = sensor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public double getAverage_watt() {
		return average_watt;
	}

	public void setAverage_watt(double average_watt) {
		this.average_watt = average_watt;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}
}
