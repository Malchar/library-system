package org.paulschmitz.librarysystem.database.dao;

import java.util.List;
import org.paulschmitz.librarysystem.database.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {
	
	public Item findItemById(@Param("id") Integer id);
	
	// find one item matching input book title
	public Item findItemByBookTitle(@Param("bookTitle") String bookTitle);

	// returns all inventory items that represent a particular book.
	public List<Item> findItemsByBookId(@Param("bookId") Integer bookId);
	
}
