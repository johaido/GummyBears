package com.gummybear.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.gummybear.common.entity.User;
import com.gummybear.user.UserRepository;


/**
 * @author thitari - logger
 *
 */
public class GummyBearUserDetailsService implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GummyBearUserDetailsService.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		LOGGER.trace("Entering method loadUserbyUsername");
		LOGGER.debug("Authenticating user with email: " +  email);
		
		User user = userRepo.getUserByEmail(email);
		if (user != null) {
			LOGGER.warn("We are tesitng logging with Spring Boot");
			LOGGER.info("User: " + email + " logged-in successfully");
			
			return new GummyBearUserDetails(user);
		}
		LOGGER.error("User not found");
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
