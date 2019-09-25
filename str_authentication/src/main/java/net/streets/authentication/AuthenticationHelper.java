package net.streets.authentication;

import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_country;
import net.streets.persistence.enumeration.StrResponseObject;

import static net.streets.persistence.enumeration.StrChannel.fromString;
import static net.streets.persistence.enumeration.StrResponseCode.*;
import static net.streets.persistence.helper.StrEnumHelper.countryFromString;
import static net.streets.utilities.StrValidator.*;

/***************************************************************************
 * *
 * Created:     30 / 12 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

class AuthenticationHelper {

    static StrResponseObject<str_user> validateMandatoryChannelData(str_user userData, str_channel channel) {

        if (channel == null) {
            return new StrResponseObject<str_user>(GENERAL_ERROR).setMessage("Invalid channel specified");
        } else if (userData == null) {
            return new StrResponseObject<str_user>(INPUT_INVALID_REQUEST).setMessage("Invalid user data (null) specified");
        } else if (userData.getCountry() == null || !isValidName(userData.getCountry().getName())) {
            return new StrResponseObject<str_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
        } else if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
            return new StrResponseObject<str_user>(INPUT_INVALID_REQUEST).setMessage("Invalid language specified");
        }

        str_country country = countryFromString(userData.getCountry().getName());
        if (country == null) {
            return new StrResponseObject<str_user>(NOT_SUPPORTED).setMessage("Invalid country specified");
        } else if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
            return new StrResponseObject<str_user>(NOT_SUPPORTED).setMessage("Invalid language specified");
        } else if (!isValidUsername(userData.getUsername())) {
		    return new StrResponseObject<str_user>(INPUT_INVALID_USERNAME).setMessage("Invalid username (" + userData.getUsername() + ") specified");
	    } else if (!isValidName(userData.getFirst_name())) {
		    return new StrResponseObject<str_user>(INPUT_INVALID_FIRST_NAME).setMessage("Invalid first name (" + userData.getFirst_name() + ") specified");
	    } else if (!isValidName(userData.getLast_name())) {
		    return new StrResponseObject<str_user>(INPUT_INVALID_LAST_NAME).setMessage("Invalid last name (" + userData.getLast_name() + ") specified");
	    }  else if (!isValidTenDigitMsisdn(userData.getMsisdn()) && !isValidMsisdn(userData.getMsisdn(), country.getDialing_code())) {
		    return new StrResponseObject<str_user>(INPUT_INVALID_MSISDN).setMessage("Invalid phone number (" + userData.getMsisdn() + ") specified");
	    }

	    switch (fromString(channel.getName())) {
            case WEB: {
	            if (!isValidEmail(userData.getEmail())) {
		            return new StrResponseObject<str_user>(INPUT_INVALID_EMAIL).setMessage("Invalid email (" + userData.getEmail() + ") specified");
	            } else if (!isValidPin(userData.getPin())) {
		            return new StrResponseObject<str_user>(INPUT_INVALID_PASSWORD).setMessage("Invalid pin specified");
	            } else return new StrResponseObject<str_user>(SUCCESS).setResponseObject(userData);
            }
	        case SMART_PHONE: {
		        if (!isValidPin(userData.getPin())) {
			        return new StrResponseObject<str_user>(INPUT_INVALID_PASSWORD).setMessage("Invalid pin specified");
		        } else return new StrResponseObject<str_user>(SUCCESS).setResponseObject(userData);
	        }
            default:
                return new StrResponseObject<str_user>(NOT_SUPPORTED).setMessage("Channel not supported: " + channel.getName());
        }
    }

}
