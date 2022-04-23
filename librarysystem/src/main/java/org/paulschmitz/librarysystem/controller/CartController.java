package org.paulschmitz.librarysystem.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.paulschmitz.librarysystem.database.dao.CheckoutDAO;
import org.paulschmitz.librarysystem.database.dao.LineItemDAO;
import org.paulschmitz.librarysystem.database.dao.UserDAO;
import org.paulschmitz.librarysystem.database.entity.Checkout;
import org.paulschmitz.librarysystem.database.entity.Item;
import org.paulschmitz.librarysystem.database.entity.LineItem;
import org.paulschmitz.librarysystem.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * This class manages the functions of the cart (checkout) workflow. The user
 * can view their cart of line items, add/remove line items, and complete the
 * checkout. At each point, the cart's line items are immediately stored in the
 * database.
 * 
 * @author p_schmitz
 *
 */
@Slf4j
@Controller
public class CartController {

	@Autowired
	private CheckoutDAO checkoutDao;

	@Autowired
	private LineItemDAO lineItemDao;

	@Autowired
	private UserDAO userDao;

	/**
	 * This method displays the cart contents.
	 * 
	 * @return MAV with user's cart contents.
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/cart", method = RequestMethod.GET)
	public ModelAndView view() throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("user/cart");

		/*
		 * returns the list of items in cart, or an empty list if there are no items in
		 * cart. then, add that list to the response so it can be accessed in cart.jsp.
		 */
		List<LineItem> cartItems = lineItemDao.findLineItemsByCheckout(getUserPendingCart());
		response.addObject("cartItems", cartItems);
		return response;
	}

	/**
	 * This method adds the incoming item to the user's cart. It creates a line item
	 * in the database which joins the item to the cart. Sets the due date to 30
	 * days after the current date. Returns a view of the updated cart contents.
	 * 
	 * @param item the item to add
	 * @return MAV showing the new cart contents
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/cart/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(Item item) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("user/cart");

		// get checkout entity
		Checkout cart = getUserPendingCart();

		// create line item and fill in the fields
		LineItem line = new LineItem();
		line.setItem(item);
		line.setCheckout(cart);
		line.setDue(LocalDate.now().plusDays(30));
		line = lineItemDao.save(line);

		// retrieve all line items currently in cart
		List<LineItem> cartItems = lineItemDao.findLineItemsByCheckout(cart);
		response.addObject("cartItems", cartItems);
		return response;
	}

	/**
	 * Removes the incoming line item id from the user's cart by deleting it from
	 * the database. Returns a view of the updated cart contents.
	 * 
	 * @param lineItemId the line item id to be removed
	 * @return MAV with updated cart contents
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/cart/removeItem", method = RequestMethod.POST)
	public ModelAndView removeItem(Integer lineItemId) throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("user/cart");

		// query for the line item entity
		LineItem line = lineItemDao.findLineItemById(lineItemId);

		// validates that the query was not null before proceeding.
		if (line != null) {
			lineItemDao.delete(line);
		}

		// retrieves all line items currently in cart
		List<LineItem> cartItems = lineItemDao.findLineItemsByCheckout(getUserPendingCart());
		response.addObject("cartItems", cartItems);
		return response;
	}

	/**
	 * Removes the "pending" flag from the checkout, which means that this checkout
	 * is fully committed. Returns a view of this checkout's detail page. It
	 * includes the line items and their status. The method also creates a log file
	 * with the transaction details.
	 * 
	 * @return MAV of checkout details including all line items.
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/cart/checkout", method = RequestMethod.POST)
	public ModelAndView checkout() throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("user/orderDetails");

		// get cart, set to not pending, save, and add to display
		Checkout cart = getUserPendingCart();
		cart.setPending(false);
		cart = checkoutDao.save(cart);
		response.addObject("checkout", cart);

		// retrieve all line items from the cart, add to display
		List<LineItem> lineItemList = lineItemDao.findLineItemsByCheckout(cart);
		response.addObject("lineItemList", lineItemList);

		// log cart details to logs/recipts/recipt{cartId}.txt
		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter("logs/recipts/recipt" + cart.getId() + ".txt", false));
			// log user details
			out.write(getAuthenticatedUser().toString());
			out.newLine();
			// log cart details
			out.write(cart.toString());
			out.newLine();
			// log line item details
			for (LineItem li : lineItemList) {
				out.write(li.toString());
				out.newLine();
			}
			out.write("end of file");
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error(e.toString());
			log.error("Unable to create log file for checkout id " + cart.getId() + ".");
		}

		return response;
	}

	/**
	 * Helper method retrieves the authenticated user's email and then queries for
	 * that user object.
	 * 
	 * @return authenticated user object
	 */
	private User getAuthenticatedUser() {
		final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		return userDao.findUserByEmail(userEmail);
	}

	/**
	 * Helper method retrieves the authenticates user's email and then queries for
	 * that user's pending checkout cart, if any. If there is a pending cart, then
	 * that object is returned. If there is not a pending cart, then a new one is
	 * created for that user and returned.
	 * 
	 * @return authenticated user's pending cart
	 */
	private Checkout getUserPendingCart() {
		User u = getAuthenticatedUser();
		Collection<Checkout> userCheckouts = u.getCheckout();

		// search the user's current checkouts for a pending checkout and use that
		Checkout cart = null;
		for (Checkout c : userCheckouts) {
			if (c.getPending()) {
				cart = c;
				break;
			}
		}

		// if the search returned null, then create a new cart which will be pending
		if (cart == null) {
			cart = new Checkout();
			cart.setPending(true);

			// add the cart to the user's list, since the user controls the relationship
			userCheckouts.add(cart);
			u.setCheckout(userCheckouts);

			// save both entities
			cart = checkoutDao.save(cart);
			userDao.save(u);
		}

		return cart;
	}
}
