package net.streets.persistence.entity.complex_type;

import net.streets.persistence.entity.enumeration.str_auth_group;
import net.streets.persistence.entity.enumeration.str_role;
import net.streets.persistence.entity.super_class.str_enum_entity;

import javax.persistence.*;

/**
 * Created with Intelli_j IDEA.
 * User: photon
 * Date: 2015/06/10
 * Time: 3:56 PM
 */
@Entity
@AttributeOverride(name = "id", column = @Column(name = "auth_group_role_id"))
@Cacheable(false)
public class str_auth_group_role extends str_enum_entity<str_auth_group_role> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_group_id")
    private str_auth_group auth_group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id")
    private str_role role;

    public str_auth_group_role() {}

    public str_auth_group_role(String name, Boolean enabled, str_auth_group auth_group, str_role role) {
        super(name, enabled);
        this.auth_group = auth_group;
        this.role = role;
    }

    public str_auth_group getAuth_group() {
        return auth_group;
    }

    public void setAuth_group(str_auth_group auth_group) {
        this.auth_group = auth_group;
    }

    public str_role getRole() {
        return role;
    }

    public void setRole(str_role role) {
        this.role = role;
    }
}
