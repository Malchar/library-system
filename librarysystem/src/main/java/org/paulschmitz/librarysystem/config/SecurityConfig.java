package org.paulschmitz.librarysystem.config;

import org.paulschmitz.librarysystem.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is the configuration for Spring security. It utilizes
 * BCryptPasswordEncoder()
 * 
 * @author p_schmitz
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// these URL can be accessed without login
				.antMatchers("/index/**", "/details/**", "/login/**", "/error/**").permitAll()
				// these URL can only be accessed by logged-in users
				.antMatchers("/user/**", "/admin/**").authenticated()
				// these URL can only be accessed by administrators
				.antMatchers("/admin/**").hasAuthority("ADMIN").and().formLogin()
				// URL for login page
				.loginPage("/login/login")
				// URL for login submission
				.loginProcessingUrl("/login/loginSubmit")
				// URL default redirect after login
				.defaultSuccessUrl("/index").and().logout().invalidateHttpSession(true)
				// URL to log out a user
				.logoutUrl("/login/logout")
				// URL redirect after logout
				.logoutSuccessUrl("/index").and().exceptionHandling()
				// URL for exception handling
				.accessDeniedPage("/error/404");
	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(getAuthenticationProvider());
	}

}
