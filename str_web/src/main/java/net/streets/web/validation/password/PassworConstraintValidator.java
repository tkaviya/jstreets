package net.streets.web.validation.password;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.utilities.StrValidator.isValidPassword;

/**
 * ConstraintValidator for @Name
 */
public class PassworConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {

    public void initialize(PasswordConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidPassword(value);
    }

}