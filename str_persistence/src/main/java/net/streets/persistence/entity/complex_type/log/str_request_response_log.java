package net.streets.persistence.entity.complex_type.log;

import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.complex_type.str_user;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_event_type;
import net.streets.persistence.entity.enumeration.str_response_code;
import net.streets.persistence.entity.super_class.str_entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */

@Entity
public class str_request_response_log extends str_entity<str_request_response_log> {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private str_channel channel;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_type_id")
    private str_event_type event_type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_user_id")
    private str_user system_user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_user_id")
    private str_auth_user auth_user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_code_id")
    private str_response_code response_code;
    @Basic
    @Column(nullable = false)
    private Date incoming_request_time;
    @Basic
    @Column
    private Date outgoing_response_time;
    @Basic
    @Column(nullable = false, length = 2048)
    private String incoming_request;
    @Basic
    @Column(length = 2048)
    private String outgoing_response;

    public str_request_response_log() {}

    public str_request_response_log(str_channel channel, str_event_type event_type, String incoming_request) {
        this.channel = channel;
        this.event_type = event_type;
        this.incoming_request = incoming_request;
        this.incoming_request_time = new Date();
    }

    public str_request_response_log(str_channel channel, String incoming_request) {
        this(channel, null, incoming_request);
    }

    public str_request_response_log(str_channel channel, str_event_type event_type,
                                    str_user system_user, str_auth_user auth_user, str_response_code response_code,
                                    Date incoming_request_time, Date outgoing_response_time, String incoming_request, String outgoing_response) {
        this.channel = channel;
        this.event_type = event_type;
        this.system_user = system_user;
        this.auth_user = auth_user;
        if (system_user == null && auth_user != null) {
            this.system_user = auth_user.getUser();
        }
        this.response_code = response_code;
        this.incoming_request_time = incoming_request_time;
        this.outgoing_response_time = outgoing_response_time;
        this.incoming_request = incoming_request;
        this.outgoing_response = outgoing_response;
    }

    public str_channel getChannel() {
        return this.channel;
    }

    public str_request_response_log setChannel(str_channel channel) {
        this.channel = channel;
        return this;
    }

    public str_event_type getEvent_type() {
        return this.event_type;
    }

    public str_request_response_log setEvent_type(str_event_type event_type) {
        this.event_type = event_type;
        return this;
    }

    public str_auth_user getAuth_user() {
        return this.auth_user;
    }

    public str_request_response_log setAuth_user(str_auth_user auth_user) {
        this.auth_user = auth_user;
        if (this.system_user == null && auth_user != null) {
            this.system_user = auth_user.getUser();
        }
        return this;
    }

    public str_user getSystem_user() {
        return this.system_user;
    }

    public str_request_response_log setSystem_user(str_user system_user) {
        this.system_user = system_user;
        return this;
    }

    public str_response_code getResponse_code() {
        return this.response_code;
    }

    public str_request_response_log setResponse_code(str_response_code response_code) {
        this.response_code = response_code;
        return this;
    }

    public Date getIncoming_request_time() {
        return this.incoming_request_time;
    }

    public str_request_response_log setIncoming_request_time(Date incoming_request_time) {
        this.incoming_request_time = incoming_request_time;
        return this;
    }

    public Date getOutgoing_response_time() {
        return this.outgoing_response_time;
    }

    public str_request_response_log setOutgoing_response_time(Date outgoing_response_time) {
        this.outgoing_response_time = outgoing_response_time;
        return this;
    }

    public String getIncoming_request() {
        return this.incoming_request;
    }

    public str_request_response_log setIncoming_request(String incoming_request) {
        this.incoming_request = incoming_request;
        return this;
    }

    public String getOutgoing_response() {
        return this.outgoing_response;
    }

    public str_request_response_log setOutgoing_response(String outgoing_response) {
        this.outgoing_response = outgoing_response;
        return this;
    }
}
