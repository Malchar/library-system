package org.paulschmitz.librarysystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.paulschmitz.librarysystem.database.dao.CheckoutDAO;
import org.paulschmitz.librarysystem.database.dao.LineItemDAO;
import org.paulschmitz.librarysystem.database.dao.UserDAO;
import org.paulschmitz.librarysystem.database.dao.UserRoleDAO;
import org.paulschmitz.librarysystem.database.entity.Checkout;
import org.paulschmitz.librarysystem.database.entity.LineItem;
import org.paulschmitz.librarysystem.database.entity.User;
import org.paulschmitz.librarysystem.database.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller manages the functions of the "My Account" view. This includes
 * viewing the user's past checkouts, and the details of those checkouts. It
 * also controls the "Return Item" function on that page.
 * 
 * @author p_schmitz
 *
 */
@Controller
public class OrderController {

	@Autowired
	private CheckoutDAO checkoutDao;

	@Autowired
	private LineItemDAO lineItemDao;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserRoleDAO userRoleDao;

	/**
	 * This function populates the "My Account" view. It retrieves the authorized
	 * user, their roles, and checkouts. Another list is created which corresponds
	 * to the checkout list. It contains booleans which are true iff the checkout
	 * has any outstanding line items.
	 * 
	 * @return MAV with user, user's roles, user's checkouts, and a corresponding
	 *         list of booleans showing which checkouts have outstanding line items.
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/orders", method = RequestMethod.GET)
	public ModelAndView view() throws Exception {
		ModelAndView response = new ModelAndView();

		// get authenticated user
		final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userDao.findUserByEmail(userEmail);

		// gather the user's roles for display
		List<UserRole> userRoles = new ArrayList<UserRole>(userRoleDao.queryRoles(user));

		/*
		 * list of this user's checkouts and a corresponding list of booleans which will
		 * be true iff the order contains outstanding line items.
		 */
		List<Checkout> checkoutList = new ArrayList<Checkout>(user.getCheckout());

		// Lambda function to remove checkouts that haven't been finalized yet
		checkoutList.removeIf(e -> e.getPending());

		// builds the list of booleans
		List<Boolean> outstandingList = new ArrayList<Boolean>();
		for (int i = 0; i < checkoutList.size(); i++) {
			Integer query = lineItemDao.queryOutstanding(checkoutList.get(i));
			outstandingList.add(query == 1);
		}

		// builds and return response
		response.addObject("user", user);
		response.addObject("userRoles", userRoles);
		response.addObject("checkoutList", checkoutList);
		response.addObject("outstandingList", outstandingList);
		response.setViewName("user/orders");
		return response;
	}

	/**
	 * This is the administrator's version of the view() method. It gets all
	 * checkouts from the database along with the same boolean list. The boolean
	 * lists true for a checkout iff the checkout has outstanding items.
	 * 
	 * @return MAV with all checkouts, and a corresponding list of booleans showing
	 *         which checkouts have outstanding line items.
	 * @throws Exception
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/user/allOrders")
	public ModelAndView viewAll() throws Exception {
		ModelAndView response = new ModelAndView();

		// retrieves all checkouts from the database
		List<Checkout> checkoutList = checkoutDao.findAll();

		// builds the list of booleans
		List<Boolean> outstandingList = new ArrayList<Boolean>();
		for (int i = 0; i < checkoutList.size(); i++) {
			Integer query = lineItemDao.queryOutstanding(checkoutList.get(i));
			outstandingList.add(query == 1);
		}

		// builds and return response
		response.addObject("checkoutList", checkoutList);
		response.addObject("outstandingList", outstandingList);
		response.setViewName("user/orders");
		return response;
	}

	/**
	 * Uses a path variable to navigate to the specified checkout details based on
	 * id. Note, this method is not protected, so one user may view another user's
	 * order details.
	 * 
	 * @param checkoutId the checkout to display
	 * @return MAV with with checkout and list of line items in this checkout
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/orderDetails/{checkoutId}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView details(@PathVariable(name = "checkoutId", required = false) Integer checkoutId)
			throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("user/orderDetails");

		Checkout cart = checkoutDao.findCheckoutById(checkoutId);
		response.addObject("checkout", cart);

		List<LineItem> lineItemList = lineItemDao.findLineItemsByCheckout(cart);
		response.addObject("lineItemList", lineItemList);

		return response;
	}

	/**
	 * Marks the incoming lineItem as returned with current date. Redirects to
	 * effectively refresh the page.
	 * 
	 * @param lineItemId lineItem to mark as returned
	 * @return MAV redirects to user/orderDetails/{checkoutId}
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/orderReturn", method = RequestMethod.POST)
	public ModelAndView lineItemReturn(Integer lineItemId) throws Exception {
		ModelAndView response = new ModelAndView();

		LineItem current = lineItemDao.findLineItemById(lineItemId);
		current.setReturned(LocalDate.now());
		current = lineItemDao.save(current);

		response.setViewName("redirect:/user/orderDetails/" + current.getCheckout().getId());
		return response;
	}

}
