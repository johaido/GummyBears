package com.gummybear.time;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gummybear.common.entity.TimeDifference;
import com.gummybear.common.entity.TimeStamp;
import com.gummybear.common.entity.TimeStampNew;
import com.gummybear.common.entity.User;

/**
 * Extension of the CrudRepository interface to be able to perform CRUD operations on the DB.
 * @author Olga
 *
 */
@Repository
public interface TimeRepositoryNew extends CrudRepository <TimeStampNew, Integer>{	
	
	/**
	 * SQL statement to create new table with the time difference between clock-in and clock-out time per userid per date.
	 * @author Olga
	 */
	@Query("SELECT new com.gummybear.common.entity.TimeDifference(x.id, x.userid, x.date, x.timestamp as start_time, MIN(y.timestamp) as end_time, TIMEDIFF(MIN(y.timestamp), x.timestamp) as time_diff) FROM TimeStampNew x JOIN TimeStampNew y ON y.timestamp >= x.timestamp AND x.userid = y.userid WHERE x.event = 'in' AND y.event = 'out' GROUP BY x.userid, x.id, x.date")
	public List<TimeDifference> calculateTimeDifference();
}