package net.streets.web.validation.msisdn;

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

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static net.streets.utilities.StrValidator.isValidTenDigitMsisdn;
import static net.streets.web.validation.ConstraintHelper.changeAnnotationValue;

/**
 * Custom JSF Validator for @Msisdn input
 */
@FacesValidator("custom.msisdnValidator")
public class MsisdnValidator implements Validator, ClientValidator {

    private static final String MSISDN_VALIDATION_ERROR =
            "Invalid phone number: Must be in the format 0123456789";

    public MsisdnValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("msisdn").getAnnotations()[0],
                    "message", MSISDN_VALIDATION_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().equals("")) {
            return;
        }
        if (!isValidTenDigitMsisdn(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    MSISDN_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.msisdnValidator";
    }

}