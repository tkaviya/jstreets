package net.streets.core.contract;

import net.streets.persistence.entity.complex_type.str_auth_user;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class StrDeviceUser implements Serializable {

    protected Long walletId;
    protected String group;
    protected String authToken;
    protected Date lastLoginDate;
    protected String deviceVersionId;

    public StrDeviceUser(str_auth_user authUser, String deviceVersionId) {
        this.walletId = authUser.getUser().getWallet().getId();
        this.group = authUser.getAuth_group().getName();
        this.authToken = authUser.getAuth_token();
        this.lastLoginDate = authUser.getLast_login_date();
        this.deviceVersionId = deviceVersionId;
    }

    public StrDeviceUser(String group, String authToken, Date lastLoginDate, String deviceVersionId) {
        this.group = group;
        this.authToken = authToken;
        this.lastLoginDate = lastLoginDate;
        this.deviceVersionId = deviceVersionId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getDeviceVersionId() {
        return deviceVersionId;
    }

    public void setDeviceVersionId(String deviceVersionId) {
        this.deviceVersionId = deviceVersionId;
    }
}
