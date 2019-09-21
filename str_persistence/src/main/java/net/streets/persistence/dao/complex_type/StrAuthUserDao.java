package net.streets.persistence.dao.complex_type;

import net.streets.persistence.dao.super_class.StreetsDaoInterface;
import net.streets.persistence.entity.complex_type.str_auth_user;
import net.streets.persistence.entity.enumeration.str_channel;

/**
 * Created by tsungai.kaviya on 2015-08-24.
 */

public interface StrAuthUserDao extends StreetsDaoInterface<str_auth_user, Long> {

    str_auth_user findByStrUserFieldAndChannel(String fieldName, String value, str_channel channel, boolean onlyActive);

    str_auth_user findByUsernameAndChannel(String username, str_channel channel, boolean onlyActive);

    str_auth_user findByEmailAndChannel(String email, str_channel channel, boolean onlyActive);

    str_auth_user findByMsisdnAndChannel(String msisdn, str_channel channel, boolean onlyActive);

    str_auth_user findByUsernameAndChannel(String username, str_channel channel);

    str_auth_user findByEmailAndChannel(String email, str_channel channel);

    str_auth_user findByMsisdnAndChannel(String msisdn, str_channel channel);
}
