package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_enum_entity;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
public class str_event_type extends str_enum_entity<str_event_type> {
    public str_event_type() {
    }

    public str_event_type(String name, Boolean enabled) {
        super(name, enabled);
    }
}
