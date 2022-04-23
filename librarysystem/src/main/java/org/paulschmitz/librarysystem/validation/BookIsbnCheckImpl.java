package org.paulschmitz.librarysystem.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class implements the ISBN checker for Books. It allows an empty string,
 * a valid ISBN 10, or a valid ISBN 13.
 * 
 * @author p_schmitz
 *
 */
public class BookIsbnCheckImpl implements ConstraintValidator<BookIsbnCheck, String> {

	@Override
	public void initialize(BookIsbnCheck constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// empty strings will be allowed
		if (value == "") {
			return true;
		}

		// checker for ISBN 10
		else if (value.length() == 10) {
			// convert input to array
			char[] chars = value.toCharArray();
			// initialize sum to use for checksum later
			int sum = 0;

			/*
			 * Builds the sum using a standard implementation. The value at each position is
			 * multiplied by the position ordinal and then added. The input value 'X' is
			 * converted to the value 10. Indexing starts at ordinal 1.
			 * https://en.wikipedia.org/wiki/International_Standard_Book_Number#ISBN-
			 * 10_check_digit_calculation
			 */
			for (int i = 0; i < chars.length; i++) {
				// character 'X' has value 10
				if (chars[i] == 'X') {
					sum += (1 + i) * 10;
				}
				// converts character ASCII value to decimal value
				else if (48 <= chars[i] && chars[i] <= 57) {
					sum += (1 + i) * (chars[i] - 48);
				}
				// case where an invalid character is found
				else {
					return false;
				}
			} // end for-loop

			// return checksum result
			return 0 == (sum % 11);

		} // end checker for ISBN 10

		// checker for ISBN 13
		else if (value.length() == 13) {
			// convert input to array
			char[] chars = value.toCharArray();
			// initialize sum to use for checksum later
			int sum = 0;

			/*
			 * Builds the sum using a standard implementation. The value at each position is
			 * multiplied by 1 or 3 alternating with index parity. Note that only integer
			 * characters are allowed (no 'X'). Indexing starts at ordinal 1.
			 * https://en.wikipedia.org/wiki/International_Standard_Book_Number#ISBN-
			 * 13_check_digit_calculation
			 */
			for (int i = 0; i < chars.length; i++) {
				// converts character ASCII value to decimal value
				if (48 <= chars[i] && chars[i] <= 57) {
					sum += (((i % 2) * 2) + 1) * (chars[i] - 48);
				}
				// case where an invalid character is found
				else {
					return false;
				}
			} // end for-loop

			// return checksum result
			return 0 == (sum % 10);

		} // end checker for ISBN 13

		// case where input has invalid length
		else {
			return false;
		}
	} // end isValid method
}
