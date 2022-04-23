package org.paulschmitz.librarysystem.database.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.paulschmitz.librarysystem.database.entity.Book;
import org.paulschmitz.librarysystem.database.entity.Item;
import org.paulschmitz.librarysystem.database.entity.Item.Condition;
import org.springframework.test.annotation.Rollback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * This class contains the testing for the ItemDAO. Note that it depends on the
 * BookDAO since items have a foreign key pointing to books.
 * 
 * @author p_schmitz
 *
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class TestItemDAO {

	@Autowired
	private ItemDAO itemDao;

	@Autowired
	private BookDAO bookDao;

	/**
	 * Creates an item entity. This data will be committed to the database for use
	 * in later tests.
	 */
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateItem() {
		Book testBook = new Book();
		testBook.setTitle("Test Title");
		testBook = bookDao.save(testBook);

		Item testItem = new Item();
		testItem.setBook(testBook);
		testItem = itemDao.save(testItem);

		assertThat(testItem.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void testFindItemByBookTitle() {
		Item testItem = itemDao.findItemByBookTitle("Test Title");
		assertThat(testItem.getBook().getTitle()).isEqualTo("Test Title");
	}

	@Test
	@Order(3)
	public void testFindAll() {
		List<Item> testItemList = itemDao.findAll();
		assertThat(testItemList.size()).isGreaterThan(0);
	}

	/**
	 * Updates the item by changing its condition
	 */
	@Test
	@Rollback(false)
	@Order(4)
	public void testUpdateItem() {
		Item testItem = itemDao.findItemByBookTitle("Test Title");
		testItem.setItemCondition(Condition.New);
		itemDao.save(testItem);

		Item testNewItem = itemDao.findItemByBookTitle("Test Title");
		assertThat(testNewItem.getItemCondition()).isEqualTo(Condition.New);
	}

	/**
	 * Deletes the item and book that were created. If all tests are run
	 * successfully, the database will be the same as it started.
	 */
	@Test
	@Rollback(false)
	@Order(5)
	public void testDeleteItem() {
		// delete item first due to integrity constraints
		Item testItem = itemDao.findItemByBookTitle("Test Title");
		itemDao.delete(testItem);
		
		Item testNewItem = itemDao.findItemByBookTitle("Test Title");
		assertThat(testNewItem).isNull();
		
		// delete book		
		Book testBook = bookDao.findBookByTitle("Test Title");
		bookDao.delete(testBook);

		Book testNewBook = bookDao.findBookByTitle("Test Title");
		assertThat(testNewBook).isNull();
	}
}








