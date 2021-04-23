package model;

import java.io.Serializable;


public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private String momment;
	private String event;
	private String name;
	private String matchID;
	
	public Event(String matchID, String evTime, String event, String name) {
		this.momment = evTime;
		this.event = event;
		this.name = name;
		this.matchID = matchID;
	}
	
	public String getMomment() { return momment; }
	public String getEvent() { return event; }
	public String getName() { return name; }
	public String getID() { return matchID; }
	
}
