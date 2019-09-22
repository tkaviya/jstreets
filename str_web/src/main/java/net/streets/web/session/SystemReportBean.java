package net.streets.web.session;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.complex_type.log.str_request_response_log;
import net.streets.web.common.JSFReportable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static net.streets.common.utilities.StrTransformer.dateToString;
import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class SystemReportBean extends JSFReportable {

    private static final String TABLE_NAME = "System Logs";
    private List<str_request_response_log> systemLogs;

    public void initializeSystemLogs() {
        systemLogs = getEntityManagerRepo().findWhere(str_request_response_log.class, asList(
                new Pair<>("incoming_request_time >", dateToString(reportStartDate)),
                new Pair<>("incoming_request_time <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<str_request_response_log> getSystemLogs() {
        initializeSystemLogs();
        return systemLogs;
    }

    public String getTableDescription() {
        return TABLE_NAME;
    }
}

