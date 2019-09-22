package net.streets.web.validation.email;

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
import static net.streets.utilities.StrValidator.isValidEmail;
import static net.streets.web.validation.ConstraintHelper.changeAnnotationValue;

/**
 * Custom JSF Validator for @Email input
 */
@FacesValidator("custom.emailValidator")
public class EmailValidator implements Validator, ClientValidator {

    private static final String EMAIL_VALIDATION_ERROR = "Invalid email: Must be in the form xxx@yyy.com";

    public EmailValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("email").getAnnotations()[0],
                    "message", EMAIL_VALIDATION_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (!isValidEmail(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    EMAIL_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.emailValidator";
    }

}