package net.streets.web.validation.plaintext;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.annotations.PlainTextConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.utilities.StrValidator.isValidPlainText;

/**
 * ConstraintValidator for @PlainText
 */
public class PlainTextConstraintValidator implements ConstraintValidator<PlainTextConstraint, String> {

    public void initialize(PlainTextConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidPlainText(value);
    }

}