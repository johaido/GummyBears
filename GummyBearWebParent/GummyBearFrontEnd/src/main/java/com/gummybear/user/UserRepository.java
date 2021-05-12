package com.gummybear.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gummybear.common.entity.User;

/**
 * Extension of the CrudRepository interface to be able to perform CRUD operations on the DB.
 * @author Olga
 *
 */
@Repository
public interface UserRepository extends CrudRepository <User, Integer>{

	/**
	 * @author Jonas
	 * @param email
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
	/**
	 * @author Thitari
	 * @return
	 * this method follow convention specify by spring data jpa
	 * Thus we do not have to specify any sql statement or any query 
	 * and parse the parameter id in this method
	 */
	public Long countById(Integer id);
	
	/**
	 * @author Jonas
	 * @param id
	 * @param enabled
	 */
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);

	

}
