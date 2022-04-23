package org.paulschmitz.librarysystem.controller;

import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.paulschmitz.librarysystem.database.dao.BookDAO;
import org.paulschmitz.librarysystem.database.dao.ItemDAO;
import org.paulschmitz.librarysystem.database.entity.Book;
import org.paulschmitz.librarysystem.database.entity.Item;
import org.paulschmitz.librarysystem.database.entity.Item.Condition;
import org.paulschmitz.librarysystem.formbean.BookFormBean;
import org.paulschmitz.librarysystem.formbean.ItemFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * This class handles the functions of the administrator controller module. It
 * mainly creates and edits book and item entities for the database. You can
 * also view existing entities.
 * 
 * @author p_schmitz
 *
 */
@Slf4j
@Controller
public class BookController {

	@Autowired
	private BookDAO bookDao;

	@Autowired
	private ItemDAO itemDao;

	/**
	 * Shows the blank form page. The item condition list is added to populate the
	 * drop-down menu.
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/control", method = RequestMethod.GET)
	public ModelAndView control() throws Exception {
		ModelAndView response = new ModelAndView();

		// for drop-down menu
		response.addObject("itemConditionList", Condition.values());

		response.setViewName("admin/control");
		return response;
	}

	/**
	 * This function creates new books using the incoming form bean. If it fails
	 * validation, then the errors will be returned and the form will stay populated
	 * with the original input values.
	 * 
	 * If validated, then check if the incoming book id already exists. If it does,
	 * then it edits it based on the form. Otherwise, it creates a new book with a
	 * new generated id and using the form details.
	 * 
	 * @param form          the book form
	 * @param bindingResult the validation results
	 * @return MAV of blank form if no errors, or refill the form and show errors
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/addBook", method = RequestMethod.POST)
	public ModelAndView bookSubmit(@Valid BookFormBean form, BindingResult bindingResult) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");
		// this populates the drop-down menu
		response.addObject("itemConditionList", Condition.values());

		// error checking, uses @Valid, form bean
		if (bindingResult.hasErrors()) {
			HashMap<String, String> errors = new HashMap<String, String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.put(((FieldError) error).getField(), error.getDefaultMessage());
				log.info(((FieldError) error).getField() + " " + error.getDefaultMessage());
			}

			// add error list to the model
			response.addObject("formErrors", errors);
			// refill form with current values
			response.addObject("editBook", form);
			return response;
		}

		// case where validation successful

		// attempt to retrieve existing book id to edit.
		Book book = null;
		if (form.getId() != null) {
			book = bookDao.findBookById(form.getId());
		}
		// if there isn't one, then we are creating a new book.
		if (book == null) {
			book = new Book();
		}

		// setup the book's attributes using the form
		book.setTitle(form.getTitle());
		book.setAuthor(form.getAuthor());
		book.setDescription(form.getDescription());
		book.setIsbn(form.getIsbn());
		book.setImageUrl(form.getImageUrl());
		book.setCategory(form.getCategory());

		bookDao.save(book);

		return response;
	}

	/**
	 * This function creates new items using the incoming form bean. If it fails
	 * validation, then the errors will be returned and the form will stay populated
	 * with the original input values.
	 * 
	 * If validated, then check if the incoming item id already exists. If it does,
	 * then it edits it based on the form. Otherwise, it creates a new item with a
	 * new generated id and using the form details.
	 * 
	 * @param form          the item form
	 * @param bindingResult the validation results
	 * @return MAV of blank form if no errors, or refill the form and show errors
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/addItem", method = RequestMethod.POST)
	public ModelAndView itemSubmit(@Valid ItemFormBean form, BindingResult bindingResult) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");
		// this populates the drop-down menu
		response.addObject("itemConditionList", Condition.values());

		// error checking, uses @Valid, form bean
		if (bindingResult.hasErrors()) {
			HashMap<String, String> errors = new HashMap<String, String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.put(((FieldError) error).getField(), error.getDefaultMessage());
				log.info(((FieldError) error).getField() + " " + error.getDefaultMessage());
			}

			// add error list to the model
			response.addObject("formErrors", errors);
			// refill the form with the current values
			response.addObject("editItem", form);
			return response;
		}

		// case where validation successful

		// attempt to retrieve existing item id to edit.
		Item item = null;
		if (form.getId() != null) {
			item = itemDao.findItemById(form.getId());
		}
		// if there isn't one, then we are creating a new item.
		if (item == null) {
			item = new Item();
		}

		// at this point, the book id has already been validated from the form bean
		Book b = bookDao.findBookById(form.getBookId());
		item.setBook(b);
		item.setItemCondition(form.getItemCondition());

		itemDao.save(item);

		return response;
	}

	/**
	 * This function retrieves and returns a list of all items for the search
	 * display. It also passes through any error messages from other methods that
	 * redirect into this.
	 * 
	 * @param errorMessage the error message (optional)
	 * @return MAV with item list and any errors
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/searchItem", method = RequestMethod.GET)
	public ModelAndView searchItem(@RequestParam(name = "errorMessage", required = false) String errorMessage)
			throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");

		List<Item> itemList = itemDao.findAll();
		response.addObject("itemList", itemList);

		// to populate the drop-down menu
		response.addObject("itemConditionList", Condition.values());
		// pass-through from redirecting methods
		response.addObject("errorMessage", errorMessage);
		return response;
	}

	/**
	 * This function retrieves and returns a list of all books for the search
	 * display. It also passes through any error messages from other methods that
	 * redirect into this.
	 * 
	 * @param errorMessage the error message (optional)
	 * @return MAV with book list and any errors
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/searchBook", method = RequestMethod.GET)
	public ModelAndView searchBook(@RequestParam(name = "errorMessage", required = false) String errorMessage)
			throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");

		List<Book> bookList = bookDao.findAll();
		response.addObject("bookList", bookList);

		// to populate the drop-down menu
		response.addObject("itemConditionList", Condition.values());
		// pass-through from redirecting methods
		response.addObject("errorMessage", errorMessage);
		return response;
	}

	/**
	 * Prefills the form with the details of the selected book. Meant to be followed
	 * by the addBook() method when doing an edit.
	 * 
	 * @param book the book to edit
	 * @return MAV with prefilled form for this book
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/editBook", method = RequestMethod.POST)
	public ModelAndView editBook(Book book) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");

		BookFormBean form = new BookFormBean();
		form.setEverything(book);

		response.addObject("editBook", form);
		response.addObject("itemConditionList", Condition.values());
		return response;
	}

	/**
	 * Prefills the form with the details of the selected item. Meant to be followed
	 * by the addItem() method when doing an edit.
	 * 
	 * @param item the item to edit
	 * @return MAV with prefilled form for this item
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/editItem", method = RequestMethod.POST)
	public ModelAndView editItem(Item item) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");

		ItemFormBean form = new ItemFormBean();
		form.setEverything(item);

		response.addObject("editItem", form);
		response.addObject("itemConditionList", Condition.values());
		return response;
	}

	/**
	 * Deletes the incoming book from the database if able. Returns an error message
	 * if the delete is impossible due to integrity constraints. This happens if
	 * another entity (item) has a foreign key pointing to this book.
	 * 
	 * @param book the book to delete
	 * @return MAV redirects to the search page, and may include error message.
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/deleteBook", method = RequestMethod.POST)
	public ModelAndView deleteBook(Book book) throws Exception {
		ModelAndView response = new ModelAndView();

		try {
			bookDao.delete(book);
		}
		// exception happens when database integrity constraints prevent the deletion.
		catch (Exception e) {
			response.addObject("errorMessage",
					"Unable to delete book id " + book.getId() + " due to integrity containts.");
		}

		response.setViewName("redirect:/admin/searchBook");
		return response;
	}

	/**
	 * Deletes the incoming item from the database if able. Returns an error message
	 * if the delete is impossible due to integrity constraints. This happens if
	 * another entity (line item) has a foreign key pointing to this item.
	 * 
	 * @param item the item to delete
	 * @return MAV redirects to the search page, and may include error message.
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/deleteItem", method = RequestMethod.POST)
	public ModelAndView deleteItem(Item item) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("admin/control");

		try {
			itemDao.delete(item);
		}
		// exception happens when database integrity constraints prevent the deletion.
		catch (Exception e) {
			response.addObject("errorMessage",
					"Unable to delete item id " + item.getId() + " due to integrity containts.");
		}
		response.setViewName("redirect:/admin/searchItem");
		return response;
	}
}
