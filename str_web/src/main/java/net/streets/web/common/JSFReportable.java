package net.streets.web.common;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;
import static java.util.Calendar.*;
import static net.streets.persistence.enumeration.StrConfig.CONFIG_DEFAULT_REPORTING_DAYS;
import static net.streets.persistence.helper.DaoManager.getStrConfigDao;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public abstract class JSFReportable extends JSFExportable implements Serializable {

    protected Date reportEndDate;
    protected Date reportStartDate;

    @PostConstruct
    public void init() {
        initializeReportTimes();
    }

    private void initializeReportTimes() {
        Calendar endTime = Calendar.getInstance();
        endTime.set(HOUR_OF_DAY, 23);
        endTime.set(MINUTE, 59);
        endTime.set(SECOND, 59);
        endTime.set(MILLISECOND, 999);
        reportEndDate = endTime.getTime();

        Calendar startTime = Calendar.getInstance();
        startTime.setTime(new Date(reportEndDate.getTime() - (86400000 * parseInt(getStrConfigDao().getConfig(CONFIG_DEFAULT_REPORTING_DAYS)))));
        startTime.set(HOUR_OF_DAY, 0);
        startTime.set(MINUTE, 0);
        startTime.set(SECOND, 0);
        startTime.set(MILLISECOND, 0);
        reportStartDate = startTime.getTime();
    }

    public Date getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }
}
