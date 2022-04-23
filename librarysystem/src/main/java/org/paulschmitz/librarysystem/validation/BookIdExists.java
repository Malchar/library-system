package org.paulschmitz.librarysystem.validation;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BookIdExistsImpl.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BookIdExists {

	String message() default "{}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
