package org.paulschmitz.librarysystem.formbean;

import org.paulschmitz.librarysystem.database.entity.Item;
import org.paulschmitz.librarysystem.database.entity.Item.Condition;
import org.paulschmitz.librarysystem.validation.BookIdExists;
import lombok.Data;

/**
 * This form bean is used with the admin create item function. It passes all
 * fields back and forth, and the fields correspond to an item entity. The book
 * is passed by id rather than object because the client needs to be able to
 * input the book by id. The book id is also validated to check that it actually
 * exists in the database.
 * 
 * @author p_schmitz
 *
 */
@Data
public class ItemFormBean {

	private Integer id;

	// Checks if the book id matches a book in the database.
	@BookIdExists(message = "Book Id not found in database.")
	private Integer bookId;

	private Condition itemCondition;

	// This method sets up all the attributes to match the input item
	public void setEverything(Item item) {
		this.id = item.getId();
		this.bookId = item.getBook().getId();
		this.itemCondition = item.getItemCondition();
	}
}
