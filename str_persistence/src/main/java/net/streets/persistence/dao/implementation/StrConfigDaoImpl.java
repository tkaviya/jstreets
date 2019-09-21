package net.streets.persistence.dao.implementation;

import net.streets.persistence.dao.complex_type.StrConfigDao;
import net.streets.persistence.entity.enumeration.str_config;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.streets.common.enumeration.StrConfig.CONFIG_CONFIG_UPDATE_DURATION;

/***************************************************************************
 *                                                                         *
 * Created:     26 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Repository
@Transactional
public class StrConfigDaoImpl extends StrEnumEntityDaoImpl<str_config, Long> implements StrConfigDao {

    private static final Logger logger = Logger.getLogger(StrConfigDaoImpl.class.getSimpleName());
    private static HashMap<String, String> configMap = null;
    private static Long lastUpdateTime = null;
    private static Long updateDuration = 3600000L; //default is to update configs every hour

    protected StrConfigDaoImpl() { super(str_config.class); }

    @Override
    public HashMap<String, String> getAllConfigs() {
        if (configMap == null || lastUpdateTime == null || (new Date().getTime() - lastUpdateTime) >= updateDuration) {

            configMap = new HashMap<>();

            //get all configs from database
            List<str_config> symConfigs = findAll();

            //add all configs to memory for future use
            for (str_config symConfig : symConfigs) {
                logger.info(format("Loading config '%s' with value '%s'",
                        symConfig.getName(),
                        symConfig.getConfig_value()));
                configMap.put(symConfig.getName(), symConfig.getConfig_value());
            }

            //if config update duration has changed, get the new value
            if (configMap.containsKey(CONFIG_CONFIG_UPDATE_DURATION)) {
                updateDuration = Long.parseLong(configMap.get(CONFIG_CONFIG_UPDATE_DURATION)) * 60000;
            }

            //update lastUpdateTime
            lastUpdateTime = new Date().getTime();
        }
        return configMap;

    }

    @Override
    public String getConfig(String configName) {
        return getAllConfigs().get(configName);
    }
}
