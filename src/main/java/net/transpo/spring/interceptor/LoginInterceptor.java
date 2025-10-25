package net.transpo.spring.interceptor;

import net.transpo.spring.helper.UserHelper;
import net.transpo.spring.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static net.transpo.spring.constant.Constants.EMPLOYEE;
import static net.transpo.spring.constant.Constants.IS_LOGGED_IN;
import static net.transpo.spring.constant.URLConstants.LOGOUT_URL;

/**
 * @author shoebakib
 * @since 4/8/24
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        log.info(request.getRequestURI() + " has started.");

        HttpSession session = request.getSession();
        boolean isLoggedInUser = (nonNull(session)
                && nonNull(session.getAttribute(IS_LOGGED_IN))
                && nonNull(session.getAttribute(EMPLOYEE)));

        if (isLoggedInUser) {
            if (!isEmployeeStillActive(session)) {
                response.sendRedirect(request.getContextPath() + LOGOUT_URL);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info(request.getRequestURI() + " has finished.");
    }

    private boolean isEmployeeStillActive(HttpSession session) {
        int employeeId = userHelper.getActiveUserId(session);

        return employeeService.isExistByIdAndStatus(employeeId);
    }

}
