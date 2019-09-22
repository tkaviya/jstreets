package net.streets.authentication;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.complex_type.log.str_session;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.enumeration.StrResponseCode;
import net.streets.persistence.enumeration.StrResponseObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.streets.authentication.AuthenticationHelper.validateMandatoryChannelData;
import static net.streets.common.security.Security.generateIV;
import static net.streets.common.security.Security.hashWithSalt;
import static net.streets.common.security.StreetsSecurityEncryption.DEFAULT_SECURITY_HASH;
import static net.streets.common.utilities.CommonUtilities.formatFullMsisdn;
import static net.streets.common.utilities.CommonUtilities.isNullOrEmpty;
import static net.streets.common.utilities.ReferenceGenerator.GenPin;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_MAX_PASSWORD_TRIES;
import static net.streets.persistence.enumeration.StrResponseCode.*;
import static net.streets.persistence.helper.DaoManager.*;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
import static net.streets.utilities.StrValidator.*;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/6/13
 * Time: 7:06 PM
 */
@Service
public class StreetsAuthenticator {

    private static Logger logger = Logger.getLogger(StreetsAuthenticator.class.getSimpleName());

    static StrResponseObject<str_session> startSession(str_channel channel,
                                                       String deviceId, String username, String password, boolean searchAllUsernameTypes) {

        if (channel == null) {
            return new StrResponseObject<str_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
        } else if (deviceId != null && !isValidAuthData(deviceId)) {
            return new StrResponseObject<str_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id: " + deviceId);
        } else if (!isValidUsername(username)) {
            return new StrResponseObject<>(INPUT_INVALID_USERNAME);
        } else if (!isValidPassword(password) && !isValidPin(password)) {
            return new StrResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        logger.info(format("Finding user %s on channel %s ", username, channel.getName()));
        StrResponseObject<str_auth_user> userResponse = getUserByUsername(username, channel, searchAllUsernameTypes);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new StrResponseObject<>(userResponse.getResponseCode());
        }

        logger.info(format("Authenticating user %s onto channel %s ", username, channel.getName()));
        userResponse = authenticateUser(channel, userResponse.getResponseObject(), password);

        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            logger.info(format("Authentication failed with response code %s ", userResponse.getResponseCode().name()));
            return new StrResponseObject<>(userResponse.getResponseCode());
        }

        logger.info("Authenticated " + username);
        str_auth_user authUser = userResponse.getResponseObject();
        authUser.setLast_login_date(authUser.getLast_auth_date() != null ? authUser.getLast_auth_date() : new Date());
        authUser.setLast_auth_date(new Date());

        logger.info(format("Creating new session for user %s ", username));
        str_session newSession = new str_session(authUser, deviceId, generateIV(), new Date(), null).save();

        logger.info("Returning SUCCESS response");
        return new StrResponseObject<>(SUCCESS, newSession);
    }

    public static StrResponseObject<str_session> verifyLogin(Long authUserId, String deviceId, String authToken) {

        logger.info(format("Verifying auth token %s for auth user %s with imei %s", authToken, authUserId, deviceId));

        if (authUserId == null) {
            logger.info("Login verification failed! authUserId cannot be null");
            return new StrResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        if (!isValidAuthData(authToken)) {
            logger.info(format("Login verification failed! Invalid auth token %s", authToken));
            return new StrResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        str_session authSession = getEntityManagerRepo().findLast(str_session.class, asList(
            new Pair<>("auth_user_id", authUserId),
            new Pair<>("device_id", deviceId),
            new Pair<>("auth_token", authToken)
        ));

        if (authSession == null || authSession.getEnd_time() != null) {
            logger.info(format("Login verification failed! Session does not exist for auth user %s with deviceId %s", authToken, deviceId));
            return new StrResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        authSession.getAuth_user().setLast_auth_date(new Date()).save();
        return new StrResponseObject<>(SUCCESS, authSession);
    }

    static StrResponseObject endSession(Long sessionId,
                                        str_channel channel, String deviceId, String authToken) {

        if (sessionId == null) {
            return new StrResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Session ID");
        } else if (channel == null) {
            return new StrResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
        } else if (!isValidAuthData(deviceId)) {
            return new StrResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id");
        } else if (!isValidAuthData(authToken)) {
            return new StrResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Auth Token");
        }

        logger.info(format("Got end session request for session %s on channel %s by device %s",
                sessionId, channel.getName(), deviceId));

        str_session currentSession = getEntityManagerRepo().findById(str_session.class, sessionId);

        if (currentSession == null) {
            return new StrResponseObject<>(DATA_NOT_FOUND);
        }

        if (!currentSession.getDevice_id().equals(deviceId) ||
                !currentSession.getAuth_token().equals(authToken) ||
                !currentSession.getAuth_user().getChannel().getId().equals(channel.getId())) {
            return new StrResponseObject<>(AUTH_INSUFFICIENT_PRIVILEGES);
        }

        terminateSession(currentSession);

        return new StrResponseObject<>(SUCCESS);
    }

    static void terminateSession(str_session currentSession) {
        if (currentSession == null) {
            return;
        }
        logger.info(format("Updating session end time to current time and terminating session %s", currentSession.getId()));
        currentSession.setEnd_time(new Date());
        getEntityManagerRepo().saveOrUpdate(currentSession);
    }

    static StrResponseObject<str_auth_user> authenticateUser(str_channel channel,
                                                             String username, String password, boolean optimistic) {

        logger.info(format("Authenticating user %s with password %s on channel %s ", username, password, channel.getName()));
        StrResponseObject<str_auth_user> response = getUserByUsername(username, channel, optimistic);

        if (!response.getResponseCode().equals(SUCCESS)) {
            logger.info(format("Authentication failed! %s : %s", response.getResponseCode().name(), response.getMessage()));
            return new StrResponseObject<>(response.getResponseCode());
        }

        return validatePin(response.getResponseObject(), password);
    }

    static StrResponseObject<str_auth_user> authenticateUser(str_channel channel, str_auth_user user, String password) {

        if (user == null) {
            return new StrResponseObject<>(INPUT_INCOMPLETE_REQUEST);
        }

        logger.info(format("Authenticating user %s with password %s on channel %s ",
                user.getUser().getUsername(), password, channel.getName()));

        return validatePin(user, password);
    }

    static StrResponseObject<str_auth_user> setResponse(str_auth_user authUser) {

        if (authUser == null) {
            return new StrResponseObject<>(AUTH_NON_EXISTENT);
        }

        logger.info(format("Returning auth user %s in status %s ", authUser.getId(), authUser.getUser().getUser_status().getName()));
        return new StrResponseObject<>(SUCCESS, authUser);
    }

    /* When optimistic boolean is set, the function will try to find a user based on username only
     * When optimistic boolean is set, the function will try to find a user based on username, email or msisdn */
    static StrResponseObject<str_auth_user> getUserByUsername(
            String username, str_channel channel, boolean optimistic) {

        if (username == null || channel == null) {
            logger.info("Invalid request specified for username=" + username + " and channel=" + channel);
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by username " + username + " on channel " + channel.getName());
        logger.info("Optimistic mode enabled ? " + optimistic);

        if (!isValidUsername(username)) {
            if (!optimistic || (!isValidEmail(username) && !isValidMsisdn(username))) {
                logger.info("Invalid username \"" + username + "\" specified");
                return new StrResponseObject<>(INPUT_INVALID_USERNAME);
            }
        }

        logger.info("Attempting to find user by username");
        str_auth_user authUser = getAuthUserDao().findByUsernameAndChannel(username, channel);

        if (authUser == null && optimistic) {
            logger.info("Attempting to find user by email");
            authUser = getAuthUserDao().findByEmailAndChannel(username, channel);
        }
        if (authUser == null && optimistic) {
            logger.info("Attempting to find user by msisdn");
            authUser = getAuthUserDao().findByMsisdnAndChannel(username, channel);
        }
        return setResponse(authUser);
    }

    static StrResponseObject<str_auth_user> getUserByUsername(
            String username, str_channel strChannel) {
        return getUserByUsername(username, strChannel, false);
    }

    static StrResponseObject<str_auth_user> getUserByEmail(
            String email, str_channel channel) {

        if (email == null || channel == null) {
            logger.info("Invalid request specified for username=" + email + " and channel=" + channel);
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by email " + email + " on channel " + channel.getName());

        if (isValidEmail(email)) {
            return setResponse(getAuthUserDao().findByEmailAndChannel(email, channel));
        } else return new StrResponseObject<>(INPUT_INVALID_EMAIL);
    }

    public static StrResponseObject<str_auth_user> getUserByMsisdn(
            String msisdn, str_channel channel) {

        if (msisdn == null || channel == null) {
            logger.info("Invalid request specified for username=" + msisdn + " and channel=" + channel);
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by msisdn " + msisdn + " on channel " + channel.getName());

        if (isValidTenDigitMsisdn(msisdn)) {
            return setResponse(getAuthUserDao().findByMsisdnAndChannel(msisdn, channel));
        } else {
            return new StrResponseObject<>(INPUT_INVALID_MSISDN);
        }
    }

    static StrResponseObject<str_auth_user> registerUser(
            str_user newUser, str_channel channel, str_auth_group authGroup, String deviceId) {

        if (channel == null) {
            logger.info("Registration failed! Invalid channel (null) specified");
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        } else if (newUser == null) {
            logger.info("Registration failed! Invalid user (null) specified");
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        if (isNullOrEmpty(newUser.getUsername()) && isNullOrEmpty(newUser.getEmail()) && isNullOrEmpty(newUser.getMsisdn())) {
            logger.info("Registration failed! User has no valid identifier (username/email/phone) specified.");
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        if (newUser.getCountry() == null || !isValidName(newUser.getCountry().getName())) {
            return new StrResponseObject<str_auth_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
        }

        newUser.setMsisdn(formatFullMsisdn(newUser.getMsisdn(), newUser.getCountry().getDialing_code()));

        StrResponseObject<str_user> validationResponse = validateMandatoryChannelData(newUser, channel);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            logger.info("Registration failed! User data incomplete for registration on channel " + channel.getName());
            logger.info(validationResponse.getMessage());
            return new StrResponseObject<>(validationResponse.getResponseCode());
        }

        //valid data supplied, now check for previous registration
        List<Pair<String, ?>> conditionList = new ArrayList<>();
        if (!isNullOrEmpty(newUser.getEmail())) {
            conditionList.add(new Pair<>("email", newUser.getEmail()));
        }
        if (!isNullOrEmpty(newUser.getMsisdn())) {
            conditionList.add(new Pair<>("msisdn", newUser.getMsisdn()));
        }
        if (!isNullOrEmpty(newUser.getUsername())) {
            conditionList.add(new Pair<>("username", newUser.getUsername()));
        }
        List<str_user> existingUser = getEntityManagerRepo().findWhere(str_user.class, conditionList,
                1, false, false, true, false
        );

        if (existingUser != null && existingUser.size() >= 1) {

            if (existingUser.get(0).getEmail() != null && existingUser.get(0).getEmail().equalsIgnoreCase(newUser.getEmail())) {
                logger.info("Registration failed! User with email \"" + newUser.getEmail() + "\" already exists.");
                return new StrResponseObject<>(PREVIOUS_EMAIL_FOUND);
            }

            if (existingUser.get(0).getMsisdn() != null && existingUser.get(0).getMsisdn().equals(newUser.getMsisdn())) {
                logger.info("Registration failed! User with MSISDN \"" + newUser.getMsisdn() + "\" already exists.");
                return new StrResponseObject<>(PREVIOUS_MSISDN_FOUND);
            }

            if (existingUser.get(0).getUsername() != null && existingUser.get(0).getUsername().equalsIgnoreCase(newUser.getUsername())) {
                logger.info("Registration failed! User with username \"" + newUser.getUsername() + "\" already exists.");
                return new StrResponseObject<>(PREVIOUS_USERNAME_FOUND);
            }
        }

        //No for previous registration, proceed to register user
        newUser.setUser_status(fromEnum(ACC_ACTIVE));
        newUser.setSalt(generateIV());

        newUser.setPin(hashPassword(newUser.getPin(), newUser.getSalt()));

        newUser.save();

        return new StrResponseObject<>(SUCCESS, new str_auth_user(newUser, channel, authGroup, deviceId,
                newUser.getSalt(), new Date(), null, null).save());
    }

    static StrResponseObject<str_auth_user> assignChannel(
            str_user user, str_channel channel, str_auth_group auth_group, String deviceId) {

        if (channel == null) {
            logger.info("Channel assignment failed! Invalid channel (null) specified");
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        } else if (user == null) {
            logger.info("Channel assignment failed! Invalid user (null) specified");
            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
        }

        //valid data supplied, now check for previous registration
        List<str_auth_user> existingUsers = getEntityManagerRepo().findWhere(str_auth_user.class, asList(
                new Pair<>("user", user.getId()),
                new Pair<>("channel", channel.getId())
        ));

        if (existingUsers != null && existingUsers.size() > 0) {
            logger.info("Channel assignment failed! Auth User with username \"" + user.getUsername()
                    + "\" already exists on channel " + channel.getName());
            return new StrResponseObject<>(PREVIOUS_USERNAME_FOUND);
        }

        user.save();

        return new StrResponseObject<>(SUCCESS, new str_auth_user(
                user, channel, auth_group, deviceId, null, new Date(), null, null
        ).save());
    }

//    static StrResponseObject<str_auth_user> confirmUserRegistration(Long userId, String authToken,
//                                                                    String deviceId, String username,
//                                                                    String password, str_channel channel) {
//        if (!isValidAuthData(authToken)) {
//            logger.info(format("Registration failed! authToken is invalid (%s).", authToken));
//            return new StrResponseObject<str_auth_user>(INPUT_INVALID_REQUEST)
//                    .setMessage("Registration failed! Auth token is invalid (null).");
//        }
//        if (!isValidAuthData(deviceId)) {
//            logger.info(format("Registration failed! deviceId is invalid (%s).", deviceId));
//            return new StrResponseObject<str_auth_user>(INPUT_INVALID_REQUEST)
//                    .setMessage("Registration failed! Invalid device id");
//        }
//        if (!isValidUsername(username)) {
//            logger.info(format("Registration failed! Username is invalid (%s).", username));
//            return new StrResponseObject<>(INPUT_INVALID_USERNAME);
//        }
//        if (!isValidPassword(password)) {
//            logger.info(format("Registration failed! Password is invalid (%s).", password));
//            return new StrResponseObject<>(INPUT_INVALID_PASSWORD);
//        }
//        if (channel == null) {
//            logger.info("Registration failed! Invalid channel (null) specified");
//            return new StrResponseObject<>(INPUT_INVALID_REQUEST);
//        }
//
//        //verify that user has entered a valid username and auth token
//        StrResponseObject<str_auth_user> strAuthUserResponse = getUserByUsername(username, channel);
//        if (strAuthUserResponse.getResponseObject() == null || !strAuthUserResponse.getResponseObject().getUser().getId().equals(userId)) {
//            logger.info("Registration failed! User " + username + " with id " + userId + " does not exist.");
//            return new StrResponseObject<>(AUTH_NON_EXISTENT);
//        }
//
//        str_user regUser = strAuthUserResponse.getResponseObject().getUser();
//
//        if (!regUser.getUser_status().getId().equals(fromEnum(ACC_INACTIVE).getId())) {
//            logger.info("Registration failed! Previous registration found for user " + username + " in status "
//                    + regUser.getUser_status().getName());
//            return new StrResponseObject<>(PREVIOUS_USERNAME_FOUND);
//        }
//
//
//        if (!regUser.getSalt().equals(authToken)) {
//            logger.info("Registration failed! Invalid authToken " + authToken);
//            return new StrResponseObject<>(AUTH_AUTHENTICATION_FAILED);
//        }
//
//        regUser.setUser_status(fromEnum(ACC_ACTIVE));
//        regUser.setSalt(generateSalt());
//        regUser.setPassword(hashPassword(password, regUser.getSalt()));
//        regUser.setPassword_tries(0);
//        regUser.save();
//        return new StrResponseObject<>(SUCCESS, strAuthUserResponse.getResponseObject());
//    }

    static StrResponseObject<str_auth_user> changePin(str_auth_user authUser,
                                                      String newPin, boolean validatePrevious, String oldPassword) {

        logger.info(format("Changing pin for user %s ", authUser.getUser().getUsername()));
        StrResponseObject<str_auth_user> response;

        if (validatePrevious) {
            response = validatePin(authUser, oldPassword);
            if (!response.getResponseCode().equals(SUCCESS)) {
                return response;
            }
        }

        if (newPin == null) {
            newPin = GenPin();
        }

        if (!isValidPin(newPin)) {
            logger.severe(format("Failed to change user %s pin. Pin was not valid", authUser.getUser().getUsername()));
            return new StrResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        str_user user = authUser.getUser();

        if (user.getSalt() == null) {
            user.setSalt(generateSalt());
        }

        user.setPin(hashPassword(newPin, authUser.getUser().getSalt()));
        user.setPin_tries(0);
        if (user.getUser_status().equals(findByName(str_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
            user.setUser_status(findByName(str_response_code.class, ACC_ACTIVE.name()));
        }
        user.save().refresh();

        logger.info(format("Pin change response: %s", SUCCESS.getMessage()));
        return new StrResponseObject<>(SUCCESS, authUser);
    }

//    static StrResponseObject<str_auth_user> changePassword(str_auth_user authUser,
//                                                           String newPassword, boolean validatePrevious, String oldPassword) {
//
//        logger.info(format("Changing password for user %s ", authUser.getUser().getUsername()));
//        StrResponseObject<str_auth_user> response;
//
//        if (validatePrevious) {
//            response = validatePassword(authUser, oldPassword);
//            if (!response.getResponseCode().equals(SUCCESS)) {
//                return response;
//            }
//        }
//
//        if (newPassword == null) {
//            newPassword = Gen();
//        }
//
//        if (!isValidPassword(newPassword)) {
//            logger.severe(format("Failed to change user %s pin. Password was not valid", authUser.getUser().getUsername()));
//            return new StrResponseObject<>(INPUT_INVALID_PASSWORD);
//        }
//
//        str_user user = authUser.getUser();
//
//        if (user.getSalt() == null) {
//            user.setSalt(generateSalt());
//        }
//
//        user.setPassword(hashPassword(newPassword, authUser.getUser().getSalt()));
//        user.setPassword_tries(0);
//        if (user.getUser_status().equals(findByName(str_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
//            user.setUser_status(findByName(str_response_code.class, ACC_ACTIVE.name()));
//        }
//        user.save().refresh();
//
//        logger.info(format("Password change response: %s", SUCCESS.getMessage()));
//        return new StrResponseObject<>(SUCCESS, authUser);
//
//    }

    public static String hashPassword(String rawPassword, String salt) {
        logger.info(format("Hashing password %s with salt %s", rawPassword, salt));
        return hashWithSalt(rawPassword, DEFAULT_SECURITY_HASH, salt.getBytes());
    }

//    static StrResponseObject<str_auth_user> validatePassword(str_auth_user authUser, String password) {
//
//        logger.info(format("Validating password for auth user %s ", authUser.getId()));
//        StrResponseObject<str_auth_user> response = new StrResponseObject<>(GENERAL_ERROR, authUser);
//
//        str_user strUser = authUser.getUser();
//
//        authUser.setLast_auth_date(new Date());
//        if (!strUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
//            logger.info("Account is not active. Setting response to " + authUser.getUser().getUser_status().getName());
//            response.setResponseCode(StrResponseCode.valueOf(strUser.getUser_status().getId()));
//        } else {
//            int passwordTries = strUser.getPassword_tries();
//
//            if (passwordTries >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
//                logger.info("Authentication failed! Password tries exceeded");
//                response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
//            } else if (!isValidPassword(password)) {
//                logger.info("Authentication failed! Password format was invalid");
//                response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Password format was invalid");
//                strUser.setPassword_tries(++passwordTries);
//                if (strUser.getPassword_tries() >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
//                    logger.info("Authentication failed! Password format was invalid. Password tries exceeded");
//                    strUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
//                }
//            } else if (strUser.getPassword().equals(hashPassword(password, strUser.getSalt()))) {
//                logger.info("Authentication success");
//                response.setResponseCode(SUCCESS);
//                logger.info("Updating password tries to 0");
//                strUser.setPassword_tries(0);
//            } else {
//                logger.info("Authentication failed! Incorrect password");
//                response.setResponseCode(AUTH_INCORRECT_PASSWORD);
//                strUser.setPassword_tries(++passwordTries);
//                authUser.setLast_login_date(new Date());
//                if (strUser.getPassword_tries() >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
//                    logger.info("Password tries exceeded");
//                    strUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
//                }
//            }
//        }
//
//        strUser.save().refresh();
//        authUser.save();
//
//        logger.info("Returning response " + response.getResponseCode().name());
//        return response;
//    }
//
    static StrResponseObject<str_auth_user> validatePin(str_auth_user authUser, String pin) {

        logger.info(format("Validating pin for auth user %s ", authUser.getId()));
        StrResponseObject<str_auth_user> response = new StrResponseObject<>(GENERAL_ERROR, authUser);

        str_user strUser = authUser.getUser();

        authUser.setLast_auth_date(new Date());
        if (!strUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
            logger.info("Account is not active. Setting response to " + authUser.getUser().getUser_status().getName());
            response.setResponseCode(StrResponseCode.valueOf(strUser.getUser_status().getId()));
        } else {
            int pinTries = strUser.getPin_tries();

            if (pinTries >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                logger.info("Authentication failed! Pin tries exceeded");
                response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
            } else if (!isValidPin(pin)) {
                logger.info("Authentication failed! Pin format was invalid");
                response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Pin format was invalid");
                strUser.setPin_tries(++pinTries);
                if (strUser.getPin_tries() >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Authentication failed! Pin format was invalid. Pin tries exceeded");
                    strUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            } else if (strUser.getPin().equals(hashPassword(pin, strUser.getSalt()))) {
                logger.info("Authentication success");
                response.setResponseCode(SUCCESS);
                logger.info("Updating pin tries to 0");
                strUser.setPin_tries(0);
            } else {
                logger.info("Authentication failed! Incorrect pin");
                response.setResponseCode(AUTH_INCORRECT_PASSWORD);
                strUser.setPin_tries(++pinTries);
                authUser.setLast_login_date(new Date());
                if (strUser.getPin_tries() >= parseInt(getStrConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Pin tries exceeded");
                    strUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            }
        }

        strUser.save().refresh();
        authUser.save();

        logger.info("Returning response " + response.getResponseCode().name());
        return response;
    }

    public static String generateSalt() {
        return generateIV();
    }
}
