package org.paulschmitz.librarysystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.AccessDeniedException;

import lombok.extern.slf4j.Slf4j;

/**
 * This class handles all requests that cause errors. It may also log errors.
 * The requests will be redirected to the 404 or 500 page.
 * 
 * @author p_schmitz
 *
 */
@Slf4j
@Controller
@ControllerAdvice
public class ErrorController {

	/**
	 * This function picks up any "access denied" errors and displays the 404 page.
	 * 
	 * @param request the request
	 * @return 404 page
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@RequestMapping(value = "/error/404")
	public String error404(HttpServletRequest request) {

		String origialUri = (String) request.getAttribute("javax.servlet.forward.request_uri");

		// log the request
		log.error("Requested URL not found : " + request.getMethod() + " " + origialUri);

		return "error/404";
	}

	/**
	 * This function picks up any 500 errors. It displays the 500 page and the
	 * exception information.
	 * 
	 * @param request the request
	 * @param ex      the incoming exception
	 * @return MAV showing stack trace, error, and request on the 500 page
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllExceptions(HttpServletRequest request, Exception ex) {

		// log the request
		log.error("Error page exception : " + getRequestURL(request), ex);

		// get error
		String stackTrace = getHTMLStackTrace(ExceptionUtils.getStackFrames(ex));

		// setup model and view
		ModelAndView model = new ModelAndView();
		model.setViewName("error/500");
		model.addObject("requestUrl", getRequestURL(request));
		model.addObject("message", ex.getMessage());
		model.addObject("stackTrace", stackTrace);

		// retrieve root causes and stack frames if available
		if (ex.getCause() != null) {
			Throwable root = ExceptionUtils.getRootCause(ex);
			model.addObject("rootcause", root);

			String roottrace = getHTMLStackTrace(ExceptionUtils.getStackFrames(ex));
			model.addObject("roottrace", roottrace);
		}

		return model;
	}

	/**
	 * Helper method retrieves and builds the stack trace
	 * 
	 * @param stack the stack string array
	 * @return formatted string of stack trace
	 */
	private String getHTMLStackTrace(String[] stack) {
		StringBuffer result = new StringBuffer();

		for (String frame : stack) {
			if (frame.contains("librarysystem")) {
				result.append(" &nbsp; &nbsp; &nbsp;" + frame.trim().substring(3) + "<br>\n");
			} else if (frame.contains("Caused by:")) {
				result.append("Caused By: " + frame + "<br>");
			}
		}

		return result.toString();
	}

	/**
	 * Helper method retrieves the requested URL including query strings
	 * 
	 * @param request the servlet request
	 * @return formatted string of request URL
	 */
	private String getRequestURL(HttpServletRequest request) {
		String result = request.getRequestURL().toString();

		if (request.getQueryString() != null) {
			result = result + "?" + request.getQueryString();
		}

		return result;
	}

}