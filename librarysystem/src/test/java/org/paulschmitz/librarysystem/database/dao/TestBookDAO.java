package org.paulschmitz.librarysystem.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.paulschmitz.librarysystem.database.entity.Book;
import org.springframework.test.annotation.Rollback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class TestBookDAO {

	@Autowired
	private BookDAO bookDao;

	/**
	 * Creates a book entity. This data will be committed to the database for use in
	 * later tests.
	 */
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateBook() {
		Book testBook = new Book();
		testBook.setTitle("Test Title");
		testBook.setAuthor("Test Author");
		testBook = bookDao.save(testBook);

		assertThat(testBook.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void testFindBookByTitle() {
		Book testBook = bookDao.findBookByTitle("Test Title");
		assertThat(testBook.getTitle()).isEqualTo("Test Title");
	}

	@Test
	@Order(3)
	public void testFindAll() {
		List<Book> testBookList = bookDao.findAll();
		assertThat(testBookList.size()).isGreaterThan(0);
	}

	/**
	 * Updates the book by changing its author
	 */
	@Test
	@Rollback(false)
	@Order(4)
	public void testUpdateBook() {
		Book testBook = bookDao.findBookByTitle("Test Title");
		testBook.setAuthor("Second Author");
		bookDao.save(testBook);

		Book testNewBook = bookDao.findBookByTitle("Test Title");
		assertThat(testNewBook.getAuthor()).isEqualTo("Second Author");
	}

	/**
	 * Deletes the book that was created. If all tests are run successfully, the
	 * database will be the same as it started.
	 */
	@Test
	@Rollback(false)
	@Order(5)
	public void testDeleteBook() {
		Book testBook = bookDao.findBookByTitle("Test Title");
		bookDao.delete(testBook);

		Book testNewBook = bookDao.findBookByTitle("Test Title");
		assertThat(testNewBook).isNull();
	}
}
