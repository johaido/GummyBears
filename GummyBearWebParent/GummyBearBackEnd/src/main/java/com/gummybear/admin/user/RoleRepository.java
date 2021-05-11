package com.gummybear.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gummybear.common.entity.Role;

/**
 * Extension of the CrudRepository interface to be able to perform CRUD operations on the DB.
 * @author Olga
 *
 */
@Repository
public interface RoleRepository extends CrudRepository <Role, Integer>{

}