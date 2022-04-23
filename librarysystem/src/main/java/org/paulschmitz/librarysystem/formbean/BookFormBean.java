package org.paulschmitz.librarysystem.formbean;

import javax.validation.constraints.NotBlank;

import org.paulschmitz.librarysystem.database.entity.Book;
import org.paulschmitz.librarysystem.validation.BookIsbnCheck;

import lombok.Data;

/**
 * This form bean is used by the admin create book function. It passes all the
 * fields back and forth when adding or editing books. The ISBN is validated
 * with a checker.
 * 
 * @author p_schmitz
 *
 */
@Data
public class BookFormBean {

	private Integer id;
	
	@NotBlank(message = "Title is required")
	private String title;
	
	private String author;
	
	private String description;
	
	@BookIsbnCheck(message = "Invalid ISBN")
	private String isbn;
	
	private String imageUrl;
	
	private String category;

	// This method sets up all the fields to match the input book.
	public void setEverything(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.description = book.getDescription();
		this.isbn = book.getIsbn();
		this.imageUrl = book.getImageUrl();
		this.category = book.getCategory();
	}
}
