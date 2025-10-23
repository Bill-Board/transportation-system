package net.therap.spring.helper;

import net.therap.spring.command.EmployeeCmd;
import net.therap.spring.entity.*;
import net.therap.spring.service.AssignmentService;
import net.therap.spring.service.AuthenticationInfoService;
import net.therap.spring.service.CarService;
import net.therap.spring.service.EmployeeService;
import net.therap.spring.util.UtilEmployeeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.therap.spring.constant.CmdConstants.EMPLOYEE_CMD;
import static net.therap.spring.constant.Constants.*;
import static net.therap.spring.constant.URLConstants.*;

/**
 * @author shoebakib
 * @since 4/3/24
 */
@Component
public class EmployeeHelper {

    private static final Logger log = LoggerFactory.getLogger(EmployeeHelper.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CarService carService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AuthenticationInfoService authenticationInfoService;

    @Autowired
    private UserHelper userHelper;

    public void populateModelForRegister(ModelMap model) {
        EmployeeCmd employeeCmd = this.createEmployeeCmd(new Employee(), LIST_EMPLOYEE_URL);
        employeeCmd.setGenderOptions(GenderType.values());
        employeeCmd.setTypeOptions(UtilEmployeeType.getGeneralEmployeeTypes());

        model.addAttribute(EMPLOYEE_CMD, employeeCmd);
    }

    public void populateModelForList(ModelMap model, EmployeeType employeeType) {
        model.addAttribute("employees", employeeService.findEmployeeListByType(employeeType));

        if (employeeType.equals(EmployeeType.SUPPORT_STAFF)) {
            model.addAttribute("whosList", "Diver");
        } else {
            model.addAttribute("whosList", "Employee");
        }
    }

    public void populateModelForPendingList(ModelMap model) {
        model.addAttribute("pendingEmployees", employeeService.findPendingEmployeeList());
    }

    public void populateModelForEdit(ModelMap model, int id) {
        Employee employee = this.findByIdAndStatus(id);

        String backLink = "";

        if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
            backLink += LIST_DRIVER_URL;
        } else {
            backLink += LIST_EMPLOYEE_URL;
        }

        EmployeeCmd employeeCmd = this.createEmployeeCmd(employee, backLink);

        model.addAttribute(EMPLOYEE_CMD, employeeCmd);
    }

    public void populateModelForUpdate(ModelMap model, HttpSession session) {
        Employee employee = this.findByIdAndStatus(userHelper.getActiveUserId(session));

        EmployeeCmd employeeCmd = this.createEmployeeCmd(employee, HOME_URL);

        model.addAttribute(EMPLOYEE_CMD, employeeCmd);
    }

    public void saveEmployee(Employee employee) {
        log.debug("\n\nSave employee : \n" + employee + "/n" + employee.getAuthenticationInfo() + "\n\n");

        AuthenticationInfo authenticationInfo = employee.getAuthenticationInfo();
        authenticationInfo.setEmployee(employee);

        authenticationInfoService.saveOrUpdate(authenticationInfo);
    }

    public void updateEmployee(Employee employee) {
        employeeService.saveOrUpdate(employee);
    }

    public void approveEmployee(int id) {
        Employee employee = this.findPendingEmployee(id);

        employee.setStatus(STATUS_ACTIVE);
        employeeService.saveOrUpdate(employee);
    }

    public void declineEmployee(int id) {
        Employee employee = this.findPendingEmployee(id);

        employee.setStatus(STATUS_INACTIVE);
        employeeService.saveOrUpdate(employee);
    }

    public void removeEmployee(Employee employee) {
        if (assignmentService.isAssignInCar(employee.getId())) {
            Assignment assignment = this.findAssignmentByEmployeeId(employee.getId());

            if (nonNull(assignment.getCar().getManager()) && assignment.getCar().getManager().getId() == employee.getId()) {
                assignment.getCar().setManager(null);
            }

            int availableSeat = assignment.getCar().getAvailableSeat();
            assignment.getCar().setAvailableSeat(availableSeat + INCREMENT_ONE_SEAT);
            carService.saveOrUpdate(assignment.getCar());

            assignmentService.remove(assignment.getId());
        }

        employee.setStatus(STATUS_INACTIVE);
        employeeService.saveOrUpdate(employee);
    }

    public void removeSupportStaff(Employee driver) {
        driver.setStatus(STATUS_INACTIVE);
        employeeService.saveOrUpdate(driver);
    }

    public boolean isRemovable(Employee employee) {
        if (UtilEmployeeType.isEmployeeAdmin(employee.getEmployeeTypes())) {
            return false;
        }

        if (UtilEmployeeType.isEmployeeSupportStaff(employee.getEmployeeTypes())) {
            return isSupportStaffRemovable(employee);
        } else {
            return isEmployeeRemovable(employee);
        }
    }

    public Employee findByIdAndStatus(int id) {
        Employee employee = employeeService.findByIdAndStatus(id);

        if (isNull(employee)) {
            throw new NoResultException();
        }

        return employee;
    }

    private Employee findPendingEmployee(int id) {
        Employee employee = employeeService.findPendingEmployee(id);

        if (isNull(employee)) {
            throw new NoResultException();
        }

        return employee;
    }

    private boolean isSupportStaffRemovable(Employee employee) {
        if (employee.getDrivingStatus() == DRIVING_STATUS_INACTIVE) {
            return true;
        }

        List<Car> cars = carService.findAll();

        for (Car car : cars) {
            if (car.getDriver().getId() == employee.getId()) {
                return false;
            }
        }

        return true;
    }

    private boolean isEmployeeRemovable(Employee employee) {
        return !assignmentService.isAssignInCar(employee.getId());
    }

    private Assignment findAssignmentByEmployeeId(int id) {
        Assignment assignment = assignmentService.findByEmployeeId(id);

        if (isNull(assignment)) {
            throw new NoResultException();
        }

        return assignment;
    }

    private EmployeeCmd createEmployeeCmd(Employee employee, String backLink) {
        EmployeeCmd employeeCmd = new EmployeeCmd();
        employeeCmd.setEmployee(employee);
        employeeCmd.setBackLink(backLink);
        employeeCmd.setReadOnly(false);

        return employeeCmd;
    }

}
