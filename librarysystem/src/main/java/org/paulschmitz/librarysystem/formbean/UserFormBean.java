package org.paulschmitz.librarysystem.formbean;

import java.util.Objects;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.paulschmitz.librarysystem.validation.EmailUnique;
import lombok.Data;

/**
 * This form bean corresponds to the user registration page. It passes all
 * fields back and forth. The isRegistered field is hidden to the client and is
 * used to track whether or not the user is already registered.
 * 
 * @author p_schmitz
 *
 */
@Data
public class UserFormBean {

	// this validation blocks duplicate emails and also blank emails
	@EmailUnique(message = "This email cannot be used")
	private String email;

	@Length(min = 6, max = 16, message = "Password must be between 6 and 16 characters")
	private String password;

	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;

	@NotBlank(message = "Name is required")
	private String name;

	// https://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303
	@AssertTrue(message = "Confirm password does not match password")
	private boolean isValidPass() {
		return Objects.equals(password, confirmPassword);
	}

}
