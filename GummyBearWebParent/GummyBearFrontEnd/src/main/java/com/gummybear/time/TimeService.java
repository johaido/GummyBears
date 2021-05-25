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
	public TimeStamp save(int userid, String event) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	    TimeStamp ts = new TimeStamp();
	    ts.setDate(date);
	    ts.setTimestamp(calendar);
	    ts.setUserid(userid);
	    ts.setEvent(event);
	    timeRepo.save(ts);
		return(ts);
	}
	
	/**
	 * Method to calculate time difference between check-in and check-out time.
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
	 * Method to calculate total working time for a user. 
	 * It sums up the time differences calculated per user with the calculateTimeDifference() method. 
	 * @author Olga
	 * @return String with the total working time in the format - hours:minutes:seconds
	 */
	public String calculateTotalWorkingTime(Integer userId) {
		
		List<TimeDifference> timeDifference = calculateTimeDifference(userId);
		List<Duration> durations = new ArrayList<>();
		
		for (TimeDifference td : timeDifference) {			
			Long hours = (long) td.getTimeDiff().getHours();
			Long minutes= (long) td.getTimeDiff().getMinutes();
			Long seconds = (long) td.getTimeDiff().getSeconds();
			// Idea to use Duration class to sum up time intervals is from here:
			// https://stackoverflow.com/questions/48685158/sum-of-time-durations-from-a-list-in-android-studio
			durations.add(Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds));
		}
		
		Duration totalDuration = Duration.ZERO;
	    for (Duration dur : durations) {
	    	totalDuration = totalDuration.plus(dur);
	    }
	    
	    // Duration class string formatting example is from here: 
	    // https://stackoverflow.com/questions/266825/how-to-format-a-duration-in-java-e-g-format-hmmss
	    String hms = String.format("%d:%02d:%02d", totalDuration.toHours(), totalDuration.toMinutesPart(), totalDuration.toSecondsPart());

		return hms;
	}
	
}
