package org.paulschmitz.librarysystem.controller;

import java.util.List;
import javax.validation.Valid;
import org.paulschmitz.librarysystem.database.dao.BookDAO;
import org.paulschmitz.librarysystem.database.dao.ItemDAO;
import org.paulschmitz.librarysystem.database.entity.Book;
import org.paulschmitz.librarysystem.database.entity.Item;
import org.paulschmitz.librarysystem.formbean.IndexSearchFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

/**
 * This class manages the functions for the index/search page.
 * 
 * @author p_schmitz
 *
 */
@Slf4j
@Controller
public class IndexController {

	@Autowired
	private BookDAO bookDao;

	@Autowired
	private ItemDAO itemDao;

	/**
	 * Sets up a blank form bean for the search function.
	 * 
	 * @return MAV with blank search form bean
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() throws Exception {
		ModelAndView response = new ModelAndView();

		IndexSearchFormBean form = new IndexSearchFormBean();

		response.addObject("form", form);
		response.setViewName("index");
		return response;
	}

	/**
	 * Receives the client's search form and returns the search results. If the form
	 * fails validation, then an error message is returned instead.
	 * 
	 * @param form          client submitted search form bean
	 * @param bindingResult search form bean validation
	 * @return MAV with search results or error display
	 * @throws Exception
	 */
	@RequestMapping(value = "/index/searchSubmit", method = RequestMethod.POST)
	public ModelAndView searchSubmit(@Valid IndexSearchFormBean form, BindingResult bindingResult) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("index");

		if (bindingResult.hasErrors()) {
			response.addObject("error", "Cannot do a blank search.");
			return response;
		}

		// create result list
		List<Book> result = null;
	
		// case sensitive search
		if (form.getSearchCheck() != null && form.getSearchCheck().equals("caseSensitive")) {
			if (form.getSearchRadio().equals("category")) {
				result = bookDao.findBooksByCategoryContaining(form.getSearchInput());
			}
			else if (form.getSearchRadio().equals("author")) {
				result = bookDao.findBooksByAuthorContaining(form.getSearchInput());
			}
			// default case is search by title
			else {
				result = bookDao.findBooksByTitleContaining(form.getSearchInput());
			}
		}
		// ignore case search
		else {
			if (form.getSearchRadio().equals("category")) {
				result = bookDao.findBooksByCategoryContainingIgnoreCase(form.getSearchInput());
			}
			else if (form.getSearchRadio().equals("author")) {
				result = bookDao.findBooksByAuthorContainingIgnoreCase(form.getSearchInput());
			}
			// default case is search by title
			else {
				result = bookDao.findBooksByTitleContainingIgnoreCase(form.getSearchInput());
			}
		}

		form.setSearchResult(result);
		response.addObject("form", form);
		return response;
	}

	/**
	 * This function receives a book id and returns a display of the corresponding
	 * book object and a list of all available item instances of that book.
	 * 
	 * @param id bookId
	 * @return MAV with the book and a list of items that represent that book
	 * @throws Exception
	 */
	@RequestMapping(value = "/details", method = RequestMethod.POST)
	public ModelAndView details(@RequestParam(name = "id", required = false) Integer id) throws Exception {
		ModelAndView response = new ModelAndView();

		// TODO: this if-statement is for debugging purposes only
		if (id == null) {
			log.debug("details page received a null book id input");
			// sets a default value of 1
			id = 1;
		}

		Book book = bookDao.findBookById(id);

		// TODO: this if-statement is for debugging purposes only
		if (book == null) {
			log.debug("details page tried to display a null book");
			// create a blank book for the display object
			book = new Book();
		}

		// get all items which are copies of the specified book
		// TODO: change this so that only "available" books can be selected
		List<Item> itemList = itemDao.findItemsByBookId(book.getId());

		// add all retrieves items to the page display
		response.addObject("book", book);
		response.addObject("itemList", itemList);
		response.setViewName("details");
		return response;
	}

}
