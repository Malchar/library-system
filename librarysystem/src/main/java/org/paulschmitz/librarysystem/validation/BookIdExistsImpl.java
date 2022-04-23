package org.paulschmitz.librarysystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.paulschmitz.librarysystem.database.dao.BookDAO;
import org.paulschmitz.librarysystem.database.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is the implementation of the Book Id checker. It should be used to
 * validate the creation of an Item entity. The Item's book reference must refer
 * to a book that already exists in the database. It is checked by querying the
 * provided book Id.
 * 
 * @author p_schmitz
 *
 */
public class BookIdExistsImpl implements ConstraintValidator<BookIdExists, Integer> {

	@Autowired
	private BookDAO bookDao;

	@Override
	public void initialize(BookIdExists constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// Returns true iff the input book id matches a database record.
		Book b = bookDao.findBookById(value);
		return (b != null);
	}
}