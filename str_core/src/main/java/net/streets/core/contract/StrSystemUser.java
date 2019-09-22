package net.streets.core.contract;

import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

import static net.streets.common.utilities.CommonUtilities.format10DigitPhoneNumber;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_COUNTRY_CODE;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@XmlRootElement
public class StrSystemUser implements Serializable {

    protected Long sessionId;
    protected Long authUserId;
    protected Long userId;
    protected Long walletId;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String email;
    protected String msisdn;
    protected String phoneNumber;
    protected Double walletBalance;
    protected String group;
    protected String deviceId;
    protected String authToken;
    protected Date lastLoginDate;
    protected String userStatus;
    protected String country;
    protected String language;
    protected Date dateOfBirth;

    public StrSystemUser() {
    }

    public StrSystemUser(Long sessionId, str_auth_user symAuthUser, str_user symUser) {
        this(sessionId, symAuthUser.getId(), symUser.getId(), symUser.getWallet().getId(), symUser.getFirst_name(),
                symUser.getLast_name(), symUser.getUsername(), symUser.getEmail(), symUser.getMsisdn(),
                format10DigitPhoneNumber(symUser.getMsisdn(), getStrConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)),
                symAuthUser.getUser().getWallet().getCurrent_balance().doubleValue(),
                symAuthUser.getAuth_group().getName(), symAuthUser.getDevice_id(),
                symAuthUser.getAuth_token(), symAuthUser.getLast_login_date());
    }

    public StrSystemUser(Long sessionId, Long authUserId, Long userId, Long walletId, String firstName, String lastName,
                         String username, String email, String msisdn, String phoneNumber, Double walletBalance,
                         String group, String deviceId, String authToken, Date lastLoginDate) {
        this.sessionId = sessionId;
        this.authUserId = authUserId;
        this.userId = userId;
        this.walletId = walletId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.msisdn = msisdn;
        this.walletBalance = walletBalance;
        this.group = group;
        this.deviceId = deviceId;
        this.authToken = authToken;
        this.lastLoginDate = lastLoginDate;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

	public Long getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(Long authUserId) {
		this.authUserId = authUserId;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Double getWalletBalance() { return walletBalance; }

    public void setWalletBalance(Double walletBalance) { this.walletBalance = walletBalance; }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public StrSystemUser setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
