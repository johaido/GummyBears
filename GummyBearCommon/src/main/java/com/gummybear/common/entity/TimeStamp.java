package com.gummybear.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to hold check-in and check-out time information.
 * @author Olga
 */
@Entity
@Table(name = "timestamps")
public class TimeStamp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 4, nullable = false, unique = false)
	private int year;
	
	@Column(length = 2, nullable = false, unique = false)
	private int month;
	
	@Column(length = 2, nullable = false, unique = false)
	private int day;
	
	@Column(length = 2, nullable = false, unique = false)
	private int hour;
	
	@Column(length = 2, nullable = false, unique = false)
	private int minute;
	
	// true means check-in and false means check-out 
	@Column(nullable = false)
	private boolean eventType;
	
	//TODO: link to the user id table still have to be implemented
		
	public TimeStamp() {
		
	}

	public TimeStamp(int year, int month, int day, int hour, int minute, boolean eventType) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.eventType = eventType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public boolean isEventType() {
		return eventType;
	}

	public void setEventType(boolean eventType) {
		this.eventType = eventType;
	}
}
