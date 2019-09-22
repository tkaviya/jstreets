package net.streets.web.validation.name;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.NameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.utilities.StrValidator.isValidName;

/**
 * ConstraintValidator for @Name
 */
public class NameConstraintValidator implements ConstraintValidator<NameConstraint, String> {

    public void initialize(NameConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidName(value);
    }
}