package net.therap.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@Controller
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    private static final String ERROR_VIEW = "error";

    @GetMapping("/errors")
    public String handleError(HttpServletRequest request, ModelMap model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        log.info("\n\n" + status + "\n" + request.getContextPath() + "\n" + request.getRequestURI() + "\n\n");

        if (Objects.nonNull(status)) {
            model.addAttribute("errorCode", Integer.valueOf(status.toString()));
        }

        return ERROR_VIEW;
    }

}
