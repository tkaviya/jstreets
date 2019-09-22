package net.streets.web.annotations;

import net.streets.web.validation.email.EmailClientValidationConstraint;
import net.streets.web.validation.email.EmailConstraintValidator;
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
@Constraint(validatedBy = EmailConstraintValidator.class)
@ClientConstraint(resolvedBy = EmailClientValidationConstraint.class)
@Documented
public @interface EmailConstraint {
    String message() default "Invalid email specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}