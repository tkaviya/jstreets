package net.streets.persistence.entity.complex_type;

import net.streets.persistence.entity.complex_type.wallet.str_wallet;
import net.streets.persistence.entity.enumeration.str_country;
import net.streets.persistence.entity.enumeration.str_language;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "str_user_id"))
@Cacheable(false)
public class str_user extends str_entity<str_user> {
    @Column(length = 20)
    private String first_name;
    @Column(length = 20)
    private String last_name;
    @Basic
    private Date date_of_birth;
    @Column(unique = true, length = 20)
    private String username;
    @Basic
    private String pin;
    @Column(nullable = false, precision = 1)
    private Integer pin_tries;
    @Basic(optional = false)
    @Column(length = 128)
    private String salt;
    @Column(unique = true)
    private String email;
    @Basic(optional = false)
    @Column(unique = true, length = 12)
    private String msisdn;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_status_id")
    private str_response_code user_status;
    @OneToOne
    @JoinColumn(name = "wallet_id")
    private str_wallet wallet;
    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    private str_country country;
    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private str_language language;

    public str_user() { }

    public str_user(String first_name, String last_name, Date date_of_birth, String username,
                    String pin, Integer pin_tries, String salt, String email,
                    String msisdn, str_response_code user_status,
                    str_wallet wallet, str_country country, str_language language) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.username = username;
        this.pin = pin;
        this.pin_tries = pin_tries;
        this.salt = salt;
        this.email = email;
        this.msisdn = msisdn;
        this.user_status = user_status;
        this.wallet = wallet;
        this.country = country;
        this.language = language;
    }

    public str_user(String first_name, String last_name, Date date_of_birth, String username,
                    String pin, Integer pin_tries, String salt, String email,
                    String msisdn, str_response_code user_status,
                    str_country country, str_language language) {
        this(first_name, last_name, date_of_birth, username, pin,
             pin_tries, salt, email, msisdn, user_status, null, country, language);
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
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

    public Integer getPin_tries() {
        return pin_tries;
    }

    public void setPin_tries(Integer pin_tries) {
        this.pin_tries = pin_tries;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public str_response_code getUser_status() {
        return user_status;
    }

    public void setUser_status(str_response_code user_status) {
        this.user_status = user_status;
    }

    public str_wallet getWallet() {
        return wallet;
    }

    public str_user setWallet(str_wallet wallet) {
        this.wallet = wallet;
        return this;
    }

    public str_country getCountry() {
        return country;
    }

    public void setCountry(str_country country) {
        this.country = country;
    }

    public str_language getLanguage() {
        return language;
    }

    public void setLanguage(str_language language) {
        this.language = language;
    }
}
