package com.gummybear.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.gummybear.common.entity.Role;

/**
 * Unit tests to validate Role and RoleRepository classes and 
 * to verify that communication with the DB works correctly.
 * @author Olga
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository repo;

	//Admin Role
	@Test
	public void testCreateAdminRole() {
		Role roleAdmin = new Role("Admin", "Manage employee accounts");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	//Other Roles
	@Test
	public void testCreateEmployeeRole() {
		Role roleEmployee = new Role("Employee", "Company employee");
		Role savedRole = repo.save(roleEmployee);
		assertThat(savedRole.getId()).isGreaterThan(0);
	} 
}
