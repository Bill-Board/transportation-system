package net.transpo.spring.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static net.transpo.spring.constant.Constants.EMPLOYEE;
import static net.transpo.spring.constant.Constants.IS_LOGGED_IN;
import static net.transpo.spring.constant.URLConstants.HOME_URL;
import static net.transpo.spring.constant.URLConstants.LOGIN_URL;

/**
 * @author shoebakib
 * @since 4/8/24
 */
public class AuthenticationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        boolean isLoggedInUser = (nonNull(session)
                && nonNull(session.getAttribute(IS_LOGGED_IN))
                && nonNull(session.getAttribute(EMPLOYEE)));

        log.debug("\n\nIn filter:\n\n" + httpRequest.getRequestURI() + "\n\n");

        if (httpRequest.getRequestURI().contains("assets")) {
            chain.doFilter(request, response);
        }

        if (isLoggedInUser) {

            if (httpRequest.getRequestURI().contains("login")) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + HOME_URL);
            } else {
                chain.doFilter(request, response);
            }
        } else {

            if (httpRequest.getRequestURI().contains("register") || httpRequest.getRequestURI().contains("login")) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN_URL);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
