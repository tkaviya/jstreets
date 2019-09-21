package net.streets.persistence.entity.enumeration;

import net.streets.persistence.entity.super_class.str_enum_entity;

import javax.persistence.*;

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

    @Basic
    @Column(nullable = false, updatable = false, columnDefinition = "bit default false")
    private Boolean is_pin_based;

    public str_channel() {
    }

    public str_channel(String name, Boolean enabled, Boolean is_pin_based) {
        super(name, enabled);
        this.is_pin_based = is_pin_based;
    }

    public Boolean is_pin_based() {
        return is_pin_based;
    }

    public void setIs_pin_based(Boolean is_pin_based) {
        this.is_pin_based = is_pin_based;
    }

}
