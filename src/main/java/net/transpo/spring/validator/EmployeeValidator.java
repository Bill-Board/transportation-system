package net.transpo.spring.validator;

import net.transpo.spring.command.EmployeeCmd;
import net.transpo.spring.entity.AuthenticationInfo;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.service.AuthenticationInfoService;
import net.transpo.spring.service.EmployeeService;
import net.transpo.spring.util.UtilDateTime;
import net.transpo.spring.util.UtilEmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author shoebakib
 * @since 4/4/24
 */
@Component
public class EmployeeValidator implements Validator {

    @Autowired
    private AuthenticationInfoService authenticationInfoService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeCmd employeeCmd = (EmployeeCmd) target;

        if (employeeCmd.getEmployee().isNew()) {
            this.isEmployeeNew(employeeCmd.getEmployee(), errors);
        } else {
            this.isEmployeeNotNew(employeeCmd.getEmployee(), errors);
        }
    }

    private void isEmployeeNew(Employee employee, Errors errors) {
        this.phoneNumberExistValidation(employee, errors);

        this.dateOfBirthValidation(employee, errors);

        this.passwordValidation(employee.getAuthenticationInfo(), errors);

        this.emailValidation(employee.getAuthenticationInfo(), errors);

        this.employeeTypesValidation(employee, errors);
    }

    private void isEmployeeNotNew(Employee employee, Errors errors) {

        Employee existingEmployee = employeeService.findByIdAndStatus(employee.getId());

        this.phoneNumberExistValidation(employee, existingEmployee, errors);

        this.dateOfBirthValidation(employee, errors);

        this.emailCanNotChangeValidation(employee.getAuthenticationInfo(), errors);

    }

    private void phoneNumberExistValidation(Employee employee, Errors errors) {
        if (nonNull(employee.getPhoneNumber())
                && employeeService.isPhoneNumberExists(employee.getPhoneNumber())) {

            errors.rejectValue("employee.phoneNumber", "PhoneNumberExist");
        }
    }

    private void dateOfBirthValidation(Employee employee, Errors errors) {
        if (nonNull(employee.getDateOfBirth())) {
            int age = UtilDateTime.getTimePeriod(employee.getDateOfBirth());

            if (age < 18 || 100 < age) {
                errors.rejectValue("employee.dateOfBirth", "Age.NotMatch");
            }
        }
    }

    private void passwordValidation(AuthenticationInfo authenticationInfo, Errors errors) {
        if (nonNull(authenticationInfo.getPassword())) {
            if (nonNull(authenticationInfo.getConfirmPassword())) {
                if (!authenticationInfo.getPassword().equals(authenticationInfo.getConfirmPassword())) {

                    errors.rejectValue("employee.authenticationInfo.confirmPassword", "Size.AuthenticationInfo.confirmPassword", "password didn't match");
                }
            } else {
                errors.rejectValue("employee.authenticationInfo.confirmPassword", "passwordMissMatch", "password didn't match");
            }
        } else {
            errors.rejectValue("employee.authenticationInfo.password", "NotNull");
        }
    }

    private void emailValidation(AuthenticationInfo authenticationInfo, Errors errors) {
        if (nonNull(authenticationInfo.getEmail())
                && authenticationInfoService.isEmailExist(authenticationInfo.getEmail())) {

            errors.rejectValue("employee.authenticationInfo.email", "EmailExist");
        }
    }

    private void employeeTypesValidation(Employee employee, Errors errors) {
        if (isNull(employee.getEmployeeTypes())) {
            errors.rejectValue("employee.employeeTypes", "NotNull");
        } else {

            Set<EmployeeType> employeeTypes = employee.getEmployeeTypes();

            if (UtilEmployeeType.isEmployeeSuperAdmin(employeeTypes)) {
                throw new RuntimeException();
            }

            if (employeeTypes.isEmpty()
                    || employeeTypes.size() >= 3) {

                errors.rejectValue("employee.employeeTypes", "NotNull");

            } else if (employeeTypes.size() == 2) {
                if (UtilEmployeeType.isEmployeeSupportStaff(employeeTypes)) {

                    errors.rejectValue("employee.employeeTypes", "TypeMismatch");
                }
            }
        }
    }

    private void phoneNumberExistValidation(Employee employee, Employee existingEmployee, Errors errors) {
        if (nonNull(employee.getPhoneNumber())
                && employeeService.isPhoneNumberExists(employee.getPhoneNumber())) {

            String phoneNumber = existingEmployee.getPhoneNumber();

            if (!phoneNumber.equals(employee.getPhoneNumber())) {
                errors.rejectValue("employee.phoneNumber", "PhoneNumberExist");
            }
        }
    }

    private void emailCanNotChangeValidation(AuthenticationInfo authenticationInfo, Errors errors) {
        if (nonNull(authenticationInfo.getEmail())) {
            AuthenticationInfo existingAuth = authenticationInfoService.findByIdAndEmailAndHash(authenticationInfo);

            if (isNull(existingAuth)) {
                errors.rejectValue("employee.authenticationInfo.email", "EmailCantNotBeChange");
            }
        }
    }

}
