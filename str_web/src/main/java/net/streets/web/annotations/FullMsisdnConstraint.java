package net.streets.web.annotations;

import net.streets.web.validation.full_msisdn.FullMsisdnClientValidationConstraint;
import net.streets.web.validation.full_msisdn.FullMsisdnConstraintValidator;
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
@Constraint(validatedBy = FullMsisdnConstraintValidator.class)
@ClientConstraint(resolvedBy = FullMsisdnClientValidationConstraint.class)
@Documented
public @interface FullMsisdnConstraint {
    String message() default "Invalid phone number specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}