package net.streets.web.request;

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
public class NewCredentials implements Serializable {

    private String oldPin;
    private String newPin;
    private String newConfirmPin;

    public String getOldPin() {
        return oldPin;
    }

    public void setOldPin(String oldPin) {
        this.oldPin = oldPin;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public String getNewConfirmPin() {
        return newConfirmPin;
    }

    public void setNewConfirmPin(String newConfirmPin) {
        this.newConfirmPin = newConfirmPin;
    }

}
