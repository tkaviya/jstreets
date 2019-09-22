package net.streets.web.validation.name;

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
 * Custom JSF Validator for Name input
 */
@FacesValidator("custom.nameValidator")
public class NameValidator implements Validator, ClientValidator {

    private static final String NAME_VALIDATION_ERROR = format(
            "Invalid name: (Valid characters=%s, length=%s-%s)",
            NAME_CHARS.replaceAll("\\[", "").replaceAll("]", ""), MIN_NAME_LEN, MAX_NAME_LEN);

    public NameValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("firstName").getAnnotations()[0],
                    "message", NAME_VALIDATION_ERROR.replace("name", "first name"));
            changeAnnotationValue(Registration.class.getDeclaredField("lastName").getAnnotations()[0],
                    "message", NAME_VALIDATION_ERROR.replace("name", "last name"));
            changeAnnotationValue(Registration.class.getDeclaredField("addressCity").getAnnotations()[0],
                    "message", NAME_VALIDATION_ERROR.replace("name", "city"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (!isValidName(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    NAME_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.nameValidator";
    }

}