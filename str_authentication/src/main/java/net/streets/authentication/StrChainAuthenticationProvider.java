package net.streets.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 */

import net.streets.common.structure.Pair;
import net.streets.persistence.dao.EnumEntityRepoManager;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.log.str_session;
import net.streets.persistence.entity.complex_type.str_auth_group_role;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.entity.enumeration.str_role;
import net.streets.persistence.enumeration.StrResponseCode;
import net.streets.persistence.enumeration.StrResponseObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.streets.authentication.StreetsAuthenticator.getUserByUsername;
import static net.streets.persistence.enumeration.StrEventType.USER_LOGIN;
import static net.streets.persistence.enumeration.StrResponseCode.*;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;

public abstract class StrChainAuthenticationProvider {

    protected static final Logger logger = Logger.getLogger(StrChainAuthenticationProvider.class.getSimpleName());
    private final str_channel strChannel;
    private str_request_response_log requestResponseLog;
    str_auth_user strAuthUser;
    str_user strUser;
    str_session strSession;
    private StrResponseObject<str_auth_user> responseObject;
    private String username, email, msisdn, password, deviceId, authToken;

    protected static final HashMap<str_channel, ArrayList<AuthenticationStep>> authenticationChain = new HashMap<>();

    private static final HashMap<StrResponseCode, StrResponseCode> mappedResponseCode = new HashMap<>();

    static {
        // We will mask any response code < 0 because it is a general system error that a user should not see
        EnumEntityRepoManager.findEnabled(str_response_code.class)
                .stream()
                .filter(strResponseCode -> strResponseCode.getId() < 0)
                .forEach(errResponseCode -> mappedResponseCode.put(errResponseCode.asStrResponseCode(), GENERAL_ERROR));

        // We will mask certain authentication response codes to avoid username/password guessing
        mappedResponseCode.put(DATA_NOT_FOUND, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(INPUT_INVALID_REQUEST, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(AUTH_INCORRECT_PASSWORD, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(AUTH_NON_EXISTENT, AUTH_AUTHENTICATION_FAILED);
    }

    protected interface AuthenticationStep {
        StrResponseObject<str_auth_user> executeAuthenticationStep();
    }

    //each auth provider must determine its own chain of authentication
    protected abstract void initializeAuthenticationChain();

    public StrChainAuthenticationProvider(str_request_response_log requestResponseLog, str_channel channel) {
        this.requestResponseLog = requestResponseLog;
        this.strChannel = channel;
        initializeAuthenticationChain();
    }

    // Functions to set auth data. They return StrChainAuthenticationProvider simply to allow method chaining
    public StrChainAuthenticationProvider setUser(str_user user) {
        this.strUser = user;
        return this;
    }

    public StrChainAuthenticationProvider setAuthUser(str_auth_user authUser) {
        this.strAuthUser = authUser;
        return this;
    }

    public StrChainAuthenticationProvider setSession(str_session session) {
        this.strSession = session;
        return this;
    }

    public StrChainAuthenticationProvider setAuthUsername(String username) {
        this.username = username;
        return this;
    }

    public StrChainAuthenticationProvider setAuthEmail(String email) {
        this.email = email;
        return this;
    }

    public StrChainAuthenticationProvider setAuthMsisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public StrChainAuthenticationProvider setAuthPassword(String password) {
        this.password = password;
        return this;
    }

    public StrChainAuthenticationProvider setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public StrChainAuthenticationProvider setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public static void addMappedResponseCode(StrResponseCode strResponseCode, StrResponseCode returnResponse) {
        mappedResponseCode.put(strResponseCode, returnResponse);
    }

    public static StrResponseCode getMappedResponseCode(StrResponseCode strResponseCode) {
        return mappedResponseCode.get(strResponseCode);
    }

    public final StrResponseObject<str_auth_user> authenticateUser() {

        requestResponseLog.setEvent_type(fromEnum(USER_LOGIN));

        ArrayList<AuthenticationStep> chain = authenticationChain.get(strChannel);

        for (AuthenticationStep authenticationStep : chain) {
            responseObject = authenticationStep.executeAuthenticationStep();
            if (responseObject.getResponseCode() != SUCCESS) {
                logger.info("Authentication failed with response: " +
                        responseObject.getResponseCode().name() + " -> " +
                        responseObject.getResponseCode().getMessage());
                requestResponseLog.setResponse_code(fromEnum(responseObject.getResponseCode()));
                break;
            }
        }
//		return logAndReturn();
        return responseObject;

    }

    protected StrResponseObject<str_auth_user> getUserByUsernameAndChannel() {
        StrResponseObject<str_auth_user> userResponse = getUserByUsername(username, strChannel);

        if (userResponse.getResponseCode() == SUCCESS) {
            strAuthUser = userResponse.getResponseObject();
        } else {
            return userResponse;
        }

        return userResponse;
    }

    protected StrResponseObject<str_auth_user> getUserByOptimisticUsernameAndChannel() {
        StrResponseObject<str_auth_user> userResponse = getUserByUsername(username, strChannel, true);

        if (userResponse.getResponseCode() == SUCCESS) {
            strAuthUser = userResponse.getResponseObject();
        } else {
            return userResponse;
        }

        return userResponse;
    }

//    protected StrResponseObject<str_auth_user> validatePassword() {
//        StrResponseObject<str_auth_user> authUserResponse = StreetsAuthenticator.validatePassword(strAuthUser, password);
//        return new StrResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
//    }

    protected StrResponseObject<str_auth_user> validatePin() {
        if (strAuthUser == null) {
            getUserByUsernameAndChannel();
        }
        StrResponseObject<str_auth_user> authUserResponse = StreetsAuthenticator.validatePin(strAuthUser, password);
        return new StrResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
    }

    protected StrResponseObject<str_auth_user> startSession() {
        StrResponseObject<str_session> loginResponse = StreetsAuthenticator.startSession(
                strChannel, deviceId, username, password, true);
        if (loginResponse.getResponseCode().equals(SUCCESS)) {
            strSession = loginResponse.getResponseObject();
            strAuthUser = loginResponse.getResponseObject().getAuth_user();
            strUser = loginResponse.getResponseObject().getAuth_user().getUser();
            return new StrResponseObject<>(loginResponse.getResponseCode(), loginResponse.getResponseObject().getAuth_user());
        } else {
            return new StrResponseObject<>(loginResponse.getResponseCode());
        }
    }

    public StrResponseObject endSession() {
        return StreetsAuthenticator.endSession(strSession.getId(), strChannel, deviceId, authToken);
    }

    public boolean hasRole(str_role role) {
        if (strAuthUser == null) {
            return false;
        }

        List results = getEntityManagerRepo().findWhere(str_auth_group_role.class, new ArrayList<>(
                asList(
                        new Pair<>("auth_group", strAuthUser.getAuth_group().getId()),
                        new Pair<>("role", role.getId()),
                        new Pair<>("is_enabled", 1)
                )
        ));

        logger.info(format("Found %s results for auth_group %s role %s",
                results == null ? 0 : results.size(), strAuthUser.getAuth_group(), role));

        return results != null && results.size() >= 1;
    }

//	protected StrResponseObject<str_auth_user> logAndReturn() {
//        str_auth_user auth_user, String device_id, String auth_token, Date start_time, Date end_time
//
//		str_session log = new str_session(strAuthUser, strAuthUser.getDevice_id(), strChannel, requestResponseLog.getEvent_type(),
//			getStrResponseCode(responseObject.getResponseCode()), requestResponseLog.getId(),
//			new Date(), responseObject.getMessage());
//
//		StreetsLogHelper.logSystemEvent(log);
//
//		if (responseObject.getResponseCode() != SUCCESS) {
//			Object mappedResponse = mappedResponseCode.get(responseObject.getResponseCode());
//			if (mappedResponse != null && mappedResponse instanceof str_response_code) {
//				logger.info("Returning response " + mappedResponse + " for response code " + responseObject.getResponseCode());
//				StrResponseCode strResponseCode = StrResponseCode.valueOf(((str_response_code) mappedResponse).getId());
//				responseObject.setResponseCode(strResponseCode);
//			}
//		}
//		return responseObject;
//	}
}

