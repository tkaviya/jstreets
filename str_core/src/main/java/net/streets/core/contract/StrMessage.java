package net.streets.core.contract;

import net.streets.persistence.entity.complex_type.str_user;

/***************************************************************************
 *                                                                         *
 * Created:     25 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public class StrMessage {

	public enum MESSAGE_TYPE { MSG_STREETS, MSG_PRIVATE }

	MESSAGE_TYPE message_type;
	str_user recipient;
	String message;

	public StrMessage(MESSAGE_TYPE message_type, str_user recipient, String message) {
		this.message_type = message_type;
		this.recipient = recipient;
		this.message = message;
	}

	public MESSAGE_TYPE getMessage_type() { return message_type; }

	public void setMessage_type(MESSAGE_TYPE message_type) { this.message_type = message_type; }

	public str_user getRecipient() { return recipient; }

	public void setRecipient(str_user recipient) { this.recipient = recipient; }

	public String getMessage() { return message; }

	public void setMessage(String message) { this.message = message; }
}
