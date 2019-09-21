package net.streets.persistence.dao.implementation;

import net.streets.common.structure.Pair;
import net.streets.persistence.dao.complex_type.StrGroupRoleDao;
import net.streets.persistence.entity.complex_type.str_auth_group_role;
import net.streets.persistence.entity.enumeration.str_auth_group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ***************************************************************************
 * *
 * Created:     20 / 09 / 2015                                             *
 * Platform:    Red Hat Linux 9                                            *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * ****************************************************************************
 */

@Repository
@Transactional
public class StrGroupRoleDaoImpl extends StrEnumEntityDaoImpl<str_auth_group_role, Long> implements StrGroupRoleDao {

    protected StrGroupRoleDaoImpl() { super(str_auth_group_role.class); }

    @Override
    public List<str_auth_group_role> findByGroup(str_auth_group symGroup) {
        return findWhere(new Pair<String, Object>("group_id", symGroup.getId()));
    }
}
