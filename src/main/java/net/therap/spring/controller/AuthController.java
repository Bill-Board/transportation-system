package net.therap.spring.controller;

import net.therap.spring.command.AuthCmd;
import net.therap.spring.helper.AuthHelper;
import net.therap.spring.validator.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static net.therap.spring.constant.CmdConstants.AUTH_CMD;
import static net.therap.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.therap.spring.constant.URLConstants.*;
import static net.therap.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 3/24/24
 */
@Controller
@SessionAttributes(AUTH_CMD)
public class AuthController {

    private static final String LOGIN_VIEW = "login";

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private AuthHelper authHelper;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        dataBinder.setAllowedFields("authenticationInfo.email", "authenticationInfo.password");

        dataBinder.setValidator(authenticationValidator);
    }

    @GetMapping(value = "/login")
    public String showLoginForm(ModelMap model) {
        authHelper.populateModel(model);

        return LOGIN_VIEW;
    }

    @PostMapping(value = "/login")
    public String login(@Valid @ModelAttribute(AUTH_CMD) AuthCmd authCmd,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        SessionStatus status,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            return LOGIN_VIEW;
        }

        authHelper.setUserInSession(authCmd, session);
        status.setComplete();

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("login.success.message"));

        return redirectTo(HOME_URL);
    }

    @PostMapping(value = "/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("logout.success.message"));

        return redirectTo(LOGIN_URL);
    }

}