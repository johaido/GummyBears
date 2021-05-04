package com.gummybear.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.gummybear.common.entity.Role;
import com.gummybear.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userJohnDoe = new User("johndoe@gummybear.com", "password12345", "John", "Doe", 8.0);
		userJohnDoe.addRole(roleAdmin);
		
		User savedUser = repo.save(userJohnDoe);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		User userHarryPotter = new User("harrypotter@gummybear.com", "password12345", "Harry", "Potter", 4.0);
		userHarryPotter.addRole(roleAdmin);
		userHarryPotter.addRole(roleEmployee);
		
		User savedUser = repo.save(userHarryPotter);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.print(user));
	}
	
	@Test
	public void testGetUserById() {
		User firstUser = repo.findById(1).get();
		System.out.println(firstUser);
		assertThat(firstUser).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userFirst = repo.findById(1).get();
		userFirst.setEnabled(true);
		userFirst.setEmail("firstUser@gammybear.com");
		repo.save(userFirst);
	}
	
	@Test 
	public void testUpdateRoles() {
		User userJohnDoe = repo.findById(1).get();
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		userJohnDoe.addRole(roleEmployee);
		userJohnDoe.getRoles().remove(roleAdmin);
		// for some reasons getRoles().remove() doesn't work??
		System.out.println(userJohnDoe.getRoles());
		repo.save(userJohnDoe);
	}
	
	@Test
	public void testDeleteUser() { 
		Integer userId = 1;
		repo.deleteById(userId);
	}
	
	/**
	 * @author Thitari
	 */
	@Test
	public void testCountById() {
		//Integer id  = 100; //right now is no user with ID 100 that exist in the DB ->Thus it should failed when you run the test
		Integer id = 1; //It will success
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	/**
	 * @author Thitari
	 * Testing disable and enable for functionallity 
	 */
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	

}
