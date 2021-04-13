package model;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Match implements Serializable{
	private static final long serialVersionUID = 1L;
	private String matchID;
	private String home;
	private String away;
	private int scoreHome;
	private int scoreAway;
	private LocalDateTime time;
	private String objID;
	private Event ev;
	
	public Match(String matchID, String home, String away, int scoreHome, int scoreAway
			, LocalDateTime time, String objID, Event ev) {
		this.objID = objID;
		this.matchID = matchID;
		this.home = home;
		this.away = away;
		this.scoreHome = scoreHome;
		this.scoreAway = scoreAway;
		this.time = time;
		this.ev = ev;
	}
	
	
	public String getID() { return matchID; }
	public String getHome() { return home; }
	public String getAway() { return away; }
	public int getScoreHome() { return scoreHome; }
	public int getScoreAway() { return scoreAway; }
	public LocalDateTime getTime() { return time; }
	public String getObjID() { return objID; }
	public Event getEvent() { return ev; }
	
}
