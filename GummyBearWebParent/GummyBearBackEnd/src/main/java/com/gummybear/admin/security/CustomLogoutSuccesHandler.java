package com.gummybear.admin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.gummybear.admin.user.UserService;

/**
 * 
 * @author thitari
 *
 */
@Component
public class CustomLogoutSuccesHandler extends SimpleUrlLogoutSuccessHandler {

	
	private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutSuccesHandler.class);
	
	@Autowired
	private UserService service;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOG.info("User: " + authentication.getName() + " has logged out.");
		super.onLogoutSuccess(request, response, authentication);
	}
	
	

}
