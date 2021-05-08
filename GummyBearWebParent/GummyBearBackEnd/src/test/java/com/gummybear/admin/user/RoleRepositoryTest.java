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

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository repo;
	
	
	  @Test 
	  public void testCreateFirstRole() { 
		  Role roleAdmin = new Role("Admin", "Manage employee accounts"); 
		  Role savedRole = repo.save(roleAdmin);
	  
	  assertThat(savedRole.getId()).isGreaterThan(0); }
	 
	
	@Test
	public void testCreateSeveralRoles() {
		//Role roleAdmin = new Role("Admin", "Manage employee accounts");
		Role roleEmployee = new Role("Employee", "Company employee");
		//repo.saveAll(List.of(roleAdmin, roleEmployee));
		repo.saveAll(List.of(roleEmployee));
		
	}
}
