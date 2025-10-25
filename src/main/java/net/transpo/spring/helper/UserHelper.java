package net.transpo.spring.helper;

import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.util.UtilEmployeeType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.transpo.spring.constant.Constants.*;

/**
 * @author shoebakib
 * @since 4/2/24
 */
@Component
public class UserHelper {

    public void setActiveUser(Employee employee, HttpSession session) {
        Set<EmployeeType> employeeTypes = employee.getEmployeeTypes();

        if (isNull(employeeTypes)) {
            return;
        }

        session.setAttribute(EMPLOYEE, employee);
        session.setAttribute(IS_LOGGED_IN, true);

        if (UtilEmployeeType.isEmployeeSuperAdmin(employeeTypes)) {
            session.setAttribute(IS_SUPER_ADMIN, true);
        }

        if (UtilEmployeeType.isEmployeeAdmin(employeeTypes)) {
            session.setAttribute(IS_ADMIN, true);
        }

        if (UtilEmployeeType.isEmployeeGeneralEmployee(employeeTypes)) {
            session.setAttribute(IS_EMPLOYEE, true);
        }

        if (UtilEmployeeType.isEmployeeSupportStaff(employeeTypes)) {
            session.setAttribute(IS_DRIVER, true);
        }
    }

    public Employee findActiveUser(HttpSession session) {
        if (!isUserActive(session)) {
            session.invalidate();

            throw new RuntimeException();
        }

        return (Employee) session.getAttribute(EMPLOYEE);
    }

    public boolean isUserActive(HttpSession session) {
        return nonNull(session.getAttribute(IS_LOGGED_IN)) && nonNull(session.getAttribute(EMPLOYEE));
    }

    public int getActiveUserId(HttpSession session) {
        Employee employee = findActiveUser(session);

        return nonNull(employee) ? employee.getId() : 0;
    }

}
