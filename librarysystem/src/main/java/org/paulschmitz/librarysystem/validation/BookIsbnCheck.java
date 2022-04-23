package org.paulschmitz.librarysystem.validation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BookIsbnCheckImpl.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BookIsbnCheck {
	String message() default "{}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
