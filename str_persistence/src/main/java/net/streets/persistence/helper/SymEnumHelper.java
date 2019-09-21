package net.streets.persistence.helper;

import net.streets.common.enumeration.StrResponseCode;
import net.streets.common.structure.Pair;
import net.streets.persistence.entity.enumeration.str_country;
import net.streets.persistence.entity.enumeration.str_language;
import net.streets.persistence.entity.enumeration.str_response_mapping;

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

public class SymEnumHelper {

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
}
