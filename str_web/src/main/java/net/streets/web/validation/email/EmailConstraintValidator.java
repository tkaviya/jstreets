package net.streets.web.validation.email;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.EmailConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.utilities.StrValidator.isValidEmail;

/**
 * ConstraintValidator for @Email
 */
public class EmailConstraintValidator implements ConstraintValidator<EmailConstraint, String> {

    public void initialize(EmailConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidEmail(value);
    }

}