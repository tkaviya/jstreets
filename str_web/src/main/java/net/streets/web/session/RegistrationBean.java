package net.streets.web.session;

import net.streets.authentication.WebAuthenticationProvider;
import net.streets.common.structure.Pair;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.web.request.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.streets.common.utilities.CommonUtilities.formatFullMsisdn;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.enumeration.StrConfig.*;
import static net.streets.persistence.enumeration.StrEventType.USER_REGISTRATION;
import static net.streets.persistence.enumeration.StrResponseCode.*;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;
import static net.streets.persistence.helper.StrEnumHelper.*;
import static net.streets.web.common.SystemPages.PAGE_LOGIN;
import static net.streets.web.common.SystemPages.PAGE_REGISTRATION;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class RegistrationBean implements Serializable {

    private final Registration registrationData;
    private final SessionBean sessionBean;

    private static final Logger logger = Logger.getLogger(RegistrationBean.class.getSimpleName());

    @Autowired
    public RegistrationBean(Registration registrationData, SessionBean sessionBean) {
        this.registrationData = registrationData;
        this.sessionBean = sessionBean;
    }

    public String goToLogin() {
        logger.info("Going to login page");
        return sessionBean.setCurrentPage(PAGE_LOGIN).getBaseXHTML();
    }

    public String register() {
        logger.info(format("Performing registration for %s %s", registrationData.getFirstName(), registrationData.getLastName()));

        var requestResponseLog = new str_request_response_log(
                fromEnum(WEB), fromEnum(USER_REGISTRATION), registrationData.toPrintableString()).save();

        if (!registrationData.getPassword().equals(registrationData.getConfirmPassword())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Registration Failed: Password and password confirmation must match",
                "Registration Failed: Password and password confirmation must match"));
            logger.warning("Registration failed: Password and password confirmation must match");
            requestResponseLog.setOutgoing_response("Registration Failed: Password and password confirmation must match");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_PASSWORD));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        var existingUser = getEntityManagerRepo().findWhere(str_user.class,
                new Pair<>("username", registrationData.getUsername())
        );
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Registration Failed: Username " + registrationData.getUsername() + " is already registered",
                "Registration Failed: Username " + registrationData.getUsername() + " is already registered"));
            logger.warning("Registration failed: Username " + registrationData.getUsername() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Username " + registrationData.getUsername() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_USERNAME_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        existingUser = getEntityManagerRepo().findWhere(str_user.class, new Pair<>("email", registrationData.getEmail()));
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Registration Failed: Email address " + registrationData.getEmail() + " is already registered",
                "Registration Failed: Email address " + registrationData.getEmail() + " is already registered"));
            logger.warning("Registration failed: Email address " + registrationData.getEmail() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Email address " + registrationData.getEmail() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_EMAIL_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        registrationData.setMsisdn(formatFullMsisdn(registrationData.getMsisdn(), getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));

        existingUser = getEntityManagerRepo().findWhere(str_user.class, new Pair<>("msisdn", registrationData.getMsisdn()));
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered",
                "Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered"));
            logger.warning("Registration failed: Phone number " + registrationData.getMsisdn() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_MSISDN_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        str_user newUser = new str_user(registrationData.getFirstName(), registrationData.getLastName(),
                null, registrationData.getUsername(),
                null, 0, null, registrationData.getEmail(), registrationData.getMsisdn(),
                fromEnum(ACC_INACTIVE), countryFromString(getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY)),
                languageFromString(getStrConfigDao().getConfig(CONFIG_DEFAULT_LANGUAGE)));

        var webAuthenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, newUser.getUsername(), newUser.getPin(), null);

        var registrationResponse = webAuthenticationProvider.registerWebUser(newUser,
            findByName(str_auth_group.class, getStrConfigDao().getConfig(CONFIG_INITIAL_AUTH_GROUP)), null);

        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                    "Registration Successful. Confirmation email sent to " + newUser.getEmail(), "Registration Successful"));
            requestResponseLog.setOutgoing_response("Registration Successful. Confirmation email sent to " + newUser.getEmail());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(SUCCESS));
            requestResponseLog.save();
            return PAGE_LOGIN.getBaseXHTML();
        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: " + registrationResponse.getMessage(),
                    "Registration Failed: " + registrationResponse.getMessage()));
            requestResponseLog.setOutgoing_response("Registration Failed: " + registrationResponse.getMessage());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(registrationResponse.getResponseCode()));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }
    }
}
