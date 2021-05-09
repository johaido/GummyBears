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
		User userJohnDoe = new User("John@gummybear.com", "1234567890", "John", "Doe");
		userJohnDoe.addRole(roleAdmin);

		User savedUser = repo.save(userJohnDoe);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateUserWithTwoRoles() {
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		User userHarryPotter = new User("harrypotter@gummybear.com", "password12345", "Harry", "Potter");
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
		userFirst.setEmail("John@gammybear.com");
		repo.save(userFirst);
	}

	@Test 
	public void testUpdateRoles() {
		User userJohnDoe = repo.findById(1).get();
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		userJohnDoe.getRoles().remove(roleAdmin);
		userJohnDoe.addRole(roleEmployee);
		// for some reasons getRoles().remove() doesn't work?? -> work now (swicth lines between addRoles // getRoles)
		
		//System.out.println(userJohnDoe.getRoles());
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
	public void testGetUserByEmail() {
		String email = "another@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();		
	}
	
	@Test
	public void testCountById() {
		//Integer id = 100;
		Integer id = 1;
		Long countById = repo.countById(id);	
		
		assertThat(countById).isNotNull().isGreaterThan(0);
		}
	
	
	@Test
	public void testDisableUser() {
		Integer id = 4;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 10;
		repo.updateEnabledStatus(id, true);
	}
	
	
}