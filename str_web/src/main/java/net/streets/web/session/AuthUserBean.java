package net.streets.web.session;

import net.streets.authentication.MobileAuthenticationProvider;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.web.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.streets.common.utilities.ReferenceGenerator.GenPin;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.enumeration.StrEventType.USER_PIN_RESET;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
import static net.streets.web.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 04 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class AuthUserBean extends JSFUpdatable {

    private static final Logger logger = Logger.getLogger(AuthUserBean.class.getSimpleName());
    private static final String TABLE_NAME = "User Authentication";
    private List<str_auth_user> authUsers;
    private str_auth_user selectedAuthUser;

    @Autowired
    public AuthUserBean(SessionBean sessionBean) {
        super(sessionBean);
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_USERNAME);
        updatableColumns.add(HEADER_TEXT_FIRST_NAME);
        updatableColumns.add(HEADER_TEXT_LAST_NAME);
        updatableColumns.add(HEADER_TEXT_AUTH_GROUP);
        notUpdatableColumns.add(HEADER_TEXT_CHANNEL);
        updatableColumns.add(HEADER_TEXT_USER_STATUS);
        updatableColumns.add(HEADER_TEXT_PIN_TRIES);
        notUpdatableColumns.add(HEADER_TEXT_REG_DATE);
        notUpdatableColumns.add(HEADER_TEXT_LAST_AUTH_DATE);
        initializeUsers();
    }

    public void initializeUsers() {
        authUsers = getEntityManagerRepo().findAll(str_auth_user.class, true);
    }

    public List<str_auth_user> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<str_auth_user> authUsers) {
        this.authUsers = authUsers;
    }

    public str_auth_user getSelectedAuthUser() {
        return selectedAuthUser;
    }

    public void setSelectedAuthUser(str_auth_user selectedAuthUser) {
        this.selectedAuthUser = selectedAuthUser;
    }

    public void resetPin() {
        logger.info(format("Resetting pin for %s", selectedAuthUser.getUser().getUsername()));

        var pinChangeLog = new str_request_response_log(fromEnum(WEB), fromEnum(USER_PIN_RESET),
            format("Reset pin for %s", selectedAuthUser.getUser().getUsername())).save();

        var authenticationProvider = new MobileAuthenticationProvider(pinChangeLog,
            selectedAuthUser.getDevice_id(), selectedAuthUser.getUser().getUsername(), null);

        var pinResponse = authenticationProvider.
            changePin(selectedAuthUser, GenPin(), false, null);

        if (pinResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Pin Reset Successful",
                "Pin Reset Successful. New pin emailed to " + selectedAuthUser.getUser().getEmail()));
            pinChangeLog.setOutgoing_response("Pin Reset Successful. New pin emailed to " + selectedAuthUser.getUser().getEmail());
        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to Reset Pin",
                "Failed to Reset Pin! " + pinResponse.getMessage()));
            pinChangeLog.setOutgoing_response("Failed to Reset Pin! " + pinResponse.getMessage());
        }
        pinChangeLog.setResponse_code(fromEnum(pinResponse.getResponseCode()));
        pinChangeLog.setSystem_user(selectedAuthUser.getUser());
        pinChangeLog.setAuth_user(selectedAuthUser);
        pinChangeLog.setOutgoing_response_time(new Date());
        pinChangeLog.save();
        logger.info("Pin change result: " + pinResponse.getMessage());
    }

    @Override
    public String getTableDescription() {
        return TABLE_NAME;
    }
}

