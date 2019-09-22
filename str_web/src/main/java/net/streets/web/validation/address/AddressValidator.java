package net.streets.web.validation.address;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.request.Registration;
import org.primefaces.validate.ClientValidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Map;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static net.streets.utilities.StrValidator.*;
import static net.streets.web.validation.ConstraintHelper.changeAnnotationValue;

/**
 * Custom JSF Validator for Address input
 */
@FacesValidator("custom.addressValidator")
public class AddressValidator implements Validator, ClientValidator {

    public static final String ADDRESS_VALIDATION_ERROR = format(
            "Invalid input: (Valid characters=%s, length=%s-%s)",
            ADDRESS_CHARS.replaceAll("\\[", "").replaceAll("]", ""), MIN_ADDRESS_LEN, MAX_ADDRESS_LEN);

    public AddressValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("addressLine1").getAnnotations()[0],
                    "message", ADDRESS_VALIDATION_ERROR.replace("input", "address"));
            changeAnnotationValue(Registration.class.getDeclaredField("addressLine2").getAnnotations()[0],
                    "message", ADDRESS_VALIDATION_ERROR.replace("input", "address"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().equals("")) {
            return;
        }

        if (!isValidAddress(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    ADDRESS_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.addressValidator";
    }

}