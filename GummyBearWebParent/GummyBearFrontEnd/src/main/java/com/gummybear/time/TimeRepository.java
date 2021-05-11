package com.gummybear.time;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gummybear.common.entity.TimeStamp;

/**
 * Extension of the CrudRepository interface to be able to perform CRUD operations on the DB.
 * @author Olga
 *
 */
@Repository
public interface TimeRepository extends CrudRepository <TimeStamp, Integer>{	

}