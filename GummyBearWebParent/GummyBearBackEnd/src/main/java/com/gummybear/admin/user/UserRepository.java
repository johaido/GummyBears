package com.gummybear.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gummybear.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Integer>{

	/**
	 * @author Thitari
	 * @return
	 * this method follow convention specify by spring data jpa
	 * Thus we do not have to specify any sql statement or any query 
	 * and parse the parameter id in this method
	 */
	public Long countById(Integer id);

	

}
