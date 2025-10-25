package net.transpo.spring.helper;

import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.exception.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

import static java.util.Objects.isNull;
import static net.transpo.spring.util.Util.isZero;

/**
 * @author shoebakib
 * @since 5/5/24
 */
@Component
public class Authorization {

    private static final Logger log = LoggerFactory.getLogger(Authorization.class);

    @Autowired
    private UserHelper userHelper;

    public void checkAccess(HttpSession session, EmployeeType... employeeTypes) {
        Employee employee = userHelper.findActiveUser(session);

        if (isNull(employee) || isNull(employeeTypes)) {
            throw new AccessDeniedException("Access denied");
        }

        if (isZero(employeeTypes.length)) {
            throw new IllegalArgumentException();
        }

        if (Arrays.stream(employeeTypes).anyMatch(employeeType -> employee.getEmployeeTypes().contains(employeeType))) {
            return;
        }

        log.debug("\n\n In valid. Access Denied\n\n");

        throw new AccessDeniedException("Access denied");
    }

}
