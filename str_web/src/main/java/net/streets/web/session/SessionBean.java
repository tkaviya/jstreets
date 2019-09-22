package net.streets.web.session;

import net.streets.authentication.WebAuthenticationProvider;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.entity.enumeration.str_role;
import net.streets.web.common.SystemPage;
import net.streets.web.request.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.*;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.enumeration.StrAuthGroup.SUPER_USER;
import static net.streets.persistence.enumeration.StrAuthGroup.SYS_ADMIN;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.enumeration.StrEventType.USER_LOGIN;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
import static net.streets.web.common.SystemPages.*;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@Component
@Scope("session")
public class SessionBean implements Serializable {

    private final Credentials credentials;
    private static final Logger logger = Logger.getLogger(SessionBean.class.getSimpleName());
    private str_auth_user strAuthUser;
    private SystemPage currentPage = PAGE_LOGIN;
    private static final HashMap<String, SystemPage> pageHandlers = new HashMap<>();
    private static final HashMap<String, Boolean> userPermissions = new HashMap<>();

    private WebAuthenticationProvider authProvider;

    @Autowired
    public SessionBean(Credentials credentials) {
        this.credentials = credentials;
        registerPageHandler(PAGE_LOGIN);
        registerPageHandler(PAGE_REGISTRATION);
        registerPageHandler(PAGE_RESET_PASSWORD);
        registerPageHandler(PAGE_SUMMARY);
        registerPageHandler(PAGE_USER_UPDATE);
        registerPageHandler(PAGE_AU_UPDATE);
        registerPageHandler(PAGE_AUTH_REPORT);
        registerPageHandler(PAGE_SYS_REPORT);
        registerPageHandler(PAGE_S_AUTH_REPORT);
        registerPageHandler(PAGE_U_SETTINGS);
    }

    static void registerPageHandler(SystemPage systemPage) {
        logger.info("Registering new page " + systemPage.getIdString());
        pageHandlers.put(systemPage.getIdString(), systemPage);
    }

    SystemPage setCurrentPage(SystemPage systemPage) {
        currentPage = systemPage;
        return currentPage;
    }

    public SystemPage getCurrentPage() {
        return currentPage;
    }

    private SystemPage getDefaultStartPage(str_auth_group group) {
        if (group.equals(fromEnum(SUPER_USER)) || group.equals(fromEnum(SYS_ADMIN))) {
            return PAGE_SUMMARY;
        } else {
            return PAGE_S_AUTH_REPORT;
        }
    }

    public String login() {

        logger.info(format("Authenticating user %s to channel WEB", credentials.getUsername()));
        var authLog = new str_request_response_log(
                fromEnum(WEB), fromEnum(USER_LOGIN), format("Authenticating user %s to channel WEB",
                credentials.getUsername())).save();

        ExternalContext externalContext = getCurrentInstance().getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");


        authProvider = new WebAuthenticationProvider(
                authLog, credentials.getUsername(), credentials.getPin(), userAgent
        );

        var authResponse = authProvider.authenticateUser();

        authLog.setAuth_user(authResponse.getResponseObject());
        authLog.setSystem_user(authResponse.getResponseObject() == null ? null : authResponse.getResponseObject().getUser());
        authLog.setResponse_code(findByName(str_response_code.class, authResponse.getResponseCode().name()));
        authLog.setOutgoing_response(authResponse.getMessage());
        authLog.setOutgoing_response_time(new Date());
        authLog.save();

        if (authResponse.getResponseCode().equals(SUCCESS)) {
            strAuthUser = authResponse.getResponseObject();
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_INFO, "Authentication successful",
                            format("Successfully logged in %s %s", strAuthUser.getAuth_group().getName(), credentials.getUsername())));
            return setCurrentPage(getDefaultStartPage(strAuthUser.getAuth_group())).getBaseXHTML();
        } else {
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, format("Authentication failed: %s", authResponse.getMessage()), authResponse.getMessage()));
            return setCurrentPage(PAGE_LOGIN).getBaseXHTML();
        }
    }

    public String logout() {
        logger.info("Logout invoked by user " + (strAuthUser == null ? null : strAuthUser.getUser().getUsername()));
        if (strAuthUser != null && authProvider != null) {
            authProvider.endSession();
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_INFO, format("Logged out %s", strAuthUser.getUser().getUsername()), ""));
            strAuthUser = null;
        }
        return setCurrentPage(PAGE_LOGIN).getBaseXHTML();
    }

    public String goToRegistration() {
        logger.info("Going to registration page");
        return setCurrentPage(PAGE_REGISTRATION).getBaseXHTML();
    }

    public String goToResetPassword() {
        logger.info("Going to reset password page");
        return setCurrentPage(PAGE_RESET_PASSWORD).getBaseXHTML();
    }

    private boolean isLoggedIn() {
        return strAuthUser != null;
    }

    public WebAuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public str_auth_user getStrAuthUser() {
        return strAuthUser;
    }

    public void checkValidSession() throws IOException {
        if (!isLoggedIn()) {
            logger.warning("*** User login required to access the page. *** ");
            ExternalContext context = getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN,
                    "User login required to access admin page", ""));

        }
    }

    public void changeDisplay(String display) {
        if (pageHandlers.get(display) == null) {
            logger.severe("Display " + display + " is not mapped in pageHandlers");
            return;
        }
        setCurrentPage(pageHandlers.get(display));
        logger.info("Displayed page " + currentPage.getIdString());
    }

    public boolean isCurrentPage(String display) {
        return currentPage.getIdString().equals(display);
    }

    public boolean hasRole(String permission) {
        logger.info("Checking for permission: " + permission);
        if (!isLoggedIn()) { return false; }
        //cache user permissions for this session
        if (!userPermissions.containsKey(permission)) {
            userPermissions.put(permission, authProvider.hasRole(findByName(str_role.class, permission)));
        }
        return userPermissions.get(permission);
    }
}
