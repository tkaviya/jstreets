package net.streets.web.annotations;

import net.streets.web.validation.plaintext.PlainTextClientValidationConstraint;
import net.streets.web.validation.plaintext.PlainTextConstraintValidator;
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
@Constraint(validatedBy = PlainTextConstraintValidator.class)
@ClientConstraint(resolvedBy = PlainTextClientValidationConstraint.class)
@Documented
public @interface PlainTextConstraint {
    String message() default "Invalid input specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}