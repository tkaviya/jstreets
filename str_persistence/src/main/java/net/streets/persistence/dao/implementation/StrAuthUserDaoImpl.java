package net.streets.persistence.dao.implementation;

import net.streets.common.structure.Pair;
import net.streets.persistence.dao.complex_type.StrAuthUserDao;
import net.streets.persistence.dao.super_class.AbstractDao;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.enumeration.str_channel;
import net.streets.persistence.entity.enumeration.str_response_code;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static net.streets.common.enumeration.StrResponseCode.ACC_ACTIVE;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/**
 * Created with IntelliJ IDEA.
 * str_user: tkaviya
 * Date: 8/10/13
 * Time: 11:25 AM
 */

@Repository
@Transactional
public class StrAuthUserDaoImpl extends AbstractDao<str_auth_user, Long> implements StrAuthUserDao {
    public StrAuthUserDaoImpl() {
        super(str_auth_user.class);
    }

    public str_auth_user findByStrUserFieldAndChannel(
		    String fieldName, String value, str_channel channel, boolean onlyActive) {

        if (channel == null) {
            return null;
        }

        List<Pair<String, ?>> criteria;

        if (!onlyActive) {
            criteria = asList(
                    new Pair<>("user." + fieldName, value),
                    new Pair<>("channel_id", channel.getId()));
        } else {
            criteria = asList(
                    new Pair<>("user." + fieldName, value),
                    new Pair<>("channel_id", channel.getId()),
                    new Pair<>("user.user_status", findByName(str_response_code.class, ACC_ACTIVE.name()).getId()));
        }

        List<str_auth_user> results = getEntityManagerRepo().findWhere(getEntityClass(), criteria);

        if (results.size() == 1) {
            return results.get(0);
        }
        return null;
    }

    public str_auth_user findByUsernameAndChannel(String username, str_channel channel, boolean onlyActive) {
        return findByStrUserFieldAndChannel("username", username, channel, onlyActive);
    }

    public str_auth_user findByEmailAndChannel(String email, str_channel channel, boolean onlyActive) {
        return findByStrUserFieldAndChannel("email", email, channel, onlyActive);
    }

    public str_auth_user findByMsisdnAndChannel(String msisdn, str_channel channel, boolean onlyActive) {
        return findByStrUserFieldAndChannel("msisdn", msisdn, channel, onlyActive);
    }

    public str_auth_user findByUsernameAndChannel(String username, str_channel channel) {
        return findByUsernameAndChannel(username, channel, false);
    }

    public str_auth_user findByEmailAndChannel(String email, str_channel channel) {
        return findByEmailAndChannel(email, channel, false);
    }

    public str_auth_user findByMsisdnAndChannel(String msisdn, str_channel channel) {
        return findByMsisdnAndChannel(msisdn, channel, false);
    }
}
