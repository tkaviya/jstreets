package net.streets.web.session;

import net.streets.common.utilities.StrTransformer;
import net.streets.core.Streets;
import net.streets.core.contract.StrMessage;
import net.streets.persistence.entity.complex_type.str_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import static java.lang.String.format;

/***************************************************************************
 *                                                                         *
 * Created:     22 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Named
@Component
@Scope("session")
@SessionScoped
public class StreetsBean implements Serializable, PropertyChangeListener {

	private final static Logger logger = Logger.getLogger(StreetsBean.class.getSimpleName());
	private final PushContext streetsChannel;
	private final PushContext privateChannel;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	@Size(min = 1, message = "Please enter a chat message to send.")
	private String userCommand;
	private str_user currentUser;
	@Autowired
	Streets streets;

	@Inject
	public StreetsBean(@Push(channel = "streetsChannel") PushContext streetsChannel,
	                   @Push(channel = "privateChannel") PushContext privateChannel) {
		this.streetsChannel = streetsChannel;
		this.privateChannel = privateChannel;
	}

	private void sendStreetsMessage(String message) {
		logger.info("ChannelMessage: " +  message);
		var channelMessage = StrTransformer.localDateTimeToString(LocalDateTime.now()) + ": " + message;
		streetsChannel.send(channelMessage);
	}

	private void sendPrivateMessage(String message) {
		logger.info(format("PrivateMessage to user %s: %s", currentUser.getUsername(), message));
		var channelMessage = StrTransformer.localDateTimeToString(LocalDateTime.now()) + ": " + message;
		privateChannel.send(channelMessage, currentUser.getId());
	}

	public String getUserCommand() { return userCommand; }

	public void setUserCommand(String userCommand) { this.userCommand = userCommand; }

	public void sendCommand() {
		System.out.println("Got user command: " + userCommand);
		privateChannel.send(LocalDateTime.now() + ": " + userCommand);
		//TODO Execute Command
		userCommand = "";
	}

	public void setCurrentUser(str_user currentUser) { this.currentUser = currentUser; }

	public void setStreetsBean(Streets streets) {
		this.streets = streets;
		this.streets.addPropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		var message = (StrMessage)event.getNewValue();
		switch (message.getMessage_type())
		{
			case MSG_STREETS: {
				sendStreetsMessage(message.getMessage());
				break;
			}
			case MSG_PRIVATE: {
				if (message.getRecipientUserID().equals(this.currentUser.getId())) {
					sendPrivateMessage(message.getMessage());
				}
				break;
			}
		}
	}
}
