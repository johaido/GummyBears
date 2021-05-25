package com.gummybear.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gummybear.common.entity.User;
import com.gummybear.common.entity.Role;

/**
 * Performs operations on the User Repository (DB).
 * 
 * @author Olga
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo; // update param reop -> userRepo

	/*
	 * @Autowired private RoleRepository roleRepo;
	 */

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}

	/**
	 * @author Thitari
	 *//*
		 * public List<Role> listRoles(){ return (List<Role>) roleRepo.findAll(); }
		 */

	/**
	 * @author Thitari
	 *
	 */
	public void save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();

			/**
			 * Check if password on the form is empty or not if Empty -> The password is
			 * unchanged We need to read the password form the database and set to the
			 * object
			 */
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encoderPassword(user);
			}
		} else {
			encoderPassword(user);
		}

		userRepo.save(user);
	}

	private void encoderPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}

	/**
	 * @author Thitari
	 */
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail == null)
			return true;
		/**
		 * if id is null means that the form in new modal otherwise means user is being
		 * edited.
		 */
		boolean isCreatingNew = (id == null);
		if (isCreatingNew) {
			if (userByEmail != null)
				return false;
		} else {
			// Email is not unique
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @author Thitari
	 * @param id
	 * @throws UserNotFoundException
	 */
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}

	/**
	 * @author Thitari
	 * @param id - parse the given Id We need to count the user by Id Then check
	 *           with the condition - if no user exists in DB
	 */
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		// No user exists
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		userRepo.deleteById(id);
	}

	/**
	 * @author Thitari
	 */
	public void updateUserEnableStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}

}
