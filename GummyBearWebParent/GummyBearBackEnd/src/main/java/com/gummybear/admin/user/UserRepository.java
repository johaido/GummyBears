package com.gummybear.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gummybear.common.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Integer>{
	
	@Query("SELECT u FROM user u Where u.email = :email")
	public User getUserByEmail(@Param("email") String email);

	/**
	 * @author Thitari
	 * @return
	 * this method follow convention specify by spring data jpa
	 * Thus we do not have to specify any sql statement or any query 
	 * and parse the parameter id in this method
	 */
	public Long countById(Integer id);

	public User getUserByEmail(String email);

	

}
