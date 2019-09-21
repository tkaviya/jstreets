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
public class str_language extends str_enum_entity<str_language> {
    public str_language() {
    }

    public str_language(String name, Boolean enabled) {
        super(name, enabled);
    }
}
