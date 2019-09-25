package net.streets.web.common;

import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;

import static net.streets.persistence.enumeration.StrConfig.CONFIG_SYSTEM_NAME;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 07 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@ApplicationScoped
@Component
public class SystemSettings {

	private String systemName;

	public SystemSettings() {
		this.systemName = getStrConfigDao().getConfig(CONFIG_SYSTEM_NAME);
	}

	public String getSystemName() { return systemName; }

	public String getSystemNameToLower() { return getSystemName().toLowerCase(); }
}
