package net.streets.web.validation.full_msisdn;

import net.streets.web.annotations.FullMsisdnConstraint;
import net.streets.web.validation.ConstraintHelper;
import org.primefaces.validate.bean.ClientValidationConstraint;

import javax.validation.metadata.ConstraintDescriptor;
import java.util.Map;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

/**
 * ClientValidationConstraint for @FullMsisdnConstraint annotation
 */
public class FullMsisdnClientValidationConstraint implements ClientValidationConstraint {

    public static final String MESSAGE_METADATA = "data-p-fullmsisdn-msg";

    @Override
    public Map<String, Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
        return ConstraintHelper.getMetadata(MESSAGE_METADATA, constraintDescriptor);
    }

    @Override
    public String getValidatorId() {
        return FullMsisdnConstraint.class.getSimpleName();
    }
}
