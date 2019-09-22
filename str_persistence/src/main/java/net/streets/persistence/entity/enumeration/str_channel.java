package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_enum_entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "channel_id"))
@Cacheable
public class str_channel extends str_enum_entity<str_channel> {

    public str_channel() {
    }

    public str_channel(String name, Boolean enabled) {
        super(name, enabled);
    }
}
