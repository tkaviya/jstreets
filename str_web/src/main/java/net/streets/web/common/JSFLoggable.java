package net.streets.web.common;

import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_event_type;
import net.streets.persistence.entity.enumeration.str_response_code;

import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.persistence.enumeration.StrChannel.WEB;
import static net.streets.persistence.helper.StrEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface JSFLoggable {
    Logger logger = Logger.getLogger(JSFLoggable.class.getSimpleName());
    str_channel LOG_CHANNEL = fromEnum(WEB);

    default void log(str_event_type eventType, str_auth_user user, str_response_code responseCode,
                     Date requestTime, Date responseTime, String incomingRequest, String outgoingResponse) {
        logger.info(format("Logging event type %s by user %s: [%s]",
                eventType.getName(), user.getUser().getUsername(), incomingRequest)
        );
        new str_request_response_log(
            LOG_CHANNEL, eventType, user.getUser(), user, responseCode, requestTime, responseTime,
            incomingRequest, outgoingResponse).save();
    }
}
