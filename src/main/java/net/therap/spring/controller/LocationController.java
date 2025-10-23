package net.therap.spring.controller;

import net.therap.spring.command.LocationCmd;
import net.therap.spring.entity.EmployeeType;
import net.therap.spring.helper.Authorization;
import net.therap.spring.helper.LocationHelper;
import net.therap.spring.validator.LocationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import static net.therap.spring.constant.CmdConstants.LOCATION_CMD;
import static net.therap.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.therap.spring.constant.URLConstants.LIST_LOCATION_URL;
import static net.therap.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Controller
@RequestMapping("/location")
@SessionAttributes(LOCATION_CMD)
public class LocationController {

    public static final String LOCATION_LIST_VIEW = "location/list-location";

    public static final String LOCATION_FORM_VIEW = "location/location-form";

    public static final String LOCATION_DETAILS_VIEW = "location/location-details";

    @Autowired
    private LocationValidator locationValidator;

    @Autowired
    private LocationHelper locationHelper;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        dataBinder.setAllowedFields("location.name");

        dataBinder.addValidators(locationValidator);
    }

    @GetMapping(value = "/list")
    public String getListOfLocations(ModelMap model, SessionStatus status) {
        locationHelper.populateModelForList(model);

        return LOCATION_LIST_VIEW;
    }

    @GetMapping(value = "/create")
    public String showLocationCreateForm(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);

        locationHelper.populateModelForCreate(model);

        return LOCATION_FORM_VIEW;
    }

    @PostMapping(value = "/location", params = "action_save")
    public String saveLocation(@Valid @ModelAttribute(LOCATION_CMD) LocationCmd locationCmd,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("attachment") MultipartFile attachment,
                               SessionStatus status,
                               HttpSession session) throws IOException {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        if (result.hasErrors()) {
            return LOCATION_FORM_VIEW;
        }

        locationHelper.saveOrUpdate(locationCmd, attachment);
        status.setComplete();

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("success.message"));

        return redirectTo(LIST_LOCATION_URL);
    }

    @GetMapping(value = "/edit")
    public String editLocation(@RequestParam(value = "locationId", defaultValue = "0") int locationId,
                               ModelMap model,
                               HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        locationHelper.populateModelForEdit(model, locationId);

        return LOCATION_FORM_VIEW;
    }

    @GetMapping(value = "/details")
    public String getDetails(@RequestParam(value = "locationId", defaultValue = "0") int locationId,
                             ModelMap model,
                             HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN, EmployeeType.SUPPORT_STAFF);

        locationHelper.populateModelForDetails(model, locationId);

        return LOCATION_DETAILS_VIEW;
    }

    @PostMapping(value = "/location", params = "action_back")
    public String back(@ModelAttribute(value = LOCATION_CMD, binding = false) LocationCmd locationCmd, SessionStatus status) {
        status.setComplete();

        return redirectTo(locationCmd.getBackLink());
    }

}
