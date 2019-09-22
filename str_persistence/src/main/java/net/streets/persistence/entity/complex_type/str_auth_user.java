package net.streets.persistence.entity.complex_type;

import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_user_id"))
@Cacheable(false)
public class str_auth_user extends str_entity<str_auth_user> {
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "str_user_id")
    private str_user user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private str_channel channel;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_group_id")
    private str_auth_group auth_group;
    @Basic
    private String device_id;
    @Basic
    private String auth_token;
    @Basic(optional = false)
    private Date registration_date;
    @Basic
    private Date last_auth_date;
    @Basic
    private Date last_login_date;

    public str_auth_user() {}

    public str_auth_user(str_user user, str_channel channel, str_auth_group auth_group,
                         String device_id, String auth_token, Date registration_date, Date last_auth_date,
                         Date last_login_date) {
        this.user = user;
        this.channel = channel;
        this.auth_group = auth_group;
        this.device_id = device_id;
        this.auth_token = auth_token;
        this.registration_date = registration_date;
        this.last_auth_date = last_auth_date;
        this.last_login_date = last_login_date;
    }

    public str_user getUser() {
        return user;
    }

    public str_auth_user setUser(str_user user) {
        this.user = user;
        return this;
    }

    public str_channel getChannel() {
        return channel;
    }

    public str_auth_user setChannel(str_channel channel) {
        this.channel = channel;
        return this;
    }

    public str_auth_group getAuth_group() {
        return auth_group;
    }

    public void setAuth_group(str_auth_group group) {
        this.auth_group = group;
    }

    public String getDevice_id() {
        return device_id;
    }

    public str_auth_user setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public str_auth_user setAuth_token(String auth_token) {
        this.auth_token = auth_token;
        return this;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public str_auth_user setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
        return this;
    }

    public Date getLast_auth_date() {
        return last_auth_date;
    }

    public str_auth_user setLast_auth_date(Date last_auth_date) {
        this.last_auth_date = last_auth_date;
        return this;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public str_auth_user setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
        return this;
    }
}
