package org.paulschmitz.librarysystem.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.paulschmitz.librarysystem.database.dao.UserDAO;
import org.paulschmitz.librarysystem.database.dao.UserRoleDAO;
import org.paulschmitz.librarysystem.database.entity.User;
import org.paulschmitz.librarysystem.database.entity.UserRole;
import org.paulschmitz.librarysystem.formbean.UserFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

/**
 * This class manages the user creation methods.
 * 
 * @author p_schmitz
 *
 */
@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserRoleDAO userRoleDao;

	// for password hashing
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * This function is used when the client wants to register a new user. It
	 * prepares and sends a blank form.
	 * 
	 * @return MAV with user form
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/register", method = RequestMethod.GET)
	public ModelAndView create() throws Exception {
		ModelAndView response = new ModelAndView();

		UserFormBean form = new UserFormBean();
		response.addObject("form", form);

		response.setViewName("login/register");
		return response;
	}

	/**
	 * This function receives the client's user form and its validation. If there
	 * are validation errors, then they will be sent back to the client, and the
	 * registration page will stay visible.
	 * 
	 * If there are no validation errors, then it will check if the user email
	 * already exists. If not, it makes a new user using the provided information.
	 * If the email already exists, then it returns an error and keeps the view on
	 * the registration page. TODO A successful creation will redirect the user to
	 * the login page, where they can now login.
	 * 
	 * @param form          incoming user form
	 * @param bindingResult validation of the form
	 * @return if errors, return errors and refill the form. if no errors, creates a
	 *         new user and user role using the form input and moves to the login
	 *         view.
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/registerSubmit", method = RequestMethod.POST)
	public ModelAndView registerSubmit(@Valid UserFormBean form, BindingResult bindingResult) throws Exception {
		ModelAndView response = new ModelAndView();

		// error checking, uses @Valid, form bean
		if (bindingResult.hasErrors()) {
			HashMap<String, String> errors = new HashMap<String, String>();

			for (ObjectError error : bindingResult.getAllErrors()) {
				errors.put(((FieldError) error).getField(), error.getDefaultMessage());
				log.info(((FieldError) error).getField() + " " + error.getDefaultMessage());
			}

			// add error list to the model
			response.addObject("formErrors", errors);

			// refill the form and stay at the register page
			response.addObject("form", form);
			response.setViewName("login/register");
			return response;
		}

		// case where the validation was successful
		// create new user
		User user = new User();

		// create a "USER" role for the new user
		UserRole userRole = new UserRole();
		userRole.setRoleName("USER");

		// add the role to the user and save the role
		Set<UserRole> userRoleList = new HashSet<UserRole>();
		userRoleList.add(userRole);
		user.setUserRole(userRoleList);
		userRoleDao.save(userRole);

		// setup user based on incoming form
		user.setEmail(form.getEmail());
		user.setName(form.getName());

		// send password through encoder
		String password = passwordEncoder.encode(form.getPassword());
		user.setPassword(password);

		userDao.save(user);

		// redirect to login page. user will need to login using their new credentials
		response.setViewName("redirect:/login/login");
		return response;
	}

}
