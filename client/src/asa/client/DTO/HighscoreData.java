package asa.client.DTO;

public class HighscoreData {
	private int lastHighscoreRank;
	private int topDraw;
	private int lastHighscoreDelta;
	private float rotationDelta;

	public HighscoreData() {
	}

	public int getLastHighscoreRank() {
		return lastHighscoreRank;
	}

	public void setLastHighscoreRank(int lastHighscoreRank) {
		this.lastHighscoreRank = lastHighscoreRank;
	}

	public int getTopDraw() {
		return topDraw;
	}

	public void setTopDraw(int topDraw) {
		this.topDraw = topDraw;
	}

	public int getLastHighscoreDelta() {
		return lastHighscoreDelta;
	}

	public void setLastHighscoreDelta(int lastHighscoreDelta) {
		this.lastHighscoreDelta = lastHighscoreDelta;
	}

	public float getRotationDelta() {
		return rotationDelta;
	}

	public void setRotationDelta(float rotationDelta) {
		this.rotationDelta = rotationDelta;
	}
}