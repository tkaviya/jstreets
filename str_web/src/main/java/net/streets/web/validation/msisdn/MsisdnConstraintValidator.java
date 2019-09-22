package net.streets.web.validation.msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.MsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.utilities.StrValidator.isValidTenDigitMsisdn;

/**
 * ConstraintValidator for @Msisdn
 */
public class MsisdnConstraintValidator implements ConstraintValidator<MsisdnConstraint, String> {

    public void initialize(MsisdnConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidTenDigitMsisdn(value);
    }

}