package net.streets.web.session;

import net.streets.persistence.entity.complex_type.str_user;
import net.streets.web.common.JSFReportable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.Calendar;
import java.util.logging.Logger;

import static java.util.Calendar.*;
import static net.streets.common.utilities.StrTransformer.dateToString;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Named
@Component
@Scope("session")
public class SummaryBean extends JSFReportable {

    private static final Logger logger = Logger.getLogger(SummaryBean.class.getSimpleName());
    private static final String TABLE_NAME = "System Status";
    private String todayStartTime = null;

    public SummaryBean(){}

    private String getTodayStartTime() {
        if (todayStartTime == null) {
            Calendar date = Calendar.getInstance();
            date.set(HOUR_OF_DAY, 0);
            date.set(MINUTE, 0);
            date.set(SECOND, 0);
            date.set(MILLISECOND, 0);
            logger.info("Using start time as " + date.getTime());
            todayStartTime = dateToString(date.getTime());
        }
        return todayStartTime;
    }

    public Long getTotalUsers() {
        return getEntityManagerRepo().countAll(str_user.class);
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}
