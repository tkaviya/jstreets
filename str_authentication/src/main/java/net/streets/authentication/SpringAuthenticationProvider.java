//package net.streets.authentication;
//
///* *************************************************************************
// * Created:     2016/01/01                                                 *
// * Author:      Tich de Blak (Tsungai Kaviya)                              *
// *                                      *
// */
//
//import net.streets.common.enumeration.StrResponseCode;
//import net.streets.common.response.StrResponseObject;
//import net.streets.persistence.entity.complex_type.log.str_request_response_log;
//import net.streets.persistence.entity.complex_type.str_auth_user;
//import net.streets.persistence.entity.complex_type.str_user;
//import net.streets.persistence.entity.enumeration.str_channel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.logging.Logger;
//
//import static net.streets.common.enumeration.StrChannel.WEB;
//import static net.streets.common.enumeration.StrEventType.USER_LOGIN;
//import static net.streets.common.enumeration.StrResponseCode.SUCCESS;
//import static net.streets.persistence.helper.StrEnumHelper.fromEnum;
//
//public class SpringAuthenticationProvider implements AuthenticationProvider {
//
//    Logger logger = Logger.getLogger(SpringAuthenticationProvider.class.getSimpleName());
//
//    private final str_channel channel = fromEnum(WEB);
//
//    @Autowired
//    private StrUserDetailsService userService;
//
//    SpringAuthenticationProvider() {
//        userService.setStrChannel(channel);
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        str_request_response_log request_response_log = new str_request_response_log(
//                channel, fromEnum(USER_LOGIN), authentication.getDetails().toString()).save();
//
//        logger.info("Authenticating user " + authentication.getPrincipal() + " on to channel WEB");
//
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        WebAuthenticationProvider webAuthenticationProvider = new WebAuthenticationProvider(
//                request_response_log, username, password, null
//        );
//
//        logger.info("Initialized WebAuthenticationProvider chain provider.");
//
//
//        logger.info("Running authentication chain.");
//        StrResponseObject<str_auth_user> strUserResponse = webAuthenticationProvider.authenticateUser();
//        request_response_log.setSystem_user(strUserResponse.getResponseObject().getUser());
//
//        logger.info("Authentication response: " + strUserResponse.getMessage());
//        if (strUserResponse.getResponseCode() == SUCCESS) {
//
//            str_user user = strUserResponse.getResponseObject().getUser();
//
//            Collection<? extends GrantedAuthority> authorities =
//                    SpringWebAuthenticationProvider.getAuthorities(strUserResponse.getResponseObject().getAuth_group());
//
//            logger.info("Returning authenticated used with granted authorities");
//
//            request_response_log.setOutgoing_response_time(new Date()).save();
//
//            return new UsernamePasswordAuthenticationToken(user, password, authorities);
//        } else {
//            StrResponseCode result = StrChainAuthenticationProvider.getMappedResponseCode(strUserResponse.getResponseCode());
//            logger.info("Returning mapped auth response: " + result);
//
//            request_response_log.setOutgoing_response_time(new Date()).save();
//            throw new BadCredentialsException(result.getMessage());
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(Authentication.class);
//    }
//}
