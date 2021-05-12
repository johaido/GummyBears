package com.gummybear.time;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gummybear.common.entity.TimeStamp;
import com.gummybear.common.entity.User;

@Service
@Transactional
public class TimeService {
	
	@Autowired
	private TimeRepository timeRepo;
	
	/**
	 * Method to register check-in and check-out time in the DB.
	 * Save year, month, day, hour and minute of the time stamp in the DB.
	 * @author Olga
	 */
	public TimeStamp save(boolean eventType) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = (calendar.get(Calendar.MONTH) + 1); // counting from 0 (zero)
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		
		// TODO: adjust eventType to true or false depending on whether it was check-in or check-out event.
		// TODO: when user login id will be available it will be passed to the constructor to link time stamp to specific user
		TimeStamp ts = new TimeStamp(year, month, day, hour, minute, eventType);
		timeRepo.save(ts);
		return(ts);
	}

}
