package net.streets.core.service;

import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.enumeration.StrResponseCode;

import java.util.Date;

import static net.streets.persistence.helper.StrEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2018                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface RequestProcessor {

    default void logResponse(str_auth_user authUser, str_request_response_log requestResponseLog, str_response_code responseCode, String responseMessage) {
        if (authUser != null) {
            requestResponseLog.setAuth_user(authUser);
            requestResponseLog.setSystem_user(authUser.getUser());
        }
        requestResponseLog.setOutgoing_response(responseMessage);
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(responseCode);
        requestResponseLog.save();
    }

    default void logResponse(str_auth_user authUser, str_request_response_log requestResponseLog, str_response_code responseCode) {
        logResponse(authUser, requestResponseLog, responseCode, responseCode.getResponse_message());
    }

    default void logResponse(str_auth_user authUser, str_request_response_log requestResponseLog, StrResponseCode responseCode, String responseMessage) {
        logResponse(authUser, requestResponseLog, fromEnum(responseCode), responseMessage);
    }

    default void logResponse(str_auth_user authUser, str_request_response_log requestResponseLog, StrResponseCode responseCode) {
        logResponse(authUser, requestResponseLog, fromEnum(responseCode));
    }
}
