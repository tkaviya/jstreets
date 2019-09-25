package net.streets.web.session;

import net.streets.common.structure.Pair;
import net.streets.persistence.entity.complex_type.log.str_session;
import net.streets.web.common.JSFReportable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
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
public class SAuthReportBean extends JSFReportable {

    private static final String TABLE_NAME = "Authentications";
    private final SessionBean sessionBean;

    private List<str_session> sessions;

    @Autowired
    public SAuthReportBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void initializeSessions() {
        sessions = getEntityManagerRepo().findWhere(str_session.class, asList(
                new Pair<>("auth_user.user", sessionBean.getStrAuthUser().getUser().getId()),
                new Pair<>("start_time >", dateToString(reportStartDate)),
                new Pair<>("start_time <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<str_session> getSessions() {
        initializeSessions();
        return sessions;
    }

    @Override
    public String getTableDescription() {
        return TABLE_NAME;
    }
}

