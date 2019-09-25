package net.streets.web.session;

import net.streets.authentication.WebAuthenticationProvider;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.enumeration.StrResponseObject;
import net.streets.web.common.JSFLoggable;
import net.streets.web.request.NewCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.enumeration.StrEventType.USER_PIN_UPDATE;
import static net.streets.persistence.enumeration.StrResponseCode.AUTH_AUTHENTICATION_FAILED;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class UserSettingsBean implements JSFLoggable, Serializable {

    private static final Logger logger = Logger.getLogger(UserSettingsBean.class.getSimpleName());
    private final SessionBean sessionBean;
    private final NewCredentials newCredentials;

    @Autowired
    public UserSettingsBean(SessionBean sessionBean, NewCredentials newCredentials) {
        this.sessionBean = sessionBean;
        this.newCredentials = newCredentials;
    }

    public NewCredentials getNewCredentials() {
        return newCredentials;
    }

    public void changePin() {
        Date requestTime = new Date();
        str_auth_user authUser = sessionBean.getStrAuthUser();
        str_user user = sessionBean.getStrAuthUser().getUser();
        logger.info(format("Changing pin for %s", user.getUsername()));

        if (!newCredentials.getNewPin().equals(newCredentials.getNewConfirmPin())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Change Pin Failed", "Pin and pin confirmation must match"));
            logger.warning("Change Pin Failed: Pin and pin confirmation must match");
            log(fromEnum(USER_PIN_UPDATE), sessionBean.getStrAuthUser(), fromEnum(AUTH_AUTHENTICATION_FAILED),
                    requestTime, new Date(), "UPDATE PASSWORD",
                    "Change Pin Failed: Pin and pin confirmation must match");
            return;
        }

        String request = format("Change pin: %s", user.getUsername());
        str_request_response_log requestResponseLog = new str_request_response_log(
                fromEnum(WEB), fromEnum(USER_PIN_UPDATE), request).save();

        requestResponseLog.setAuth_user(authUser);
        requestResponseLog.setSystem_user(authUser.getUser());
        requestResponseLog.save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(requestResponseLog, authUser);
        StrResponseObject<str_auth_user> pinResponse = authenticationProvider.changePin(
                authUser, newCredentials.getNewPin(), true, newCredentials.getOldPin());

        requestResponseLog.setOutgoing_response(pinResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(fromEnum(pinResponse.getResponseCode()));
        requestResponseLog.save();

        if (!pinResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Change Pin Failed", pinResponse.getMessage()));
            logger.warning("Change Pin Failed: " + pinResponse.getMessage());
            log(fromEnum(USER_PIN_UPDATE), sessionBean.getStrAuthUser(), fromEnum(pinResponse.getResponseCode()),
                    requestTime, new Date(), "UPDATE PASSWORD",
                    "Change Pin Failed: " + pinResponse.getMessage());
            return;
        }

        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Pin changed successfully", "Pin changed successfully"));
        logger.info("Pin changed successfully");
        log(fromEnum(USER_PIN_UPDATE), sessionBean.getStrAuthUser(), fromEnum(SUCCESS),
                requestTime, new Date(), "UPDATE PASSWORD",
                "Pin changed successfully");
    }
}

