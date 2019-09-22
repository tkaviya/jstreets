package net.streets.web.validation.password;

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
import static net.streets.utilities.StrValidator.*;
import static net.streets.web.validation.ConstraintHelper.changeAnnotationValue;

/**
 * Custom JSF Validator for @Password input
 */
@FacesValidator("custom.passwordValidator")
public class PasswordValidator implements Validator, ClientValidator {

    private static final String PASSWORD_VALIDATION_ERROR = format(
            "Invalid password: (Valid characters=%s, length=%s-%s)",
            PASSWORD_CHARS.replaceAll("\\[", "").replaceAll("]", ""), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);

    public PasswordValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("password").getAnnotations()[0],
                    "message", PASSWORD_VALIDATION_ERROR);
            changeAnnotationValue(Registration.class.getDeclaredField("confirmPassword").getAnnotations()[0],
                    "message", PASSWORD_VALIDATION_ERROR);
            changeAnnotationValue(Credentials.class.getDeclaredField("password").getAnnotations()[0],
                    "message", PASSWORD_VALIDATION_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (!isValidPassword(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    PASSWORD_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.passwordValidator";
    }

}