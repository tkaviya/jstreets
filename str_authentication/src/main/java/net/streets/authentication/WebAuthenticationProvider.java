package net.streets.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 */

import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.complex_type.str_user_attributes;
import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.enumeration.StrResponseObject;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static net.streets.authentication.StreetsAuthenticator.registerUser;
import static net.streets.common.utilities.CommonUtilities.throwableAsString;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.enumeration.StrConfig.*;
import static net.streets.persistence.enumeration.StrResponseCode.SUCCESS;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
import static net.streets.utilities.NetworkUtilities.sendEmail;
import static net.streets.utilities.NetworkUtilities.sendEmailAlert;
import static net.streets.utilities.mail.EMailer.CONTENT_TYPE_HTML;

public class WebAuthenticationProvider extends StrChainAuthenticationProvider {

    private static final str_channel CHANNEL = fromEnum(WEB);

    @Override
    protected void initializeAuthenticationChain() {
        authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::startSession)));
    }

    public WebAuthenticationProvider(str_request_response_log requestResponseLog, String username, String password, String deviceId) {
        super(requestResponseLog, CHANNEL);
        setAuthUsername(username);
        setAuthPassword(password);
        setDeviceId(deviceId);
    }

    public WebAuthenticationProvider(str_request_response_log requestResponseLog, str_auth_user authUser) {
        super(requestResponseLog, CHANNEL);
        setUser(authUser.getUser());
        setAuthUser(authUser);
    }

    public StrResponseObject<str_auth_user> registerWebUser(str_user newUser, str_auth_group authGroup, str_user_attributes str_user_attributes) {

        logger.info(format("Performing WEB registration for %s %s", newUser.getFirst_name(), newUser.getLast_name()));

        var registrationResponse = registerUser(newUser, fromEnum(WEB),
            findByName(str_auth_group.class, authGroup == null ? getStrConfigDao().getConfig(CONFIG_INITIAL_AUTH_GROUP) : authGroup.getName()),
            null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        strAuthUser = registrationResponse.getResponseObject();
        strUser = registrationResponse.getResponseObject().getUser();

        if (str_user_attributes == null) {
            logger.info(format("Creating user_attributes for user %s", newUser.getUsername()));

            str_user_attributes = new str_user_attributes(newUser,
                new BigDecimal(getStrConfigDao().getConfig(CONFIG_INITIAL_POCKET_BALANCE)),
                new BigDecimal(getStrConfigDao().getConfig(CONFIG_INITIAL_ACCOUNT_BALANCE)),
                parseInt(getStrConfigDao().getConfig(CONFIG_INITIAL_CURRENT_ENERGY)),
                parseInt(getStrConfigDao().getConfig(CONFIG_INITIAL_MAX_ENERGY))
            ).save();

        } else {
            logger.info(format("Setting user_attributes to %s for user %s", str_user_attributes.getId(), newUser.getUsername()));
        }

        newUser.setUser_attributes(str_user_attributes).save();

        logger.info("Sending WEB registration email");

        sendAppRegistrationEmail(newUser, registrationResponse);
        return registrationResponse;
    }

    public StrResponseObject<str_auth_user> changePin(str_auth_user authUser,
                                                      String newPin, boolean validatePrevious, String oldPin) {

        var passResponse = StreetsAuthenticator.changePin(authUser, newPin, validatePrevious, oldPin);
        if (passResponse.getResponseCode().equals(SUCCESS)) {
            logger.info("Sending password changed email");
            sendPinChangeEmail(authUser, newPin);
        }
        return passResponse;
    }

    static void sendAppRegistrationEmail(str_user newUser, StrResponseObject<str_auth_user> registrationResponse) {
        FileReader fr = null;
        BufferedReader br = null;
        try {

            ClassPathResource resource = new ClassPathResource("authentication/app_reg_success.html");
            fr = new FileReader(resource.getFile().getAbsolutePath());
            br = new BufferedReader(fr);
            String line;
            StringBuilder registrationEmail = new StringBuilder();

            while ((line = br.readLine()) != null) {
                registrationEmail.append(line.replaceAll("%reg_fname%", newUser.getFirst_name())
                        .replaceAll("%reg_lname%", newUser.getLast_name())
                        .replaceAll("%reg_username%", newUser.getUsername())
                        .replaceAll("%reg_userId%", String.valueOf(registrationResponse.getResponseObject().getId()))
                        .replaceAll("%reg_authToken%", registrationResponse.getResponseObject().getAuth_token())
                        .replaceAll("%contact_address%", getStrConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                        .replaceAll("%support_phone%", getStrConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                        .replaceAll("%support_email%", getStrConfigDao().getConfig(CONFIG_SUPPORT_EMAIL))).append("\r\n");
            }
            sendEmail(getStrConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[]{newUser.getEmail(),
                      getStrConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)}, "Welcome to " +
                      getStrConfigDao().getConfig(CONFIG_SYSTEM_NAME) + " Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
        } catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert("Failed to send registration email! \r\n", throwableAsString(e));
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    static void sendPinChangeEmail(str_auth_user authUser, String newPin) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            ClassPathResource resource = new ClassPathResource("authentication/pin_change_success.html");
            fr = new FileReader(resource.getFile().getAbsolutePath());
            br = new BufferedReader(fr);
            StringBuilder registrationEmail = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                registrationEmail.append(line.replaceAll("%auth_fname%", authUser.getUser().getFirst_name())
                        .replaceAll("%auth_lname%", authUser.getUser().getLast_name())
                        .replaceAll("%auth_username%", authUser.getUser().getUsername())
                        .replaceAll("%auth_pin%", newPin)
                        .replaceAll("%contact_address%", getStrConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                        .replaceAll("%support_phone%", getStrConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                        .replaceAll("%support_email%", getStrConfigDao().getConfig(CONFIG_SUPPORT_EMAIL))).append("\r\n");
            }
            sendEmail(getStrConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[]{authUser.getUser().getEmail(),
                    getStrConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)}, "Pin Changed on " +
                    getStrConfigDao().getConfig(CONFIG_SYSTEM_NAME) + " Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
        } catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert("Failed to send pin changed email! \r\n", e.getMessage());
        } finally {
            try {
                if (br != null) { br.close(); }
                if (fr != null) { fr.close(); }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
