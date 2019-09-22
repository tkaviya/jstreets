package net.streets.persistence.helper;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.enumeration.*;
import net.streets.persistence.enumeration.StrAuthGroup;
import net.streets.persistence.enumeration.StrChannel;
import net.streets.persistence.enumeration.StrEventType;
import net.streets.persistence.enumeration.StrResponseCode;

import java.util.List;

import static java.util.Arrays.asList;
import static net.streets.persistence.dao.EnumEntityRepoManager.findByName;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class StrEnumHelper {

    public static str_country countryFromString(String name) {
        return findByName(str_country.class, name);
    }

    public static str_language languageFromString(String name) {
        return findByName(str_language.class, name);
    }

    public static Pair<Long, String> getMappedResponseMessage(String systemId, Long responseCode) {
        List<str_response_mapping> mappedResponseList = getEntityManagerRepo().findWhere(str_response_mapping.class, asList(
            new Pair<>("system_id", systemId), new Pair<>("response_code_id", responseCode)
        ));

        if (mappedResponseList != null && mappedResponseList.size() == 1) {
            str_response_mapping mappedResponse = mappedResponseList.get(0);
            return new Pair<>(mappedResponse.getResponse_code_id(), mappedResponse.getMapped_response_message());
        } else {
            return new Pair<>((long) StrResponseCode.GENERAL_ERROR.code, StrResponseCode.GENERAL_ERROR.message);
        }
    }

    public static str_auth_group fromEnum(StrAuthGroup authGroup) {
        return findByName(str_auth_group.class, authGroup.name());
    }

    public static str_channel fromEnum(StrChannel channel) {
        return findByName(str_channel.class, channel.name());
    }

    public static str_event_type fromEnum(StrEventType eventType) {
        return findByName(str_event_type.class, eventType.name());
    }

    public static str_response_code fromEnum(StrResponseCode responseCode) {
        return findByName(str_response_code.class, responseCode.name());
    }
}
