package net.streets.web.request;

import net.streets.common.interfaces.PrintableStringClass;
import net.streets.web.annotations.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class Registration implements PrintableStringClass, Serializable {

    @UsernameConstraint
    private String username;
    @NameConstraint
    private String firstName;
    @NameConstraint
    private String lastName;
    @EmailConstraint
    private String email;
    @MsisdnConstraint
    private String msisdn;
    @PinConstraint
    private String pin;
    @PinConstraint
    private String confirmPin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getConfirmPin() {
        return confirmPin;
    }

    public void setConfirmPin(String confirmPin) {
        this.confirmPin = confirmPin;
    }
}
