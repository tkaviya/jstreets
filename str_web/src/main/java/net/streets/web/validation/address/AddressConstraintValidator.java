package net.streets.web.validation.address;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.AddressConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.utilities.StrValidator.isValidAddress;

/**
 * ConstraintValidator for @Address
 */
public class AddressConstraintValidator implements ConstraintValidator<AddressConstraint, String> {

    public void initialize(AddressConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidAddress(value);
    }

}