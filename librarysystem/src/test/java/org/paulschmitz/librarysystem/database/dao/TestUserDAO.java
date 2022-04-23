package org.paulschmitz.librarysystem.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.paulschmitz.librarysystem.database.entity.User;
import org.springframework.test.annotation.Rollback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class TestUserDAO {

	@Autowired
	private UserDAO userDao;

	/**
	 * Creates a user entity. This data will be committed to the database for use in
	 * later tests.
	 */
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateUser() {
		User testUser = new User();
		testUser.setEmail("Test Email");
		testUser.setName("Test Name");
		testUser = userDao.save(testUser);

		assertThat(testUser.getEmail()).isNotNull();
	}

	@Test
	@Order(2)
	public void testFindUserByEmail() {
		User testUser = userDao.findUserByEmail("Test Email");
		assertThat(testUser.getEmail()).isEqualTo("Test Email");
	}

	@Test
	@Order(3)
	public void testFindAll() {
		List<User> testUserList = userDao.findAll();
		assertThat(testUserList.size()).isGreaterThan(0);
	}

	/**
	 * Updates the user by changing its name
	 */
	@ParameterizedTest
	@ValueSource(strings = {"Paul", "Ryan", "Schmitz"})
	@Rollback(false)
	@Order(4)
	public void testUpdateUser(String name) {
		User testUser = userDao.findUserByEmail("Test Email");
		testUser.setName(name);
		userDao.save(testUser);

		User testNewUser = userDao.findUserByEmail("Test Email");
		assertThat(testNewUser.getName()).isEqualTo(name);
	}

	/**
	 * Deletes the user that was created. If all tests are run successfully, the
	 * database will be the same as it started.
	 */
	@Test
	@Rollback(false)
	@Order(5)
	public void testDeleteUser() {
		User testUser = userDao.findUserByEmail("Test Email");
		userDao.delete(testUser);;
		
		User testNewUser = userDao.findUserByEmail("Test Email");
		assertThat(testNewUser).isNull();
	}
}
