package net.streets.web.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/***************************************************************************
 *                                                                         *
 * Created:     22 / 09 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * System:      IntelliJ 2019 / Windows 10                                 *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class StreetsBean implements Serializable {

	@Inject
	@Push(channel = "mainChannel")
	private PushContext mainChannel;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	@Size(min = 1, message = "Please enter a chat message to send.")
	private String userCommand;
	private Vector<String> userCommands = new Vector<>();

	public StreetsBean() {
		sendBeepForAMinute();
	}

	public void sendBeepForAMinute() {

		final Runnable beeper = () -> sendMessage("I'm the streets, look both ways before you cross me!");

		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);

		scheduler.schedule(() -> { beeperHandle.cancel(true); }, 60, SECONDS);
	}

	public void sendMessage(Object message) { mainChannel.send(message); }

	public String getUserCommand() { return userCommand; }

	public void setUserCommand(String userCommand) { this.userCommand = userCommand; }

	public void sendCommand() {
		System.out.println("Got user command: " + userCommand);
		userCommands.add(userCommand);
		userCommand = "";
	}

	public void sendMessageToClient() {}

	public void sendMessageToEveryone() {}

}
