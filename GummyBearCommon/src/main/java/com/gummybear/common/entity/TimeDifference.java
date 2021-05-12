package com.gummybear.common.entity;

import java.util.Calendar;
import java.util.Date;

public class TimeDifference {
	private Integer id;
	private int userid;
	private Date date;
	private Calendar startTime;
	private Calendar endTime;
	private Date timeDiff;
	
	public TimeDifference(Integer id, int userid, Date date, Calendar startTime, Calendar endTime, Date timeDiff) {
		super();
		this.id = id;
		this.userid = userid;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeDiff = timeDiff;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Date getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(Date timeDiff) {
		this.timeDiff = timeDiff;
	}
}