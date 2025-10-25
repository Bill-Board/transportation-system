package net.transpo.spring.controller;

import net.transpo.spring.command.NotificationCmd;
import net.transpo.spring.entity.EmployeeType;
import net.transpo.spring.helper.Authorization;
import net.transpo.spring.helper.NotificationHelper;
import net.transpo.spring.service.CarRequestService;
import net.transpo.spring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static net.transpo.spring.constant.CmdConstants.NOTIFICATION_CMD;
import static net.transpo.spring.constant.Constants.*;
import static net.transpo.spring.constant.URLConstants.LIST_NOTIFICATION_URL;
import static net.transpo.spring.util.Util.redirectTo;

/**
 * @author shoebakib
 * @since 4/2/24
 */
@Controller
@RequestMapping("/notification")
@SessionAttributes(NOTIFICATION_CMD)
public class NotificationController {

    public static final String NOTIFICATION_LIST_VIEW = "request/list-notification";

    public static final String REQUEST_DETAILS_VIEW = "request/request-details";

    @Autowired
    private NotificationHelper notificationHelper;

    @Autowired
    private CarRequestService carRequestService;

    @Autowired
    private Authorization authorization;

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

        String[] allowFields = {"request", "employee", "comment", "route", "car"};
        dataBinder.setAllowedFields(allowFields);
    }

    @GetMapping(value = "/list")
    public String getNotificationList(ModelMap model, HttpSession session) {
        authorization.checkAccess(session, EmployeeType.ADMIN);
        notificationHelper.populateModelForList(model);

        return NOTIFICATION_LIST_VIEW;
    }

    @GetMapping(value = "/details")
    public String getDetails(@RequestParam(value = "requestId", defaultValue = "0") int requestId,
                             ModelMap model,
                             HttpSession session) {

        authorization.checkAccess(session, EmployeeType.ADMIN);
        notificationHelper.populateModelForDetails(model, requestId);

        return REQUEST_DETAILS_VIEW;
    }

    @PostMapping(value = "/request", params = "action_approve")
    public String approveRequest(@ModelAttribute("notificationCmd") NotificationCmd notificationCmd,
                                 SessionStatus status,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        if (Util.isSeatAvailable(notificationCmd.getCar().getAvailableSeat())) {
            notificationHelper.assignInACar(notificationCmd);

            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.approve.message"));
        } else {
            notificationCmd.getRequest().setComment(messageSourceAccessor.getMessage("car.request.seat.not.available.comment"));
            carRequestService.saveOrUpdate(notificationCmd.getRequest());

            redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE,
                    messageSourceAccessor.getMessage("car.request.seat.not.available.message"));
        }

        status.setComplete();

        return redirectTo(LIST_NOTIFICATION_URL);
    }

    @PostMapping(value = "/request", params = "action_decline")
    public String declineRequest(@ModelAttribute("notificationCmd") NotificationCmd notificationCmd,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes,
                                 SessionStatus status) {

        authorization.checkAccess(session, EmployeeType.ADMIN);

        notificationCmd.getRequest().setStatus(DECLINE);
        carRequestService.saveOrUpdate(notificationCmd.getRequest());

        status.setComplete();

        redirectAttributes.addFlashAttribute(DECLINE_MESSAGE,
                messageSourceAccessor.getMessage("car.request.decline.message"));

        return redirectTo(LIST_NOTIFICATION_URL);
    }

    @PostMapping(value = "/request", params = "action_back")
    public String back(@ModelAttribute(value = "notificationCmd", binding = false) NotificationCmd notificationCmd,
                       HttpSession session,
                       SessionStatus status) {

        authorization.checkAccess(session, EmployeeType.ADMIN);
        status.setComplete();

        return redirectTo(notificationCmd.getBackLink());
    }

}
