package net.therap.spring.controller;

import net.therap.spring.helper.HomeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static net.therap.spring.constant.URLConstants.HOME_URL;

/**
 * @author shoebakib
 * @since 2/27/24
 */
@Controller
@RequestMapping({"/"})
public class HomeController {

    public static final String HOME_VIEW = "home";

    @Autowired
    private HomeHelper homeHelper;

    @GetMapping(value = HOME_URL)
    public String showHomePage(ModelMap model, HttpSession session) {
        homeHelper.populateHomePageModel(model, session);

        return HOME_VIEW;
    }

}