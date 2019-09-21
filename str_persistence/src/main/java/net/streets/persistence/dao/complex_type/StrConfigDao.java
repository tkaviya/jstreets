package net.streets.persistence.dao.complex_type;

import net.streets.persistence.dao.super_class.StreetsEnumEntityDao;
import net.streets.persistence.entity.enumeration.str_config;

import java.util.HashMap;

/***************************************************************************
 *                                                                         *
 * Created:     26 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface StrConfigDao extends StreetsEnumEntityDao<str_config, Long> {
    HashMap<String, String> getAllConfigs();
    String getConfig(String configName);
}
