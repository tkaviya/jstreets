package net.streets.core.contract;

import net.streets.core.contract.base.DataContract;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/***************************************************************************
 * Created:     11 / 06 / 2016                                             *
 * Platform:    Mint Linux x86_64                                          *
 * Author:      Tsungai Kaviya                                             *
 ***************************************************************************/

@XmlRootElement
public class StrDeviceUserResponse extends DataContract<StrDeviceUserResponse> implements Serializable {

    private StrDeviceUser systemUser;

    public StrDeviceUserResponse() {
    }

    public StrDeviceUserResponse(StrResponseCode symResponseCode) {
        super(symResponseCode);
    }

    public StrDeviceUserResponse(StrResponseCode symResponseCode, StrDeviceUser systemUser) {
        super(symResponseCode);
        this.systemUser = systemUser;
    }

    public StrDeviceUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(StrDeviceUser systemUser) {
        this.systemUser = systemUser;
    }
}
