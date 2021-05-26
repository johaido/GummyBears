package com.gummybear.time;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gummybear.common.entity.TimeDifference;
import com.gummybear.common.entity.TimeStamp;
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
	public TimeStamp save(Integer userId, String event) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    TimeStamp ts = new TimeStamp();
	    ts.setDate(date);
	    ts.setTimestamp(calendar);
	    ts.setUserid(userId);
	    ts.setEvent(event);
	    timeRepo.save(ts);
		return(ts);
	}
	
	/**
	 * Method to get time differences between check-in and check-out events for a specific user.
	 * This information is extracted from the Database.
	 * @author Olga
	 * @return List of TimeDifference objects.
	 */
	public List<TimeDifference> calculateTimeDifference(Integer userId) {
		// timeRepo.calculateTimeDifference() returns time difference calculated for all users in the database
		List<TimeDifference> timeDifference = timeRepo.calculateTimeDifference();

		// select time difference entries for a specific user based on the userId 
		List<TimeDifference> timeDifferenceForUser = new ArrayList<>();
		for (TimeDifference td : timeDifference) {
			if (td.getUserid() == userId) {
				timeDifferenceForUser.add(td);
			}
		}

		return(timeDifferenceForUser);		
	}	
	
	/**
	 * Method to calculate total duration of the working time for a user for a specific day (year-month-day).
	 * It sums up the time differences between clock-in and clock-out events (calculated per user with the calculateTimeDifference() method). 
	 * @author Olga
	 * @return Duration object (total working time for a specific day)
	 */
	public Duration calculateWorkingTime(Integer userId, Date date) {
		
		List<TimeDifference> timeDifference = calculateTimeDifference(userId);
		List<Duration> durations = new ArrayList<>();
		
		for (TimeDifference td : timeDifference) {
			if (isYearMonthDayEqual(td.getDate(), date)) {
				System.out.println("Selected events based on date: " + td.getId());
				Long hours = (long) td.getTimeDiff().getHours();
				Long minutes= (long) td.getTimeDiff().getMinutes();
				Long seconds = (long) td.getTimeDiff().getSeconds();
				// Idea to use Duration class to sum up time intervals is from here:
				// https://stackoverflow.com/questions/48685158/sum-of-time-durations-from-a-list-in-android-studio
				durations.add(Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds));
			}
		}
		
		Duration totalDuration = Duration.ZERO;
	    for (Duration dur : durations) {
	    	totalDuration = totalDuration.plus(dur);
	    }
	    
	    return totalDuration;
	}
	
	/**
	 * Method to calculate time difference between expected and observed working time. 
	 * @author Olga
	 * @return Duration object
	 */
	public Duration calculateDifferenceInWorkingTime(Integer userId, Date date, Double expected) {
		// Expected working times is stored in the Users database as Double
		// It has to be converted to the Duration object in order to calculate the difference with the actual working time (also Duration object)
		// The expected working time is stored in the DB as hours, e.g. 8.0 which means 8 hours 0 minutes, hence, 
		// it has to be converted to minutes first and then converted to Duration
		// After that, actual working time can be subtracted from the expected time
		// Idea suggested here: https://stackoverflow.com/questions/57410559/convert-hours-represented-in-double-to-duration
		Duration expectedTime = Duration.ofMinutes((long) (expected * 60));
	  
	    // Calculate total working time for a user for a specific day
	    Duration observedTime = calculateWorkingTime(userId, date);
	    
	    // Substract observed from expected
	    Duration difference = expectedTime.minus(observedTime);
	    
	    return difference;
	}
	
	/**
	 * A helper method to compare two dates and return true if they are equal and false if not.
	 * Two dates are equal if the have the same year, month and day.
	 * @author Olga
	 * @return Boolean
	 */
	private Boolean isYearMonthDayEqual(Date date1, Date date2) {
		Boolean result = false;
		
		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay()) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * A helper method to convert Duration object to a String in the format: hours:minutes:seconds.
	 * @author Olga
	 * @return String (formatted Duration)
	 */
	public String DurationToString(Duration x) {
	    // Duration class string formatting example is from here: 
	    // https://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss
		String hms = String.format("%d:%02d:%02d", x.toHours(), x.toMinutesPart(), x.toSecondsPart());
		return hms;
	}
	
	
}
