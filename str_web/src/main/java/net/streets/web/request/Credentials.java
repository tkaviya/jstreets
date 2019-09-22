package net.streets.web.request;

import net.streets.web.annotations.PinConstraint;
import net.streets.web.annotations.UsernameConstraint;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class Credentials implements Serializable {

    @UsernameConstraint
    private String username;
    @PinConstraint
    private String pin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
