package asa.client.DTO;

public class GameData {
	private int deviceId = 1;
	private int lastHighscoreId = 0;
	private double deviceScore = 0;
	private double playerScore = 0;

	public GameData() {
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public double getDeviceScore() {
		return deviceScore;
	}

	public void setDeviceScore(double deviceScore) {
		this.deviceScore = deviceScore;
	}

	public double getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(double playerScore) {
		this.playerScore = playerScore;
	}

	public int getLastHighscoreId() {
		return lastHighscoreId;
	}

	public void setLastHighscoreId(int lastHighscoreId) {
		this.lastHighscoreId = lastHighscoreId;
	}
}
