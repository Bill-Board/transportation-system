package net.transpo.spring.controller;

import net.transpo.spring.command.CarCmd;
import net.transpo.spring.entity.Car;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.formatter.EmployeeFormatter;
import net.transpo.spring.formatter.RouteFormatter;
import net.transpo.spring.helper.Authorization;
import net.transpo.spring.helper.CarHelper;
import net.transpo.spring.validator.CarValidator;
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

import static net.transpo.spring.constant.CmdConstants.CAR_CMD;
import static net.transpo.spring.constant.Constants.DECLINE_MESSAGE;
import static net.transpo.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.transpo.spring.constant.URLConstants.HOME_URL;
import static net.transpo.spring.constant.URLConstants.LIST_CAR_URL;
import static net.transpo.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 3/31/24
 */
@Controller
@RequestMapping("/car")
@SessionAttributes(CAR_CMD)
public class CarController {

    public static final String CAR_LIST_VIEW = "car/list-car";

    public static final String CAR_FORM_VIEW = "car/car-form";

    public static final String CAR_DETAILS_VIEW = "car/car-details";

    @Autowired
    private CarHelper carHelper;

    @Autowired
    private CarValidator carValidator;

    @Autowired
    private RouteFormatter routeFormatter;

    @Autowired
    private EmployeeFormatter employeeFormatter;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.addCustomFormatter(routeFormatter);
        dataBinder.addCustomFormatter(employeeFormatter);

        dataBinder.setDisallowedFields("car.id", "car.status");

        dataBinder.addValidators(carValidator);
    }

    @GetMapping(value = "/list")
    public String getCarList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        carHelper.populateModelForList(model);

        return CAR_LIST_VIEW;
    }

    @GetMapping(value = "/create")
    public String showCarCreateForm(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        carHelper.populateModel(model);

        return CAR_FORM_VIEW;
    }

    @PostMapping(value = "/car", params = "action_save")
    public String save(@Valid @ModelAttribute(CAR_CMD) CarCmd carCmd,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       SessionStatus status,
                       HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        if (bindingResult.hasErrors()) {
            return CAR_FORM_VIEW;
        }

        carHelper.saveOrUpdate(carCmd);
        status.setComplete();

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("success.message"));

        return redirectTo(LIST_CAR_URL);
    }

    @GetMapping(value = "/edit")
    public String showCarEditForm(@RequestParam(value = "carId", defaultValue = "0") int id,
                                  ModelMap model,
                                  HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);
        carHelper.populateModel(model, id);

        return CAR_FORM_VIEW;
    }

    @PostMapping(value = "/car", params = "action_remove")
    public String remove(@RequestParam(value = "carId", defaultValue = "0") int id,
                         RedirectAttributes redirectAttributes,
                         SessionStatus status, HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        Car car = carHelper.findCarByIdAndStatus(id);

        if (carHelper.isCarRemovable(car.getId())) {
            carHelper.removeCar(car);

            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, messageSourceAccessor.getMessage("car.remove.success.message"));
        } else {
            redirectAttributes.addFlashAttribute(DECLINE_MESSAGE, messageSourceAccessor.getMessage("car.remove.decline.message"));
        }

        status.setComplete();

        return redirectTo(LIST_CAR_URL);
    }

    @GetMapping(value = "/details")
    public String details(@RequestParam(value = "carId", defaultValue = "0") int carId,
                          @RequestParam(value = "backLink", defaultValue = HOME_URL) String backLink,
                          ModelMap model,
                          HttpSession session) {

        carHelper.checkCarDetailsAccessible(session, carId);
        carHelper.populateModelForDetails(model, carId, backLink);

        return CAR_DETAILS_VIEW;
    }

    @PostMapping(value = "/car", params = "action_back")
    public String back(@ModelAttribute(value = CAR_CMD, binding = false) CarCmd carCmd, SessionStatus status) {
        status.setComplete();

        return redirectTo(carCmd.getBackLink());
    }

}
