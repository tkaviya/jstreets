package net.streets.persistence.entity.super_class;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@MappedSuperclass
public abstract class str_enum_entity<E extends str_enum_entity> extends str_entity<E> {

    @Basic
    @Column(nullable = false, unique = true)
    protected String name;

    @Basic
    @Column(nullable = false, columnDefinition = "bit default true")
    protected Boolean isEnabled = true;

    public str_enum_entity() {
    }

    public str_enum_entity(String name, Boolean isEnabled) {
        this.name = name;
        this.isEnabled = isEnabled;
    }

    public String getName() {
        return name;
    }

    /* this function is private because we do not want to be
     * able to update an enum entity to set name programmatically */
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean enabled) {
        this.isEnabled = enabled;
    }

    @Override
    public String toString() {
        return name;
    }
}
