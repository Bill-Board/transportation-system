package net.transpo.spring.validator;

import net.transpo.spring.command.AuthCmd;
import net.transpo.spring.entity.AuthenticationInfo;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.helper.HashCodeHelper;
import net.transpo.spring.service.AuthenticationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.transpo.spring.constant.Constants.STATUS_INACTIVE;
import static net.transpo.spring.constant.Constants.STATUS_PENDING;

/**
 * @author shoebakib
 * @since 3/21/24
 */
@Component
public class AuthenticationValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationValidator.class);

    public static final String EMAIL = "authenticationInfo.email";

    public static final String INVALID_CREDENTIALS = "InvalidCredentials";

    public static final String PENDING_CREDENTIALS = "PendingCredentials";

    @Autowired
    private AuthenticationInfoService authenticationInfoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AuthCmd.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthCmd authCmd = (AuthCmd) target;
        AuthenticationInfo authenticationInfo = authCmd.getAuthenticationInfo();

        if (nonNull(authenticationInfo.getEmail()) && nonNull(authenticationInfo.getPassword())) {
            this.checkInvalidCredentials(authenticationInfo, errors);
        } else {
            errors.rejectValue(EMAIL, INVALID_CREDENTIALS);
        }
    }

    private void checkInvalidCredentials(AuthenticationInfo authenticationInfo, Errors errors) {
        AuthenticationInfo credentials = authenticationInfoService.findByEmail(authenticationInfo);

        if (isNull(credentials)) {
            errors.rejectValue(EMAIL, INVALID_CREDENTIALS);

            return;
        }

        if (!isEqualHasedPassword(authenticationInfo, credentials, errors)) {

            return;
        }

        checkEmployeeStatus(credentials.getEmployee(), errors);
    }

    private boolean isEqualHasedPassword(AuthenticationInfo authenticationInfo, AuthenticationInfo credentials, Errors errors) {
        String hasedPassword = HashCodeHelper.generateHash(authenticationInfo.getPassword() + credentials.getSalt());

        if (!hasedPassword.equals(credentials.getHasedPassword())) {
            errors.rejectValue(EMAIL, INVALID_CREDENTIALS);

            return false;
        }

        return true;
    }

    private void checkEmployeeStatus(Employee employee, Errors errors) {
        if (Objects.equals(employee.getStatus(), STATUS_INACTIVE)) {
            errors.rejectValue(EMAIL, INVALID_CREDENTIALS);
        } else if (Objects.equals(employee.getStatus(), STATUS_PENDING)) {
            errors.rejectValue(EMAIL, PENDING_CREDENTIALS);
        }
    }

}
