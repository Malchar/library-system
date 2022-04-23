package org.paulschmitz.librarysystem.database.dao;

import java.util.Set;

import org.paulschmitz.librarysystem.database.entity.User;
import org.paulschmitz.librarysystem.database.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
	
	// Non-native query returns all of the user's roles
	@Query(value = "select u.userRole from User u where u = :user", nativeQuery = false)
	public Set<UserRole> queryRoles(@Param("user") User user);
	
}
