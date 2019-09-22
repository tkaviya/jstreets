package net.streets.persistence.helper;

import net.streets.persistence.dao.EntityManagerRepo;
import net.streets.persistence.dao.complex_type.StrAuthUserDao;
import net.streets.persistence.dao.complex_type.StrConfigDao;
import net.streets.persistence.dao.complex_type.StrGroupRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 11/14/13
 * Time: 8:00 PM
 */
@Component
public class DaoManager {
    @Autowired
    private EntityManagerRepo entityManagerRepo;
    @Autowired
    private StrConfigDao strConfigDao;
    @Autowired
    private StrAuthUserDao authUserDao;
    @Autowired
    private StrGroupRoleDao groupRoleDao;

    private static DaoManager daoManager = null;

    private DaoManager() {
        daoManager = this;
    }

    public static DaoManager getInstance() {
        if (daoManager == null) {
            daoManager = new DaoManager();
        }
        return daoManager;
    }

    public static StrAuthUserDao getAuthUserDao() {
        return getInstance().authUserDao;
    }

    public static StrConfigDao getStrConfigDao() {
        return getInstance().strConfigDao;
    }

    private static StrGroupRoleDao getUserGroupRoleDao() {
        return getInstance().groupRoleDao;
    }

    public static EntityManagerRepo getEntityManagerRepo() {
        return getInstance().entityManagerRepo;
    }
}
