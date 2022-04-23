package org.paulschmitz.librarysystem.formbean;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.paulschmitz.librarysystem.database.entity.Book;

import lombok.Data;

/**
 * This form bean is used by the index search page. It transports the search
 * string and the list of results back and forth.
 * 
 * @author p_schmitz
 *
 */
@Data
public class IndexSearchFormBean {
	
	// Checks if the search input is blank
	@NotBlank(message = "Cannot do a blank search")
	private String searchInput;
	
	private String searchRadio;
	
	private String searchCheck;
	
	private List<Book> searchResult;
	
}
