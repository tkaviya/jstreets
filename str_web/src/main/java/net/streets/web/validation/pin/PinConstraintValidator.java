package net.streets.web.validation.pin;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.PinConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.utilities.StrValidator.isValidPin;

/**
 * ConstraintValidator for @Pin
 */
public class PinConstraintValidator implements ConstraintValidator<PinConstraint, String> {

    public void initialize(PinConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidPin(value);
    }

}