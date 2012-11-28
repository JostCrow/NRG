
package Domain;

import java.util.Date;

public class Highscore {
	
	private int id;
	private Double score;
	private String foto;	
	private Date timestamp;

	public Highscore() {
	}

	public Highscore(int id, Double score, String foto, Date timestamp) {
		this.id = id;		
		this.score = score;
		this.foto = foto;
		this.timestamp = timestamp;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
