package com.gummybear.common.entity;

import java.util.Calendar;
import java.util.Date;

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
@Table(name = "timeregister")
public class TimeStampNew {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	   
	@Column(columnDefinition = "DATETIME", nullable = false, unique = false)
	private Calendar timestamp;
	
	@Column(columnDefinition = "DATE", nullable = false, unique = false)
	private Date date;
	
	@Column(nullable = false, unique = false)
	private int userid;
	
	// true means check-in and false means check-out 
	@Column(nullable = false)
	private String event;
		
	public TimeStampNew() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
