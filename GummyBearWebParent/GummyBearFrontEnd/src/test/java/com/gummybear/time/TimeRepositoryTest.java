package com.gummybear.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.gummybear.common.entity.TimeDifference;
import com.gummybear.common.entity.TimeStamp;

/**
 * Unit tests for TimeStamp and TimeRepository classes.
 * @author Olga
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TimeRepositoryTest {
	
	@Autowired
	private TimeRepository repo;
	
	@Test
	public void testDateMethods() throws InterruptedException {
		Date d1 = new Date();
		Thread.sleep(3000);
		Date d2 = new Date();
		long diff = (d2.getTime() - d1.getTime())/1000; // difference in seconds
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(diff);
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = (calendar.get(Calendar.MONTH) + 1); // counting from 0 (zero)
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		System.out.println("year: " + year);
		System.out.println("month: " + month);
		System.out.println("day: " + day);
		System.out.println("hour: " + hour);
		System.out.println("minute: " + minute);
	}
	
	@Test
	public void testAddTimeStampNew() throws InterruptedException {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		TimeStamp ts = new TimeStamp();
	    ts.setDate(date);
	    ts.setTimestamp(calendar);
	    ts.setEvent("in");
	    ts.setUserid(2);
	    repo.save(ts);
	    
	    Thread.sleep(10000);
	    
		Date date2 = new Date();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
	    TimeStamp ts2 = new TimeStamp();
	    ts2.setDate(date2);
	    ts2.setTimestamp(calendar2);
	    ts2.setEvent("out");
	    ts2.setUserid(2);
	    repo.save(ts2);
	}
	
	@Test
	public void testTimeDifference() {
		List<TimeDifference> td = repo.calculateTimeDifference();
		System.out.println("id: " + td.get(0).getId());
		System.out.println("userid: " + td.get(0).getUserid());
		System.out.println("date: " + td.get(0).getDate());
		System.out.println("start_time: " + td.get(0).getStartTime());
		System.out.println("end_time: " + td.get(0).getEndTime());
		System.out.println("time_diff: " + td.get(0).getTimeDiff());
	}
	
}
