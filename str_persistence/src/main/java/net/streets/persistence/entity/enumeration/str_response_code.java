package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_enum_entity;
import net.streets.persistence.enumeration.StrResponseCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class str_response_code extends str_enum_entity<str_response_code> {

    @Column(nullable = false)
    private String response_message;

    public str_response_code() {}

    public str_response_code(String name, String response_message, Boolean enabled) {
        super(name, enabled);
        this.response_message = response_message;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public StrResponseCode asStrResponseCode() {
        return StrResponseCode.valueOf(this.getId());
    }
}
