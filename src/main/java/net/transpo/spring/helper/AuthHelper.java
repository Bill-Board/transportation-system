package net.transpo.spring.helper;

import net.transpo.spring.command.AuthCmd;
import net.transpo.spring.entity.AuthenticationInfo;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.service.AuthenticationInfoService;
import net.transpo.spring.validator.AuthenticationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

import static java.util.Objects.isNull;
import static net.transpo.spring.constant.CmdConstants.AUTH_CMD;

/**
 * @author shoebakib
 * @since 5/2/24
 */
@Component
public class AuthHelper {

    private static final Logger log = LoggerFactory.getLogger(AuthHelper.class);

    @Autowired
    private AuthenticationInfoService authenticationInfoService;

    @Autowired
    private UserHelper userHelper;

    public void populateModel(ModelMap model) {
        AuthCmd authCmd = new AuthCmd();
        authCmd.setAuthenticationInfo(new AuthenticationInfo());

        model.addAttribute(AUTH_CMD, authCmd);
    }

    public void setUserInSession(AuthCmd authCmd, HttpSession session) {
        AuthenticationInfo authenticationInfo = authCmd.getAuthenticationInfo();
        Employee employee = findEmployee(authenticationInfo);

        userHelper.setActiveUser(employee, session);
    }

    private Employee findEmployee(AuthenticationInfo authenticationInfo) {
        setSalt(authenticationInfo);

        Employee employee = authenticationInfoService.findEmployee(authenticationInfo);

        if (isNull(employee)) {
            log.info("In Auth helper: no found Employee");

            throw new NoResultException();
        }

        return employee;
    }

    private void setSalt(AuthenticationInfo authenticationInfo) {
        AuthenticationInfo credentials = authenticationInfoService.findByEmail(authenticationInfo);

        String hasedPassword = HashCodeHelper.generateHash(authenticationInfo.getPassword() + credentials.getSalt());

        authenticationInfo.setHasedPassword(hasedPassword);
    }

}
