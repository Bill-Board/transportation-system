package net.transpo.spring.controller;

import net.transpo.spring.command.CarRequestCmd;
import net.transpo.spring.entity.Car;
import net.transpo.spring.entity.Employee;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.formatter.CarFormatter;
import net.transpo.spring.formatter.RouteFormatter;
import net.transpo.spring.helper.Authorization;
import net.transpo.spring.helper.CarRequestHelper;
import net.transpo.spring.helper.UserHelper;
import net.transpo.spring.service.CarRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static net.transpo.spring.constant.CmdConstants.REQUEST_CMD;
import static net.transpo.spring.constant.Constants.DECLINE_MESSAGE;
import static net.transpo.spring.constant.Constants.SUCCESS_MESSAGE;
import static net.transpo.spring.constant.URLConstants.LIST_REQUEST_URL;
import static net.transpo.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 4/1/24
 */
@Controller
@RequestMapping("/request")
@SessionAttributes(REQUEST_CMD)
public class CarRequestController {

    public static final String REQUEST_LIST_VIEW = "request/list-request";

    public static final String REQUEST_FORM_VIEW = "request/request-form";

    @Autowired
    private CarRequestHelper carRequestHelper;

    @Autowired
    private CarRequestService carRequestService;

    @Autowired
    private CarFormatter carFormatter;

    @Autowired
    private RouteFormatter routeFormatter;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        String[] allowFields = {"request.employee", "request.route", "request.car", "request.comment"};
        dataBinder.setAllowedFields(allowFields);

        dataBinder.addCustomFormatter(carFormatter);
        dataBinder.addCustomFormatter(routeFormatter);
    }

    @GetMapping(value = "/list")
    public String getRequestList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.EMPLOYEE);

        carRequestHelper.populateModelForList(model, session);

        return REQUEST_LIST_VIEW;
    }

    @GetMapping(value = "/create")
    public String showCarRequestCreateForm(ModelMap model,
                                           HttpSession session,
                                           RedirectAttributes redirectAttributes) {

        authorization.checkAccess(session, EmployeeType.EMPLOYEE);

        Employee employee = userHelper.findActiveUser(session);

        if (carRequestHelper.isRequestPending(employee)) {
            redirectAttributes.addFlashAttribute(DECLINE_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.decline.message.already.make.request"));

            return redirectTo(LIST_REQUEST_URL);
        }

        carRequestHelper.populateModelForCreateFirstForm(model);

        return REQUEST_FORM_VIEW;
    }

    @PostMapping(value = "/carRequest", params = "action_save")
    public String save(@Valid @ModelAttribute(REQUEST_CMD) CarRequestCmd carRequestCmd,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       HttpSession session,
                       SessionStatus status) {

        authorization.checkAccess(session, EmployeeType.EMPLOYEE);

        if (bindingResult.hasErrors()) {
            return REQUEST_FORM_VIEW;
        }

        Employee employee = userHelper.findActiveUser(session);

        if (carRequestHelper.isRequestPending(employee)) {
            redirectAttributes.addFlashAttribute(DECLINE_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.decline.message.already.make.request"));

        } else if (carRequestHelper.isAssignedInRequestedCar(employee, carRequestCmd)) {
            redirectAttributes.addFlashAttribute(DECLINE_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.decline.message.already.in.car"));

        } else {
            carRequestCmd.getRequest().setEmployee(employee);

            carRequestService.saveOrUpdate(carRequestCmd.getRequest());

            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.success.message"));
        }

        status.setComplete();

        return redirectTo(LIST_REQUEST_URL);
    }

    @GetMapping(value = "/getCarsByRoute")
    @ResponseBody
    public ResponseEntity<List<Car>> getCarsByRoute(@RequestParam(value = "routeId", defaultValue = "0") int routeId,
                                                    HttpSession session) {

        authorization.checkAccess(session, EmployeeType.EMPLOYEE);

        return new ResponseEntity<>(carRequestHelper.getAllCarListByRouteId(routeId), HttpStatus.OK);
    }

    @PostMapping(value = "/carRequest", params = "action_back")
    public String back(@ModelAttribute(value = REQUEST_CMD, binding = false) CarRequestCmd carRequestCmd,
                       HttpSession session) {

        authorization.checkAccess(session, EmployeeType.EMPLOYEE);

        String backLink = carRequestCmd.getBackLink();

        return redirectTo(backLink);
    }

}
