package org.paulschmitz.librarysystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.paulschmitz.librarysystem.database.dao.UserDAO;
import org.paulschmitz.librarysystem.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is the implementation of the email uniqueness validation. It
 * requires the user input email to be neither an empty string nor matching any
 * existing email address.
 * 
 * @author p_schmitz
 *
 */
public class EmailUniqueImpl implements ConstraintValidator<EmailUnique, String> {

	@Autowired
	private UserDAO userDao;

	@Override
	public void initialize(EmailUnique constraintAnnotation) {

	}

	/**
	 * Checks if the email already exists in the database or if it is an empty
	 * string. In either case, it returns false (invalid). Otherwise, it is true
	 * (valid).
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}

		User user = userDao.findUserByEmail(value);

		return (user == null);
	}

}