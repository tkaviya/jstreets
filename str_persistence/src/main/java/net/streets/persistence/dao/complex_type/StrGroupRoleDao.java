package net.streets.persistence.dao.complex_type;

import net.streets.persistence.dao.super_class.StreetsEnumEntityDao;
import net.streets.persistence.entity.complex_type.str_auth_group_role;
import net.streets.persistence.entity.enumeration.str_auth_group;

import java.util.List;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */
public interface StrGroupRoleDao extends StreetsEnumEntityDao<str_auth_group_role, Long> {
    List<str_auth_group_role> findByGroup(str_auth_group symGroup);
}
