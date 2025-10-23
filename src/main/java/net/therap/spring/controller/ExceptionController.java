package net.therap.spring.controller;

import net.therap.spring.exception.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;

/**
 * @author nadimmahmud
 * @since 1/30/23
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    private static final String ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        log.info("\n\n" + e.getMessage() + "\n\n");

        return ERROR_VIEW;
    }

    @ExceptionHandler(value = NoResultException.class)
    public String noResultException(Exception e, Model model) {
        log.info("\n\n" + e.getMessage() + "\n\n");

        model.addAttribute("noResultFound", true);

        return ERROR_VIEW;
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleIllegalAccess(AccessDeniedException e, Model model) {
        log.info("\n\n" + e.getMessage() + "\n\n");

        model.addAttribute("illegalAccess", "Illegal Exception");

        return ERROR_VIEW;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e) {
        log.info("\n\n" + e.getMessage() + "\n\n");

        return ERROR_VIEW;
    }

}
