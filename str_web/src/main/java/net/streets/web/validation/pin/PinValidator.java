package net.streets.web.validation.pin;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.streets.web.request.Credentials;
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
import static net.streets.utilities.StrValidator.PIN_LEN;
import static net.streets.utilities.StrValidator.isValidPin;
import static net.streets.web.validation.ConstraintHelper.changeAnnotationValue;

/**
 * Custom JSF Validator for @Pin input
 */
@FacesValidator("custom.pinValidator")
public class PinValidator implements Validator, ClientValidator {

    private static final String PIN_VALIDATION_ERROR = format("Invalid pin! Pin must be a %s digit number", PIN_LEN);

    public PinValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("pin").getAnnotations()[0],
                    "message", PIN_VALIDATION_ERROR);
            changeAnnotationValue(Registration.class.getDeclaredField("confirmPin").getAnnotations()[0],
                    "message", PIN_VALIDATION_ERROR);
            changeAnnotationValue(Credentials.class.getDeclaredField("pin").getAnnotations()[0],
                    "message", PIN_VALIDATION_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (!isValidPin(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null, PIN_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.pinValidator";
    }

}