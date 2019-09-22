package net.streets.web.annotations;

import net.streets.web.validation.pin.PinClientValidationConstraint;
import net.streets.web.validation.pin.PinConstraintValidator;
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
@Constraint(validatedBy = PinConstraintValidator.class)
@ClientConstraint(resolvedBy = PinClientValidationConstraint.class)
@Documented
public @interface PinConstraint {
    String message() default "Invalid pin specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}