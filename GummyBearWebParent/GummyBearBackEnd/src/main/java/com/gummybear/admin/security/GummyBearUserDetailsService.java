package com.gummybear.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gummybear.admin.user.UserRepository;

public class GummyBearUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByEmail(email);
		if (user !=null) {
			return new GummyBearUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email: " + email);
	}

}
