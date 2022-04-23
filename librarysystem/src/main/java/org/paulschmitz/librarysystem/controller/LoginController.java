package org.paulschmitz.librarysystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Manages the login functions for the library system.
 * 
 * @author p_schmitz
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value="/login/login", method=RequestMethod.GET)
	public ModelAndView login() throws Exception {
		ModelAndView response = new ModelAndView();
		response.setViewName("login/loginForm");
		return response;
	}
}

