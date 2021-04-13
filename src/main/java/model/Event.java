package model;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private LocalTime momment;
	private String event;
	private String name;
	
	public Event(LocalTime evTime, String event, String name) {
		this.momment = evTime;
		this.event = event;
		this.name = name;
	}
	
	public LocalTime getMomment() { return momment; }
	public String getEvent() { return event; }
	public String getName() { return name; }
}
