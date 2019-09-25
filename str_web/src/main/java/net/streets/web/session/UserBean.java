package net.streets.web.session;

import net.streets.persistence.entity.complex_type.str_user;
import net.streets.web.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.streets.web.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class UserBean extends JSFUpdatable {

    private static final String TABLE_NAME = "System Users";
    private List<str_user> users;

    @Autowired
    public UserBean(SessionBean sessionBean) {
        super(sessionBean);
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_USERNAME);
        updatableColumns.add(HEADER_TEXT_FIRST_NAME);
        updatableColumns.add(HEADER_TEXT_LAST_NAME);
        updatableColumns.add(HEADER_TEXT_MSISDN);
        updatableColumns.add(HEADER_TEXT_EMAIL);
        updatableColumns.add(HEADER_TEXT_LANGUAGE);
        updatableColumns.add(HEADER_TEXT_COUNTRY);
        initializeUsers();
    }

    public void initializeUsers() {
        users = getEntityManagerRepo().findAll(str_user.class, true);
    }

    public List<str_user> getUsers() {
        return users;
    }

    public void setUsers(List<str_user> users) {
        this.users = users;
    }

    @Override
    public String getTableDescription() {
        return TABLE_NAME;
    }
}

