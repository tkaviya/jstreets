package net.streets.web.validation.plaintext;

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
 * Custom JSF Validator for Plain Text input
 */
@FacesValidator("custom.plainTextValidator")
public class PlainTextValidator implements Validator, ClientValidator {

    private static final String PLAIN_TEXT_VALIDATION_ERROR = format(
            "Invalid input: (Valid characters=%s, length=%s-%s)",
            PLAIN_TEXT_CHARS.replaceAll("\\[", "").replaceAll("]", ""), MIN_PLAIN_TEXT_LEN, MAX_PLAIN_TEXT_LEN);

    public PlainTextValidator() {
        try {
            changeAnnotationValue(Registration.class.getDeclaredField("companyName").getAnnotations()[0],
                    "message", PLAIN_TEXT_VALIDATION_ERROR.replace("input", "company name"));
            changeAnnotationValue(Registration.class.getDeclaredField("vatNumber").getAnnotations()[0],
                    "message", PLAIN_TEXT_VALIDATION_ERROR.replace("input", "VAT number"));
            changeAnnotationValue(Registration.class.getDeclaredField("registrationNumber").getAnnotations()[0],
                    "message", PLAIN_TEXT_VALIDATION_ERROR.replace("input", "company registration number"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().equals("")) {
            return;
        }

        if (!isValidPlainText(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null,
                    PLAIN_TEXT_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.plainTextValidator";
    }

}