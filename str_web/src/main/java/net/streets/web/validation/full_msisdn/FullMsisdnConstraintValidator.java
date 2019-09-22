package net.streets.web.validation.full_msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.FullMsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.utilities.StrValidator.isValidFullMsisdn;

/**
 * ConstraintValidator for @FullMsisdn
 */
public class FullMsisdnConstraintValidator implements ConstraintValidator<FullMsisdnConstraint, String> {

    public void initialize(FullMsisdnConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidFullMsisdn(value);
    }

}