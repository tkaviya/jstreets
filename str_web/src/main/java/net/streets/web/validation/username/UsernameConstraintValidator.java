package net.streets.web.validation.username;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.utilities.StrValidator.isValidUsername;

/**
 * ConstraintValidator for @Email
 */
public class UsernameConstraintValidator implements ConstraintValidator<UsernameConstraint, String> {

    public void initialize(UsernameConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidUsername(value);
    }

}