package org.paulschmitz.librarysystem.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.paulschmitz.librarysystem.database.dao.UserDAO;
import org.paulschmitz.librarysystem.database.entity.User;
import org.paulschmitz.librarysystem.database.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This service layer utilizes the userDao and userRole entity. It returns the
 * authenticated user and that user's authority levels (which correspond to
 * userRole).
 * 
 * @author p_schmitz
 *
 */

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findUserByEmail(email);

		if (user == null) {
			log.debug("username not found exception");
			throw new UsernameNotFoundException("Email '" + email + "' not found in database");
		}

		// gets this user's roles
		List<UserRole> userRoles = new ArrayList<UserRole>(user.getUserRole());

		// spring security configs
		boolean accountIsEnabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		Collection<? extends GrantedAuthority> springRoles = buildGrantAuthorities(userRoles);

		// gets encrypted password and other fields from the database
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				accountIsEnabled, accountNonExpired, credentialsNonExpired, accountNonLocked, springRoles);
	}

	private Collection<? extends GrantedAuthority> buildGrantAuthorities(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		// each userRole represents a different authority level
		for (UserRole role : userRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

}