package com.gummybear.admin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.UrlPathHelper;
/**
 * 
 * @author Jonas
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new GummyBearUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			 auth.authenticationProvider(authenticationProvider());
	}

	
//	 @Override 
//	 protected void configure(HttpSecurity http) throws Exception {
//		 http.authorizeRequests().anyRequest().permitAll(); 
//	 }
	 
	
	@Override 
	  protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
	  .antMatchers("/**").hasAuthority("Admin")
	  .anyRequest()
	  .authenticated()
	  .and().formLogin()
	  			.loginPage("/login")
	  			.usernameParameter("email")
	  			.permitAll()
	  			.defaultSuccessUrl("/")
	  .and().logout()
	  		.logoutSuccessHandler(logoutSuccesHandler)
	  		.permitAll();
}
	
//	@Override protected void configure(HttpSecurity http) throws Exception {
//		  http.authorizeRequests() .antMatchers("/**").hasRole("Admin")
//		  .anyRequest().authenticated() .and() .formLogin() .loginPage("/login")
//		  .usernameParameter("email") .and().logout().permitAll(); }
	

// .and().rememberMe()
//	.key("AbcDefgHijKlmnOpqrs_1234567890")
//	.tokenValiditySeconds(7 * 24 * 60 * 60); //7days 24hrs 60Min 60Sec	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
	/**
	 * @author thitari
	 */
	@Autowired
	private CustomLogoutSuccesHandler logoutSuccesHandler;
}
