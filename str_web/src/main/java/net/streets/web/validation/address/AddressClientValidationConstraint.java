package net.streets.web.validation.address;

import net.streets.web.annotations.AddressConstraint;
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
 * ClientValidationConstraint for @AddressConstraint annotation
 */
public class AddressClientValidationConstraint implements ClientValidationConstraint {

    public static final String MESSAGE_METADATA = "data-p-address-msg";

    @Override
    public Map<String, Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
        return ConstraintHelper.getMetadata(MESSAGE_METADATA, constraintDescriptor);
    }

    @Override
    public String getValidatorId() {
        return AddressConstraint.class.getSimpleName();
    }
}
