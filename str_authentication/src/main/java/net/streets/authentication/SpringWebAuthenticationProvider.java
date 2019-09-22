//package net.streets.authentication;
//
///* *************************************************************************
// * Created:     2016/01/01                                                 *
// * Author:      Tich de Blak (Tsungai Kaviya)                              *
// * *************************************************************************
// */
//
//import net.streets.common.structure.Pair;
//import net.streets.persistence.entity.complex_type.log.str_request_response_log;
//import net.streets.persistence.entity.complex_type.str_auth_group_role;
//import net.streets.persistence.entity.enumeration.str_auth_group;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.logging.Logger;
//
//import static net.streets.common.enumeration.StrResponseCode.ACC_ACTIVE;
//import static net.streets.persistence.helper.DaoManager.getEntityManagerRepo;
//import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
//
//public class SpringWebAuthenticationProvider extends WebAuthenticationProvider {
//
//    protected static final Logger logger = Logger.getLogger(SpringWebAuthenticationProvider.class.getSimpleName());
//
//    public SpringWebAuthenticationProvider(str_request_response_log requestResponseLog, String username,
//                                           String password, String deviceId) {
//        super(requestResponseLog, username, password, deviceId);
//    }
//
//    public User getSpringUser() {
//
//        if (strAuthUser == null) {
//            return null;
//        }
//
//        return new User(
//                strAuthUser.getUser().getUsername(),
//                strAuthUser.getUser().getPassword(),
//                strAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account enabled
//                strAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non expired
//                strAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //credentials non expired
//                strAuthUser.getUser().getUser_status().equals(fromEnum(ACC_ACTIVE)), //account non locked
//                getAuthorities(strAuthUser.getAuth_group().getName())
//        );
//    }
//
//    public static Collection<? extends GrantedAuthority> getAuthorities(String userGroup) {
//        List<SimpleGrantedAuthority> authList = new ArrayList<>();
//
//        logger.info("Getting authorities for access group " + userGroup);
//
//        List<str_auth_group_role> userGroupRoles = getEntityManagerRepo().findWhere(
//                str_auth_group_role.class, new Pair<String, Object>("auth_group.name", userGroup));
//
//        for (str_auth_group_role authGroupRole : userGroupRoles) {
//            logger.info("Adding role " + authGroupRole.getName());
//            authList.add(new SimpleGrantedAuthority(authGroupRole.getName()));
//        }
//
//        return authList;
//    }
//
//    public static Collection<? extends GrantedAuthority> getAuthorities(str_auth_group auth_group) {
//        return getAuthorities(auth_group.getName());
//    }
//}
