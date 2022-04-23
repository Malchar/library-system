package org.paulschmitz.librarysystem.database.dao;

import org.paulschmitz.librarysystem.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, String> {
	
	// Lookup user by primary key
	public User findUserByEmail(@Param("email") String email);

}
