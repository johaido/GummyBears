package com.gummybear.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.gummybear.common.entity.Role;
import com.gummybear.common.entity.User;

/**
 * Unit tests to create users (Admins and Employees) in the users table in the DB 
 * @author Olga
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryAddUsersTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;
	
	// Store the passwords in the encrypted form in the DB
	// the Spring security mechanism will encrypt the password provided by the user in the login page 
	// and compare it with the encrypted password stored in the DB
	@Test
	public void testEncryptedPasswordAdmin() {
		// Create new user and set attributes
		Role roleAdmin = new Role(1);
		Role roleEmployee = new Role(2);
		User newUser = new User();
		newUser.setEmail("bob@gummybear.com");
		newUser.setFirstName("Bob");
		newUser.setLastName("Doe");
		newUser.setWorkingHours(8.0);
		newUser.setEnabled(true);
		newUser.addRole(roleAdmin);
		newUser.addRole(roleEmployee);
		
		// Create hashed password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "qwerty"; 
		String encodedPassword = passwordEncoder.encode(rawPassword);
		boolean matches =  passwordEncoder.matches(rawPassword, encodedPassword);
		
		newUser.setPassword(encodedPassword);
		
		// Save user to the database
		User savedUser = repo.save(newUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testEncryptedPasswordEmployee() {
		// Create new user and set attributes
		Role roleEmployee = new Role(2);
		User newUser = new User();
		newUser.setEmail("anna@gummybear.com");
		newUser.setFirstName("Anna");
		newUser.setLastName("Doe");
		newUser.setWorkingHours(8.0);
		newUser.setEnabled(true);
		newUser.addRole(roleEmployee);
		
		// Create hashed password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "qwerty"; 
		String encodedPassword = passwordEncoder.encode(rawPassword);
		boolean matches =  passwordEncoder.matches(rawPassword, encodedPassword);
		
		newUser.setPassword(encodedPassword);
		
		// Save user to the database
		User savedUser = repo.save(newUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testEncryptedPasswordNonAdmin() {
		// Create new user and set attributes
		Role roleEmployee = new Role(2);
		User newUser = new User();
		newUser.setEmail("john@gummybear.com");
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setWorkingHours(8.0);
		newUser.setEnabled(true);
		newUser.addRole(roleEmployee);
		
		// Create hashed password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "qwerty"; 
		String encodedPassword = passwordEncoder.encode(rawPassword);
		boolean matches =  passwordEncoder.matches(rawPassword, encodedPassword);
		
		newUser.setPassword(encodedPassword);
		
		// Save user to the database
		User savedUser = repo.save(newUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
}