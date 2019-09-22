package net.streets.web.annotations;

import net.streets.web.validation.name.NameClientValidationConstraint;
import net.streets.web.validation.name.NameConstraintValidator;
import org.primefaces.validate.bean.ClientConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameConstraintValidator.class)
@ClientConstraint(resolvedBy = NameClientValidationConstraint.class)
@Documented
public @interface NameConstraint {
    String message() default "Invalid name specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}